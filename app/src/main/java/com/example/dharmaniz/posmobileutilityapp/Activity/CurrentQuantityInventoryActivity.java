package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.dharmaniz.posmobileutilityapp.Adapter.CurrenTQuantityAdapter;
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

public class CurrentQuantityInventoryActivity extends PostMobileBaseActivity {
    Activity mActivity = CurrentQuantityInventoryActivity.this;
    private CurrenTQuantityAdapter mAdapter;
    private ArrayList<RecoderModel> modelArrayList = new ArrayList<RecoderModel>();
    private TextView backTV;
    private RelativeLayout backRL, homeRL;
    private EditText editSearchET;
    private LinearLayout mainNormalLL, micLL;
    private RecyclerView innventoryRV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_quantity_inventory);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        homeRL = findViewById(R.id.homeRL);
        innventoryRV = findViewById(R.id.innventoryRV);
        mainNormalLL = findViewById(R.id.mainNormalLL);
        editSearchET = findViewById(R.id.editSearchET);
        micLL = findViewById(R.id.micLL);
//    Show Search visibility
        mainNormalLL.setVisibility(View.VISIBLE);

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
                Intent mIntent = new Intent(mActivity, HomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
        micLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        editSearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.SetUpCurrentQuanity = false;
        Intent mIntent = new Intent(mActivity, InventoryHomeActivity.class);
        startActivity(mIntent);
        finish();
        Constants.strSearchText = "";
        overridePendingTransitionExit();
    }

    private void ExcecuteSerachAPI(String strSearchText) {
        modelArrayList.clear();
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/SearchInventoryByCurrentQty.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("Name", strSearchText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utilities.hideProgressDialog();
                        Log.e("INVENTORY DATA", response.toString());
                        try {
                            if (response.getString("status").equals("1")) {
                                modelArrayList.clear();

                                parsseresponce(response);
                            } else if (response.getString("status").equals("0")) {
                                AlertDialogManager.showAlertDialog(mActivity, "Please try again", response.getString("message"));
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
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "ExcecuteSerachAPI");

    }



    private void parsseresponce(JSONObject responce) {
        modelArrayList.clear();
        try {
            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    RecoderModel mInventoryModel = new RecoderModel();

                    if (!jsonDATAObject.getString("name").equals("")) {
                        mInventoryModel.setName(jsonDATAObject.getString("name"));
                    }
                    if (!jsonDATAObject.getString("calculatedQty").equals("")) {
                        mInventoryModel.setCalculatedQty(jsonDATAObject.getString("calculatedQty"));
                    }

                    if (!jsonDATAObject.getString("vendorName").equals("")) {
                        mInventoryModel.setVendorName(jsonDATAObject.getString("vendorName"));
                    }
                    if (!jsonDATAObject.getString("inventoryProductId").equals("")) {
                        mInventoryModel.setInventoryProductId(jsonDATAObject.getString("inventoryProductId"));
                    }
                    if (!jsonDATAObject.getString("minLevel").equals("")) {
                        mInventoryModel.setMinLevel(jsonDATAObject.getString("minLevel"));
                    }
                    if (!jsonDATAObject.getString("accountId").equals("")) {
                        mInventoryModel.setAccountId(jsonDATAObject.getString("accountId"));
                    }
                    if (!jsonDATAObject.getString("maxLevel").equals("")) {
                        mInventoryModel.setMaxLevel(jsonDATAObject.getString("maxLevel"));
                    }
                    if (!jsonDATAObject.getString("reorderState").equals("")) {
                        mInventoryModel.setReorderState(jsonDATAObject.getString("reorderState"));
                    }
                    if (!jsonDATAObject.getString("receivingInstructions").equals("")) {
                        mInventoryModel.setReceivingInstructions(jsonDATAObject.getString("receivingInstructions"));
                    }
                    if (!jsonDATAObject.getString("ingredientInstructions").equals("")) {
                        mInventoryModel.setIngredientInstructions(jsonDATAObject.getString("ingredientInstructions"));
                    }
                    if (!jsonDATAObject.getString("vendorSku").equals("")) {
                        mInventoryModel.setVendorSku(jsonDATAObject.getString("vendorSku"));
                    }
                    if (!jsonDATAObject.getString("maxDaysBetweenLogging").equals("")) {
                        mInventoryModel.setMaxDaysBetweenLogging(jsonDATAObject.getString("maxDaysBetweenLogging"));
                    }
                    if (!jsonDATAObject.getString("logDueDateOverride").equals("")) {
                        mInventoryModel.setLogDueDateOverride(jsonDATAObject.getString("logDueDateOverride"));
                    }
                    if (!jsonDATAObject.getString("primaryZone").equals("")) {
                        mInventoryModel.setPrimaryZone(jsonDATAObject.getString("primaryZone"));
                    }
                    if (!jsonDATAObject.getString("primaryBin").equals("")) {
                        mInventoryModel.setPrimaryBin(jsonDATAObject.getString("primaryBin"));
                    }
                    if (!jsonDATAObject.getString("secondaryZone").equals("")) {
                        mInventoryModel.setSecondaryZone(jsonDATAObject.getString("secondaryZone"));
                    }
                    if (!jsonDATAObject.getString("secondaryBin").equals("")) {
                        mInventoryModel.setSecondaryBin(jsonDATAObject.getString("secondaryBin"));
                    }

                    modelArrayList.add(mInventoryModel);
                    setAdapter();
                }

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setAdapter() {
        mAdapter = new CurrenTQuantityAdapter(mActivity, modelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        innventoryRV.setLayoutManager(mLayoutManager);
        innventoryRV.setItemAnimator(new DefaultItemAnimator());
        innventoryRV.setAdapter(mAdapter);
        Utilities.hideProgressDialog();
    }
}


