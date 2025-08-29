document.addEventListener("DOMContentLoaded", () => {
    const botaoVoltar = document.querySelector(".voltar");
    const botaoProximo = document.querySelector(".proximo");
    const botaoCadastrar = document.querySelector(".cadastrar");
    const paineis = document.querySelectorAll(".painel");
    const circulos = document.querySelectorAll(".circulo");

    let passoAtual = 0;

    // Inicializa a interface
    atualizarPasso();

    // Avançar para o próximo passo
    botaoProximo.addEventListener("click", botaoProximoHandler);

    function botaoProximoHandler() {
        if (passoAtual < paineis.length - 1) {
            passoAtual++;
            atualizarPasso();
        }
    }

    // Voltar para o passo anterior
    botaoVoltar.addEventListener("click", botaoVoltarHandler);

    function botaoVoltarHandler() {
        if (passoAtual > 0) {
            passoAtual--;
            atualizarPasso();
        }
    }

    function atualizarPasso() {
        // Itera sobre os painéis
        paineis.forEach((painel, i) => {
            painel.classList.toggle("ativo", i === passoAtual);
        });

        // Itera sobre os círculos
        circulos.forEach((circulo, i) => {
            circulo.classList.remove("ativo", "completo");

            if (i < passoAtual) {
                circulo.classList.add("completo");
            } else if (i === passoAtual) {
                circulo.classList.add("ativo");
            }
        });

        // Define a lógica de aparição dos botões
        const primeiroPasso = passoAtual == 0;
        const passosIntermediarios = passoAtual < paineis.length - 1;

        if (primeiroPasso) {
            botaoVoltar.style.display = "none";
            botaoProximo.style.display = "block";
            botaoCadastrar.style.display = "none";
            return;
        }

        if (passosIntermediarios) {
            botaoVoltar.style.display = "block";
            botaoProximo.style.display = "block";
            botaoCadastrar.style.display = "none";
            return;
        }

        botaoVoltar.style.display = "block";
        botaoProximo.style.display = "none";
        botaoCadastrar.style.display = "block";
    }
});
