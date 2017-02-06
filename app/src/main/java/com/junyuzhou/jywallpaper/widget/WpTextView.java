package com.junyuzhou.jywallpaper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.junyuzhou.jywallpaper.R;

/**
 * Created by Junyu on 2017-02-05.
 */

public class WpTextView extends TextView {
    public WpTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public WpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public WpTextView(Context context) {
        super(context);
        init(null);
    }

    //accept string from xml attribute, and set it here
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WpTextView);
            String fontName = a.getString(R.styleable.WpTextView_font);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}