package com.junyuzhou.jywallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.junyuzhou.jywallpaper.dialog.PhotoInfoDialog;
import com.junyuzhou.jywallpaper.dialog.ScheduleDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import timber.log.Timber;

import static android.R.attr.description;

/**
 * Created by Junyu on 2017-02-05.
 */

public class MainFragment extends Fragment {
    private ImageView mbgImg;
    private FloatingActionButton mInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mbgImg = (ImageView) rootView.findViewById(R.id.img_bg);
        mInfo = (FloatingActionButton) rootView.findViewById(R.id.show_info);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_save_image, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveImageToGallery();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoInfoDialog dialog = new PhotoInfoDialog(getContext());
                dialog.show();
            }
        });

        //registerForContextMenu(mbgImg);
    }

    @Override
    public void onResume() {
        super.onResume();
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        mbgImg.setImageDrawable(wallpaperDrawable);
    }

    private void saveImageToGallery() {


        Toast.makeText(getContext(), "Image saved to your Gallery", Toast.LENGTH_SHORT).show();
        mbgImg.setDrawingCacheEnabled(true);
        Bitmap b = mbgImg.getDrawingCache();
        //"Randomatic Wallpaper" + DateFormat.getDateTimeInstance().format(new Date())
        new ImageSaver(getContext()).
                setFileName("Junyu.png").
                setDirectoryName("Images").
                save(b);

    }


}
