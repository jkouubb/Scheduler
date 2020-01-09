package com.example.scheduler.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ActivityLayout extends ViewGroup {

    public ActivityLayout(Context context) {
        super(context,null);
    }

    public ActivityLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ActivityLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mwidthsize= MeasureSpec.getSize(widthMeasureSpec);
        int mheight=0;

        int childnum=getChildCount();
        for(int i=0; i<childnum; i++){
            ActivityTextView child=(ActivityTextView)getChildAt(i);
            int start_y=child.getActivity().getStart_hour()*360+child.getActivity().getStart_minute()*6;
            int end_y=child.getActivity().getEnd_hour()*360+child.getActivity().getEnd_minute()*6;
            int height=end_y-start_y;
            child.setHeight(height);
            child.setWidth(mwidthsize);
            measureChild(child,mwidthsize,height);
            mheight=Math.max(mheight,end_y);
        }
        setMeasuredDimension(mwidthsize,mheight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childnum=getChildCount();
        for(int i=0; i<childnum; i++){
            ActivityTextView child=(ActivityTextView)getChildAt(i);
            int start_y=child.getActivity().getStart_hour()*360+child.getActivity().getStart_minute()*6;
            //int end_y=child.getActivity().getEnd_hour()*360+child.getActivity().getEnd_minute()*6;
            child.layout(0,start_y,getWidth(),start_y+child.getMeasuredHeight());
            child.layout(0,start_y,getWidth(),start_y+child.getMeasuredHeight());
        }
    }
}
