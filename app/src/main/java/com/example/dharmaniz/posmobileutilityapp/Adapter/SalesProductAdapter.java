package com.example.dharmaniz.posmobileutilityapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dharmaniz.posmobileutilityapp.Activity.InventoryHomeActivity;
import com.example.dharmaniz.posmobileutilityapp.Activity.SaleProductHomeActivity;
import com.example.dharmaniz.posmobileutilityapp.Models.InventoryModel;
import com.example.dharmaniz.posmobileutilityapp.Models.SalesProductModel;
import com.example.dharmaniz.posmobileutilityapp.R;
import com.example.dharmaniz.posmobileutilityapp.Util.Constants;

import java.util.ArrayList;

/**
 * Created by dharmaniz on 5/9/18.
 */

public class SalesProductAdapter extends RecyclerView.Adapter<SalesProductAdapter.ViewWorkHolder> {

    private static final String TAG = "ActivityAdapter";
    private ArrayList<SalesProductModel> modelArrayList;
    private Context context;

    public SalesProductAdapter(Context context, ArrayList<SalesProductModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public SalesProductAdapter.ViewWorkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory_data, parent, false);
        return new SalesProductAdapter.ViewWorkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SalesProductAdapter.ViewWorkHolder holder, int position) {
        final SalesProductModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {
            if (tempValue.getName().equals("")) {
                holder.nameTv.setVisibility(View.GONE);
            } else {
                holder.nameTv.setText(tempValue.getName());
            }
            if (tempValue.getDescription().equals("")) {
                holder.venderNameTV.setVisibility(View.GONE);
            } else {
                holder.venderNameTV.setText(tempValue.getDescription());
            }

holder.arrowNextRL.setVisibility(View.VISIBLE);
            holder.arrowNextRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tempValue.getSalesProductId().equals("")) {
                        Constants.Product_ID = tempValue.getSalesProductId();
                        Intent mIntent = new Intent(context, SaleProductHomeActivity.class);
                        context.startActivity(mIntent);
                    } else {
                        Intent mIntent = new Intent(context, SaleProductHomeActivity.class);
                        context.startActivity(mIntent);
                    }
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewWorkHolder extends RecyclerView.ViewHolder {
        public TextView nameTv, venderNameTV;

        public RelativeLayout arrowNextRL;


        public ViewWorkHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            venderNameTV = (TextView) view.findViewById(R.id.venderNameTV);
            arrowNextRL = (RelativeLayout) view.findViewById(R.id.arrowNextRL);

        }
    }


}
