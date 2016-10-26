package com.qxsx.chaersi.driverclient.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Lee on 2015/11/17.
 * @desc 完全显示所有listview中的item
 */
public class ListViewShowView extends ListView {

    public ListViewShowView(Context context) {
        super(context);
    }

    public ListViewShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
