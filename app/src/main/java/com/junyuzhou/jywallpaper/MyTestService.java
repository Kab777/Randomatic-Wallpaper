package com.junyuzhou.jywallpaper;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.junyuzhou.jywallpaper.api.ImageService;
import com.junyuzhou.jywallpaper.model.RandomImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.junyuzhou.jywallpaper.WpUserPreference.getScreenSize;

/**
 * Created by Junyu on 2017-02-02.
 */

public class MyTestService extends IntentService {
    private WallpaperManager wallpaperManager;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    @Inject
    Retrofit retrofit;

    public MyTestService() {
        super("IntentService");

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        WeperApplication.getNetComponent(this).inject(this);
        Timber.v("onCreatea");
        wallpaperManager = WallpaperManager
                .getInstance(this);


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.v("is it Here?? Junyu");

        ImageService service = retrofit.create(ImageService.class);

        Point point = WpUserPreference.getScreenSize(this);
        Observable<RandomImage> call = service.getImg(WeperConstants.API_APPLICATION_ID,
                Integer.toString(point.y), Integer.toString(point.x), "portrait");
        call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RandomImage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RandomImage randomImage) {
                        Timber.v(randomImage.getUrls().getRegular());
                        WpUserPreference.storeCurImageInfo(MyTestService.this, randomImage.getUser().getName(), randomImage.getUser().getPortfolioUrl());
                        Glide.with(MyTestService.this)
                                .load(randomImage.getUrls().getRegular())
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                        try {
                                            wallpaperManager.setBitmap(resource);

                                        } catch (IOException e) {
                                            e.printStackTrace();

                                        }
                                    }
                                });


                    }
                });
    }
}
