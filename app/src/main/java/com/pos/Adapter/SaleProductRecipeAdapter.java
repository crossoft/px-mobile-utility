package com.pos.Adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pos.Activity.ShowSaleProductRecipedetailActivity;
import com.pos.Models.SaleProductReciepeModel;
import com.pos.R;
import com.pos.Util.Constants;

import java.util.ArrayList;

public class SaleProductRecipeAdapter extends RecyclerView.Adapter<SaleProductRecipeAdapter.ViewWorkHolder> {

    private static final String TAG = "ActivityAdapter";
    private ArrayList<SaleProductReciepeModel> modelArrayList;
    private Activity context;

    public SaleProductRecipeAdapter(Activity context, ArrayList<SaleProductReciepeModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewWorkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show_name, parent, false);
        return new ViewWorkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewWorkHolder holder, int position) {
        final SaleProductReciepeModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {
            if (Constants.RecipeBackClick == true) {
                Constants.RecipeBackClick = false;
                if (tempValue.getInventoryProductName().equals("")) {
                } else {
                    holder.nameTv.setText(tempValue.getInventoryProductName());
                }
            } else {
                if (modelArrayList.size() == 1) {
                    holder.arrowNextRL.setVisibility(View.INVISIBLE);
                    Constants.Tittle = tempValue.getInventoryProductName();
                    Constants.Product_ID = tempValue.getProduct_id();
                    Intent mIntent = new Intent(context, ShowSaleProductRecipedetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentQTY", tempValue);
                    mIntent.putExtras(bundle);
                    context.startActivity(mIntent);
                    context.finish();
                } else {
                    if (tempValue.getInventoryProductName().equals("")) {
                    } else {
                        holder.nameTv.setText(tempValue.getInventoryProductName());
                    }
                }
            }

            holder.arrowNextRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constants.Tittle = tempValue.getInventoryProductName();
                    Constants.Product_ID = tempValue.getProduct_id();
                    Intent mIntent = new Intent(context, ShowSaleProductRecipedetailActivity.class);
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
                    Constants.Tittle = tempValue.getInventoryProductName();
                    Constants.Product_ID = tempValue.getProduct_id();
                    Intent mIntent = new Intent(context, ShowSaleProductRecipedetailActivity.class);
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
                    Constants.Tittle = tempValue.getInventoryProductName();
                    Constants.Product_ID = tempValue.getProduct_id();
                    Intent mIntent = new Intent(context, ShowSaleProductRecipedetailActivity.class);
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
