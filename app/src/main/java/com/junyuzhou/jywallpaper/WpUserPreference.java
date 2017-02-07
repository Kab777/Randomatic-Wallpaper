package com.junyuzhou.jywallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import static android.R.attr.width;
import static android.R.attr.x;
import static com.junyuzhou.jywallpaper.WpConstant.AUTHOR_URL;
import static com.junyuzhou.jywallpaper.WpConstant.PHOTO_AUTHOR;
import static com.junyuzhou.jywallpaper.WpConstant.PHOTO_NAME;
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
        editor.apply();
    }

    public static Point getScreenSize(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        Point point = new Point(sharedPreferences.getInt(SCREEN_WIDTH, 1080), sharedPreferences.getInt(SCREEN_HEIGHT, 1920));
        return point;
    }

    public static void storeCurImageInfo(Context context, String author, String profileUrl) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHOTO_AUTHOR, author);
        editor.putString(AUTHOR_URL, profileUrl);
        editor.apply();
    }

    public static String getAuthorName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO_AUTHOR, "Anonymous");
    }

    public static String getAuthorUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTHOR_URL, "https://unsplash.com/");
    }

    public static String getImageName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WpConstant.PREFERENCE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO_NAME, "Randomatic Wallpaper");
    }
}
