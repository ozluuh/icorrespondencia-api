package br.com.icorrespondencia.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to aid in date conversion
 *
 * <p>
 * The following are examples of using to perform conversion:
 *
 * <pre>
 * {@code
 * // Convert datetime in string format
 * LocalDateTime now = LocalDateTime.now();
 * String result = DateUtil.formatDateTimeToSQL(now);
 *
 * // Parse string date format to LocalDateTime
 * String date = "2021-07-02 12:10:52";
 * LocalDateTime result = DateUtil.formatSQLDateStringToLocalDateTime(date);
 * }</pre>
 *
 * @author Luís Paulino
 */
public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Private constructor to prevent class to be instantiate
    private DateUtil() {
    }

    /**
     * Receive a date to be formatted
     *
     * @param date instance of date
     * @return String date formatted
     */
    public static String formatDateTimeToSQL(final LocalDateTime date) {
        if (date == null) return null;

        return formatter.format(date);
    }

    /**
     * Receive a date string do be parsed
     *
     * @param date instance of date string
     * @return LocalDateTime parsed date
     */
    public static LocalDateTime formatSQLDateStringToLocalDateTime(final String date) {
        if (date.isEmpty()) return null;

        return LocalDateTime.parse(date, formatter);
    }
}
