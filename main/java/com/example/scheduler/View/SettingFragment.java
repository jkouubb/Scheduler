package com.example.scheduler.View;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.scheduler.R;
import com.example.scheduler.Utils.PickerDialog;
import com.example.scheduler.Utils.Setting;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment{

    private View view;
    private RadioButton theme_black,theme_trans,theme_blue,theme_pink;
    private RadioButton nature,manual;
    private Button first_week,select_background,save, time_btn, import_btn;
    private EditText week_length;
    private Switch setting_switch;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_setting, container, false);
        theme_black=view.findViewById(R.id.setting_black_theme_btn);
        theme_trans=view.findViewById(R.id.setting_tran_theme_btn);
        theme_blue=view.findViewById(R.id.setting_blue_theme_btn);
        theme_pink=view.findViewById(R.id.setting_pink_theme_btn);
        nature=view.findViewById(R.id.setting_nature_week_btn);
        manual=view.findViewById(R.id.setting_manual_week_btn);
        first_week=view.findViewById(R.id.setting_start_week_btn);
        week_length=view.findViewById(R.id.setting_manual_week_lenth_btn);
        select_background=view.findViewById(R.id.setting_select_background_btn);
        save=view.findViewById(R.id.setting_save_btn);
        setting_switch=view.findViewById(R.id.setting_switch_btn);
        time_btn = view.findViewById(R.id.setting_time_btn);
        import_btn = view.findViewById(R.id.setting_import_activity_btn);
        Init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private void Init(){
        switch (Setting.getInstance().getTheme_number()){
            case 1:theme_black.setChecked(true);break;
            case 2:theme_trans.setChecked(true);break;
            case 3:theme_blue.setChecked(true);break;
            case 4:theme_pink.setChecked(true);break;
        }

        if(Setting.getInstance().isNatureWeek()){
            nature.setChecked(true);
        }
        else {
            manual.setChecked(true);
        }

        first_week.setText(String.format("第%d周",Setting.getInstance().getFirstweeknum()));
        first_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialog dialog=new PickerDialog(getContext(),0);
                dialog.setListener(new PickerDialog.ConfirmListener() {
                    @Override
                    public void getText(String string) {
                        first_week.setText(string);
                    }
                });
                dialog.show();
            }
        });

        week_length.setText(String.format("%d",Setting.getInstance().getWeeklength()));

        select_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 9);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        if(Setting.getInstance().isImageShow()){
            setting_switch.setChecked(true);
        }
        else{
            setting_switch.setChecked(false);
        }

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimeList.class);
                startActivity(intent);
            }
        });

        import_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImportActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Save(){
        if(theme_black.isChecked()){
            Setting.getInstance().setTheme_number(1);
        }
        else if(theme_trans.isChecked()){
            Setting.getInstance().setTheme_number(2);
        }
        else if(theme_blue.isChecked()){
            Setting.getInstance().setTheme_number(3);
        }
        else{
            Setting.getInstance().setTheme_number(4);
        }

        if(nature.isChecked()){
            Setting.getInstance().setNatureWeek(true);
        }
        else{
            Setting.getInstance().setNatureWeek(false);
        }

        if(setting_switch.isChecked()){
            Setting.getInstance().setImageShow(true);
        }
        else {
            Setting.getInstance().setImageShow(false);
        }

        Setting.getInstance().setFirstweeknum(Integer.valueOf(first_week.getText().toString().substring(1,first_week.getText().toString().length()-1)));
        Setting.getInstance().setWeeklength(Integer.valueOf(week_length.getText().toString()));

        Setting.getInstance().saveChange();
        getActivity().recreate();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 9:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();//返回的是uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    Setting.getInstance().setBackground(path);
                }
        }
    }
}
