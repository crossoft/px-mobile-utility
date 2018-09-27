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

import com.example.dharmaniz.posmobileutilityapp.Activity.InstructionShowDetailActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.ShowLocationDetailActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.ShowSetupdetailActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.informationShowdetailActivity;
import com.example.dharmaniz.posmobileutilityapp.Models.LocationModel;
import com.example.dharmaniz.posmobileutilityapp.Models.RecoderModel;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.Constants;

import java.util.ArrayList;

/**
 * Created by dharmaniz on 4/9/18.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewWorkHolder> {

    private static final String TAG = "ActivityAdapter";
    private ArrayList<LocationModel> modelArrayList;
    private Activity context;

    public LocationAdapter(Activity context, ArrayList<LocationModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public LocationAdapter.ViewWorkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show_name, parent, false);
        return new LocationAdapter.ViewWorkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewWorkHolder holder, int position) {
        final LocationModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {

// If  Search Result is One Than automaticaly Open detail screen

            if(Constants.LocationBackClick==true){
                Constants.LocationBackClick=false;
                if(tempValue.getName().equals("")){
                }else {
                    holder.nameTv.setText(tempValue.getName());
                }
            }
            else {
                if(modelArrayList.size()==1){

                    holder.arrowNextRL.setVisibility(View.INVISIBLE);
                    Log.e("","Possition"+"2"+position);
                    Constants.Tittle=tempValue.getName() ;
                    Intent mIntent = new Intent(context,ShowLocationDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();


                }else {
                    if (tempValue.getName().equals("")) {
                    } else {
                        holder.nameTv.setText(tempValue.getName());
                    }
                }
            }
            holder.arrowNextRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constants.Tittle=tempValue.getName() ;
                    Intent mIntent = new Intent(context,ShowLocationDetailActivity.class);
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
                    Constants.Tittle=tempValue.getName() ;
                    Intent mIntent = new Intent(context,ShowLocationDetailActivity.class);
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
                    Constants.Tittle=tempValue.getName() ;
                    Intent mIntent = new Intent(context,ShowLocationDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();

                }
            });


            // Adding Alternate color in Background

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