package utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConverterDados {
    public static Long stringParaLong(String str) {
        if (str == null || str.isEmpty())
            return null;

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LocalDateTime stringParaLocalDateTime(String str) {
        if (str == null || str.isEmpty())
            return null;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            return LocalDateTime.parse(str, formato);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDateTime timestampParaLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
