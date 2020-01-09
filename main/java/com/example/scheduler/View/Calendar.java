package com.example.scheduler.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.Model.DeadLine;
import com.example.scheduler.R;
import com.example.scheduler.Utils.ActivityLayout;
import com.example.scheduler.Utils.ActivityTextView;
import com.example.scheduler.Utils.DateLayout;
import com.example.scheduler.Utils.DateUtil;
import com.example.scheduler.Utils.CalendarViewModel;

import java.util.ArrayList;
import java.util.List;

public class Calendar extends Fragment{
    private CalendarViewModel mViewModel;
    private View view;
    private List<TextView> datelist=new ArrayList<>();
    private List<ActivityLayout> layoutlist=new ArrayList<>();
    private Spinner week;
    private Button add,clear;
    private ScrollView scrollView;

    public static Calendar newInstance() {
        return new Calendar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calendar, container, false);

        mViewModel=ViewModelProviders.of(this).get(CalendarViewModel.class);

        scrollView = view.findViewById(R.id.scrollview);

        initToolBar();
        initDateLayout();
        initActivityLayout();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, DateUtil.getScrollPosition());
            }
        });
    }

    private void initDateBar(int weeknum){
        if(datelist.size()==0){
            datelist.add((TextView)view.findViewById(R.id.text_date));
            datelist.add((TextView)view.findViewById(R.id.text_mon));
            datelist.add((TextView)view.findViewById(R.id.text_tue));
            datelist.add((TextView)view.findViewById(R.id.text_wed));
            datelist.add((TextView)view.findViewById(R.id.text_thu));
            datelist.add((TextView)view.findViewById(R.id.text_fri));
            datelist.add((TextView)view.findViewById(R.id.text_sat));
            datelist.add((TextView)view.findViewById(R.id.text_sun));
        }

        List<String> date= DateUtil.getDate(weeknum);

        for(int i=0; i<datelist.size(); i++){

            datelist.get(i).setBackground(getResources().getDrawable(R.color.textview_background));
            datelist.get(i).setTextColor(getResources().getColor(R.color.textview_text));

            if(i==0){
                continue;
            }

            final int day=Integer.valueOf(date.get(i-1).split("-")[2]);
            final int month=Integer.valueOf(date.get(i-1).split("-")[1]);
            final int year=Integer.valueOf(date.get(i-1).split("-")[0]);

            final TextView textView=datelist.get(i);
            mViewModel.getDeadLine(year,month,day).observe(this, new Observer<List<DeadLine>>() {
                @Override
                public void onChanged(List<DeadLine> deadLines) {
                    if(!deadLines.isEmpty()){
                        textView.setBackground(getResources().getDrawable(R.color.red));
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getContext(),DeadLineDetail.class);
                                intent.putExtra("year",year);
                                intent.putExtra("month",month);
                                intent.putExtra("day",day);

                                startActivity(intent);
                            }
                        });
                    }
                }
            });

            StringBuilder stringBuilder=new StringBuilder(String.format("%d/%d\n",month,day));
            switch (i){
                case 1:stringBuilder.append("周一");break;
                case 2:stringBuilder.append("周二");break;
                case 3:stringBuilder.append("周三");break;
                case 4:stringBuilder.append("周四");break;
                case 5:stringBuilder.append("周五");break;
                case 6:stringBuilder.append("周六");break;
                case 7:stringBuilder.append("周日");break;
            }
            datelist.get(i).setText(stringBuilder);
        }
    }

    private void initActivityLayout(){
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_mon));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_tue));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_wed));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_thu));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_fri));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_sat));
        layoutlist.add((ActivityLayout)view.findViewById(R.id.calendar_activitylayout_sun));
    }

    private void initDateLayout(){
        DateLayout layout=view.findViewById(R.id.calendar_datelayout);

        for(int i=0; i<24; i++){
            StringBuilder builder=new StringBuilder();
            TextView textView=new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.textview_text));
            textView.setBackgroundColor(getResources().getColor(R.color.textview_background));
            textView.setTextSize(12);
            builder.append(String.format("-%d:00",i));
            textView.setText(builder);
            textView.setVisibility(View.VISIBLE);
            layout.addView(textView);
        }
    }

    private void initToolBar(){

        week=view.findViewById(R.id.calendar_week_spinner);
        final List<String> list=new ArrayList<>();
        if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
            int max_week = DateUtil.getMaxWeek();
            for(int i=1; i<=max_week;i++){
                list.add(String.format("第%d周",i));
            }
        }
        else{
            for(int i=1; i<=com.example.scheduler.Utils.Setting.getInstance().getWeeklength();i++){
                list.add(String.format("第%d周",i));
            }
            list.add(String.format("假期中"));
        }
        week.setAdapter(new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,list));
        week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
                    initDateBar(position+1);
                    mViewModel.Load(position+1);
                }
                else{
                    if(position == list.size()-1){
                        initDateBar(java.util.Calendar.getInstance().get(java.util.Calendar.WEEK_OF_YEAR));
                        for(int i=0; i<layoutlist.size(); i++){
                            layoutlist.get(i).removeAllViews();
                        }
                    }
                    else{
                        initDateBar(position+1);
                        mViewModel.Load(position+com.example.scheduler.Utils.Setting.getInstance().getFirstweeknum());
                    }
                }

                mViewModel.getActivity().observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
                    @Override
                    public void onChanged(List<Activity> activities) {
                        RefreshLayout(activities);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
            week.setSelection(DateUtil.getWeekNum()-1);
        }
        else{
            if(DateUtil.getWeekNum() > com.example.scheduler.Utils.Setting.getInstance().getFirstweeknum() + com.example.scheduler.Utils.Setting.getInstance().getWeeklength() -1){
                week.setSelection(list.size()-1);
            }
            else{
                week.setSelection(DateUtil.getWeekNum()-1);
            }
        }

        add=view.findViewById(R.id.calendar_add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),add);
                popupMenu.getMenuInflater().inflate(R.menu.add_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent=new Intent();

                        switch (item.getTitle().toString()){
                            case "添加日程":intent.setClass(getContext(),AddActivity.class);break;
                            case "添加DDL":intent.setClass(getContext(),AddDeadLine.class);break;
                        }

                        startActivity(intent);
                        return true;
                    }
                });
            }
        });

        clear=view.findViewById(R.id.calendar_clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),clear);
                popupMenu.getMenuInflater().inflate(R.menu.clear_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getTitle().toString()){
                            case "清空日程":mViewModel.ClearActivity();break;
                            case "清空ddl":mViewModel.ClearDeadLine();break;
                        }

                        getActivity().recreate();
                        return true;
                    }
                });
            }
        });
    }

    private void RefreshLayout(final List<Activity> activities){
        for(int i=0; i<layoutlist.size(); i++){
            layoutlist.get(i).removeAllViews();
        }

        if(activities==null){
            return;
        }

        for(int i=0; i<activities.size(); i++){
            final ActivityTextView activityTextView=new ActivityTextView(getContext());
            activityTextView.setActivity(activities.get(i));
            activityTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),ActivityDetail.class);
                    intent.putExtra("activity_id",activityTextView.getActivity().getId());
                    startActivity(intent);
                }
            });
            layoutlist.get(activities.get(i).getWeekday()-1).addView(activityTextView);
        }
    }


}
