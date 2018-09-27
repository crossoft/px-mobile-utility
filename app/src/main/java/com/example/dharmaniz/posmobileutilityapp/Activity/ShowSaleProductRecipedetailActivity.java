package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.dharmaniz.posmobileutilityapp.Models.SaleProductReciepeModel;
import com.example.dharmaniz.posmobileutilityapp.Models.ShowRecipeDetailModel;
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

public class ShowSaleProductRecipedetailActivity extends PostMobileBaseActivity {
    Activity mActivity = ShowSaleProductRecipedetailActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView sizenameTV, tittleTVq, searchAgainTv, smallTV,babyTV,meduimTV,largeTV,cloaseAgainTV, productNameTV, calculatedQtyNameTV, reorderQtyNameTV, minNameTV, maxNameTV, vendorNameTV;
    public LinearLayout vendorLL, reorderQtyLL, babyLL,smallLL,meduimLL,largeLL,mainLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    SaleProductReciepeModel mRecorderModel;
    static String strNameBaby = "";
    static String strNameSmall = "";
    static String strNameMedium = "";
    static String strNameLarge = "";
    ArrayList<ShowRecipeDetailModel> mArray = new ArrayList<ShowRecipeDetailModel>();
    ArrayList<String> mArrayBaby = new ArrayList<String>();
    ArrayList<String> mArraySmall = new ArrayList<String>();
    ArrayList<String> mArrayMedium = new ArrayList<String>();
    ArrayList<String> mArrayLarge = new ArrayList<String>();
    StringBuilder builder = new StringBuilder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sale_product_recipedetail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArray.clear();
        builder.setLength(0);
        mArrayBaby.clear();
        mArraySmall.clear();
        mArrayMedium.clear();
        mArrayLarge.clear();
        babyLL.removeAllViews();
        meduimLL.removeAllViews();
        largeLL.removeAllViews();
        smallLL.removeAllViews();
        if (!Utilities.isNetworkAvailable(mActivity)) {
           showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {
            getInventoryData();
        }
    }

    @Override
    protected void setUpEvents() {
        shareRL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                Constants.strSearchText = "";
                Intent mIntent = new Intent(mActivity, SaleproductReceipeActivity.class);
                startActivity(mIntent);
                finish();
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
//        sizenameTV = findViewById(R.id.sizenameTV);
        babyLL = findViewById(R.id.babyLL);
        tittleTVq = findViewById(R.id.tittleTVq);
        smallTV = findViewById(R.id.smallTV);
        babyTV = findViewById(R.id.babyTV);
        meduimTV = findViewById(R.id.meduimTV);
        largeTV = findViewById(R.id.largeTV);
        smallLL = findViewById(R.id.smallLL);
        meduimLL = findViewById(R.id.meduimLL);
        largeLL = findViewById(R.id.largeLL);
        mainLL = findViewById(R.id.mainLL);

        tittleTVq.setText(Constants.Tittle);

    }

    //
    private void getInventoryData() {

        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/GetRecipeDataByProductId.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));
            jsonObject.put("salesProductId", Constants.Product_ID);
