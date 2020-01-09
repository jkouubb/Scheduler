package com.example.scheduler.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.scheduler.Model.DeadLine;
import com.example.scheduler.R;
import com.example.scheduler.Utils.DeadLineRepository;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.databinding.ActivityAddDeadlineBinding;

import java.util.Calendar;

public class AddDeadLine extends AppCompatActivity {
    private DeadLineRepository repository;
    private ActivityAddDeadlineBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_deadline);

        repository=new DeadLineRepository();

        binding.ddlAddTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time_Onclick();
            }
        });

        binding.ddlAddDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date_Onclick();
            }
        });

        binding.ddlAddSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit_Onclick();
            }
        });

    }

    private boolean check(){
        if(binding.ddlAddName.getText().toString().equals("")){
            Toast.makeText(this,"ddl名称不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.ddlAddDateBtn.getText().toString().equals("未选择日期") || binding.ddlAddTimeBtn.getText().toString().equals("未选择时间")){
            Toast.makeText(this,"未选择时间或日期",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Date_Onclick(){
        Calendar calendar=Calendar.getInstance();
        new DatePickerDialog(AddDeadLine.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String str=String.format("%d-%d-%d",year,month+1,dayOfMonth);
                binding.ddlAddDateBtn.setText(str);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void Time_Onclick(){
         new TimePickerDialog(AddDeadLine.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str=String.format("%02d:%02d",hourOfDay,minute);
                binding.ddlAddTimeBtn.setText(str);
            }
        },0,0,true).show();
    }

    private void Submit_Onclick(){
        if(!check()){
            return;
        }
        DeadLine deadLine=new DeadLine();
        deadLine.setName(binding.ddlAddName.getText().toString());
        deadLine.setContent(binding.ddlAddContent.getText().toString());
        deadLine.setYear(Integer.valueOf(binding.ddlAddDateBtn.getText().toString().split("-")[0]));
        deadLine.setMonth(Integer.valueOf(binding.ddlAddDateBtn.getText().toString().split("-")[1]));
        deadLine.setDay(Integer.valueOf(binding.ddlAddDateBtn.getText().toString().split("-")[2]));
        deadLine.setHour(Integer.valueOf(binding.ddlAddTimeBtn.getText().toString().split(":")[0]));
        deadLine.setMinute(Integer.valueOf(binding.ddlAddTimeBtn.getText().toString().split(":")[1]));

        repository.Insert(deadLine);

        Intent intent=new Intent();
        setResult(android.app.Activity.RESULT_OK,intent);
        finish();
    }
}
