package com.pos.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pos.PostMobileBaseActivity;
import com.pos.R;
import com.pos.Util.Constants;


public class InventoryHomeActivity extends PostMobileBaseActivity {
    Activity mActivity = InventoryHomeActivity.this;
    private TextView backTV;
    RelativeLayout backRL, setUpTV, locationTV, instructionsTV, reorderTV, inventoryquantityTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_home);
    }

    @Override
    protected void setUpEvents() {


        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SetUpcleck = true;
                Intent mIntent = new Intent(mActivity, SetUpIneventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        reorderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.recorderListClick = true;
                Intent mIntent = new Intent(mActivity, RecoderInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();

            }
        });
        instructionsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.InstructionClick = true;
                Intent mIntent = new Intent(mActivity, InstructionInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        locationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.locationClick = true;
                Intent mIntent = new Intent(mActivity, locationInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        inventoryquantityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SetUpCurrentQuanity = true;
                Intent mIntent = new Intent(mActivity, CurrentQuantityInventoryActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();

            }
        });
    }

    @Override
    protected void setUpViews() {
        reorderTV = findViewById(R.id.reorderTV);
        instructionsTV = findViewById(R.id.instructionsTV);
        setUpTV = findViewById(R.id.setUpTV);
        inventoryquantityTV = findViewById(R.id.inventoryquantityTV);
        locationTV = findViewById(R.id.locationTV);
        backRL = (RelativeLayout) findViewById(R.id.backRL);
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
