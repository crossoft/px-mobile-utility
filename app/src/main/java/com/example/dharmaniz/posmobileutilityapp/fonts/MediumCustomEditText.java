package com.example.dharmaniz.posmobileutilityapp.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.dharmaniz.posmobileutilityapp.myfonts.RobotoMedium;

/**
 * Created by Dharmani Apps on 5/2/2017.
 */

@SuppressLint("AppCompatCustomView")
public class MediumCustomEditText extends EditText {
    public MediumCustomEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MediumCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MediumCustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MediumCustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
