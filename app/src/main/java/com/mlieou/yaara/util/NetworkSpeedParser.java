package com.mlieou.yaara.util;

/**
 * Created by mlieou on 1/13/18.
 */

public class NetworkSpeedParser {

    private static String[] UNITS = {"B/s", "KB/s", "MB/s", "GB/s"};

    public static String parse(long speed) {
        if (speed < (1 << 10))
            return speed + " " + UNITS[0];
        else if (speed < (1 << 20))
            return formatNumber(speed, 1 << 10) + UNITS[1];
        else if (speed < (1 << 30))
            return formatNumber(speed, 1 << 20) + UNITS[2];
        else
            return formatNumber(speed, 1 << 30) + UNITS[3];
    }

    private static String formatNumber(long speed, long div) {
        double res = 1.0 * speed / div;
        return String.format("%.2f ", res);
    }
}
