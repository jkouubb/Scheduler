package com.example.scheduler.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.scheduler.R;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.Utils.TimeListAdapter;
import com.example.scheduler.databinding.ActivityTimeBinding;

public class TimeList extends AppCompatActivity {
    private ActivityTimeBinding binding;
    private TimeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_time);
        adapter=new TimeListAdapter(this,this);
        binding.timeList.setAdapter(adapter);
        binding.timeList.setLayoutManager(new LinearLayoutManager(this));
        binding.timeList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        binding.timeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOnClick();
            }
        });

        binding.timeClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearOnClick();
            }
        });
    }

    private void AddOnClick(){
        Intent intent=new Intent(this,AddTime.class);
        startActivity(intent);
    }

    private void ClearOnClick(){
        adapter.Clear();
    }
}
