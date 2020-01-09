package com.example.scheduler.View;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.R;
import com.example.scheduler.Utils.ActivityRepository;
import com.example.scheduler.Utils.PickerDialog;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.databinding.ActivityActivityDetailBinding;

public class ActivityDetail extends AppCompatActivity {
    private int status;
    Activity activity;
    private ActivityRepository repository;
    ActivityActivityDetailBinding binding;
    int start_week, end_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_activity_detail);

        status=0;
        repository=new ActivityRepository();

        repository.SelectActivityById(getIntent().getIntExtra("activity_id",-1)).observe(this, new Observer<Activity>() {
            @Override
            public void onChanged(Activity activity) {
                Load(activity);
            }
        });

        binding.activityDetailDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_Onclicked();
            }
        });

        binding.activityDetailUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_Onclicked();
            }
        });

        binding.activityDetailEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                End_Time_Onclicked();
            }
        });

        binding.activityDetailStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start_Time_Onclicked();
            }
        });

        binding.activityDetailWeekdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weekday_Onclicked();
            }
        });

        binding.activityDetailEndWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                End_Week_Onclicked();
            }
        });

        binding.activityDetailStartWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start_Week_Onclicked();
            }
        });
    }

    private void Load(Activity activity){
        this.activity=activity;
        if(activity==null){
            return;
        }
        if(status==0){
            binding.activityDetailName.setText(activity.getName());
            binding.activityDetailPlace.setText(activity.getPlace());
            binding.activityDetailMember.setText(activity.getMember());
            if(Setting.getInstance().isNatureWeek()){
                binding.activityDetailStartWeekBtn.setText(String.format("第%d周",activity.getStart_week()));
                binding.activityDetailEndWeekBtn.setText(String.format("第%d周",activity.getEnd_week()));
            }
            else{
                binding.activityDetailStartWeekBtn.setText(String.format("第%d周",activity.getStart_week()-Setting.getInstance().getFirstweeknum()+1));
                binding.activityDetailEndWeekBtn.setText(String.format("第%d周",activity.getEnd_week()-Setting.getInstance().getFirstweeknum()+1));
            }
            start_week = activity.getStart_week();
            end_week = activity.getEnd_week();
            binding.activityDetailStartTimeBtn.setText(String.format("%02d:%02d",activity.getStart_hour(),activity.getStart_minute()));
            binding.activityDetailEndTimeBtn.setText(String.format("%02d:%02d",activity.getEnd_hour(),activity.getEnd_minute()));
            switch (activity.getWeekday()){
                case 1:binding.activityDetailWeekdayBtn.setText("周一");break;
                case 2:binding.activityDetailWeekdayBtn.setText("周二");break;
                case 3:binding.activityDetailWeekdayBtn.setText("周三");break;
                case 4:binding.activityDetailWeekdayBtn.setText("周四");break;
                case 5:binding.activityDetailWeekdayBtn.setText("周五");break;
                case 6:binding.activityDetailWeekdayBtn.setText("周六");break;
                case 7:binding.activityDetailWeekdayBtn.setText("周日");break;
            }
            if(activity.getWeek_status()==0){
                binding.activityDetailNormal.setChecked(true);
            }
            else if(activity.getWeek_status()==1){
                binding.activityDetailSingle.setChecked(true);
            }
            else{
                binding.activityDetailDual.setChecked(true);
            }

            binding.activityDetailName.setEnabled(false);
            binding.activityDetailPlace.setEnabled(false);
            binding.activityDetailMember.setEnabled(false);
            binding.activityDetailStartWeekBtn.setClickable(false);
            binding.activityDetailEndWeekBtn.setClickable(false);
            binding.activityDetailStartTimeBtn.setClickable(false);
            binding.activityDetailEndTimeBtn.setClickable(false);
            binding.activityDetailWeekdayBtn.setClickable(false);
            binding.activityDetailSingle.setClickable(false);
            binding.activityDetailDual.setClickable(false);
            binding.activityDetailNormal.setClickable(false);
            binding.activityDetailDeleteBtn.setVisibility(View.VISIBLE);
        }
        else{
            binding.activityDetailName.setEnabled(true);
            binding.activityDetailPlace.setEnabled(true);
            binding.activityDetailMember.setEnabled(true);
            binding.activityDetailStartWeekBtn.setClickable(true);
            binding.activityDetailEndWeekBtn.setClickable(true);
            binding.activityDetailStartTimeBtn.setClickable(true);
            binding.activityDetailEndTimeBtn.setClickable(true);
            binding.activityDetailWeekdayBtn.setClickable(true);
            binding.activityDetailSingle.setClickable(true);
            binding.activityDetailDual.setClickable(true);
            binding.activityDetailNormal.setClickable(true);
            binding.activityDetailDeleteBtn.setVisibility(View.INVISIBLE);
        }
    }

    private boolean CollectData(){
        if(binding.activityDetailName.getText().toString().equals("") || binding.activityDetailMember.getText().toString().equals("") || binding.activityDetailPlace.getText().toString().equals("")){
            Toast.makeText(this,"默认值的日程名、成员、地点不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        activity.setName(binding.activityDetailName.getText().toString());
        activity.setPlace(binding.activityDetailPlace.getText().toString());
        activity.setMember(binding.activityDetailMember.getText().toString());
        activity.setStart_week(start_week);
        activity.setEnd_week(end_week);
        if(binding.activityDetailWeekdayBtn.getText().toString().equals("选择周中次序")){
            Toast.makeText(this,"未选择周中次序",Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (binding.activityDetailWeekdayBtn.getText().toString()){
            case "周一": activity.setWeekday(1);break;
            case "周二": activity.setWeekday(2);break;
            case "周三": activity.setWeekday(3);break;
            case "周四": activity.setWeekday(4);break;
            case "周五": activity.setWeekday(5);break;
            case "周六": activity.setWeekday(6);break;
            case "周日": activity.setWeekday(7);break;
        }
        int sth=Integer.valueOf(binding.activityDetailStartTimeBtn.getText().toString().split(":")[0]);
        int stm=Integer.valueOf(binding.activityDetailStartTimeBtn.getText().toString().split(":")[1]);
        int eth=Integer.valueOf(binding.activityDetailEndTimeBtn.getText().toString().split(":")[0]);
        int etm=Integer.valueOf(binding.activityDetailEndTimeBtn.getText().toString().split(":")[1]);
        if(eth * 60 + etm <= sth* 60 + stm){
            Toast.makeText(this,"日程结束时间应晚于起始时间",Toast.LENGTH_SHORT).show();
            return false;
        }
        activity.setStart_hour(Integer.valueOf(binding.activityDetailStartTimeBtn.getText().toString().split(":")[0]));
        activity.setStart_minute(Integer.valueOf(binding.activityDetailStartTimeBtn.getText().toString().split(":")[1]));
        activity.setEnd_hour(Integer.valueOf(binding.activityDetailEndTimeBtn.getText().toString().split(":")[0]));
        activity.setEnd_minute(Integer.valueOf(binding.activityDetailEndTimeBtn.getText().toString().split(":")[1]));
        return true;
    }

    private void Start_Week_Onclicked(){
        PickerDialog dialog=new PickerDialog(ActivityDetail.this,0);
        dialog.setListener(new PickerDialog.ConfirmListener() {
            @Override
            public void getText(String string) {
                binding.activityDetailStartWeekBtn.setText(string);
                if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
                    start_week=Integer.valueOf(string.substring(1,string.length()-1));
                }
                else{
                    start_week=Integer.valueOf(string.substring(1,string.length()-1))+com.example.scheduler.Utils.Setting.getInstance().getFirstweeknum()-1;
                }
            }
        });
        dialog.show();
    }

    private void End_Week_Onclicked(){
        PickerDialog dialog=new PickerDialog(ActivityDetail.this,0);
        dialog.setListener(new PickerDialog.ConfirmListener() {
            @Override
            public void getText(String string) {
                binding.activityDetailEndWeekBtn.setText(string);
                if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
                    end_week=Integer.valueOf(string.substring(1,string.length()-1));
                }
                else{
                    end_week=Integer.valueOf(string.substring(1,string.length()-1))+com.example.scheduler.Utils.Setting.getInstance().getFirstweeknum()-1;
                }
            }
        });
        dialog.show();
    }

    private void Start_Time_Onclicked(){
        new TimePickerDialog(ActivityDetail.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str=String.format("%02d:%02d",hourOfDay,minute);
                binding.activityDetailStartTimeBtn.setText(str);
            }
        },0,0,true).show();
    }

    private void End_Time_Onclicked(){
        new TimePickerDialog(ActivityDetail.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str=String.format("%02d:%02d",hourOfDay,minute);
                binding.activityDetailEndTimeBtn.setText(str);
            }
        },0,0,true).show();
    }

    private void Weekday_Onclicked(){
        PickerDialog dialog=new PickerDialog(ActivityDetail.this,1);
        dialog.setListener(new PickerDialog.ConfirmListener() {
            @Override
            public void getText(String string) {
                binding.activityDetailWeekdayBtn.setText(string);

            }
        });
        dialog.show();
    }

    private void Update_Onclicked(){
        if(status==0){
            status=1;
            binding.activityDetailUpdateBtn.setText("保存");
            Load(activity);
        }
        else if(CollectData()){

            repository.Update(activity);
            finish();
        }
    }

    private void Delete_Onclicked(){
        repository.Delete(activity);
        activity=null;
        finish();
    }

}
