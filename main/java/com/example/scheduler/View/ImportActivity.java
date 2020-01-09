package com.example.scheduler.View;

import android.content.Context;;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.example.scheduler.R;
import com.example.scheduler.Utils.ActivityHelper;
import com.example.scheduler.Utils.Setting;
import com.example.scheduler.databinding.ActivityImportActivityBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ImportActivity extends AppCompatActivity {
    private WebSettings settings;
    private ActivityImportActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (Setting.getInstance().getTheme_number()) {
            case 1:
                setTheme(R.style.Darkula);
                break;
            case 2:
                setTheme(R.style.trans);
                break;
            case 3:
                setTheme(R.style.blue);
                break;
            case 4:
                setTheme(R.style.pink);
                break;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_import_activity);

        binding.importActivityOpenWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenWebOnClick();
            }
        });

        binding.importActivityImportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReadWeb(binding.importActivityWebview.getUrl(), CookieManager.getInstance().getCookie(binding.importActivityWebview.getUrl()), ImportActivity.this,ImportActivity.this).execute();
            }
        });

        binding.importActivityHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportActivity.this,ImportHelper.class);
                startActivity(intent);
            }
        });

    }

    private void OpenWebOnClick(){
        String url = binding.importActivityWebInput.getText().toString();
        settings = binding.importActivityWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        binding.importActivityWebview.loadUrl(url);
        binding.importActivityWebview.onResume();
        binding.importActivityWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    static class ReadWeb extends AsyncTask<Void, Void, Boolean> {
        String url;
        String cookie;
        Context context;
        LifecycleOwner owner;
        ActivityHelper helper;

        public ReadWeb(String url, String cookie,  Context context, LifecycleOwner owner) {
            this.url = url;
            this.cookie = cookie;
            this.context = context;
            this.owner = owner;
            helper = new ActivityHelper(owner);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                Document doc = new Document("");
                Elements lines = new Elements();

                do{
                    doc = Jsoup.connect(url).cookie("JSESSIONID", cookie).post();
                    lines = doc.getElementsByTag("tr");
                }while(lines.size()==0);

                helper.BIT_Generater(lines);
            }
            catch (IOException e){
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Toast.makeText(context,"导入成功",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"导入失败",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
