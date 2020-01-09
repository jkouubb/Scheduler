package com.example.scheduler.Utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.scheduler.R;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PickerDialog extends Dialog {
    private Context context;
    private LoopView picker;
    private Button confirm,cancel;
    private ConfirmListener listener;
    private int status;
    private List<String> list;

    public PickerDialog(@NonNull Context context,int status) {
        super(context);
        this.context=context;
        this.status=status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_dialog);
        picker=findViewById(R.id.dialog_picker);

        confirm=findViewById(R.id.dialog_confirm_btn);
        cancel=findViewById(R.id.dialog_cancel_btn);

        if(status==0){
            list=new ArrayList<>();
            if(Setting.getInstance().isNatureWeek()){
                java.util.Calendar calendar= java.util.Calendar.getInstance();
                calendar.set(java.util.Calendar.YEAR, java.util.Calendar.DECEMBER,31);
                for(int i=0; i<calendar.get(Calendar.WEEK_OF_YEAR);i++){
                    list.add(String.format("第%d周",i+1));
                }
            }
            else{
                for(int i=0; i<Setting.getInstance().getWeeklength();i++){
                    list.add(String.format("第%d周",i+1));
                }
            }

            picker.setItems(list);
            picker.setInitPosition(0);//初始化，设置默认选项
            picker.setTextSize(14);
            picker.setListener(new OnItemSelectedListener() {//设置监听
                @Override
                public void onItemSelected(int index) {
                    ;
                }
            });
        }
        else{
            list=new ArrayList<>();
            list.add("周一");
            list.add("周二");
            list.add("周三");
            list.add("周四");
            list.add("周五");
            list.add("周六");
            list.add("周日");

            picker.setItems(list);
            picker.setInitPosition(0);//初始化，设置默认选项
            picker.setTextSize(14);
            picker.setListener(new OnItemSelectedListener() {//设置监听
                @Override
                public void onItemSelected(int index) {
                    ;
                }
            });
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getText(list.get(picker.getSelectedItem()));
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface ConfirmListener{
        void getText(String string);
    }

    public void setListener(ConfirmListener listener){
        this.listener=listener;
    }
}
