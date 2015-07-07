package com.home.purecalendar;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		RelativeLayout layout = (RelativeLayout)this.findViewById(R.id.layout);
//		TextView childView = new TextView(this);
//		childView.setText(String.format("%1$-10te", Calendar.getInstance()));
//		childView.setTextSize(20);
//		layout.addView(childView);
//		Log.d("viewgroup", "textview is added");

		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.DATE, 1);
		
		TextView titleView = (TextView)findViewById(R.id.title);
		titleView.setPadding(0, 0, 0, 10);
		titleView.setText(String.format("%1$tB, %1$tY", thisMonth));
		
		CalendarMonthView listView = (CalendarMonthView)this.findViewById(R.id.listview);
//		Sample: test basic array adapter
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.week_row, new String[]{"sunday", "monday"});
		WeekListAdapter adapter = new WeekListAdapter(this, thisMonth);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View thisView = super.onCreateView(name, context, attrs);
		return thisView;
	}
}
