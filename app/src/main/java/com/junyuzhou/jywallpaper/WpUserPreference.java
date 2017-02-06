package com.junyuzhou.jywallpaper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Junyu on 2017-02-05.
 */

public class WpUserPreference {
    public static Boolean getScheduled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(WpConstant.SCHEDULED, false);
    }
}
