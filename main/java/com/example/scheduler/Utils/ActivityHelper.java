package com.example.scheduler.Utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.Model.Time;

import org.jsoup.select.Elements;

import java.util.List;

public class ActivityHelper {
    private List<Time> time_list;
    private ActivityRepository activity_repository = new ActivityRepository();
    private TimeRepository time_repository = new TimeRepository();

    public ActivityHelper(LifecycleOwner owner){

        time_repository.GetTime().observe(owner, new Observer<List<Time>>() {
            @Override
            public void onChanged(List<Time> times) {
                time_list = times;
            }
        });
    }

    public void BIT_Generater(Elements lines){
        for(int i=2; i<lines.size()-1; i++){
            Elements weekdays = lines.get(i).getElementsByClass("kbcontent");
            for(int j=0; j<weekdays.size(); j++){
                if(weekdays.get(j).text().equals("")){
                    continue;
                }
                String[] classes = weekdays.get(j).text().split("--------------------- ");
                for(int p=0; p<classes.length; p++){
                    String[] value = classes[p].split(" ");

                    String start_and_end_week = value[2].split("周")[0].substring(0,value[2].split("周")[0].length()-1);
                    String[] split_s_a_e_week = start_and_end_week.split(",");
                    for(int q=0; q<split_s_a_e_week.length; q++){
                        String[] tmp = split_s_a_e_week[q].split("-");

                        Activity activity = new Activity();
                        activity.setWeekday(j+1);

                        if(tmp.length==1){
                            activity.setStart_week(Setting.getInstance().getFirstweeknum()+Integer.valueOf(tmp[0])-1);
                            activity.setEnd_week(Setting.getInstance().getFirstweeknum()+Integer.valueOf(tmp[0])-1);
                        }
                        else{
                            activity.setStart_week(Setting.getInstance().getFirstweeknum()+Integer.valueOf(tmp[0])-1);
                            activity.setEnd_week(Setting.getInstance().getFirstweeknum()+Integer.valueOf(tmp[1])-1);
                        }

                        activity.setName(value[0]);
                        activity.setPlace(value[3]);
                        activity.setMember(value[1]);

                        String[] start_and_end_time = value[2].split("周")[1].substring(2,value[2].split("(周)")[1].length()-2).split("-");

                        int start_label=Integer.valueOf(start_and_end_time[0]);
                        int end_label=Integer.valueOf(start_and_end_time[start_and_end_time.length-1]);

                        for(int k=0; k<time_list.size(); k++){
                            if(time_list.get(k).getLabel()==start_label){
                                activity.setStart_hour(time_list.get(k).getStart_hour());
                                activity.setStart_minute(time_list.get(k).getStart_minute());
                                break;
                            }
                        }

                        for(int k=0; k<time_list.size(); k++){
                            if(time_list.get(k).getLabel()==end_label){
                                activity.setEnd_hour(time_list.get(k).getEnd_hour());
                                activity.setEnd_minute(time_list.get(k).getEnd_minute());
                                break;
                            }
                        }

                        activity_repository.Insert(activity);
                    }

                }

            }
        }
    }
}