//            jsonObject.put("salesProductId", "100");


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

    public void parsseresponce(JSONObject response) {
        Utilities.hideProgressDialog();

        mArrayBaby.clear();
        mArraySmall.clear();
        mArrayMedium.clear();
        mArrayLarge.clear();
        babyLL.removeAllViews();
        meduimLL.removeAllViews();
        largeLL.removeAllViews();
        smallLL.removeAllViews();
        try {
            if (!response.getString("status").equals("")) {
                JSONArray mJsonArray = response.getJSONArray("data");

if(!response.isNull("data")){

    for (int i = 0; i < mJsonArray.length(); i++) {
                    ShowRecipeDetailModel mDetailModel = new ShowRecipeDetailModel();

                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);

                    if (!jsonDATAObject.getString("sizeName").equals("")) {

                        mDetailModel.setSizeName(jsonDATAObject.getString("sizeName"));
                        if (jsonDATAObject.getString("sizeName").equals("Baby")) {
                            strNameBaby = "Baby";
                            mArrayBaby.add(jsonDATAObject.getString("instructionLine"));
                        } else if (jsonDATAObject.getString("sizeName").equals("Small/Default")) {
                            strNameSmall = "Small/Default";
                            mArraySmall.add(jsonDATAObject.getString("instructionLine"));
                        } else if (jsonDATAObject.getString("sizeName").equals("Medium")) {
                            strNameMedium = "Medium";
                            mArrayMedium.add(jsonDATAObject.getString("instructionLine"));
                        } else if (jsonDATAObject.getString("sizeName").equals("Large")) {
                            strNameLarge = "Large";
                            mArrayLarge.add(jsonDATAObject.getString("instructionLine"));
                        }
                    }

                    mArray.add(mDetailModel);
                }
}
else {
    showAlertDialog(mActivity,"","Detail is not found");
}
 // Adding View According to Serial order Like  1) Baby 2) Small/default 3) Medium 4) Large

                if(!strNameBaby.equals("")){
                    if(mArrayBaby.size()!=0) {
                        builder.append(strNameBaby + "\n");
                        babyTV.setText(strNameBaby);
                        for (int i = 0; i < mArrayBaby.size(); i++) {


                            LayoutInflater layoutInflater =
                                    (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.item_recepie_data, null);
                            TextView dataTV = addView.findViewById(R.id.dataTV);
                            TextView inventoryNameTV = addView.findViewById(R.id.inventoryNameTV);
                            TextView qtyTV = addView.findViewById(R.id.qtyTV);
                            TextView unitNameTV = addView.findViewById(R.id.unitNameTV);
                            TextView instructionsTV = addView.findViewById(R.id.instructionsTV);
                            qtyTV.setVisibility(View.GONE);
                            unitNameTV.setVisibility(View.GONE);
                            instructionsTV.setVisibility(View.GONE);
                            dataTV.setVisibility(View.GONE);

                            if (!mArrayBaby.get(i).equals("")) {

                                builder.append(mArrayBaby.get(i) + "\n");
                                inventoryNameTV.setText(mArrayBaby.get(i));
                            } else {
                                builder.append("N/A" + "\n");
                                inventoryNameTV.setText("N/A");
                            }

                            if (i % 2 == 0) {
                                inventoryNameTV.setBackgroundResource(R.color.gray_Blue);
                            } else {
                                inventoryNameTV.setBackgroundResource(R.color.white);
                            }

                            babyLL.addView(addView);
                        }
                    }else {
                        babyTV.setVisibility(View.GONE);
                    }
                }else {
                    babyTV.setVisibility(View.GONE);
                    babyLL.setVisibility(View.GONE);
                }


                if(!strNameSmall.equals("")){
                    if(mArraySmall.size()!=0) {
                        builder.append(strNameSmall + "\n");
                        smallTV.setText(strNameSmall);
                        for (int i = 0; i < mArraySmall.size(); i++) {


                            LayoutInflater layoutInflater =
                                    (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.item_recepie_data, null);
                            TextView dataTV = addView.findViewById(R.id.dataTV);
                            TextView inventoryNameTV = addView.findViewById(R.id.inventoryNameTV);
                            TextView qtyTV = addView.findViewById(R.id.qtyTV);
                            TextView unitNameTV = addView.findViewById(R.id.unitNameTV);
                            TextView instructionsTV = addView.findViewById(R.id.instructionsTV);
                            qtyTV.setVisibility(View.GONE);
                            unitNameTV.setVisibility(View.GONE);
                            instructionsTV.setVisibility(View.GONE);
                            dataTV.setVisibility(View.GONE);
                            if (!mArraySmall.get(i).equals("")) {
                                builder.append(mArraySmall.get(i) + "\n");
                                inventoryNameTV.setText(mArraySmall.get(i));
                            } else {
                                builder.append("N/A" + "\n");
                                inventoryNameTV.setText("N/A");
                            }

                            if (i % 2 == 0) {
                                inventoryNameTV.setBackgroundResource(R.color.gray_Blue);
                            } else {
                                inventoryNameTV.setBackgroundResource(R.color.white);
                            }
                            smallLL.addView(addView);
                        }
                    }else {
                        smallTV.setVisibility(View.GONE);
                    }

                }else {
                    smallTV.setVisibility(View.GONE);
                    smallLL.setVisibility(View.GONE);
                }

                if(!strNameMedium.equals("")){

                    if(mArrayMedium.size()!=0){
                        meduimTV.setText(strNameMedium);
                        builder.append(strNameMedium+ "\n");

                        for (int i = 0; i < mArrayMedium.size(); i++) {


                            LayoutInflater layoutInflater =
                                    (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.item_recepie_data, null);
                            TextView dataTV = addView.findViewById(R.id.dataTV);
                            TextView inventoryNameTV = addView.findViewById(R.id.inventoryNameTV);
                            TextView qtyTV = addView.findViewById(R.id.qtyTV);
                            TextView unitNameTV = addView.findViewById(R.id.unitNameTV);
                            TextView instructionsTV = addView.findViewById(R.id.instructionsTV);
                            qtyTV.setVisibility(View.GONE);
                            unitNameTV.setVisibility(View.GONE);
                            instructionsTV.setVisibility(View.GONE);
                            dataTV.setVisibility(View.GONE);

                            if (!mArrayMedium.get(i).equals("")) {


                                builder.append(mArrayMedium.get(i) + "\n");
                                inventoryNameTV.setText(mArrayMedium.get(i));
                            } else {
                                builder.append("N/A" + "\n");
                                inventoryNameTV.setText("N/A");
                            }

                            if (i % 2 == 0) {
                                inventoryNameTV.setBackgroundResource(R.color.gray_Blue);
                            } else {
                                inventoryNameTV.setBackgroundResource(R.color.white);
                            }
                            meduimLL.addView(addView);
                        }
                    }else {
                        meduimTV.setVisibility(View.GONE);
                    }

                }else {
                    meduimTV.setVisibility(View.GONE);
                    meduimLL.setVisibility(View.GONE);
                }

                if(!strNameLarge.equals("")){

                    if(mArrayLarge.size()!=0) {
                        largeTV.setText(strNameLarge);
                        builder.append(strNameLarge+ "\n");
                        for (int i = 0; i < mArrayLarge.size(); i++) {
                            LayoutInflater layoutInflater =
                                    (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.item_recepie_data, null);
                            TextView dataTV = addView.findViewById(R.id.dataTV);
                            TextView inventoryNameTV = addView.findViewById(R.id.inventoryNameTV);
                            TextView qtyTV = addView.findViewById(R.id.qtyTV);
                            TextView unitNameTV = addView.findViewById(R.id.unitNameTV);
                            TextView instructionsTV = addView.findViewById(R.id.instructionsTV);
                            qtyTV.setVisibility(View.GONE);
                            unitNameTV.setVisibility(View.GONE);
                            instructionsTV.setVisibility(View.GONE);
                            dataTV.setVisibility(View.GONE);
                            if (!mArrayLarge.get(i).equals("")) {



                                builder.append(mArrayLarge.get(i)+ "\n");
                                inventoryNameTV.setText(mArrayLarge.get(i));

                            } else {
                                builder.append("N/A" + "\n");
                                inventoryNameTV.setText("N/A");
                            }
                            if (i % 2 == 0) {
                                inventoryNameTV.setBackgroundResource(R.color.gray_Blue);
                            } else {
                                inventoryNameTV.setBackgroundResource(R.color.white);
                            }
                            largeLL.addView(addView);
                        }
                    }else {
                        largeTV.setVisibility(View.GONE);
                    }

                }else {
                    largeTV.setVisibility(View.GONE);
                    largeLL.setVisibility(View.GONE);
                }

//                sizenameTV.setText(strName);
                String strSizeName = "";


                mainLL.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.RecipeBackClick = true;
        Intent mIntent = new Intent(mActivity, SaleproductReceipeActivity.class);
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
                Intent mIntent = new Intent(mActivity, SaleproductReceipeActivity.class);
                mActivity.startActivity(mIntent);
                mActivity.finish();

            }
        });

        alertDialog.show();
    }
}
