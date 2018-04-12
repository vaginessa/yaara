package com.mlieou.yaara.util;

/**
 * Created by mlieou on 1/13/18.
 */

public class ParserUtil {

    private static String[] QUANTIFIERS = {"B", "KB", "MB", "GB"};

    public static String parseSpeed(long speed) {
        return parseSize(speed) + "/s";
    }

    private static String formatNumber(long speed, long div) {
        double res = 1.0 * speed / div;
        return String.format("%.2f ", res);
    }

    public static String parseSize(long size) {
        if (size < (1 << 10))
            return size + " " + QUANTIFIERS[0];
        else if (size < (1 << 20))
            return formatNumber(size, 1 << 10) + QUANTIFIERS[1];
        else if (size < (1 << 30))
            return formatNumber(size, 1 << 20) + QUANTIFIERS[2];
        else
            return formatNumber(size, 1 << 30) + QUANTIFIERS[3];
    }
}
