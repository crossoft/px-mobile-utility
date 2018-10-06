package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pos.myfonts.RobotoBold;


@SuppressLint("AppCompatCustomView")
public class BoldCustomTextView extends TextView {

    public BoldCustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public BoldCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public BoldCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        try {
            this.setTypeface(new RobotoBold(context).getFont());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}