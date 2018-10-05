package com.example.dharmaniz.posmobileutilityapp.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.example.dharmaniz.posmobileutilityapp.R;


/**
 * Created by dharmaniz on 1/9/18.
 */

public class Utilities {
    static Dialog pdialog = null;
    public static void showProgressDialog(Activity mActivity) {
        pdialog = new Dialog(mActivity);
        pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdialog.setContentView(R.layout.dialog_progess);
        pdialog.setCanceledOnTouchOutside(false);
        ProgressBar circlePB = (ProgressBar) pdialog.findViewById(R.id.circlePB);
        pdialog.show();
    }
    public static void hideProgressDialog() {
        if (pdialog.isShowing()) {
            pdialog.dismiss();
        }
    }

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}