package com.junyuzhou.jywallpaper.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.junyuzhou.jywallpaper.R;
import com.junyuzhou.jywallpaper.WpUserPreference;

import timber.log.Timber;

/**
 * Created by Junyu on 2017-02-06.
 */

public class PhotoInfoDialog {
    private Context context;
    private AlertDialog alertDialog;
    private Typeface fontSize;
    private TextView authorInfo;

    public PhotoInfoDialog(Context context) {
        this.context = context;
        fontSize = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.GothamMedium));
        initDialog();
    }

    private void initDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_image_info, null);
        authorInfo = (TextView) mView.findViewById(R.id.author_info);
        authorInfo.setMovementMethod(LinkMovementMethod.getInstance());
        authorInfo.setText(Html.fromHtml(String.format(context.getResources().getString(R.string.photoInfo)
                , WpUserPreference.getAuthorUrl(context), WpUserPreference.getAuthorName(context))));
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.dismiss();
                    }
                });

        alertDialog = alertDialogBuilderUserInput.create();
    }

    public void show() {
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTypeface(fontSize);
        pbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    }
}
