package com.davidwestberry.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidwestberry.stormy.R;
import com.davidwestberry.stormy.Weather.Day;

/**
 * Created by davidw on 3/6/2015.
 */
public class DayAdapter extends BaseAdapter{

    private Context mContext;
    private Day[] mDays;

// Constructor
    public DayAdapter(Context context, Day[] days){
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;  // Unused. Can be used for tagging items
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.temperatureMaxLabel = (TextView) convertView.findViewById(R.id.temperatureMaxLabel);
            holder.temperatureMinLabel = (TextView) convertView.findViewById(R.id.temperatureMinLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
            holder.dateLabel = (TextView) convertView.findViewById(R.id.dateLabel);
            holder.summaryLabel = (TextView) convertView.findViewById(R.id.summaryLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Day day = mDays[position];

        holder.icon.setImageResource(day.getIconId());
        holder.temperatureMaxLabel.setText(day.getTemperatureMax() + "");
        holder.temperatureMinLabel.setText(day.getTemperatureMin() + "\u2109");
        holder.summaryLabel.setText(day.getSummary());
        holder.dateLabel.setText(day.getDate());
        if (position == 0) {
            holder.dayLabel.setText("Today");
        }
        else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView temperatureMaxLabel;
        TextView temperatureMinLabel;
        TextView dayLabel;
        TextView dateLabel;
        TextView summaryLabel;
    }
}
