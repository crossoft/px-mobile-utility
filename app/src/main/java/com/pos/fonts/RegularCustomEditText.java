package com.pos.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

import com.pos.myfonts.RobotoRegular;

@SuppressLint("AppCompatCustomView")
public class RegularCustomEditText extends EditText {
    public RegularCustomEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public RegularCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public RegularCustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegularCustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
