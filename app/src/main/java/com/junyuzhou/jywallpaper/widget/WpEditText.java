package com.junyuzhou.jywallpaper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.junyuzhou.jywallpaper.R;

/**
 * Created by Junyu on 2017-02-05.
 */

public class WpEditText extends EditText {
    public WpEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public WpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WpEditText(Context context) {
        super(context);
        init(null);
    }

    //accept string from xml attribute, and set it here
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WpEditText);
            String fontName = a.getString(R.styleable.WpEditText_font);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
