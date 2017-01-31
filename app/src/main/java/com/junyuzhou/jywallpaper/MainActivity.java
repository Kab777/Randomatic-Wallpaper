package com.junyuzhou.jywallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.junyuzhou.jywallpaper.api.ImageService;
import com.junyuzhou.jywallpaper.model.RandomImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeperApplication.getNetComponent(this).inject(this);
        Button changeBtn = (Button) findViewById(R.id.changeWp);
        final ImageView showView = (ImageView) findViewById(R.id.img);
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                showView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        showView.setTag(target);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageService service = retrofit.create(ImageService.class);
                Observable<RandomImage> call = service.getImg(WeperConstants.API_APPLICATION_ID);
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
                                Picasso.with(MainActivity.this)
                                        .load(randomImage.getUrls().getRegular())
                                        .into(target);


                            }
                        });

            }
        });
    }
}
