package br.com.icorrespondencia.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public static String formatDateTimeToSQL(LocalDateTime date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
