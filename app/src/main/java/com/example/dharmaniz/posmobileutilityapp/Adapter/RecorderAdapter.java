package com.example.dharmaniz.posmobileutilityapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dharmaniz.posmobileutilityapp.Activity.InventoryHomeActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.ShowLocationDetailActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.ShowRecorderActivity;
import com.example.dharmaniz.posmobileutilityapp.Models.InventoryModel;
import com.example.dharmaniz.posmobileutilityapp.Models.RecoderModel;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.Constants;

import java.util.ArrayList;

/**
 * Created by dharmaniz on 4/9/18.
 */

public class RecorderAdapter extends RecyclerView.Adapter<RecorderAdapter.ViewWorkHolder> {

    private static final String TAG = "ActivityAdapter";
    private ArrayList<RecoderModel> modelArrayList;
    private Activity context;

    public RecorderAdapter(Activity context, ArrayList<RecoderModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public RecorderAdapter.ViewWorkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show_name, parent, false);
        return new RecorderAdapter.ViewWorkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecorderAdapter.ViewWorkHolder holder, int position) {
        final RecoderModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {
//
            Log.e("Possition","Possition"+"1"+position);

// If  Search Result is One Than automaticaly Open detail screen

            if(Constants.ReorderBackClick==true){
                Constants.ReorderBackClick=false;
                if(tempValue.getName().equals("")){
                }else {
                    holder.nameTv.setText(tempValue.getVendorName());
                }
            }
            else {
                if(modelArrayList.size()==1){
                    holder.arrowNextRL.setVisibility(View.INVISIBLE);
                    Constants.Tittle=tempValue.getVendorName();
                    Constants.Product_ID=tempValue.getVendorName();
                    Intent mIntent = new Intent(context,ShowRecorderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();
                }else {
                    if (tempValue.getVendorName().equals("")) {
                    } else {
                        holder.nameTv.setText(tempValue.getVendorName());
                    }
                }
            }
//            if(tempValue.getName().equals("")){
//
//
//
//
//            }else {
//                holder.nameTv.setText(tempValue.getVendorName());
//
//            }
            holder.arrowNextRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] brokenName = tempValue.getName().split(" ");
                    String lastname ="";

                    String firstname = brokenName[0];
                    if (brokenName.length>=2){
                        lastname = String.valueOf(brokenName[1]);
                    }


                    Constants.Tittle=tempValue.getVendorName();
                    Constants.Product_ID=tempValue.getVendorName();
                    Intent mIntent = new Intent(context,ShowRecorderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();

                }
            });
            holder.nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] brokenName = tempValue.getName().split(" ");
                    String lastname ="";

                    String firstname = brokenName[0];
                    if (brokenName.length>=2){
                        lastname = String.valueOf(brokenName[1]);
                    }

                    Constants.Product_ID=tempValue.getVendorName();
                    Constants.Tittle=tempValue.getVendorName();
                    Intent mIntent = new Intent(context,ShowRecorderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();

                }
            });
            holder.arrowNextIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] brokenName = tempValue.getName().split(" ");
                    String lastname ="";

                    String firstname = brokenName[0];
                    if (brokenName.length>=2){
                        lastname = String.valueOf(brokenName[1]);
                    }

                    Constants.Product_ID=tempValue.getInventoryProductId();
                    Constants.Tittle=tempValue.getVendorName();
                    Intent mIntent = new Intent(context,ShowRecorderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();

                }
            });
            if (position % 2 == 0) {
                holder.arrowNextRL.setBackgroundResource(R.color.white);

            } else {
                holder.arrowNextRL.setBackgroundResource(R.color.gray_bg);

            }


        }

    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewWorkHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public RelativeLayout arrowNextRL;
        public ImageView arrowNextIV;

        public ViewWorkHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            arrowNextRL = (RelativeLayout) view.findViewById(R.id.arrowNextRL);
            arrowNextIV = (ImageView) view.findViewById(R.id.arrowNextIV);



        }
    }


}