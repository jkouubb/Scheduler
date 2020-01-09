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

import com.example.scheduler.Model.Time;
import com.example.scheduler.R;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.Utils.TimeRepository;
import com.example.scheduler.databinding.ActivityAddTimeBinding;

public class AddTime extends AppCompatActivity {
    private ActivityAddTimeBinding binding;
    private TimeRepository repository;
    private Time t=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_time);

        repository=new TimeRepository();
        int id=getIntent().getIntExtra("id",-1);
        if(id!=-1){
            repository.SelectTime(id).observe(this, new Observer<Time>() {
                @Override
                public void onChanged(Time time) {
                    t=time;
                    Load(time);
                }
            });
        }

        binding.addTimeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Onclick();
            }
        });

        binding.addTimeStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddTime.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String str=String.format("%02d:%02d",hourOfDay,minute);
                        binding.addTimeStartTimeBtn.setText(str);
                    }
                },0,0,true).show();
            }
        });

        binding.addTimeEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddTime.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String str=String.format("%02d:%02d",hourOfDay,minute);
                        binding.addTimeEndTimeBtn.setText(str);
                    }
                },0,0,true).show();
            }
        });
    }

    private void Load(Time time){
        if(time==null){
            return;
        }

        binding.addTimeLabelInput.setText(time.getId());

        StringBuilder start_time=new StringBuilder(time.getStart_hour());
        start_time.append(":");
        start_time.append(time.getStart_minute());
        binding.addTimeStartTimeBtn.setText(start_time);

        StringBuilder end_time=new StringBuilder(time.getEnd_hour());
        end_time.append(":");
        end_time.append(time.getEnd_minute());
        binding.addTimeEndTimeBtn.setText(end_time);
    }

    private boolean check(){
        int start_hour = Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[0]);
        int start_minute = Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[1]);
        int end_hour = Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[0]);
        int end_minute = Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[1]);

        if(end_hour * 60 +end_minute < start_hour * 60 + start_minute){
            Toast.makeText(this,"结束时间应晚于起始时间",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Save_Onclick(){
        if(!check()){
            return;
        }
        if(t==null){
           Time tmp = new Time();
           tmp.setLabel(Integer.valueOf(binding.addTimeLabelInput.getText().toString()));
           tmp.setStart_hour(Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[0]));
           tmp.setStart_minute(Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[1]));
           tmp.setEnd_hour(Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[0]));
           tmp.setEnd_minute(Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[1]));
            repository.Insert(tmp);
        }
        else{
            t.setLabel(Integer.valueOf(binding.addTimeLabelInput.getText().toString()));
            t.setStart_hour(Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[0]));
            t.setStart_minute(Integer.valueOf(binding.addTimeStartTimeBtn.getText().toString().split(":")[1]));
            t.setEnd_hour(Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[0]));
            t.setEnd_minute(Integer.valueOf(binding.addTimeEndTimeBtn.getText().toString().split(":")[1]));
            repository.Update(t);
        }
        finish();
    }
}
