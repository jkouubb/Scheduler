package com.example.scheduler.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduler.R;
import com.example.scheduler.Utils.DeadLineListAdpter;
import com.example.scheduler.Utils.Setting;

public class DeadLineDetail extends AppCompatActivity {
    TextView date;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        setContentView(R.layout.activity_deadline_detail);

        date=findViewById(R.id.deadline_detail_date);
        recyclerView=findViewById(R.id.deadline_detail_list);

        int day=getIntent().getIntExtra("day",0);
        int month=getIntent().getIntExtra("month",0);
        int year=getIntent().getIntExtra("year",0);

        date.setText(String.format("%d月%d日",month,day));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DeadLineListAdpter(this,year,month,day));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
