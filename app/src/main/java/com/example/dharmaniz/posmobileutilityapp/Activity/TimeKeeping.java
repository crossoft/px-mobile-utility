package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dharmaniz.posmobileutilityapp.Adapter.TimeKeepingAdapter;
import com.example.dharmaniz.posmobileutilityapp.PostMobileSharedPreference.NewAppSharedPreference;
import com.example.dharmaniz.posmobileutilityapp.PostMobileUtilityApplication;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Models.TimeKeepingModel;
import com.example.dharmaniz.posmobileutilityapp.Util.AlertDialogManager;
import com.example.dharmaniz.posmobileutilityapp.Util.Utilities;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeKeeping extends PostMobileBaseActivity {
    Activity mActivity = TimeKeeping.this;
    private RelativeLayout backRL,homeRL;
    private RecyclerView time_keepingTVRV;
    TimeKeepingAdapter mAdapter;
    ArrayList<TimeKeepingModel> metricModelArrayList = new ArrayList<TimeKeepingModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_keeping);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        time_keepingTVRV = findViewById(R.id.time_keepingTVRV);
        homeRL = findViewById(R.id.homeRL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        metricModelArrayList.clear();
        if (!Utilities.isNetworkAvailable(mActivity)) {
            AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {
            getInventoryData();
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

                // Share icon Functionality Added
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
                }else {
                    if (metricModelArrayList.size() != 0) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Time Keeping Information" + "\n" + metricModelArrayList.get(0).getFirstName() + " " + metricModelArrayList.get(0).getLastName() + "      " + metricModelArrayList.get(0).getClockInDateTime() + "\n" + metricModelArrayList.get(1).getFirstName() + " " + metricModelArrayList.get(1).getLastName() + "      " + metricModelArrayList.get(1).getClockInDateTime());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                }

            }
        });
    }

    private void getInventoryData() {
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/GetTimeKeepingByAccountId.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
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
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "getInventoryData");
    }


    private void parsseresponce(JSONObject responce) {
        try {
            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");

                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    TimeKeepingModel mMetricModel = new TimeKeepingModel();

                    if (!jsonDATAObject.getString("accountId").equals("")) {
                        mMetricModel.setAccountId(jsonDATAObject.getString("accountId"));
                    }
                    if (!jsonDATAObject.getString("userId").equals("")) {
                        mMetricModel.setUserId(jsonDATAObject.getString("userId"));
                    }
                    if (!jsonDATAObject.getString("lastName").equals("")) {
                        mMetricModel.setLastName(jsonDATAObject.getString("lastName"));
                    }
                    if (!jsonDATAObject.getString("firstName").equals("")) {
                        mMetricModel.setFirstName(jsonDATAObject.getString("firstName"));
                    }
                    if (!jsonDATAObject.getString("clockInDateTime").equals("")) {
                        mMetricModel.setClockInDateTime(jsonDATAObject.getString("clockInDateTime"));
                    }


                    metricModelArrayList.add(mMetricModel);
                    setAdapter();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //
    private void setAdapter(){
        mAdapter = new TimeKeepingAdapter(mActivity, metricModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        time_keepingTVRV.setLayoutManager(mLayoutManager);
        time_keepingTVRV.setItemAnimator(new DefaultItemAnimator());
        time_keepingTVRV.setAdapter(mAdapter);
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
