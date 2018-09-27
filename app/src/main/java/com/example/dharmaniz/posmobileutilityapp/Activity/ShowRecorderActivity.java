package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class ShowRecorderActivity extends PostMobileBaseActivity {
    Activity mActivity = ShowRecorderActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView searchAgainTv,cloaseAgainTV,productNameTV, calculatedQtyNameTV, reorderQtyNameTV, minNameTV, maxNameTV, vendorNameTV;
    public LinearLayout vendorLL, reorderQtyLL,dataLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    RecoderModel mRecorderModel;
    StringBuilder builder = new StringBuilder();
    ArrayList<RecoderModel> mArray = new ArrayList<RecoderModel>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recorder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArray.clear();
        builder.setLength(0);
        dataLL.removeAllViews();
        if (!Utilities.isNetworkAvailable(mActivity)) {
            showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {
            getInventoryData();
        }
    }

    @Override
    protected void setUpEvents() {
        shareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Utilities.isNetworkAvailable(mActivity)) {
                  showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");


                }else {
                    if (mArray.size() != 0) {
                        System.out.println("OutPut" + builder.toString());
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Product Information" + " \n " + builder.toString());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                }
            }
        });
        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        searchAgainTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.strSearchText="";
                Intent mIntent = new Intent(mActivity, RecoderInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
        cloaseAgainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.strSearchText="";
                Intent mIntent = new Intent(mActivity, InventoryHomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
    }
    private void getInventoryData() {
        mArray.clear();
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/GetInventorydataByReorderList.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("vendorName", Constants.Product_ID);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Utilities.hideProgressDialog();
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
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "getInventoryData");
    }

    public void parsseresponce(JSONObject response) {

        try {
            if (!response.getString("status").equals("")) {
                JSONArray mJsonArray = response.getJSONArray("data");
                if(!response.isNull("data")) {

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        RecoderModel mDetailModel = new RecoderModel();

                        JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                        if (!jsonDATAObject.getString("inventoryProductId").equals("")) {
                            mDetailModel.setInventoryProductId(jsonDATAObject.getString("inventoryProductId"));
                        }
                        if (!jsonDATAObject.getString("calculatedQty").equals("")) {

                            mDetailModel.setCalculatedQty(jsonDATAObject.getString("calculatedQty"));
                        }
                        if (!jsonDATAObject.getString("maxLevel").equals("")) {
                            mDetailModel.setMaxLevel(jsonDATAObject.getString("maxLevel"));
                        }
                        if (!jsonDATAObject.getString("minLevel").equals("")) {
                            mDetailModel.setMinLevel(jsonDATAObject.getString("minLevel"));
                        }
                        if (!jsonDATAObject.getString("name").equals("")) {
                            mDetailModel.setName(jsonDATAObject.getString("name"));
                        }
                        if (!jsonDATAObject.getString("reorderQty").equals("")) {
                            mDetailModel.setReorderQty(jsonDATAObject.getString("reorderQty"));

                        }
                        if (!jsonDATAObject.getString("vendorName").equals("")) {
                            mDetailModel.setVendorName(jsonDATAObject.getString("vendorName"));

                        }
                        if (!jsonDATAObject.getString("name").equals("")) {
                            mDetailModel.setName(jsonDATAObject.getString("name"));

                        }
                        mArray.add(mDetailModel);
                    }


                String strReorderQTY ="";
                for (int i = 0; i < mArray.size(); i++) {
                    RecoderModel mDetailModel = mArray.get(i);
                    LayoutInflater layoutInflater =
                            (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = layoutInflater.inflate(R.layout.item_show_reorder_list, null);
                    TextView calculatedQtyNameTV = addView.findViewById(R.id.calculatedQtyNameTV);
                    TextView reorderQtyNameTV = addView.findViewById(R.id.reorderQtyNameTV);
                    TextView minNameTV = addView.findViewById(R.id.minNameTV);
                    TextView maxNameTV = addView.findViewById(R.id.maxNameTV);
                    TextView vendorNameTV = addView.findViewById(R.id.vendorNameTV);
                    TextView nameTV = addView.findViewById(R.id.nameTV);
                    LinearLayout mainLL = addView.findViewById(R.id.mainLL);
                    LinearLayout calculatedQtyLL = addView.findViewById(R.id.calculatedQtyLL);
                    strReorderQTY= mDetailModel.getReorderQty();


                    if (!mDetailModel.getName().equals("")) {
                        builder.append("Name"+"    "+mDetailModel.getName() + "\n");
                        nameTV.setText(mDetailModel.getName());
                    }else {
                        builder.append("Name"+"    "+"N/A" +"\n");
                        vendorNameTV.setText("N/A");
                    }

                        if (!mDetailModel.getCalculatedQty().equals("")) {
                            builder.append("Calculated Qty" +"     " +mDetailModel.getCalculatedQty() + "\n");
                            calculatedQtyNameTV.setText(mDetailModel.getCalculatedQty());
                        }else {
                            builder.append("Calculated Qty" +"   " +"N/A" +"\n");
                            calculatedQtyNameTV.setText("N/A");
                        }
                        if (!mDetailModel.getReorderQty().equals("")) {
                            builder.append("Reorder Qty"+"    "+mDetailModel.getReorderQty() + "\n");
                            reorderQtyNameTV.setText(mDetailModel.getReorderQty());
                        }else {
                            builder.append("Reorder Qty"+"    "+"N/A" +"\n");
                            reorderQtyNameTV.setText("N/A");
                        }
                        if (!mDetailModel.getMinLevel().equals("")) {
                            builder.append("Min"+"    "+mDetailModel.getMinLevel() + "\n");
                            minNameTV.setText(mDetailModel.getMinLevel());
                        }else {
                            builder.append("Min"+"    "+"N/A" +"\n");
                            minNameTV.setText("N/A");
                        }
                        if (!mDetailModel.getMaxLevel().equals("")) {
                            builder.append("Max"+"    "+mDetailModel.getMaxLevel() + "\n");
                            maxNameTV.setText(mDetailModel.getMaxLevel());
                        }else {
                            builder.append("Max"+"    "+"N/A" +"\n");
                            maxNameTV.setText("N/A");
                        }
                        if (!mDetailModel.getVendorName().equals("")) {
                            builder.append("Vendor"+"    "+mDetailModel.getVendorName() + "\n");
                            vendorNameTV.setText(mDetailModel.getVendorName());
                        }else {
                            builder.append("Vendor"+"    "+"N/A" +"\n");
                            vendorNameTV.setText("N/A");
                        }

//                    }


                    dataLL.addView(addView);
                }
                }
                else {
                    showAlertDialog(mActivity,"","Detail is not found");
                }
            }

            Utilities.hideProgressDialog();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void setUpViews() {
        shareRL = findViewById(R.id.shareRL);
        backRL = findViewById(R.id.backRL);
        tittleTV = findViewById(R.id.tittleTV);
        searchAgainTv = findViewById(R.id.searchAgainTv);
        cloaseAgainTV = findViewById(R.id.cloaseAgainTV);
        dataLL = findViewById(R.id.dataLL);
        tittleTV.setText(Constants.Tittle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.ReorderBackClick=true;
        Intent mIntent = new Intent(mActivity, RecoderInventoryActivity.class);
        startActivity(mIntent);
        finish();
        overridePendingTransitionExit();
    }

    public  void showAlertDialog(final Activity mActivity, String strTitle, String strMessage) {
        final Dialog alertDialog = new Dialog(mActivity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_showalert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // set the custom dialog components - text, image and button
        TextView txtTitle = (TextView) alertDialog.findViewById(R.id.txtTitle);
        TextView txtMessage = (TextView) alertDialog.findViewById(R.id.txtMessage);
        TextView txtDismiss = (TextView) alertDialog.findViewById(R.id.txtDismiss);


        txtTitle.setText(strTitle);
        txtMessage.setText(strMessage);
        txtDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Constants.ReorderBackClick=true;
                Intent mIntent = new Intent(mActivity, RecoderInventoryActivity.class);
                mActivity.startActivity(mIntent);
                mActivity.finish();

            }
        });

        alertDialog.show();
    }
}
