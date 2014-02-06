package br.com.telzir.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class StringUtils {
    public static final DecimalFormat FORMATADOR_DECIMAL = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(
            new Locale("pt", "BR")));

    private StringUtils() {
    }

    public static boolean isEmpty(String valor) {
        return valor == null || valor.trim().length() == 0;
    }

    public static boolean isNotEmpty(String valor) {
        return !isEmpty(valor);
    }

    public static String formatarValorMonetario(Double valor) {
        return valor == null ? null : FORMATADOR_DECIMAL.format(valor);
    }

    public static String clear(String valor) {
        return isEmpty(valor) ? "" : valor.trim();
    }
}
