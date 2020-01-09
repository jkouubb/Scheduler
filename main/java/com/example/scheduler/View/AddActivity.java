package com.example.scheduler.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.R;
import com.example.scheduler.Utils.ActivityRepository;
import com.example.scheduler.Utils.CardAdapter;
import com.example.scheduler.Utils.PickerDialog;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.databinding.ActivityAddActivityBinding;

import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private ActivityRepository repository;
    private String[] week;
    public CardAdapter adapter;
    private int start_week,end_week;
    ActivityAddActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_activity);

        binding.activityAddAddCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addCard();
            }
        });

        binding.activityAddEndWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                End_Week_Onclick();
            }
        });

        binding.activityAddStartWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start_Week_Onclick();
            }
        });

        binding.activityAddSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit_Onclick();
            }
        });

        binding.avtivityAddRemoveCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCard();
            }
        });

        Init();
    }

    private void Init(){
        repository=new ActivityRepository();

        if(com.example.scheduler.Utils.Setting.getInstance().isNatureWeek()){
            java.util.Calendar calendar= java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.YEAR, java.util.Calendar.DECEMBER,31);
            week=new String[calendar.get(Calendar.WEEK_OF_YEAR)];
        }
        else{
            week=new String[com.example.scheduler.Utils.Setting.getInstance().getWeeklength()];
        }

        for(int i=0; i<week.length; i++){
            week[i]=String.format("第%d周",i+1);
        }
        adapter=new CardAdapter(AddActivity.this);
        binding.activitiAddCardlist.setAdapter(adapter);
        binding.activitiAddCardlist.setLayoutManager(new LinearLayoutManager(AddActivity.this));
        binding.activitiAddCardlist.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private boolean check(){
        if(binding.activityAddName.getText().toString().equals("") || binding.activityAddMember.getText().toString().equals("") || binding.activityAddPlace.getText().toString().equals("")){
            Toast.makeText(this,"默认值的日程名、成员、地点不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.activityAddStartWeekBtn.getText().toString().equals("未选择起始周次") || binding.activityAddEndWeekBtn.getText().toString().equals("未选择结束周次")){
            Toast.makeText(this,"未选择起始或结束周次",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(end_week<start_week){
            Toast.makeText(this,"结束周次不能早于开始周次",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!binding.activityAddSingle.isChecked() && !binding.activityAddDual.isChecked() && !binding.activityAddNormal.isChecked()){
            Toast.makeText(this,"必须选择日程的单双周类型",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Start_Week_Onclick(){
        PickerDialog dialog=new PickerDialog(AddActivity.this,0);
        dialog.setListener(new PickerDialog.ConfirmListener() {
            @Override
            public void getText(String string) {
                binding.activityAddStartWeekBtn.setText(string);
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

    private void End_Week_Onclick(){
        PickerDialog dialog=new PickerDialog(AddActivity.this,0);
        dialog.setListener(new PickerDialog.ConfirmListener() {
            @Override
            public void getText(String string) {
                binding.activityAddEndWeekBtn.setText(string);
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

    private void Submit_Onclick(){
        List<Activity> activities=adapter.getActivity();
        if(activities==null || !check()){
            return;
        }
        for(int i=0; i<activities.size(); i++){
            Activity activity=activities.get(i);
            activity.setName(binding.activityAddName.getText().toString());
            if(activity.getPlace().equals("null")){
                activity.setPlace(binding.activityAddPlace.getText().toString());
            }
            if(activity.getMember().equals("null")){
                activity.setMember(binding.activityAddMember.getText().toString());
            }
            activity.setStart_week(start_week);
            activity.setEnd_week(end_week);
            if(binding.activityAddSingle.isChecked()){
                activity.setWeek_status(1);
            }
            else if(binding.activityAddDual.isChecked()){
                activity.setWeek_status(2);
            }
            else {
                activity.setWeek_status(0);
            }
            repository.Insert(activity);
            Intent intent=new Intent();
            setResult(android.app.Activity.RESULT_OK,intent);
            finish();
        }
    }

}
