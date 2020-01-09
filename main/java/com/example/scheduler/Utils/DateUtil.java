package com.example.scheduler.Utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DateUtil {
    public static final String TAG="HAHA";
    public static List<String> getDate(int weeknum){
        Calendar calendar=Calendar.getInstance();
        List<String> dates=new ArrayList<>();
        int now_week=0;
        int last_max_week=0;

        if(!Setting.getInstance().isNatureWeek()){
            now_week = weeknum-1+Setting.getInstance().getFirstweeknum();
            last_max_week = getMaxWeek(calendar.get(Calendar.YEAR-1));

            if(now_week>last_max_week){
                now_week=now_week - last_max_week;
            }
        }
        else {
            now_week=weeknum;
        }

        calendar.set(Calendar.WEEK_OF_YEAR,now_week);
        calendar.set(Calendar.DAY_OF_WEEK,2);

        for(int i=1; i<=7; i++){
            int mDay=calendar.get(Calendar.DATE);
            int mMonth=calendar.get(Calendar.MONTH);
            int mYear=calendar.get(Calendar.YEAR);
            dates.add(String.format("%d-%d-%d",mYear,mMonth+1,mDay));

            calendar.add(Calendar.DATE,1);
        }
       return dates;
    }

    public static int getWeekNum(){
        if(Setting.getInstance().isNatureWeek()){
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }
        else{
            Calendar calendar = Calendar.getInstance();

            int now_week = calendar.get(Calendar.WEEK_OF_YEAR);
            if(now_week==1){
                if(calendar.get(Calendar.MONTH)==Calendar.DECEMBER){
                    now_week=getMaxWeek();
                }
            }
            if(now_week>=Setting.getInstance().getFirstweeknum()){
                return now_week-Setting.getInstance().getFirstweeknum()+1;
            }
            else {
                int year = calendar.get(Calendar.YEAR)+1;
                int last_max_week = getMaxWeek(year);
                return now_week+last_max_week-Setting.getInstance().getFirstweeknum()+1;
            }
        }
    }

    public static int getScrollPosition(){
        Calendar calendar =Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 360 + minute * 6;
    }

    public static int getMaxWeek(){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.YEAR, java.util.Calendar.DECEMBER,31);
        return calendar.get(java.util.Calendar.WEEK_OF_YEAR);
    }

    public static int getMaxWeek(int year){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        calendar.set(year, java.util.Calendar.DECEMBER,31);
        return calendar.get(java.util.Calendar.WEEK_OF_YEAR);
    }
}
