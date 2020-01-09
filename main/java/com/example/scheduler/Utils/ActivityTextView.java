package com.example.scheduler.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.R;

import java.util.Random;

public class ActivityTextView extends TextView {
    public static final String TAG="ActivityTextView";
    private Activity activity;

    public ActivityTextView(Context context) {
        super(context,null);
    }

    public ActivityTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ActivityTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActivity(Activity activity){
        this.activity=activity;
        init();
    }

    public Activity getActivity(){
        return activity;
    }

    private void init(){
        StringBuilder builder=new StringBuilder(activity.getName());
        builder.append(String.format("\n%02d:%02d",activity.getStart_hour(),activity.getStart_minute(),activity.getEnd_hour(),activity.getEnd_minute()));
        builder.append("\n"+activity.getPlace());
        builder.append("\n"+activity.getMember());
        this.setText(builder);
        this.setTextColor(getResources().getColor(R.color.white));
        int colrcode= Math.abs(new Random().nextInt())%4;
        switch (colrcode){
            case 0:this.setBackgroundColor(getResources().getColor(R.color.t_bule));break;
            case 1:this.setBackgroundColor(getResources().getColor(R.color.t_green));break;
            case 2:this.setBackgroundColor(getResources().getColor(R.color.t_pink));break;
            case 3:this.setBackgroundColor(getResources().getColor(R.color.t_yellow));break;
        }
        this.setVisibility(View.VISIBLE);
    }
}
