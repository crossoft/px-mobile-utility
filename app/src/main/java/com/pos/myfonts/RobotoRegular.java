package com.pos.myfonts;

import android.content.Context;
import android.graphics.Typeface;


public class RobotoRegular {
    public Context mContext;
    public static Typeface fontTypeface;
    public String path = "Roboto-Regular.ttf";

    public RobotoRegular() {
    }

    public RobotoRegular(Context context) {
        mContext = context;
    }

    public Typeface getFont() {
        if (fontTypeface == null)
            fontTypeface = Typeface.createFromAsset(mContext.getAssets(), path);
        return fontTypeface;
    }

}