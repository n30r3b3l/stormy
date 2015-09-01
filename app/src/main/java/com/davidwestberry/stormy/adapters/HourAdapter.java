package com.davidwestberry.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.davidwestberry.stormy.R;
import com.davidwestberry.stormy.Weather.Hour;

import butterknife.InjectView;

/**
 * Created by davidw on 3/9/2015.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] mHours;

    public HourAdapter(Hour[] hours){
        mHours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        ButterKnife.inject(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.timeLabel) TextView mTimeLabel;
        @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
        @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
        @InjectView(R.id.iconImageView) ImageView mIconImageView;

        //public TextView mTimeLabel;
        //public TextView mSummaryLabel;
        //public TextView mTemperatureLabel;
        //public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            //mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            //mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            //mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureMaxLabel);
            //mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }
        public void bindHour(Hour hour){
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperatureMax() + "\u2109");
            mIconImageView.setImageResource(hour.getIconId());
        }
    }

}
