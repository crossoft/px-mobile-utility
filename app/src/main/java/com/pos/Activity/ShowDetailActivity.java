package com.pos.Activity;

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
import com.pos.Models.RecoderModel;
import com.pos.PostMobileBaseActivity;
import com.pos.R;
import com.pos.Util.Constants;
import com.pos.Util.Utilities;

public class ShowDetailActivity extends PostMobileBaseActivity {
    Activity mActivity = ShowDetailActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView searchAgainTv, cloaseAgainTV, productNameTV, calculatedQtyNameTV, vendorNameTV, minNameTV, maxNameTV, reorderNameTV, instrictionNameTV, vendorSkuNameTV, primaryZoneNameTV, primaryBinNameTV, secondaryZoneNameTV, secondaryBinNameTV;
    public LinearLayout vendorLL, reorderQtyLL, maxLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    RecoderModel mRecorderModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
    }

    @Override
    protected void setUpEvents() {
        shareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");


                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Product Information" + "\n" + "Name" + "   " + productNameTV.getText().toString() + "\n" + "Calculated Qty" + "     " + calculatedQtyNameTV.getText().toString() + "\n" + "Vendor Name" + "     " + vendorNameTV.getText().toString() + "\n" + "Min" + "     " + minNameTV.getText().toString() + "\n" + "Max" + "     " + maxNameTV.getText().toString() + "\n" + "Reorder State" + "     " + reorderNameTV.getText().toString() + "\n" + "Instructions" + "     " + instrictionNameTV.getText().toString() + "\n" + "VendorSku" + "     " + vendorSkuNameTV.getText().toString() + "\n" + "Primary Zone" + "     " + primaryZoneNameTV.getText().toString() + "\n" + "Primary Bin" + "     " + primaryBinNameTV.getText().toString() + "\n" + "Secondary Zone" + "     " + secondaryZoneNameTV.getText().toString() + "\n" + "Secondary Bin" + "     " + secondaryBinNameTV.getText().toString());
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
                Constants.strSearchText = "";
                Intent mIntent = new Intent(mActivity, CurrentQuantityInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionExit();
            }
        });
        cloaseAgainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.strSearchText = "";
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
        productNameTV = findViewById(R.id.productNameTV);
        calculatedQtyNameTV = findViewById(R.id.calculatedQtyNameTV);
        vendorNameTV = findViewById(R.id.vendorNameTV);
        minNameTV = findViewById(R.id.minNameTV);
        maxNameTV = findViewById(R.id.maxNameTV);
        reorderNameTV = findViewById(R.id.reorderNameTV);
        instrictionNameTV = findViewById(R.id.instrictionNameTV);
        vendorSkuNameTV = findViewById(R.id.vendorSkuNameTV);
        primaryZoneNameTV = findViewById(R.id.primaryZoneNameTV);
        primaryBinNameTV = findViewById(R.id.primaryBinNameTV);
        secondaryZoneNameTV = findViewById(R.id.secondaryZoneNameTV);
        secondaryBinNameTV = findViewById(R.id.secondaryBinNameTV);
        searchAgainTv = findViewById(R.id.searchAgainTv);
        cloaseAgainTV = findViewById(R.id.cloaseAgainTV);

        tittleTV.setText(Constants.Tittle);
        if (!Utilities.isNetworkAvailable(mActivity)) {
            showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {
            Bundle args = getIntent().getExtras();
            if (args != null && args.containsKey("CurrentQTY")) {
                mRecorderModel = (RecoderModel) args.getSerializable("CurrentQTY");
                if (mRecorderModel.getName().equals("")) {
                    productNameTV.setText("N/A");
                } else {
                    productNameTV.setText(mRecorderModel.getName());
                }

                if (mRecorderModel.getCalculatedQty().equals("")) {
                    calculatedQtyNameTV.setText("N/A");

                } else {
                    calculatedQtyNameTV.setText(mRecorderModel.getCalculatedQty());
                }
                if (mRecorderModel.getVendorName().equals("")) {
                    vendorNameTV.setText("N/A");

                } else {
                    vendorNameTV.setText(mRecorderModel.getVendorName());
                }
                if (mRecorderModel.getMinLevel().equals("")) {
                    minNameTV.setText("N/A");

                } else {
                    minNameTV.setText(mRecorderModel.getMinLevel());
                }
                if (mRecorderModel.getMaxLevel().equals("")) {
                    maxNameTV.setText("N/A");

                } else {
                    maxNameTV.setText(mRecorderModel.getMaxLevel());
                }
                if (mRecorderModel.getReorderState().equals("")) {
                    reorderNameTV.setText("N/A");

                } else {
                    reorderNameTV.setText(mRecorderModel.getReorderState());
                }
                if (mRecorderModel.getReceivingInstructions().equals("")) {
                    instrictionNameTV.setText("N/A");

                } else {
                    instrictionNameTV.setText(mRecorderModel.getReceivingInstructions());
                }
                if (mRecorderModel.getVendorSku().equals("")) {
                    vendorSkuNameTV.setText("N/A");

                } else {
                    vendorSkuNameTV.setText(mRecorderModel.getVendorSku());
                }
                if (mRecorderModel.getPrimaryZone().equals("")) {
                    primaryZoneNameTV.setText("N/A");

                } else {
                    primaryZoneNameTV.setText(mRecorderModel.getPrimaryZone());
                }
                if (mRecorderModel.getPrimaryBin().equals("")) {
                    primaryBinNameTV.setText("N/A");

                } else {
                    primaryBinNameTV.setText(mRecorderModel.getPrimaryBin());
                }
                if (mRecorderModel.getSecondaryZone().equals("")) {
                    secondaryZoneNameTV.setText("N/A");

                } else {
                    secondaryZoneNameTV.setText(mRecorderModel.getSecondaryZone());
                }
                if (mRecorderModel.getSecondaryBin().equals("")) {
                    secondaryBinNameTV.setText("N/A");

                } else {
                    secondaryBinNameTV.setText(mRecorderModel.getSecondaryBin());
                }

            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.SetUpBackCurrentQuanity = true;

        Intent mIntent = new Intent(mActivity, CurrentQuantityInventoryActivity.class);
        startActivity(mIntent);
        finish();
        overridePendingTransitionExit();
    }

    public void showAlertDialog(final Activity mActivity, String strTitle, String strMessage) {
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
                Intent mIntent = new Intent(mActivity, CurrentQuantityInventoryActivity.class);
                mActivity.startActivity(mIntent);
                mActivity.finish();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
