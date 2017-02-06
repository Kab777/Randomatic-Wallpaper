package com.junyuzhou.jywallpaper.module;

import com.junyuzhou.jywallpaper.MainActivity;
import com.junyuzhou.jywallpaper.MyTestService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Junyu on 2017-01-29.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
    void inject(MyTestService myTestService);
}

