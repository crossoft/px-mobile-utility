package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pos.myfonts.RobotoRegular;

@SuppressLint("AppCompatCustomView")
public class RegularCustomTextView extends TextView {

    public RegularCustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RegularCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RegularCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        try {
            this.setTypeface(new RobotoRegular(context).getFont());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}