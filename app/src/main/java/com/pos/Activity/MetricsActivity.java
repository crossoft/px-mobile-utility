package com.pos.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pos.Adapter.MetricAdapter;
import com.pos.Models.MetricModel;
import com.pos.PostMobileBaseActivity;
import com.pos.PostMobileSharedPreference.NewAppSharedPreference;
import com.pos.PostMobileUtilityApplication;
import com.pos.R;
import com.pos.Util.AlertDialogManager;
import com.pos.Util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetricsActivity extends PostMobileBaseActivity {
    Activity mActivity = MetricsActivity.this;
    private TextView backTV;
    private RecyclerView metricRV;
    private RelativeLayout backRL, homeRL;
    MetricAdapter mAdapter;
    ArrayList<MetricModel> metricModelArrayList = new ArrayList<MetricModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        metricRV = findViewById(R.id.metricRV);
        homeRL = findViewById(R.id.homeRL);
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
                // Sharing Intent
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
                } else {
                    if (metricModelArrayList.size() != 0) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Metrics Information" + "\n" + metricModelArrayList.get(0).getMetric() + "      " + metricModelArrayList.get(0).getResult() + "\n" + metricModelArrayList.get(1).getMetric() + "      " + metricModelArrayList.get(1).getResult());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                }
            }
        });
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

    private void getInventoryData() {
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/GetMetericsByAccountId.php";
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
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "Getting data");
    }


    private void parsseresponce(JSONObject responce) {
        try {
            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");

                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    MetricModel mMetricModel = new MetricModel();
                    if (!jsonDATAObject.getString("accountId").equals("")) {
                        mMetricModel.setAccountId(jsonDATAObject.getString("accountId"));
                    }
                    if (!jsonDATAObject.getString("metric").equals("")) {
                        mMetricModel.setMetric(jsonDATAObject.getString("metric"));
                    }
                    if (!jsonDATAObject.getString("result").equals("")) {
                        mMetricModel.setResult(jsonDATAObject.getString("result"));
                    }

                    metricModelArrayList.add(mMetricModel);
                    setAdapter();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        mAdapter = new MetricAdapter(mActivity, metricModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        metricRV.setLayoutManager(mLayoutManager);
        metricRV.setItemAnimator(new DefaultItemAnimator());
        metricRV.setAdapter(mAdapter);
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
