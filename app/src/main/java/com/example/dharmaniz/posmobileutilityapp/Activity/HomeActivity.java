package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dharmaniz.posmobileutilityapp.PostMobileSharedPreference.NewAppSharedPreference;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.PostMobileBaseActivity;

public class HomeActivity extends PostMobileBaseActivity {
    Activity mActivity =HomeActivity.this;
private TextView metricsTV,time_keepingTV,inventoryTV,sales_productsTV,tittleTV;
private RelativeLayout logoutRL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void setUpViews() {
        logoutRL =findViewById(R.id.logoutRL);
        time_keepingTV =findViewById(R.id.time_keepingTV);
        sales_productsTV =findViewById(R.id.sales_productsTV);
        inventoryTV =findViewById(R.id.inventoryTV);
        metricsTV =findViewById(R.id.metricsTV);
        tittleTV =findViewById(R.id.tittleTV);

        tittleTV.setText("Home");

    }

    @Override
    protected void setUpEvents() {
        logoutRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handling menu popUp on Custom Button
                PopupMenu popup = new PopupMenu(mActivity,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout_btn:
                                //your code
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransitionExit();
                                SharedPreferences preferences = NewAppSharedPreference.getPreferences(mActivity);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                // EX : call intent if you want to swich to other activity
                                return true;



                        }
                        return false;
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.option_logout, popup.getMenu());
                popup.show();

            }
        });
        inventoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mActivity, InventoryHomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();


            }
        });
        metricsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mActivity, MetricsActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        time_keepingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mActivity, TimeKeeping.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
        sales_productsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mActivity, SaleProductHomeActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransitionEnter();
            }
        });
    }
}
