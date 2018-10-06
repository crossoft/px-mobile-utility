package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.pos.myfonts.RobotoMedium;

@SuppressLint("AppCompatCustomView")
public class MediumCustomButtonText extends Button {

    public MediumCustomButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            this.setTypeface(new RobotoMedium(context).getFont());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
