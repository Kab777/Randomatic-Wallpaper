package com.junyuzhou.jywallpaper;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import timber.log.Timber;

/**
 * Created by Junyu on 2017-02-05.
 */

public class MainFragment extends Fragment {
    private ImageView mbgImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mbgImg = (ImageView)rootView.findViewById(R.id.img_bg);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        mbgImg.setImageDrawable(wallpaperDrawable);
    }
}
