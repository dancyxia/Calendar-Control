package com.example.myviewgrouplist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CellTextView extends TextView {

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
	
	private void init() {
		setPadding(10, 10, 10, 10);
	}
}
