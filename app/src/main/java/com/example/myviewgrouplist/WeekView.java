/**
 * 
 */
package com.example.myviewgrouplist;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author dancy
 *
 */
public class WeekView extends ViewGroup {
	private Calendar firstDayOfWeek;  
	public WeekView(Context context) {
		super(context);
	}

	
	public WeekView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public WeekView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int childCount = this.getChildCount();
		int cellWidth = MeasureSpec.getSize(widthMeasureSpec)/7;
		int rowWidth = cellWidth*7;
		int cellWidthMeasureSpec = MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY);
		MyLog.d(0, "oriRowWidth: %d", rowWidth);
		int cellHeight = 0;
		View child = null;
		for (int i=0; i<childCount; i++) {
			child = this.getChildAt(i);
			if(child.getVisibility() != View.GONE) {
				child.measure(cellWidthMeasureSpec, cellWidthMeasureSpec/*heightMeasureSpec*/);
			}
		}
		cellHeight = child == null? cellWidth: child.getMeasuredHeight();

		this.setMeasuredDimension(rowWidth, cellHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = this.getChildCount();
		int width = (r - l)/childCount; 
		for (int i=0; i<childCount; i++) {
			final View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				MyLog.d(0, "child text: %s,  width: %d", ((TextView)child).getText(), child.getMeasuredWidth());
				child.layout(l+i*width, 0, l+(i+1)*width, b-t);
			}
		}
	}

	public void setFirstDayOfWeek(Calendar calendar) {
			firstDayOfWeek = calendar;
		// TODO Auto-generated method stub
		
	}

}
