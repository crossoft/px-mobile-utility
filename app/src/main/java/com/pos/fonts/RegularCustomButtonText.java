package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.pos.myfonts.RobotoRegular;

@SuppressLint("AppCompatCustomView")
public class RegularCustomButtonText extends Button {

    public RegularCustomButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            this.setTypeface(new RobotoRegular(context).getFont());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
