package com.example.dharmaniz.posmobileutilityapp.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.dharmaniz.posmobileutilityapp.myfonts.RobotoMedium;


@SuppressLint("AppCompatCustomView")
public class MediumCustomTextView extends TextView {

    public MediumCustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public MediumCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public MediumCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        try {
            this.setTypeface(new RobotoMedium(context).getFont());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}