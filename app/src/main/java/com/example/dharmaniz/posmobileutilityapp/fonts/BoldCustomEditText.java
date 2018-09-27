package com.example.dharmaniz.posmobileutilityapp.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.dharmaniz.posmobileutilityapp.myfonts.RobotoBold;


/**
 * Created by Dharmani Apps on 5/2/2017.
 */

@SuppressLint("AppCompatCustomView")
public class BoldCustomEditText extends EditText {
    public BoldCustomEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BoldCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public BoldCustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BoldCustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
