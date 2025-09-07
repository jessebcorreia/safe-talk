package modelo.dao;

import modelo.entidade.denuncia.Denuncia;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.geral.enumeracoes.SituacaoAnaliseDenuncia;
import modelo.entidade.geral.enumeracoes.TipoDenuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;
import utils.ConverterDados;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DenunciaDAOImpl implements DenunciaDAO {

    @Override
    public void cadastrarDenuncia(Connection conexao, Denuncia denuncia)
            throws SQLException {
        String sqlLocalFato = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDenuncia = "INSERT INTO denuncia (titulo, conteudo, tipo, situacao_analise, local_fato_id, autor_id, pedagogo_id, analista_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Long localFatoId = null;
        Long denunciaId = null;

        try {
            conexao.setAutoCommit(false);

            // 1) Cadastrar endereço/local do fato
            if (denuncia.getLocalFato() != null) {
                try (PreparedStatement psLocalFato = conexao.prepareStatement(sqlLocalFato, Statement.RETURN_GENERATED_KEYS)) {
                    psLocalFato.setString(1, denuncia.getLocalFato().getLogradouro());
                    psLocalFato.setString(2, denuncia.getLocalFato().getNumero());
                    psLocalFato.setString(3, denuncia.getLocalFato().getComplemento());
                    psLocalFato.setString(4, denuncia.getLocalFato().getBairro());
                    psLocalFato.setString(5, denuncia.getLocalFato().getCidade());
                    psLocalFato.setString(6, denuncia.getLocalFato().getEstado());
                    psLocalFato.setString(7, denuncia.getLocalFato().getCep());
                    psLocalFato.setString(8, denuncia.getLocalFato().getPais());

                    psLocalFato.executeUpdate();

                    try (ResultSet idGerado = psLocalFato.getGeneratedKeys()) {
                        if (!idGerado.next())
                            throw new SQLException("Falha ao obter o ID do endereço.");

                        localFatoId = idGerado.getLong(1);
                    }
                }
            }

            // 2) Cadastrar denúncia
            try (PreparedStatement psDenuncia = conexao.prepareStatement(sqlDenuncia, Statement.RETURN_GENERATED_KEYS)) {
                psDenuncia.setString(1, denuncia.getTitulo());
                psDenuncia.setString(2, denuncia.getConteudo());
                psDenuncia.setString(3, denuncia.getTipoDenuncia().toString());
                psDenuncia.setString(4, denuncia.getSituacaoAnaliseDenuncia().toString());
                if (localFatoId == null) {
                    psDenuncia.setNull(5, Types.BIGINT);
                } else {
                    psDenuncia.setLong(5, localFatoId);
                }
                psDenuncia.setLong(6, denuncia.getAutor().getId());
                psDenuncia.setLong(7, denuncia.getPedagogo().getId());
                psDenuncia.setLong(8, denuncia.getAnalista().getId());

                psDenuncia.executeUpdate();

                try (ResultSet idGerado = psDenuncia.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID da denúncia.");

                    denunciaId = idGerado.getLong(1);
                }
            }

            // Cadastrar vítimas na tabela N:N
            List<Aluno> vitimas = denuncia.getVitimas();
            String sqlVitimas = "INSERT INTO denuncia_vitima (denuncia_id, vitima_id) VALUES (?, ?)";

            for (Aluno vitima : vitimas) {
                try (PreparedStatement psVitimas = conexao.prepareStatement(sqlVitimas)) {
                    psVitimas.setLong(1, denunciaId);
                    psVitimas.setLong(2, vitima.getId());

                    psVitimas.executeUpdate();
                }
            }

            // Cadastrar agressores na tabela N:N
            List<Aluno> agressores = denuncia.getAgressores();
            String sqlAgressores = "INSERT INTO denuncia_agressor (denuncia_id, agressor_id) VALUES (?, ?)";

            for (Aluno agressor : agressores) {
                try (PreparedStatement psAgressores = conexao.prepareStatement(sqlAgressores)) {
                    psAgressores.setLong(1, denunciaId);
                    psAgressores.setLong(2, agressor.getId());

                    psAgressores.executeUpdate();
                }
            }

            conexao.commit();

        } catch (SQLException e) {
            conexao.rollback();
            throw e;

        } finally {
            if (conexao != null)
                conexao.setAutoCommit(true);
        }
    }

    @Override
    public void atualizarDenuncia(Connection conexao, Denuncia denuncia)
            throws SQLException {
        String sqlLocalFato = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=?, cep=?, pais=? WHERE id = ?";
        String sqlDenuncia = "UPDATE denuncia SET titulo=?, conteudo=?, tipo=?, situacao_analise=?, encerrado_em=?, local_fato_id=?, autor_id=?, pedagogo_id=?, analista_id=? WHERE id=?";

        Long localFatoId = null;
        Long denunciaId = null;

        try {
            conexao.setAutoCommit(false);

            // 1) Atualizar endereco
            if (denuncia.getLocalFato() != null) {
                try (PreparedStatement psLocalFato = conexao.prepareStatement(sqlLocalFato)) {
                    psLocalFato.setString(1, denuncia.getLocalFato().getLogradouro());
                    psLocalFato.setString(2, denuncia.getLocalFato().getNumero());
                    psLocalFato.setString(3, denuncia.getLocalFato().getComplemento());
                    psLocalFato.setString(4, denuncia.getLocalFato().getBairro());
                    psLocalFato.setString(5, denuncia.getLocalFato().getCidade());
                    psLocalFato.setString(6, denuncia.getLocalFato().getEstado());
                    psLocalFato.setString(7, denuncia.getLocalFato().getCep());
                    psLocalFato.setString(8, denuncia.getLocalFato().getPais());

                    localFatoId = denuncia.getLocalFato().getId();
                    psLocalFato.setLong(9, localFatoId);

                    int linhasAfetadas = psLocalFato.executeUpdate();
                    if (linhasAfetadas == 0)
                        throw new SQLException("Não foi possível atualizar o endereço com id: " + denuncia.getLocalFato().getId());
                }
            }

            // 2) Atualizar denuncia
            try (PreparedStatement psDenuncia = conexao.prepareStatement(sqlDenuncia)) {

                psDenuncia.setString(1, denuncia.getTitulo());
                psDenuncia.setString(2, denuncia.getConteudo());
                psDenuncia.setString(3, denuncia.getTipoDenuncia().toString());
                psDenuncia.setString(4, denuncia.getSituacaoAnaliseDenuncia().toString());
                if (denuncia.getEncerradoEm() == null) {
                    psDenuncia.setNull(5, Types.TIMESTAMP);
                } else {
                    psDenuncia.setTimestamp(5, ConverterDados.localDateTimeParaTimestamp(denuncia.getEncerradoEm()));
                }
                if (localFatoId == null) {
                    psDenuncia.setNull(6, Types.BIGINT);
                } else {
                    psDenuncia.setLong(6, localFatoId);
                }
                psDenuncia.setLong(7, denuncia.getAutor().getId());
                psDenuncia.setLong(8, denuncia.getPedagogo().getId());
                psDenuncia.setLong(9, denuncia.getAnalista().getId());

                denunciaId = denuncia.getId();
                psDenuncia.setLong(10, denunciaId);

                int linhasAfetadas = psDenuncia.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar a denúncia com id: " + denunciaId);
            }

            conexao.commit();

        } catch (SQLException e) {
            conexao.rollback();
            throw e;

        } finally {
            if (conexao != null)
                conexao.setAutoCommit(true);
        }
    }

    @Override
    public void deletarDenunciaPeloId(Connection conexao, Long id)
            throws SQLException {
        String sqlDenuncia = "DELETE FROM denuncia WHERE id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlDenuncia)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Nenhuma denúncia encontrada com o ID fornecido: " + id);
        }
    }

    @Override
    public Denuncia recuperarDenunciaPeloId(Connection conexao, Long id)
            throws SQLException {
        String sql = "SELECT d.id, d.titulo, d.conteudo, d.tipo, d.situacao_analise, d.criado_em, d.atualizado_em, d.encerrado_em, d.autor_id, d.pedagogo_id, d.analista_id, "
                + "e.id AS local_fato_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais "
                + "FROM denuncia d "
                + "LEFT JOIN endereco e ON d.local_fato_id = e.id "
                + "WHERE d.id = ?";

        Denuncia denuncia = null;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long localFatoId = resultSet.getLong("local_fato_id");
                    String logradouro = resultSet.getString("logradouro");
                    String numero = resultSet.getString("numero");
                    String complemento = resultSet.getString("complemento");
                    String bairro = resultSet.getString("bairro");
                    String cidade = resultSet.getString("cidade");
                    String estado = resultSet.getString("estado");
                    String cep = resultSet.getString("cep");
                    String pais = resultSet.getString("pais");
                    Endereco localFato = new Endereco(localFatoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

                    Long denunciaId = resultSet.getLong("id");
                    String titulo = resultSet.getString("titulo");
                    String conteudo = resultSet.getString("conteudo");
                    TipoDenuncia tipoDenuncia = TipoDenuncia.valueOf(resultSet.getString("tipo"));
                    SituacaoAnaliseDenuncia situacaoAnaliseDenuncia = SituacaoAnaliseDenuncia.valueOf(resultSet.getString("situacao_analise"));
                    LocalDateTime criadoEm = ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em"));
                    LocalDateTime atualizadoEm = ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em"));
                    LocalDateTime encerradoEm = ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("encerrado_em"));

                    Long autorId = resultSet.getLong("autor_id");
                    Long pedagogoId = resultSet.getLong("pedagogo_id");
                    Long analistaId = resultSet.getLong("analista_id");

                    Aluno autor = recuperarAutorDenuncia(conexao, autorId);
                    Pedagogo pedagogo = recuperarPedagogoDenuncia(conexao, pedagogoId);
                    Analista analista = recuperarAnalistaDenuncia(conexao, analistaId);
                    List<Aluno> vitimas = recuperarVitimasPelaDenuncia(conexao, denunciaId);
                    List<Aluno> agressores = recuperarAgressoresPelaDenuncia(conexao, denunciaId);

                    denuncia = new Denuncia(denunciaId, titulo, conteudo, tipoDenuncia, situacaoAnaliseDenuncia, criadoEm, atualizadoEm, encerradoEm, localFato, autor, analista, pedagogo, vitimas, agressores, null);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar denuncia com ID: " + id, e);
            throw e;
        }

        return denuncia;
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAutor(Connection conexao, Long autorId)
            throws SQLException {
        String sql = "SELECT id FROM denuncia WHERE autor_id = ?";

        List<Denuncia> denuncias = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, autorId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Denuncia denuncia = recuperarDenunciaPeloId(conexao, resultSet.getLong("id"));
                    denuncias.add(denuncia);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo autor", e);
            throw e;
        }

        return denuncias;
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloPedagogo(Connection conexao, Long pedagogoId)
            throws SQLException {
        String sql = "SELECT id FROM denuncia WHERE pedagogo_id = ?";

        List<Denuncia> denuncias = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, pedagogoId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Denuncia denuncia = recuperarDenunciaPeloId(conexao, resultSet.getLong("id"));
                    denuncias.add(denuncia);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo pedagogo", e);
            throw e;
        }

        return denuncias;
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAnalista(Connection conexao, Long analistaId)
            throws SQLException {
        String sql = "SELECT id FROM denuncia WHERE analista_id = ?";

        List<Denuncia> denuncias = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, analistaId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Denuncia denuncia = recuperarDenunciaPeloId(conexao, resultSet.getLong("id"));
                    denuncias.add(denuncia);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo analista", e);
            throw e;
        }

        return denuncias;
    }

    @Override
    public List<Denuncia> recuperarDenunciasPelaVitima(Connection conexao, Long vitimaId)
            throws SQLException {
        String sql = "SELECT d.id FROM denuncia d INNER JOIN denuncia_vitima dv ON d.id = dv.denuncia_id WHERE dv.vitima_id = ?";

        List<Denuncia> denuncias = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, vitimaId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Denuncia denuncia = recuperarDenunciaPeloId(conexao, resultSet.getLong("id"));
                    denuncias.add(denuncia);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pela vítima", e);
            throw e;
        }

        return denuncias;
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAgressor(Connection conexao, Long agressorId)
            throws SQLException {
        String sql = "SELECT d.id FROM denuncia d INNER JOIN denuncia_agressor dv ON d.id = dv.denuncia_id WHERE dv.agressor_id = ?";

        List<Denuncia> denuncias = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, agressorId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Denuncia denuncia = recuperarDenunciaPeloId(conexao, resultSet.getLong("id"));
                    denuncias.add(denuncia);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo agressor", e);
            throw e;
        }

        return denuncias;
    }



    // TODO: Hoje está recuperando o autor apenas como aluno (precisa instanciar de acordo com o cargo - estudar sobre generics e analisar se dá pra aplicar aqui)
    @Override
    public Aluno recuperarAutorDenuncia(Connection conexao, Long denunciaId)
            throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "p.nome, p.sobrenome, p.cpf, p.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_aluno p ON u.id = p.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "INNER JOIN denuncia d ON d.autor_id = u.id "
                + "WHERE d.id = ?";

        Aluno aluno = null;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, denunciaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    aluno = new Aluno();
                    aluno.setId(resultSet.getLong("id"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    aluno.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    aluno.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    aluno.setEndereco(endereco);

                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setSobrenome(resultSet.getString("sobrenome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar aluno", e);
            throw e;
        }

        return aluno;
    }

    @Override
    public Pedagogo recuperarPedagogoDenuncia(Connection conexao, Long denunciaId)
            throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "p.nome, p.sobrenome, p.cpf, p.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_pedagogo p ON u.id = p.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "INNER JOIN denuncia d ON d.pedagogo_id = u.id "
                + "WHERE d.id = ?";

        Pedagogo pedagogo = null;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, denunciaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    pedagogo = new Pedagogo();
                    pedagogo.setId(resultSet.getLong("id"));
                    pedagogo.setEmail(resultSet.getString("email"));
                    pedagogo.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    pedagogo.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    pedagogo.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    pedagogo.setEndereco(endereco);

                    pedagogo.setNome(resultSet.getString("nome"));
                    pedagogo.setSobrenome(resultSet.getString("sobrenome"));
                    pedagogo.setCpf(resultSet.getString("cpf"));
                    pedagogo.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar pedagogo", e);
            throw e;
        }

        return pedagogo;
    }

    @Override
    public Analista recuperarAnalistaDenuncia(Connection conexao, Long denunciaId)
            throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "p.nome, p.sobrenome, p.cpf, p.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_analista p ON u.id = p.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "INNER JOIN denuncia d ON d.analista_id = u.id "
                + "WHERE d.id = ?";

        Analista analista = null;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, denunciaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    analista = new Analista();
                    analista.setId(resultSet.getLong("id"));
                    analista.setEmail(resultSet.getString("email"));
                    analista.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    analista.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    analista.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    analista.setEndereco(endereco);

                    analista.setNome(resultSet.getString("nome"));
                    analista.setSobrenome(resultSet.getString("sobrenome"));
                    analista.setCpf(resultSet.getString("cpf"));
                    analista.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar analista", e);
            throw e;
        }

        return analista;
    }

    @Override
    public List<Aluno> recuperarVitimasPelaDenuncia(Connection conexao, Long denunciaId)
            throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "ua.nome, ua.sobrenome, ua.cpf, ua.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_aluno ua ON u.id = ua.usuario_id "
                + "INNER JOIN endereco e ON e.id = u.endereco_id "
                + "INNER JOIN denuncia_vitima dv ON dv.vitima_id = u.id "
                + "WHERE dv.denuncia_id = ?";

        List<Aluno> vitimas = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, denunciaId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    Aluno aluno = new Aluno();
                    aluno.setId(resultSet.getLong("id"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    aluno.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    aluno.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    aluno.setEndereco(endereco);

                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setSobrenome(resultSet.getString("sobrenome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setSexo(Sexo.valueOf(resultSet.getString("sexo")));

                    vitimas.add(aluno);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de vítimas", e);
            throw e;
        }

        return vitimas;
    }

    @Override
    public List<Aluno> recuperarAgressoresPelaDenuncia(Connection conexao, Long denunciaId)
            throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "ua.nome, ua.sobrenome, ua.cpf, ua.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_aluno ua ON u.id = ua.usuario_id "
                + "INNER JOIN endereco e ON e.id = u.endereco_id "
                + "INNER JOIN denuncia_agressor da ON da.agressor_id = u.id "
                + "WHERE da.denuncia_id = ?";

        List<Aluno> agressores = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, denunciaId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    Aluno aluno = new Aluno();
                    aluno.setId(resultSet.getLong("id"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    aluno.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    aluno.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    aluno.setEndereco(endereco);

                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setSobrenome(resultSet.getString("sobrenome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setSexo(Sexo.valueOf(resultSet.getString("sexo")));

                    agressores.add(aluno);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de agressores", e);
            throw e;
        }

        return agressores;
    }
}
