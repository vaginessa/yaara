package com.mlieou.yaara.service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by mengdi on 1/13/18.
 */

public class YaaraServiceManager {

    public static void startGlobalStatusUpdate(Context context) {
        Intent intent = new Intent(context, YaaraService.class);
        intent.setAction(YaaraService.START_UPDATE_GLOBAL_STATUS);
        context.startService(intent);
    }

    public static void stopGlobalStatusUpdate(Context context) {
        Intent intent = new Intent(context, YaaraService.class);
        intent.setAction(YaaraService.STOP_UPDATE_GLOBAL_STATUS);
        context.startService(intent);
    }
}
