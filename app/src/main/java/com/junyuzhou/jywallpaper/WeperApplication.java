package com.junyuzhou.jywallpaper;

import android.app.Application;
import android.content.Context;

import com.junyuzhou.jywallpaper.module.DaggerNetComponent;
import com.junyuzhou.jywallpaper.module.NetComponent;
import com.junyuzhou.jywallpaper.module.NetModule;

import timber.log.Timber;

/**
 * Created by Junyu on 2017-01-29.
 */

public class WeperApplication extends Application {
    private NetComponent netComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());


        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule("http://api.unsplash.com/photos/"))
                .build();
    }

    private static WeperApplication getApp(Context context) {
        return (WeperApplication) context.getApplicationContext();
    }

    public static NetComponent getNetComponent(Context context) {
        return getApp(context).netComponent;
    }
}
