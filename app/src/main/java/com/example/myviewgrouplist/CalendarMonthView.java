package com.example.myviewgrouplist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CalendarMonthView extends ListView {
    int dividerWidth;
	private final Paint framePaint = new Paint();
	public CalendarMonthView(Context context) {
		super(context);
	}

	public CalendarMonthView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CalendarMonthView, 0, defStyle);
        dividerWidth = (int)ta.getDimension(R.styleable.CalendarMonthView_dividerwidth, 1);
		framePaint.setColor(ta.getColor(R.styleable.CalendarMonthView_dividercolor, 0xff333333));
		framePaint.setStrokeWidth(dividerWidth);
	} 

	public CalendarMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		View child = this.getChildAt(0);
        if (null == child) return;
        int top = child.getTop();
        int right = child.getRight()-dividerWidth;
        child = this.getChildAt(getChildCount()-1);
		int bottom = child.getBottom();
        int cellWidth = ((ViewGroup)this.getChildAt(0)).getChildAt(0).getWidth();
        //draw first horizontal line
        canvas.drawLine(0, top, right, top, framePaint);
        //draw first vertical line
        canvas.drawLine(0, top, 0, bottom, framePaint);
        int left = cellWidth;
        //draw remaining vertical line
        for (int i=1; i< 7; i++) {
            canvas.drawLine(left, top, left, bottom, framePaint);
            left += cellWidth;
        }
        canvas.drawLine(right, top, right, bottom, framePaint);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        //draw remaining horizontal line
		canvas.drawLine(child.getLeft(), child.getBottom(), child.getRight()-dividerWidth, child.getBottom(),framePaint);
		return super.drawChild(canvas, child, drawingTime);
	}
}
