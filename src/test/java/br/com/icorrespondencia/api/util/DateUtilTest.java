package br.com.icorrespondencia.api.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Util: DateUtil tests")
public class DateUtilTest {
    @Test
    @DisplayName("formatDateTimeToSQL should returns date formatted over a pattern when successful")
    void formatDateTimeToSQL_ShouldReturnsDateFormattedOverAPattern_WhenSuccessful() {
        String dateResult = DateUtil.formatDateTimeToSQL(LocalDateTime.now());

        assertThat(dateResult)
            .isInstanceOf(String.class)
            .matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");
    }

    @Test
    @DisplayName("formatDateTimeToSQL should returns null when date is not passed")
    void formatDateTimeToSQL_ShouldReturnsNull_WhenDateIsNotPassed() {
        String dateResult = DateUtil.formatDateTimeToSQL(null);

        assertThat(dateResult)
            .isNull();
    }

    @Test
    @DisplayName("formatSQLDateStringToLocalDateTime should returns LocalDateTime of date string when successful")
    void formatSQLDateStringToLocalDateTime_ShouldReturnsLocalDateTimeOfDateString_WhenSuccessful() {
        var date = "2021-07-02 12:10:52";

        LocalDateTime dateResult = DateUtil.formatSQLDateStringToLocalDateTime(date);

        assertThat(dateResult)
            .isInstanceOf(LocalDateTime.class)
            .isEqualTo(date.replace(" ", "T"));

    }

    @Test
    @DisplayName("formatSQLDateStringToLocalDateTime should returns null when date string is not passed")
    void formatSQLDateStringToLocalDateTime_ShouldReturnsNull_WhenDateStringIsNotPassed() {
        var date = "";

        LocalDateTime dateResult = DateUtil.formatSQLDateStringToLocalDateTime(date);

        assertThat(dateResult)
            .isNull();

    }
}
