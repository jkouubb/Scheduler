package com.example.scheduler.Utils;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.scheduler.Model.Activity;
import com.example.scheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter <CardAdapter.CardViewHolder> {
    private List<CardViewHolder> list=new ArrayList<>();
    private Context context;
    private int size=0;

    public CardAdapter(Context context){
        this.context=context;
    }


    public void addCard(){
        size++;
        notifyItemInserted(size-1);
    }

    public void deleteCard(){
        if(size>0){
            size--;
            list.remove(list.size()-1);
            notifyItemRemoved(list.size());
        }
    }

    public List<Activity> getActivity(){
        if(size==0 || list.size()==0){
            Toast.makeText(context,"请至少设置一个日程的具体起止时间",Toast.LENGTH_SHORT).show();
            return null;
        }
        List<Activity> activities=new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            if(!list.get(i).check(context)){
                return null;
            }
            activities.add(list.get(i).generateActivity());
        }
        return activities;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardViewHolder holder=new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        holder.start_time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String str=String.format("%02d:%02d",hourOfDay,minute);
                        holder.start_time_pick.setText(str);
                    }
                },0,0,true).show();
            }
        });

        holder.end_time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String str=String.format("%02d:%02d",hourOfDay,minute);
                        holder.end_time_pick.setText(str);
                    }
                },0,0,true).show();
            }
        });

        holder.weekday_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialog dialog=new PickerDialog(context,1);
                dialog.setListener(new PickerDialog.ConfirmListener() {
                    @Override
                    public void getText(String string) {
                        holder.weekday_pick.setText(string);
                    }
                });
                dialog.show();
            }
        });

        list.add(holder);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        private EditText place,member;
        private Button start_time_pick,end_time_pick,weekday_pick;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            place=itemView.findViewById(R.id.card_self_place);
            member=itemView.findViewById(R.id.card_self_member);
            start_time_pick=itemView.findViewById(R.id.card_start_time_btn);
            end_time_pick=itemView.findViewById(R.id.card_end_time_btn);
            weekday_pick=itemView.findViewById(R.id.card_weekday_btn);
        }

        public Activity generateActivity(){
            Activity activity=new Activity();

            if(member.getText().toString().equals("")){
                activity.setMember("null");
            }
            else{
                activity.setMember(member.getText().toString());
            }

            if(place.getText().toString().equals("")){
                activity.setPlace("null");
            }
            else{
                activity.setPlace(place.getText().toString());
            }

            activity.setStart_hour(Integer.valueOf(start_time_pick.getText().toString().split(":")[0]));
            activity.setStart_minute(Integer.valueOf(start_time_pick.getText().toString().split(":")[1]));
            activity.setEnd_hour(Integer.valueOf(end_time_pick.getText().toString().split(":")[0]));
            activity.setEnd_minute(Integer.valueOf(end_time_pick.getText().toString().split(":")[1]));

            switch (weekday_pick.getText().toString()){
                case "周一": activity.setWeekday(1);break;
                case "周二": activity.setWeekday(2);break;
                case "周三": activity.setWeekday(3);break;
                case "周四": activity.setWeekday(4);break;
                case "周五": activity.setWeekday(5);break;
                case "周六": activity.setWeekday(6);break;
                case "周日": activity.setWeekday(7);break;
            }
            return activity;
        }


        public boolean check(Context context){
            if(start_time_pick.getText().toString().equals("选择开始时间") || end_time_pick.getText().toString().equals("选择结束时间")){
                Toast.makeText(context,"未选择起始或结束时间",Toast.LENGTH_SHORT).show();
                return false;
            }
            int sth=Integer.valueOf(start_time_pick.getText().toString().split(":")[0]);
            int stm=Integer.valueOf(start_time_pick.getText().toString().split(":")[1]);
            int eth=Integer.valueOf(end_time_pick.getText().toString().split(":")[0]);
            int etm=Integer.valueOf(end_time_pick.getText().toString().split(":")[1]);

            if(eth * 60 + etm <= sth* 60 + stm){
                Toast.makeText(context,"日程结束时间应晚于起始时间",Toast.LENGTH_SHORT).show();
                return false;
            }
            if(weekday_pick.getText().toString().equals("选择周中次序")){
                Toast.makeText(context,"未选择周中次序",Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }
}
