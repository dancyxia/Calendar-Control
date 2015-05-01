/**
 * 
 */
package com.example.myviewgrouplist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author dancy
 *
 */
public class WeekListAdapter implements ListAdapter {
	
	private static class ViewHolder {
		TextView weekView;
	}

	List<Calendar> firstWeekDayList = new ArrayList<Calendar>();
	final int firstDayOfWeek = Calendar.SUNDAY;
	Context context;
	
	public WeekListAdapter(Context context, Calendar firstDayOfMonth) {
		this.context = context;
		Calendar next = (Calendar)firstDayOfMonth.clone();
        next.add(Calendar.DATE, firstDayOfWeek - firstDayOfMonth.get(Calendar.DAY_OF_WEEK));
        firstWeekDayList.add(next); //this is the header
        firstWeekDayList.add((Calendar)next.clone());
		boolean stop = false;
		while(!stop) {
			next = (Calendar)next.clone();
			next.add(Calendar.WEEK_OF_MONTH, 1);
			if (next.get(Calendar.MONTH) == firstDayOfMonth.get(Calendar.MONTH)) {
				firstWeekDayList.add(next);
			} else {
				stop = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return firstWeekDayList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return firstWeekDayList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View weekContainer = convertView;
		if (weekContainer == null) {
//sample1: by constructing			
//			weekContainer = new TextView(context);
//			((TextView)weekContainer).setText("by constructing");
//Sample2: by inflating
//			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			weekContainer = inflater.inflate(R.layout.week_row, null);
//			ViewHolder holder = new ViewHolder();
//			holder.weekView = (TextView)weekContainer.findViewById(R.id.week);
//			holder.weekView.setText("by inflating");
//			weekContainer.setTag(holder);

			weekContainer= new WeekView(context);
			Calendar c = this.firstWeekDayList.get(position);
			MyLog.d(0, "first day of the week: %d",c.get(Calendar.DATE));
			((WeekView)weekContainer).setFirstDayOfWeek(c);
			for (int i=0; i<7; i++) {
				CellTextView cell = new CellTextView(context);
				if (position == 0) {
					cell.setTextSize(cell.getTextSize()*0.6f);
					cell.setText(String.format("%1$ta", c));	
				} else {
					cell.setText(String.format("%1$-10te", c));	
				}
				
	            c.add(Calendar.DATE, 1);
				((WeekView)weekContainer).addView(cell);
			}
			MyLog.d(0, "children are added");
		}
		
		return weekContainer;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#registerDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#unregisterDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

}
