package com.example.dharmaniz.posmobileutilityapp.myfonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Dharmani Apps on 5/2/2017.
 */

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
