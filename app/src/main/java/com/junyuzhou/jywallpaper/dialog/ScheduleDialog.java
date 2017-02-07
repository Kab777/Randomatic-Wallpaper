package com.junyuzhou.jywallpaper.dialog;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.junyuzhou.jywallpaper.MyAlarmReceiver;
import com.junyuzhou.jywallpaper.R;
import com.junyuzhou.jywallpaper.WpUserPreference;

import timber.log.Timber;

/**
 * Created by Junyu on 2017-02-05.
 */

public class ScheduleDialog {
    private Context context;
    private AlertDialog alertDialog;
    private Typeface fontSize;
    private NumberPicker numberPicker;

    public ScheduleDialog(Context context) {
        this.context = context;
        fontSize = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.GothamMedium));
        initDialog();
    }

    private void initDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_schedule, null);
        numberPicker = (NumberPicker) mView.findViewById(R.id.num_picker);
        numberPicker.setMinValue(2);
        //Specify the maximum value/number of NumberPicker
        numberPicker.setMaxValue(24);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        alertDialogBuilderUserInput.setView(mView);


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Schedule", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        //cancel first
                        //cancelAlarm();
                        Timber.v(Integer.toString(numberPicker.getValue()));
                        if (WpUserPreference.getScheduled(context)) {
                            scheduleAlarm(numberPicker.getValue());
                        } else {
                            cancelAlarm();
                            scheduleAlarm(numberPicker.getValue());
                        }

                    }
                })
                .setNeutralButton("Disable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelAlarm();
                        Toast.makeText(context, "You successfully disabled ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        alertDialog = alertDialogBuilderUserInput.create();
    }

    public void show() {
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button disableBtn = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        nbutton.setTypeface(fontSize);
        pbutton.setTypeface(fontSize);
        disableBtn.setTypeface(fontSize);
        disableBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        nbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        pbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    }

    public void scheduleAlarm(int hours) {
        Timber.v("scheduled");

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(context.getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        //hours * AlarmManager.INTERVAL_HOUR
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, hours * AlarmManager.INTERVAL_HOUR, pIntent);
    }

    private void cancelAlarm() {
        Intent intent = new Intent(context.getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
