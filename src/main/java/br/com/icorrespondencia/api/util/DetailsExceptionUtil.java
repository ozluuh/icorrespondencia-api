package br.com.icorrespondencia.api.util;

public class DetailsExceptionUtil {

    private DetailsExceptionUtil() {
    }

    public static String formatTitle(String title) {
        return title
                .replaceAll("([a-z])([A-Z])", "$1 $2")
                .replace("Exception", "")
                .trim();
    }
}
