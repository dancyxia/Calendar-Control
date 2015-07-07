package com.home.purecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class MonthFragment extends Fragment {
    private MonthChangeListener monthChangeListener;

    public interface MonthChangeListener {
        public void onMonthChanged(Calendar thisMonth);
    }

    public static MonthFragment newInstance(int index) {
        MonthFragment newFragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.monthChangeListener = (MonthChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MonthChangeListener");
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null)
            return null;
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.activity_main, null);
        updateView(view);
        return view;
    }

    private static class ViewHolder {
        TextView titleView;
        CalendarMonthView monthView;
    }

    public void updateView(ViewGroup rootView) {
        int index = this.getArguments().getInt("index");
        Calendar thisMonth =(Calendar)MonthlyCalendarActivity.currentMonth.clone();
        thisMonth.add(Calendar.MONTH, index - 1);  //index for previous month is 0, need to minus one month(0-1=-1); for next month is 2, need to add one month(2-1=1)

        if (monthChangeListener != null)
            monthChangeListener.onMonthChanged(MonthlyCalendarActivity.currentMonth);

        ViewHolder holder = (ViewHolder)rootView.getTag();
        TextView titleView;
        CalendarMonthView monthView;
        if (holder != null) {
            titleView = holder.titleView;
            monthView = holder.monthView;
            ((WeekListAdapter)monthView.getAdapter()).setFirstDayOfMonth(thisMonth);
            //need to call setAdapter again to make the update take effect(is there any better way?)
            monthView.setAdapter(monthView.getAdapter());
        } else {
            titleView = (TextView) rootView.findViewById(R.id.title);
            holder = new ViewHolder();
            holder.titleView = titleView;
            rootView.setTag(holder);
            monthView = (CalendarMonthView) rootView.findViewById(R.id.listview);
            holder.monthView = monthView;
            WeekListAdapter adapter = new WeekListAdapter(rootView.getContext(), thisMonth);
            monthView.setAdapter(adapter);
        }

        titleView.setPadding(0, 0, 0, 10);
        titleView.setText(String.format("%1$tB, %1$tY", thisMonth));
    }
}
