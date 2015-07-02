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
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author dancy
 *
 */
public class MonthlyCalendarActivity extends FragmentActivity implements MonthFragment.MonthChangeListener {
	public static Calendar currentMonth;
	static int currentIndex = 0;
    private TextView noteView;

	/**
	 * 
	 */
	public MonthlyCalendarActivity() {
		currentMonth = Calendar.getInstance();
		currentMonth.set(Calendar.DATE, 1);
	}

    @Override
    public void onMonthChanged(Calendar thisMonth) {
        if (noteView != null)
            noteView.setText("Happy "+String.format("%1$tB, %1$tY", thisMonth));
    }

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.calendar_month_pager);

        noteView = (TextView)findViewById(R.id.note);
		final MonthPagerAdapter pageAdapter = new MonthPagerAdapter(this.getSupportFragmentManager(), this);
		final ViewPager pager = (ViewPager)this.findViewById(R.id.pager);
		pager.setAdapter(pageAdapter);
		pager.setCurrentItem(1);

		pager. addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                    currentMonth.add(Calendar.MONTH, currentIndex - 1);
                    pager.setCurrentItem(1, false);//this does the tricky
                    for (int i = 0; i < 3; i++) {
                        MonthFragment fragment = (MonthFragment) ((MonthPagerAdapter) pager.getAdapter()).instantiateItem(pager, i);
                        ViewGroup rootView = (ViewGroup) fragment.getView();
                        if (rootView != null) {
                            fragment.setView(rootView);
                        }
                    }
                }
            }

            ;
        });
	}

	private static class MonthPagerAdapter extends FragmentPagerAdapter {
        private MonthlyCalendarActivity context;

		public MonthPagerAdapter(FragmentManager fm, MonthlyCalendarActivity context) {
            super(fm);
            this.context = context;
		}

		@Override
		public Fragment getItem(int idx) {
            MonthFragment fragment = MonthFragment.newInstance(idx);
            return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}
		
	}
	
}
