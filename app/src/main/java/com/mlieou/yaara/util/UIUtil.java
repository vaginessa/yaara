/*
 *         YAARA - Aria2 Remote for Android
 *
 * Copyright 2017-2018 Mlieou <ifddd@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mlieou.yaara.util;

import com.mlieou.yaara.model.GlobalStatus;

import java.time.LocalTime;
import java.util.Locale;

public class UIUtil {
    public static String buildSubtitle(GlobalStatus status) {
        return "Download: " + NetworkSpeedParser.parse(status.getDownloadSpeed()) + " Upload: " + NetworkSpeedParser.parse(status.getUploadSpeed());
    }

    public static String secondsToTime(long totalSeconds) {
        if (totalSeconds >= 86400) {
            long days = totalSeconds / 86400;
            if (days == 1)  return "1 day";
            return "" + days + " days";
        }
        int hours = (int) totalSeconds / 3600;
        int minutes = (int) (totalSeconds % 3600) / 60;
        int sec = (int) totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, sec);
    }
}
