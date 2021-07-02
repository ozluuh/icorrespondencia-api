package br.com.icorrespondencia.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
    }

    public static String formatDateTimeToSQL(final LocalDateTime date) {
        if (date == null) return null;

        return formatter.format(date);
    }

    public static LocalDateTime formatSQLDateStringToLocalDateTime(final String date) {
        if(date.isEmpty()) return null;

        return LocalDateTime.parse(date, formatter);
    }
}
