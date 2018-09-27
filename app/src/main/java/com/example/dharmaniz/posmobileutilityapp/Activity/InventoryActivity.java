package com.example.dharmaniz.posmobileutilityapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dharmaniz.posmobileutilityapp.Adapter.InventoryAdapter;
import com.example.dharmaniz.posmobileutilityapp.Models.InventoryModel;
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

public class InventoryActivity extends PostMobileBaseActivity {
    Activity mActivity = InventoryActivity.this;
    private RecyclerView innventoryRV;
    InventoryAdapter mAdapter;
    RelativeLayout backRL;
    RelativeLayout searchRL;
    LinearLayout micLL,mainNormalLL;
    EditText editSearchET;
    SwipeRefreshLayout swipeToRefresh;
    boolean isSwipeRefresh = false;
    ArrayList<InventoryModel> mInventorArray = new ArrayList<InventoryModel>();
    ArrayList<InventoryModel> filteredList = new ArrayList<InventoryModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
    }

    @Override
    protected void setUpViews() {
        backRL = findViewById(R.id.backRL);
        innventoryRV = findViewById(R.id.innventoryRV);
        micLL = findViewById(R.id.micLL);
        searchRL = findViewById(R.id.searchRL);
        editSearchET = findViewById(R.id.editSearchET);
        mainNormalLL = findViewById(R.id.mainNormalLL);
        mainNormalLL.setVisibility(View.VISIBLE);

    }



    @Override
    protected void onResume() {
        super.onResume();
        mInventorArray.clear();
        editSearchET.setText("");

    }

    @Override
    protected void setUpEvents() {
        backRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        micLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ExcecuteSerachAPI();
            }
        });
        searchRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    showMenu(view);

            }
        });


        editSearchET .setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    ExcecuteSerachAPI();
                }
                return false;
            }
        });
    }
    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(mActivity,v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.vender_name:
                        //your code
                        Constants.strSearchName = "Vender";

                        editSearchET.setHint("Search by vender");

                        // EX : call intent if you want to swich to other activity
                        return true;
                    case R.id.name:
                        Constants.strSearchName = "Name";
                        mainNormalLL.setVisibility(View.VISIBLE);
                        editSearchET.setHint("Product search");

                        //your code
                        return true;


                }
                return false;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.show();
    }


    private void ExcecuteSerachAPI() {
        Utilities.showProgressDialog(mActivity);
        String url = "http://dharmani.com/jspro2/SearchInventoryProducts.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accountId", NewAppSharedPreference.readString(mActivity, NewAppSharedPreference.Account_ID, ""));

              jsonObject.put("Name", editSearchET.getText().toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utilities.hideProgressDialog();
                        Log.e("INVENTORY DATA", response.toString());
                        try {
                            if (response.getString("status").equals("1")) {
                                mInventorArray.clear();

                                parsseresponce(response);
                            }
                            else if(response.getString("status").equals("0")){
                                AlertDialogManager.showAlertDialog(mActivity,"Error",response.getString("message"));
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
        PostMobileUtilityApplication.getInstance().addToRequestQueue(jsonObjReq, "ExcecuteSerachAPI");

    }

    private void parsseresponce(JSONObject responce) {
        try {
            if (!responce.getString("status").equals("")) {
                JSONArray mJsonArray = responce.getJSONArray("data");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonDATAObject = mJsonArray.getJSONObject(i);
                    InventoryModel mInventoryModel = new InventoryModel();
                    if (!jsonDATAObject.getString("accountId").equals("")) {
                        mInventoryModel.setAccountId(jsonDATAObject.getString("accountId"));
                    }
                    if (!jsonDATAObject.getString("inventoryProductId").equals("")) {
                        mInventoryModel.setInventoryProductId(jsonDATAObject.getString("inventoryProductId"));
                    }
                    if (!jsonDATAObject.getString("name").equals("")) {
                        mInventoryModel.setName(jsonDATAObject.getString("name"));
                    }
                    if (!jsonDATAObject.getString("calculatedQty").equals("")) {
                        mInventoryModel.setCalculatedQty(jsonDATAObject.getString("calculatedQty"));
                    }
                    if (!jsonDATAObject.getString("vendorName").equals("")) {
                        mInventoryModel.setVendorName(jsonDATAObject.getString("vendorName"));

                    }
                    if (!jsonDATAObject.getString("minLevel").equals("")) {
                        mInventoryModel.setMinLevel(jsonDATAObject.getString("minLevel"));
                    }
                    if (!jsonDATAObject.getString("maxLevel").equals("")) {
                        mInventoryModel.setMaxLevel(jsonDATAObject.getString("maxLevel"));
                    }
                    if (!jsonDATAObject.getString("reorderState").equals("")) {
                        mInventoryModel.setReorderState(jsonDATAObject.getString("reorderState"));

                    }
                    if (!jsonDATAObject.getString("receivingInstructions").equals("")) {
                        mInventoryModel.setReceivingInstructions(jsonDATAObject.getString("receivingInstructions"));
                    }
                    if (!jsonDATAObject.getString("ingredientInstructions").equals("")) {
                        mInventoryModel.setIngredientInstructions(jsonDATAObject.getString("ingredientInstructions"));
                    }
                    if (!jsonDATAObject.getString("vendorSku").equals("")) {
                        mInventoryModel.setVendorSku(jsonDATAObject.getString("vendorSku"));
                    }
                    if (!jsonDATAObject.getString("maxDaysBetweenLogging").equals("")) {
                        mInventoryModel.setMaxDaysBetweenLogging(jsonDATAObject.getString("maxDaysBetweenLogging"));
                    }
                    if (!jsonDATAObject.getString("logDueDateOverride").equals("")) {
                        mInventoryModel.setLogDueDateOverride(jsonDATAObject.getString("logDueDateOverride"));
                    }
                    if (!jsonDATAObject.getString("primaryZone").equals("")) {
                        mInventoryModel.setPrimaryZone(jsonDATAObject.getString("primaryZone"));
                    }
                    if (!jsonDATAObject.getString("primaryBin").equals("")) {
                        mInventoryModel.setPrimaryBin(jsonDATAObject.getString("primaryBin"));
                    }
                    if (!jsonDATAObject.getString("secondaryZone").equals("")) {
                        mInventoryModel.setSecondaryZone(jsonDATAObject.getString("secondaryZone"));
                    }
                    if (!jsonDATAObject.getString("secondaryBin").equals("")) {
                        mInventoryModel.setSecondaryBin(jsonDATAObject.getString("secondaryBin"));
                    }
                    mInventorArray.add(mInventoryModel);
                    setAdapter();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {

        mAdapter = new InventoryAdapter(mActivity, mInventorArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        innventoryRV.setLayoutManager(mLayoutManager);
        innventoryRV.setItemAnimator(new DefaultItemAnimator());
        innventoryRV.setAdapter(mAdapter);
        Utilities.hideProgressDialog();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
