package com.mlieou.yaara.util;

/**
 * Created by mlieou on 1/13/18.
 */

public class ParserUtil {

    private static String[] UNITS = {"B", "KB", "MB", "GB"};
    private static String PER_SECOND = "/s";

    public static String parseSpeed(long speed) {
        return parseSize(speed) + PER_SECOND;
    }

    private static String formatNumber(long speed, long div) {
        double res = 1.0 * speed / div;
        return String.format("%.2f ", res);
    }

    public static String parseSize(long size) {
        if (size < (1 << 10))
            return size + " " + UNITS[0];
        else if (size < (1 << 20))
            return formatNumber(size, 1 << 10) + UNITS[1];
        else if (size < (1 << 30))
            return formatNumber(size, 1 << 20) + UNITS[2];
        else
            return formatNumber(size, 1 << 30) + UNITS[3];
    }
}
