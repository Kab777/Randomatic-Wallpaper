package com.junyuzhou.jywallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import static android.R.attr.x;
import static com.junyuzhou.jywallpaper.WpConstant.SCREEN_HEIGHT;
import static com.junyuzhou.jywallpaper.WpConstant.SCREEN_WIDTH;

/**
 * Created by Junyu on 2017-02-05.
 */

public class WpUserPreference {
    public static Boolean getScheduled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(WpConstant.SCHEDULED, false);
    }

    public static void storeScreenSize(Context context, int width, int height) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCREEN_WIDTH, width);
        editor.putInt(SCREEN_HEIGHT, height);
        editor.commit();
    }

    public static Point getScreenSize(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        Point point = new Point(sharedPreferences.getInt(SCREEN_WIDTH, 1080), sharedPreferences.getInt(SCREEN_HEIGHT, 1920));
        return point;
    }
}
