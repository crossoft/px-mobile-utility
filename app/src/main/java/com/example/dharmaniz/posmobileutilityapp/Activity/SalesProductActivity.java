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
import com.example.dharmaniz.posmobileutilityapp.Adapter.SalesProductAdapter;
import com.example.dharmaniz.posmobileutilityapp.Models.SalesProductModel;
import com.example.dharmaniz.posmobileutilityapp.PostMobileSharedPreference.NewAppSharedPreference;
import com.example.dharmaniz.posmobileutilityapp.PostMobileUtilityApplication;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.AlertDialogManager;
import com.example.dharmaniz.posmobileutilityapp.Util.Utilities;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesProductActivity extends PostMobileBaseActivity {
    Activity mActivity = SalesProductActivity.this;
    private RecyclerView innventoryRV;
    SalesProductAdapter mAdapter;
    RelativeLayout backRL;
    RelativeLayout searchRL;
    LinearLayout micLL,mainNormalLL;
    EditText editSearchET;
    ArrayList<SalesProductModel> mArrayList = new ArrayList<SalesProductModel>();
    ArrayList<SalesProductModel> filteredList = new ArrayList<SalesProductModel>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_product);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        innventoryRV = findViewById(R.id.innventoryRV);
        micLL = findViewById(R.id.micLL);
        searchRL = findViewById(R.id.searchRL);
        editSearchET = findViewById(R.id.editSearchET);
        mainNormalLL = findViewById(R.id.mainNormalLL);
        mainNormalLL.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArrayList.clear();

    }

    @Override
    protected void setUpEvents() {
        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        micLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInventoryData();
            }
        });

        editSearchET .setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    getInventoryData();
                }
                return false;
            }
        });
    }

    private void getInventoryData() {
        mArrayList.clear();
        Utilities.showProgressDialog(mActivity);

        String url = "http://dharmani.com/jspro2/SearchSalesProducts.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("Name", editSearchET.getText().toString());


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
                                AlertDialogManager.showAlertDialog(mActivity,"",response.getString("message"));
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
    private void parsseresponce(JSONObject responce) {
        try {

            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    SalesProductModel mSaleModel = new SalesProductModel();
                    if (!jsonDATAObject.getString("accountId").equals("")) {
                        mSaleModel.setAccountId(jsonDATAObject.getString("accountId"));
                    }
                    if (!jsonDATAObject.getString("salesProductId").equals("")) {
                        mSaleModel.setSalesProductId(jsonDATAObject.getString("salesProductId"));
                    }
                    if (!jsonDATAObject.getString("name").equals("")) {
                        mSaleModel.setName(jsonDATAObject.getString("name"));
                    }
                    if (!jsonDATAObject.getString("description").equals("")) {
                        mSaleModel.setDescription(jsonDATAObject.getString("description"));
                    }

                    mArrayList.add(mSaleModel);
                    setAdapter();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {

        mAdapter = new SalesProductAdapter(mActivity, mArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        innventoryRV.setLayoutManager(mLayoutManager);
        innventoryRV.setItemAnimator(new DefaultItemAnimator());
        innventoryRV.setAdapter(mAdapter);
        Utilities.hideProgressDialog();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(mActivity, HomeActivity.class);
        startActivity(mIntent);
        finish();
        overridePendingTransitionExit();
    }
}
