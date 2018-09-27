package com.example.dharmaniz.posmobileutilityapp.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.dharmaniz.posmobileutilityapp.myfonts.RobotoBold;


/**
 * Created by patas tech on 10/20/2016.
 */
@SuppressLint("AppCompatCustomView")
public class BoldCustomButtonText extends Button {

    public BoldCustomButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            this.setTypeface(new RobotoBold(context).getFont());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
