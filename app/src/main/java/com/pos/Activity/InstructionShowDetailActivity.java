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

import com.pos.Models.InstructionModel;
import com.pos.PostMobileBaseActivity;
import com.pos.R;
import com.pos.Util.AlertDialogManager;
import com.pos.Util.Constants;
import com.pos.Util.Utilities;


public class InstructionShowDetailActivity extends PostMobileBaseActivity {
    Activity mActivity = InstructionShowDetailActivity.this;
    private RelativeLayout shareRL, backRL;
    private TextView tittleTV;
    public TextView searchAgainTv, cloaseAgainTV, productNameTV, calculatedQtyNameTV, reorderQtyTv, reorderQtyNameTV, minTv, minNameTV, nameTv, maxNameTV, vendorNameTV, calculatedQtyTv;
    public LinearLayout vendorLL, reorderQtyLL, maxLL;
    public RelativeLayout arrowNextRL, layoutItemRL;
    InstructionModel mRecorderModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_show_detail);
    }

    @Override
    protected void setUpEvents() {
        shareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utilities.isNetworkAvailable(mActivity)) {
                    AlertDialogManager.showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");


                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Product Information" + "\n" + "Name" + "   " + productNameTV.getText().toString() + "\n" + "Instructions" + "     " + calculatedQtyNameTV.getText().toString());
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
                Intent mIntent = new Intent(mActivity, InstructionInventoryActivity.class);
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
        reorderQtyNameTV = findViewById(R.id.reorderQtyNameTV);
        minNameTV = findViewById(R.id.minNameTV);
        maxNameTV = findViewById(R.id.maxNameTV);
        vendorNameTV = findViewById(R.id.vendorNameTV);
        vendorLL = findViewById(R.id.vendorLL);
        reorderQtyLL = findViewById(R.id.reorderQtyLL);
        arrowNextRL = findViewById(R.id.arrowNextRL);
        layoutItemRL = findViewById(R.id.layoutItemRL);
        calculatedQtyTv = findViewById(R.id.calculatedQtyTv);
        reorderQtyTv = findViewById(R.id.reorderQtyTv);
        minTv = findViewById(R.id.minTv);
        maxLL = findViewById(R.id.maxLL);
        nameTv = findViewById(R.id.nameTv);
        calculatedQtyTv = findViewById(R.id.calculatedQtyTv);
        searchAgainTv = findViewById(R.id.searchAgainTv);
        cloaseAgainTV = findViewById(R.id.cloaseAgainTV);
        tittleTV.setText(Constants.Tittle);
        ;
        calculatedQtyTv.setText("Instructions:");
        if (!Utilities.isNetworkAvailable(mActivity)) {
            showAlertDialog(mActivity, "", "No Internet Connection, Please connect the valid internet Connection");
        } else {

            // Fetching data From Bundle
            Bundle args = getIntent().getExtras();
            if (args != null && args.containsKey("CurrentQTY")) {
                mRecorderModel = (InstructionModel) args.getSerializable("CurrentQTY");
                if (mRecorderModel.getName().equals("")) {
                    productNameTV.setText("N/A");
                } else {
                    productNameTV.setText(mRecorderModel.getName());
                }

                if (mRecorderModel.getReceivingInstructions().equals("")) {
                    calculatedQtyNameTV.setText("N/A");

                } else {
                    calculatedQtyNameTV.setText(mRecorderModel.getReceivingInstructions());
                }


            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.InsTructionBackClick = true;
        Intent mIntent = new Intent(mActivity, InstructionInventoryActivity.class);
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
