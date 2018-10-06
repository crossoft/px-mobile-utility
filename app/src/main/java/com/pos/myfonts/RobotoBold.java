package com.pos.myfonts;

import android.content.Context;
import android.graphics.Typeface;

public class RobotoBold {
    public static Typeface fontTypeface ;
    String path = "Roboto-Bold.ttf";
    Context mContext;

    public RobotoBold() {
    }
    public RobotoBold(Context context) {
        mContext = context;
    }

    public Typeface getFont(){
        if(fontTypeface==null)
            fontTypeface = Typeface.createFromAsset(mContext.getAssets(), path);
            return fontTypeface;
    }

}
