/**
 * 
 */
package com.example.myviewgrouplist;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author dancy
 *
 */
public class MonthlyCalendarActivity extends FragmentActivity {
	static Calendar currentMonth;
	static int currentIndex = 0;
	
	/**
	 * 
	 */
	public MonthlyCalendarActivity() {
		currentMonth = Calendar.getInstance();
		currentMonth.set(Calendar.DATE, 1);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.calendar_month_pager);
		
		final MonthPagerAdapter pageAdpater = new MonthPagerAdapter(this.getSupportFragmentManager());
		final ViewPager pager = (ViewPager)this.findViewById(R.id.pager); 
		pager.setAdapter(pageAdpater);
		pager.setCurrentItem(1);
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				currentIndex = position;
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					currentMonth.add(Calendar.MONTH, currentIndex -1);
					pager.setCurrentItem(1, false);//this does the tricky
					for (int i=0; i<3; i++) {
						Fragment fragment = (Fragment)((MonthPagerAdapter)pager.getAdapter()).instantiateItem(pager, i);
						ViewGroup rootView = (ViewGroup)fragment.getView();
						if (rootView != null) {
							((MonthFragment)fragment).setView(rootView);
						}
					}
				}
			};
		});
		
	}


	public static class MonthPagerAdapter extends FragmentPagerAdapter {

		public MonthPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int idx) {
			return MonthFragment.newInstance(idx);
		}
		
		
		@Override
		public int getCount() {
			return 3;
		}
		
	}
	
	public static class MonthFragment extends Fragment {
		
		public static MonthFragment newInstance(int index) {
			MyLog.d(1, "new instance for index %d", index);
			MonthFragment newFragment = new MonthFragment();
			Bundle args = new Bundle();
			args.putInt("index", index);
			newFragment.setArguments(args);
			return newFragment;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			if (container == null) return null;
			ViewGroup view = (ViewGroup)inflater.inflate(R.layout.activity_main, null);
			
			setView(view);
			MyLog.d(1, "on create view is called" );
			return view;
		}
		
		class ViewHolder {
			TextView titleView;
			CalendarMonthView listView;
		}
		
		private void setView(ViewGroup rootView) {
			int index = this.getArguments().getInt("index");
			MyLog.d(1, "setView is called, index: %d", index);
			Calendar thisMonth =(Calendar)currentMonth.clone();
			thisMonth.add(Calendar.MONTH, index-1);
			ViewHolder vh =(ViewHolder)rootView.getTag();
			if (vh == null) {
				vh = new ViewHolder();
				vh.titleView = (TextView)rootView.findViewById(R.id.title);
				vh.listView = (CalendarMonthView)rootView.findViewById(R.id.listview);
				rootView.setTag(vh);
			} 
			
			vh.titleView.setPadding(0, 0, 0, 10);
			vh.titleView.setText(String.format("%1$tB, %1$tY", thisMonth));
			
			WeekListAdapter adapter = new WeekListAdapter(rootView.getContext(), thisMonth);
			vh.listView.setAdapter(adapter);
		}
	}
}
