package com.example.myviewgrouplist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CalendarMonthView extends ListView {

	private final Paint framePaint = new Paint();

	public CalendarMonthView(Context context) {
		super(context);
		MyLog.d(0, "viewgroup: CalendarMonthView constructor");
		framePaint.setColor(getResources().getColor(R.color.frame_color));
		framePaint.setStrokeWidth(1);
	}

	public CalendarMonthView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		MyLog.d(0, "viewgroup: CalendarMonthView constructor");
		framePaint.setColor(getResources().getColor(R.color.frame_color));
		framePaint.setStrokeWidth(1);
	} 

	public CalendarMonthView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int height = MeasureSpec.getSize(heightMeasureSpec);
//		int childCount = this.getChildCount();
//		Log.d("ViewGroup", "listView's child count: "+childCount);
//		if (childCount != 0) {
//			int cellHeight = height / childCount;
//			Log.d("ViewGroup", "cellHeight in grid view: "+cellHeight);
//			for (int i=0; i<childCount; i++) {
//				View child = getChildAt(i);
//				if (child.getVisibility() == View.VISIBLE) {
//					child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.EXACTLY));
//				}
//			}
//			setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), cellHeight*childCount);
//		} else {
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		}
//	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		View child = this.getChildAt(0);
        if (null == child) return;
		int top = child.getTop(); 
		int bottom = getBottom();
		int cellWidth = ((ViewGroup)this.getChildAt(0)).getChildAt(0).getWidth();
//		int cellWidth = ((TextView)this.getChildAt(0)).getWidth();
		MyLog.d(0, "bottom: %d, right: %d,  cellheight: ", bottom, getRight(), child.getHeight());
//		Log.d("ViewGroup", "marginBot: "+this.getPaddingBottom()+", topPadding: "+getPaddingTop());
		//draw the horizontal line
		canvas.drawLine(0, top, cellWidth*7, top, framePaint);
		canvas.drawLine(0, top, 0, bottom, framePaint);
		for (int i=1; i<8; i++) {
			canvas.drawLine(cellWidth*i, top, cellWidth*i, bottom, framePaint);
		}
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		canvas.drawLine(child.getLeft(), child.getBottom()-1, child.getRight(), child.getBottom()-1,framePaint);
		return super.drawChild(canvas, child, drawingTime);
	}
}
