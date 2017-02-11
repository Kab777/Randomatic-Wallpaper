package com.junyuzhou.jywallpaper.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Window;
import android.widget.TextView;

import com.junyuzhou.jywallpaper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

/**
 * Created by Junyu on 2017-02-11.
 */

public class PermissionDialog extends Dialog {
    @BindView(R.id.goToSetting) TextView goToSetting;
    @BindView(R.id.cancelGuide) TextView cancelGuide;

    @OnClick(R.id.goToSetting)
    public void directToSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        getContext().startActivity(intent);
    }

    @OnClick(R.id.cancelGuide)
    public void cancelGuide() {
        dismiss();
    }

    public PermissionDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        initializeView();
    }

    private void initializeView() {
        setContentView(R.layout.dialog_permission_guide);
        ButterKnife.bind(this);
    }
}