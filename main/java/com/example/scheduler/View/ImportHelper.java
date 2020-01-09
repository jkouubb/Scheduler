package com.example.scheduler.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scheduler.R;
import com.example.scheduler.Utils.Setting;

public class ImportHelper extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        setContentView(R.layout.activity_import_help);

        TextView help_text = findViewById(R.id.helper_text);

        help_text.setText("\n"+getString(R.string.import_step1)+"\n");
        help_text.append("\n"+getString(R.string.import_step2)+"\n");
        help_text.append("\n"+getString(R.string.import_step3)+"\n");

        TextView support_text = findViewById(R.id.school_text);
        support_text.setText("\n"+getString(R.string.support_school));
    }
}
