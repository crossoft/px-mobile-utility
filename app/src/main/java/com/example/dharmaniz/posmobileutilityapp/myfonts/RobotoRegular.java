package com.example.dharmaniz.posmobileutilityapp.myfonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Dharmani Apps on 5/3/2017.
 */

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