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

    public enum LabelType {
        HEADER,DAY
    }

    private LabelType type;

//	private Calendar firstDayOfWeek;
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

        View pager =getRootView().findViewById(R.id.listview);
        if (pager == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
//        MyLog.d(1, "content view measured wdith: %d, height: %d, width: %d, height %d",contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), contentView.getWidth(), contentView.getHeight());

        final int childCount = this.getChildCount();

        int rowWidth = MeasureSpec.getSize(widthMeasureSpec);
		int cellWidth = rowWidth/childCount;

        int minHeight = pager.getMeasuredHeight()/WeekListAdapter.maxRow;
        int paddingHeight = (int)getContext().getResources().getDimension(R.dimen.padding)<<1;
        int rowHeight = Math.min(type == LabelType.HEADER? (int)getContext().getResources().getDimension(R.dimen.datelabelsize)+paddingHeight : (int)getContext().getResources().getDimension(R.dimen.daytextsize)+paddingHeight, minHeight);
        MyLog.d(1,"minHeight: %d, rowHeight: %d", minHeight, rowHeight);
		int cellWidthMeasureSpec = MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY);
		int cellHeightMeasureSpec = MeasureSpec.makeMeasureSpec(rowHeight, MeasureSpec.EXACTLY);
		View child = null;
		for (int i=0; i<childCount; i++) {
			child = this.getChildAt(i);
			if(child.getVisibility() != View.GONE) {
				child.measure(cellWidthMeasureSpec, cellHeightMeasureSpec);
			}
		}
    	this.setMeasuredDimension(rowWidth, rowHeight);
	}

    public void setType(LabelType type) {
        this.type = type;
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = this.getChildCount();
        int left = l;
		for (int i=0; i<childCount; i++) {
			final View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
                child.layout(left, 0, left + child.getMeasuredWidth(), child.getMeasuredHeight());
                left += child.getMeasuredWidth();
			}
		}
	}
}
