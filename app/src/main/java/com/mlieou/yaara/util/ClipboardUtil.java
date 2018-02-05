package com.mlieou.yaara.util;

/**
 * Created by mlieou on 2/5/18.
 */

public class ClipboardUtil {

    public static final String[] SUPPORTED_SCHEME = {"http://", "https://", "ftp://", "sftp://", "magnet:?"};

    public static boolean isValidDownloadLink(CharSequence charSequence) {
        if (charSequence == null)   return false;
        return isValidDownloadLink(charSequence.toString());
    }

    public static boolean isValidDownloadLink(String string) {
        if (string.length() == 0)   return false;
        for (String scheme : SUPPORTED_SCHEME) {
            if (string.length() > scheme.length() && string.startsWith(scheme))
                return true;
        }
        return false;
    }
}
