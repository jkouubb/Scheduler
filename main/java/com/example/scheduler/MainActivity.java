package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.scheduler.Model.MyDatabase;
import com.example.scheduler.Utils.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setting.InitInstance(getSharedPreferences("setting", Context.MODE_PRIVATE));

        switch (Setting.getInstance().getTheme_number()){
            case 1:setTheme(R.style.Darkula);break;
            case 2:setTheme(R.style.trans);break;
            case 3:setTheme(R.style.blue);break;
            case 4:setTheme(R.style.pink);break;
        }

        setContentView(R.layout.activity_main);
        verifyStoragePermissions();

        if(Setting.getInstance().isImageShow() && !Setting.getInstance().getBackground().equals("null")){
            try{

                FileInputStream file=new FileInputStream(Setting.getInstance().getBackground());
                Bitmap bitmap= BitmapFactory.decodeStream(file);

                ConstraintLayout layout1 = findViewById(R.id.background);
                layout1.setBackground(new BitmapDrawable(getResources(), bitmap));
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        MyDatabase.initDatabase(getApplicationContext());

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        NavController controller= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,controller);

    }

    private void verifyStoragePermissions() {
        String[] WRITE = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        String[] READ = {"android.permission.READ_EXTERNAL_STORAGE"};
        String[] INTERNET = {"android.permission.INTERNET"};
        int REQUEST_EXTERNAL_STORAGE = 1;

        try {
            int permission = ActivityCompat.checkSelfPermission(MainActivity.this,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, READ,REQUEST_EXTERNAL_STORAGE);
            }

            int permission2=ActivityCompat.checkSelfPermission(MainActivity.this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, WRITE,REQUEST_EXTERNAL_STORAGE);
            }

            int permission3=ActivityCompat.checkSelfPermission(MainActivity.this,
                    "android.permission.INTERNET");
            if (permission3 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, INTERNET,1);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
