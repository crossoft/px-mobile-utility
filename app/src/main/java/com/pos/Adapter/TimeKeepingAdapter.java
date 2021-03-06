package com.pos.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pos.Models.TimeKeepingModel;
import com.pos.R;

import java.util.ArrayList;


public class TimeKeepingAdapter extends RecyclerView.Adapter<TimeKeepingAdapter.ViewWorkHolder> {

    private static final String TAG = "ActivityAdapter";
    private ArrayList<TimeKeepingModel> modelArrayList;
    private Context context;

    public TimeKeepingAdapter(Context context, ArrayList<TimeKeepingModel> modelArrayList) {
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
        final TimeKeepingModel tempValue = modelArrayList.get(position);
        if (tempValue != null) {
            holder.metricNameTV.setText(tempValue.getFirstName() + " " + tempValue.getLastName());
            holder.matrixValueTV.setText(tempValue.getClockInDateTime());
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
        public TextView metricNameTV, matrixValueTV, item_description, item_quantiy;
        public LinearLayout mailLL;

        public ViewWorkHolder(View view) {
            super(view);
            metricNameTV = (TextView) view.findViewById(R.id.metricNameTV);
            matrixValueTV = (TextView) view.findViewById(R.id.matrixValueTV);
            mailLL = (LinearLayout) view.findViewById(R.id.mailLL);
        }
    }


}