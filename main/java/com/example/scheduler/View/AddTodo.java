package com.example.scheduler.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.scheduler.R;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.Utils.TodoRepository;
import com.example.scheduler.databinding.ActivityAddTodoBinding;

import com.example.scheduler.Model.Todo;

import java.util.Calendar;

public class AddTodo extends AppCompatActivity {
    private ActivityAddTodoBinding binding;
    private TodoRepository repository;
    private Todo t=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_todo);

        repository=new TodoRepository();
        int id=getIntent().getIntExtra("id",-1);
        if(id!=-1){
            repository.SelectTodoById(id).observe(this, new Observer<Todo>() {
                @Override
                public void onChanged(Todo todo) {
                    t=todo;
                    Load(todo);
                }
            });
        }

        binding.todoAddSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Onclick();
            }
        });
    }

    private void Load(Todo todo){
        if(todo==null){
            return;
        }

        binding.todoAddName.setText(todo.getName());
        binding.todoAddContent.setText(todo.getContent());
    }

    private boolean check(){
        if(binding.todoAddName.getText().toString().equals("")){
            Toast.makeText(this,"便签名称不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Save_Onclick(){
        if(!check()){
            return;
        }
        if(t==null){
            Todo tmp=new Todo();
            tmp.setName(binding.todoAddName.getText().toString());
            tmp.setContent(binding.todoAddContent.getText().toString());
            Calendar calendar=Calendar.getInstance();
            tmp.setSetup_time(String.format("%d-%d-%d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH)));
            repository.Insert(tmp);
        }
        else{
            t.setName(binding.todoAddName.getText().toString());
            t.setContent(binding.todoAddContent.getText().toString());
            Calendar calendar=Calendar.getInstance();
            t.setSetup_time(String.format("%d-%d-%d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH)));
            repository.Update(t);
        }
        finish();
    }
}
