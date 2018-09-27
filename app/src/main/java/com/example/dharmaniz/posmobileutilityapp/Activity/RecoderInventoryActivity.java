package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dharmaniz.posmobileutilityapp.Adapter.RecorderAdapter;
import com.example.dharmaniz.posmobileutilityapp.Models.RecoderModel;
import com.example.dharmaniz.posmobileutilityapp.PostMobileSharedPreference.NewAppSharedPreference;
import com.example.dharmaniz.posmobileutilityapp.PostMobileUtilityApplication;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.AlertDialogManager;
import com.example.dharmaniz.posmobileutilityapp.Util.Constants;
import com.example.dharmaniz.posmobileutilityapp.Util.Utilities;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecoderInventoryActivity extends PostMobileBaseActivity {
    Activity mActivity =RecoderInventoryActivity.this;
private RelativeLayout backRL,homeRL;
    private RecyclerView innventoryRV;
    private RecorderAdapter mAdapter;
    private ArrayList<RecoderModel> modelArrayList= new ArrayList<RecoderModel>();
    private EditText editSearchET;
    private LinearLayout mainNormalLL,micLL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoder_inventory);
    }
    @Override
    protected void setUpViews() {
        backRL=findViewById(R.id.backRL);
        homeRL=findViewById(R.id.homeRL);
        innventoryRV=findViewById(R.id.innventoryRV);
        mainNormalLL=findViewById(R.id.mainNormalLL);
        editSearchET=findViewById(R.id.editSearchET);
        micLL=findViewById(R.id.micLL);
        if(Constants.recorderListClick==true){
            mainNormalLL.setVisibility(View.VISIBLE);
        }else {
            mainNormalLL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        modelArrayList.clear();
        if (!Utilities.isNetworkAvailable(mActivity)) {
            AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {
            if (!Constants.strSearchText.equals("")) {
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                editSearchET.setText(Constants.strSearchText);
                ExcecuteSerachAPI(Constants.strSearchText);
            }
        }
//        if(Constants.recorderListClick==true){
//            modelArrayList.clear();
//            getAllSetUpInventoryData();
//        }else {
//            modelArrayList.clear();
//            getSetUpInventoryData();
//        }

    }

    @Override
    protected void setUpEvents() {

        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        homeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.recorderListClick=false;
                Intent mIntent = new Intent(mActivity, HomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
        micLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelArrayList.clear();
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
                } else {
                    int strSearch = editSearchET.getText().toString().length();
                    Constants.strSearchText = editSearchET.getText().toString();
                    if (strSearch <= 2) {
                        AlertDialogManager.showAlertDialog(mActivity, "", "Please enter at least 3 characters for your search.");
                    } else {
                        ExcecuteSerachAPI(Constants.strSearchText);
                    }
                }
            }
        });
        editSearchET .setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    modelArrayList.clear();
                    if (!Utilities.isNetworkAvailable(mActivity)) {
                        AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
                    } else {
                        Constants.strSearchText = editSearchET.getText().toString();
                        int strSearch = editSearchET.getText().toString().length();

                        if (strSearch <= 2) {
                            AlertDialogManager.showAlertDialog(mActivity, "", "Please enter at least 3 characters for your search.");
                        } else {
                            ExcecuteSerachAPI(Constants.strSearchText);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.recorderListClick=false;
        Intent mIntent = new Intent(mActivity, InventoryHomeActivity.class);
        startActivity(mIntent);
        finish();
        Constants.strSearchText="";
        overridePendingTransitionExit();
    }


    private void ExcecuteSerachAPI(String strSearchText){
        modelArrayList.clear();
        Utilities.showProgressDialog(mActivity);


        String url = "http://dharmani.com/jspro2/SearchInventoryByReorderList.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("vendorName", strSearchText);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("INVENTORY DATA", response.toString());
                        try {
                            if (response.getString("status").equals("1")) {

                                parsseresponce(response);

                            }else {
                                Utilities.hideProgressDialog();
                                AlertDialogManager.showAlertDialog(mActivity,"Please try again",response.getString("message"));
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
//      Adding request to request queue
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "ExcecuteSerachAPI");
    }




    private void parsseresponce(JSONObject responce){
        modelArrayList.clear();
        try {
            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    RecoderModel mRecoderModel = new RecoderModel();
                    if (!jsonDATAObject.getString("reorderQty").equals("")) {
                        mRecoderModel.setReorderQty(jsonDATAObject.getString("reorderQty"));
                    }

                    if (!jsonDATAObject.getString("name").equals("")) {
                        mRecoderModel.setName(jsonDATAObject.getString("name"));
                    }
                    if (!jsonDATAObject.getString("calculatedQty").equals("")) {
                        mRecoderModel.setCalculatedQty(jsonDATAObject.getString("calculatedQty"));
                    }
                    if (!jsonDATAObject.getString("vendorName").equals("")) {
                        mRecoderModel.setVendorName(jsonDATAObject.getString("vendorName"));

                    }
                    if (!jsonDATAObject.getString("minLevel").equals("")) {
                        mRecoderModel.setMinLevel(jsonDATAObject.getString("minLevel"));
                    }
                    if (!jsonDATAObject.getString("maxLevel").equals("")) {
                        mRecoderModel.setMaxLevel(jsonDATAObject.getString("maxLevel"));
                    } if (!jsonDATAObject.getString("inventoryProductId").equals("")) {
                        mRecoderModel.setInventoryProductId(jsonDATAObject.getString("inventoryProductId"));
                    }

                    modelArrayList.add(mRecoderModel);
                    setAdapter();
                }

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {

        mAdapter = new RecorderAdapter(mActivity, modelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        innventoryRV.setLayoutManager(mLayoutManager);
        innventoryRV.setItemAnimator(new DefaultItemAnimator());
        innventoryRV.setAdapter(mAdapter);
        Utilities.hideProgressDialog();
    }
}
