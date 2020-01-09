package com.example.scheduler.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateLayout extends ViewGroup {
    public DateLayout(Context context) {
        super(context,null);
    }

    public DateLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public DateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int mwidthsize= MeasureSpec.getSize(widthMeasureSpec);
        int mheight=0;

        int childnum=getChildCount();
        for(int i=0; i<childnum; i++){
            TextView child=(TextView)getChildAt(i);
            child.setHeight(360);
            child.setWidth(mwidthsize);
            measureChild(child,mwidthsize,360);
            mheight+=child.getMeasuredHeight();
        }
        setMeasuredDimension(mwidthsize,mheight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childnum=getChildCount();
        int cly=0;
        for(int i=0; i<childnum; i++){
            View child=getChildAt(i);
            int a=getWidth();
            int c=cly+child.getMeasuredHeight();
            child.layout(0,cly,a,c);
            cly=c;
        }
    }
}
