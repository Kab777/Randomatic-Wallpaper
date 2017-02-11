package com.junyuzhou.jywallpaper;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.junyuzhou.jywallpaper.dialog.PermissionDialog;
import com.junyuzhou.jywallpaper.dialog.PhotoInfoDialog;
import com.junyuzhou.jywallpaper.dialog.ScheduleDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import rx.functions.Action1;
import timber.log.Timber;

import static android.R.attr.description;

/**
 * Created by Junyu on 2017-02-05.
 */

public class MainFragment extends Fragment {
    private ImageView mbgImg;
    private FloatingActionButton mInfo;
    private PermissionDialog permissionDialog;
    private PhotoInfoDialog photoInfoDialog;

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
                RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted) {
                                    saveImageToGallery();
                                } else {
                                    permissionDialog.show();
                                }
                            }
                        });

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
                photoInfoDialog.show();
            }
        });
        permissionDialog = new PermissionDialog(getContext());
        photoInfoDialog = new PhotoInfoDialog(getContext());
        registerForContextMenu(mbgImg);
    }

    @Override
    public void onResume() {
        super.onResume();
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        mbgImg.setImageDrawable(wallpaperDrawable);
    }

    private void saveImageToGallery() {
        Bitmap bitmap = ((BitmapDrawable) mbgImg.getDrawable()).getBitmap();
        saveImageFile(bitmap);
        //MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "oasidjiojJy" , "Nothing");

    }

    private String saveImageFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Randomatic Wallpaper");
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpeg");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // tell android to show img in Gallery
            File imageFile = new File(filename);
            MediaScannerConnection.scanFile(getContext(), new String[]{imageFile.getPath()}, new String[]{"image/jpeg"}, null);
            Toast.makeText(getContext(), "Image saved to your Gallery!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }


}


