package com.example.myviewgrouplist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class CellTextView extends TextView {

    boolean isToday = false;

	public CellTextView(Context context) {
		super(context);
		init();
	}

	public CellTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CellTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

    public void setIsToday(boolean isToday) {
        this.isToday = isToday;
    }

	private void init() {
		setGravity(Gravity.CENTER);
        setPadding(10,10,10,10);
	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            Drawable bg = getContext().getResources().getDrawable(R.drawable.today_background);
            bg.setBounds(2, 2, getMeasuredWidth()-2,getMeasuredHeight()-2);
            bg.draw(canvas);
        }
    }
}
