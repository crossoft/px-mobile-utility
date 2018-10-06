package com.pos.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.pos.PostMobileBaseActivity;
import com.pos.R;
import com.pos.Util.Constants;


public class SaleProductHomeActivity extends PostMobileBaseActivity {
    Activity mActivity = SaleProductHomeActivity.this;
    private RelativeLayout backRL, informationTV, recipeTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_product_home);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        informationTV = findViewById(R.id.informationTV);
        recipeTV = findViewById(R.id.recipeTV);
    }

    @Override
    protected void setUpEvents() {
        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        informationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(mActivity, SaleProductInformationActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        recipeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.recipeClick = true;
                Intent mIntent = new Intent(mActivity, SaleproductReceipeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
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
