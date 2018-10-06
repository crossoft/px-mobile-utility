package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.pos.myfonts.RobotoBold;


@SuppressLint("AppCompatCustomView")
public class BoldCustomButtonText extends Button {

    public BoldCustomButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            this.setTypeface(new RobotoBold(context).getFont());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
