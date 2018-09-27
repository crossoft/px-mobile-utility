package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dharmaniz.posmobileutilityapp.PostMobileSharedPreference.NewAppSharedPreference;
import com.example.dharmaniz.posmobileutilityapp.PostMobileUtilityApplication;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.AlertDialogManager;
import com.example.dharmaniz.posmobileutilityapp.Util.Utilities;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Activity mActivity = LoginActivity.this;
    private EditText usernameET, passwordET;
    private TextView forgetTV, logingTV;
    private View view1, view2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        setUpe();


    }


    protected void setUp() {
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        forgetTV = findViewById(R.id.forgetTV);
        logingTV = findViewById(R.id.logingTV);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
    }

    protected void setUpe() {


        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                view2.setBackground(getDrawable(R.drawable.stroke_yellow));
                view1.setBackground(getDrawable(R.drawable.stroke));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                view1.setBackground(getDrawable(R.drawable.stroke_yellow));
                view2.setBackground(getDrawable(R.drawable.stroke));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        logingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameET.getText().toString().equals("")) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "Please enter your username.");
                } else if (passwordET.getText().toString().equals("")) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "Please enter your password.");
                } else {
                    if(!Utilities.isNetworkAvailable(mActivity)){
                        AlertDialogManager.showAlertDialog(mActivity,"","No Internet Connection, Please connect the valid internet Connection");
                    }else {
                        LoginApiSetUp();
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(!NewAppSharedPreference.readString(mActivity,NewAppSharedPreference.USER_ID,"").equals("")){
            Intent mIntent = new Intent(mActivity, HomeActivity.class);
            startActivity(mIntent);
            finish();
        }
    }

    private void LoginApiSetUp() {
        Utilities.showProgressDialog(mActivity);
        String url ="http://dharmani.com/jspro2/Login.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", "cmiller");
            jsonObject.put("userPass", "We!C0meO3");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utilities.hideProgressDialog();
                        try {
                            if (response.getString("Status").equals("1")) {

                                if(!response.isNull("data")){
                                    JSONObject mMJsonDataObj = response.getJSONObject("data");
                                    if(!mMJsonDataObj.getString("userName").equals("")){
                                        NewAppSharedPreference.writeString(mActivity, NewAppSharedPreference.USER_NAME, mMJsonDataObj.getString("userName"));
                                        NewAppSharedPreference.writeString(mActivity, NewAppSharedPreference.Account_ID, mMJsonDataObj.getString("accountId"));
                                        NewAppSharedPreference.writeString(mActivity, NewAppSharedPreference.USER_ID, "abc");
                                        Intent mIntent = new Intent(mActivity, HomeActivity.class);
                                        startActivity(mIntent);
                                        finish();

                                    }

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.hideProgressDialog();
                VolleyLog.d("", "Error: " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
////      Adding request to request queue
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "LoginAPI");
    }
}
