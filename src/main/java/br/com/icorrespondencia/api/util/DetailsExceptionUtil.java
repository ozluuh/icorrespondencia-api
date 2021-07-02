package br.com.icorrespondencia.api.util;

/**
 * Utility class to aid in format title {@literal Exception}
 *
 * <p>
 * The following are examples of using to perform format:
 *
 * <pre>
 * {@code
 * // Converts exception simple name
 * String result = DetailsExceptionUtil.formatTitle(DateTimeException.getClass().getSimpleName());
 * }</pre>
 *
 * @author Luís Paulino
 */
public class DetailsExceptionUtil {

    // Private constructor to prevent class to be instantiate
    private DetailsExceptionUtil() {
    }

    /**
     * Format given title
     *
     * @param title to be formatted
     * @return title formatted
     */
    public static String formatTitle(String title) {
        return title
                .replaceAll("([a-z])([A-Z])", "$1 $2")
                .replace("Exception", "")
                .trim();
    }
}
