package com.pos.myfonts;

import android.content.Context;
import android.graphics.Typeface;


public class RobotoMedium {

    public Context mContext;
    public static Typeface fontTypeface;
    String path = "Roboto-Medium.ttf";

    public RobotoMedium() {
    }
    public RobotoMedium(Context context) {
        mContext = context;
    }

    public Typeface getFont() {
        if (fontTypeface == null)
            fontTypeface = Typeface.createFromAsset(mContext.getAssets(), path);
            return fontTypeface;
    }
}