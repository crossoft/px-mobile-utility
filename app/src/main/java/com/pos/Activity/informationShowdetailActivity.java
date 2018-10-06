package com.pos.Activity;

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
import com.pos.Models.InformationModel;
import com.pos.PostMobileBaseActivity;
import com.pos.PostMobileSharedPreference.NewAppSharedPreference;
import com.pos.PostMobileUtilityApplication;
import com.pos.R;
import com.pos.Util.Constants;
import com.pos.Util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class informationShowdetailActivity extends PostMobileBaseActivity {
    Activity mActivity = informationShowdetailActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView sizenameTV, tittleTVq, searchAgainTv, cloaseAgainTV, productNameTV, calculatedQtyNameTV, reorderQtyNameTV, minNameTV, maxNameTV, vendorNameTV;
    public LinearLayout vendorLL, reorderQtyLL, dataLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    InformationModel mRecorderModel;
    StringBuilder builder = new StringBuilder();
    static String strName = "";
    ArrayList<InformationModel> mArray = new ArrayList<InformationModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_showdetail);
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


                } else {
                    if (!strName.equals("")) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Product Information" + "\n" + builder.toString());
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
                Intent mIntent = new Intent(mActivity, SaleProductInformationActivity.class);
                startActivity(mIntent);
                finish();
                Constants.strSearchText = "";
                overridePendingTransitionExit();
            }
        });
        cloaseAgainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.strSearchText = "";
                Intent mIntent = new Intent(mActivity, SaleProductHomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
    }


    @Override
    protected void setUpViews() {
        shareRL = findViewById(R.id.shareRL);
        backRL = findViewById(R.id.backRL);
        tittleTV = findViewById(R.id.tittleTV);
        searchAgainTv = findViewById(R.id.searchAgainTv);
        cloaseAgainTV = findViewById(R.id.cloaseAgainTV);
        dataLL = findViewById(R.id.dataLL);
        tittleTVq = findViewById(R.id.tittleTVq);
        tittleTVq.setText(Constants.Tittle);
    }

    private void getInventoryData() {
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/GetInformationdataByProductId.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("salesProductId", Constants.Product_ID);


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

    public void parsseresponce(JSONObject response) {
        Utilities.hideProgressDialog();
        try {
            if (!response.getString("status").equals("")) {
                JSONArray mJsonArray = response.getJSONArray("data");

                if (!response.isNull("data")) {
                    for (int i = 0; i < mJsonArray.length(); i++) {


                        JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);


                        InformationModel mInfoModel = new InformationModel();

                        if (!jsonDATAObject.getString("sizeName").equals("")) {
                            strName = jsonDATAObject.getString("sizeName");
                            mInfoModel.setSizeName(jsonDATAObject.getString("sizeName"));

                        }
                        if (!jsonDATAObject.getString("price").equals("")) {
                            mInfoModel.setPrice(jsonDATAObject.getString("price"));

                        }

                        if (!jsonDATAObject.getString("description").equals("")) {
                            mInfoModel.setDescription(jsonDATAObject.getString("description"));

                        }
                        if (!jsonDATAObject.getString("salesProductId").equals("")) {
                            mInfoModel.setSalesProductId(jsonDATAObject.getString("salesProductId"));

                        }
                        mArray.add(mInfoModel);
                    }
                } else {
                    showAlertDialog(mActivity, "", "Detail is not found");
                }

                //Adding Dynamic View In a Linear layout
                for (int i = 0; i < mArray.size(); i++) {
                    InformationModel mDetailModel = mArray.get(i);
                    LayoutInflater layoutInflater =
                            (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = layoutInflater.inflate(R.layout.item_infromation_detail, null);
                    TextView nameTV = addView.findViewById(R.id.nameTV);
                    TextView priceTV = addView.findViewById(R.id.priceTV);
                    LinearLayout priceLL = addView.findViewById(R.id.priceLL);

                    priceLL.setVisibility(View.VISIBLE);
                    if (i % 2 == 0) {
                        priceLL.setBackgroundResource(R.color.gray_bg);
                    } else {
                        priceLL.setBackgroundResource(R.color.white);
                    }
                    if (!mDetailModel.getSizeName().equals("")) {

                        nameTV.setText(mDetailModel.getSizeName());
                    } else {

                        nameTV.setText("N/A");
                    }
                    if (!mDetailModel.getPrice().equals("")) {
                        if (mDetailModel.getPrice().contains(".")) {

                            builder.append(mDetailModel.getSizeName() + "      " + mDetailModel.getPrice() + "\n");

                            priceTV.setText(mDetailModel.getPrice());
                        } else {
                            builder.append(mDetailModel.getSizeName() + "      " + "$ " + mDetailModel.getPrice() + ".00" + "\n");

                            priceTV.setText("$ " + mDetailModel.getPrice() + ".00");
                        }

                    } else {
                        builder.append("N/A" + "\n");
                        priceTV.setText("N/A");
                    }

                    dataLL.addView(addView);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.InformationBackClick = true;
        Intent mIntent = new Intent(mActivity, SaleProductInformationActivity.class);
        startActivity(mIntent);
        finish();
        overridePendingTransitionExit();

    }

    public void showAlertDialog(final Activity mActivity, String strTitle, String strMessage) {
        final Dialog alertDialog = new Dialog(mActivity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_showalert);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView txtTitle = (TextView) alertDialog.findViewById(R.id.txtTitle);
        TextView txtMessage = (TextView) alertDialog.findViewById(R.id.txtMessage);
        TextView txtDismiss = (TextView) alertDialog.findViewById(R.id.txtDismiss);


        txtTitle.setText(strTitle);
        txtMessage.setText(strMessage);
        txtDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Constants.ReorderBackClick = true;
                Intent mIntent = new Intent(mActivity, SaleProductInformationActivity.class);
                mActivity.startActivity(mIntent);
                mActivity.finish();

            }
        });

        alertDialog.show();
    }
}
