package com.pos.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pos.Models.MetricModel;
import com.pos.R;

import java.util.ArrayList;


public class MetricAdapter extends RecyclerView.Adapter<MetricAdapter.ViewWorkHolder> {
    private static final String TAG = "ActivityAdapter";
    private ArrayList<MetricModel> modelArrayList;
    private Context context;

    public MetricAdapter(Context context, ArrayList<MetricModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewWorkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_metricx, parent, false);
        return new ViewWorkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewWorkHolder holder, int position) {
        final MetricModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {
            holder.metricNameTV.setText(tempValue.getMetric());
            holder.matrixValueTV.setText(tempValue.getResult());
            if (position % 2 == 0) {
                holder.mailLL.setBackgroundResource(R.color.white);

            } else {
                holder.mailLL.setBackgroundResource(R.color.gray_bg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewWorkHolder extends RecyclerView.ViewHolder {
        public TextView metricNameTV, matrixValueTV;
        public LinearLayout mailLL;

        public ViewWorkHolder(View view) {
            super(view);
            matrixValueTV = (TextView) view.findViewById(R.id.matrixValueTV);
            metricNameTV = (TextView) view.findViewById(R.id.metricNameTV);
            mailLL = (LinearLayout) view.findViewById(R.id.mailLL);

        }
    }


}