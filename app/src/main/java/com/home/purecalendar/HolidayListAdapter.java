package com.home.purecalendar;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.purecalendar.holiday.HolidayContent;

import java.util.List;

/**
 * Created by dancy on 7/9/2015.
 */
public class HolidayListAdapter extends ArrayAdapter<HolidayContent.HolidayInfo> {

    int defaultIconId;
    private class ViewHolder {
        ImageView icon;
        TextView text;
    }

    public HolidayListAdapter(Context context, int resourceID, List<HolidayContent.HolidayInfo> list) {
        super(context, resourceID, list);
        defaultIconId = this.getContext().getResources().getIdentifier("ic_launcher","drawable", "com.home.purecalendar");
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        ViewHolder holder;
        if (itemView == null) {
            LayoutInflater inflater =((Activity)this.getContext()).getLayoutInflater();//SystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.holiday_detail, null);
            holder = new ViewHolder();
            itemView.setTag(holder);
            holder.icon = (ImageView)itemView.findViewById(R.id.holidayicon);
            holder.text = (TextView)itemView.findViewById(R.id.holidaytext);
        } else {
            holder = (ViewHolder)itemView.getTag();
        }

        HolidayContent.HolidayInfo holiday = getItem(i);

        int id = this.getContext().getResources().getIdentifier(holiday.getIconName(),"drawable", "com.home.purecalendar");
        if (id == 0)
            id = defaultIconId;
        holder.icon.setImageResource(id);
        holder.text.setText(holiday.toString());

        return itemView;
    }
}
