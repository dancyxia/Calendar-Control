package com.example.myviewgrouplist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
        setView(view);
        return view;
    }

    public void setView(ViewGroup rootView) {
        int index = this.getArguments().getInt("index");
        Calendar thisMonth =(Calendar)MonthlyCalendarActivity.currentMonth.clone();
        thisMonth.add(Calendar.MONTH, index - 1);
        if (monthChangeListener != null)
            monthChangeListener.onMonthChanged(MonthlyCalendarActivity.currentMonth);

        TextView titleView = (TextView)rootView.findViewById(R.id.title);
        CalendarMonthView listView = (CalendarMonthView)rootView.findViewById(R.id.listview);

        titleView.setPadding(0, 0, 0, 10);
        titleView.setText(String.format("%1$tB, %1$tY", thisMonth));

        WeekListAdapter adapter = new WeekListAdapter(rootView.getContext(), thisMonth);
        listView.setAdapter(adapter);
    }
}
