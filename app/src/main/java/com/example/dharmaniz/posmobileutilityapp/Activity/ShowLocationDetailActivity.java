package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dharmaniz.posmobileutilityapp.Models.LocationModel;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.AlertDialogManager;
import com.example.dharmaniz.posmobileutilityapp.Util.Constants;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;
import com.example.dharmaniz.posmobileutilityapp.Util.Utilities;

public class ShowLocationDetailActivity extends PostMobileBaseActivity {
    Activity mActivity = ShowLocationDetailActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView searchAgainTv,cloaseAgainTV,secondaryBinNameTV, secondaryZoneNameTV,primaryBINNameTV,calculatedQtyNameTV, productNameTV,minNameTV,nameTv, maxNameTV, vendorNameTV,calculatedQtyTv;
    public LinearLayout vendorLL, reorderQtyLL,maxLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    LocationModel mRecorderModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location_detail);
    }
    @Override
    protected void setUpEvents() {
        shareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");


                }else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Product Information" + "\n" + "Name" + "   " + productNameTV.getText().toString() + "\n" + "Primary Zone" + "     " + calculatedQtyNameTV.getText().toString() + "\n" + "Primary Bin" + "     " + primaryBINNameTV.getText().toString() + "\n" + "Secondary Zone" + "     " + secondaryZoneNameTV.getText().toString() + "\n" + "Secondary Bin" + "     " + secondaryBinNameTV.getText().toString());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
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
                Intent mIntent = new Intent(mActivity, locationInventoryActivity.class);
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
    @Override
    protected void setUpViews() {
        shareRL = findViewById(R.id.shareRL);
        backRL = findViewById(R.id.backRL);
        tittleTV = findViewById(R.id.tittleTV);
        secondaryBinNameTV = findViewById(R.id.secondaryBinNameTV);
        secondaryZoneNameTV = findViewById(R.id.secondaryZoneNameTV);
        primaryBINNameTV = findViewById(R.id.primaryBINNameTV);
        calculatedQtyNameTV = findViewById(R.id.calculatedQtyNameTV);
        productNameTV = findViewById(R.id.productNameTV);
        searchAgainTv = findViewById(R.id.searchAgainTv);
        cloaseAgainTV = findViewById(R.id.cloaseAgainTV);
        tittleTV.setText(Constants.Tittle);
        if(!Utilities.isNetworkAvailable(mActivity)){
            showAlertDialog(mActivity,"","No Internet Connection, Please connect the valid internet Connection");
        }else {

            // Fetching data From Bundle
            Bundle args = getIntent().getExtras();
            if (args != null && args.containsKey("CurrentQTY")) {
                mRecorderModel = (LocationModel) args.getSerializable("CurrentQTY");
                if (mRecorderModel.getSecondaryBin().equals("")) {
                    secondaryBinNameTV.setText("N/A");
                } else {
                    secondaryBinNameTV.setText(mRecorderModel.getSecondaryBin());
                }

                if (mRecorderModel.getSecondaryZone().equals("")) {
                    secondaryZoneNameTV.setText("N/A");

                } else {
                    secondaryZoneNameTV.setText(mRecorderModel.getSecondaryZone());
                }
                if (mRecorderModel.getPrimaryBin().equals("")) {
                    primaryBINNameTV.setText("N/A");

                } else {
                    primaryBINNameTV.setText(mRecorderModel.getPrimaryBin());
                }
                if (mRecorderModel.getPrimaryZone().equals("")) {
                    calculatedQtyNameTV.setText("N/A");

                } else {
                    calculatedQtyNameTV.setText(mRecorderModel.getPrimaryZone());
                }
                if (mRecorderModel.getName().equals("")) {
                    productNameTV.setText("N/A");

                } else {
                    productNameTV.setText(mRecorderModel.getName());
                }


            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.LocationBackClick=true;
        Intent mIntent = new Intent(mActivity, locationInventoryActivity.class);
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
                Intent mIntent = new Intent(mActivity, locationInventoryActivity.class);
                mActivity.startActivity(mIntent);
                mActivity.finish();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
