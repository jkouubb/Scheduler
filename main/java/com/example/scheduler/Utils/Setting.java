package com.example.scheduler.Utils;


import android.content.SharedPreferences;

import java.util.Calendar;

public class Setting {
    static private Setting instance;
    static public void InitInstance(SharedPreferences sharedPreferences){
        if(instance==null){
            instance=new Setting(sharedPreferences);
        }
    }

    static public Setting getInstance(){
        return instance;
    }

    private int theme_number;
    private boolean isNatureWeek;
    private int firstweeknum;
    private int weeklength;
    private String background;
    private SharedPreferences.Editor editor;
    private boolean imageShow;

    private Setting(SharedPreferences sharedPreferences) {
        editor=sharedPreferences.edit();
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.YEAR, java.util.Calendar.DECEMBER,31);

        theme_number=sharedPreferences.getInt("theme_number",1);
        isNatureWeek=sharedPreferences.getBoolean("is_Nature_Week",true);
        firstweeknum=sharedPreferences.getInt("first_week_number",1);
        weeklength=sharedPreferences.getInt("week_length",calendar.get(Calendar.WEEK_OF_YEAR));
        background=sharedPreferences.getString("background","null");
        imageShow=sharedPreferences.getBoolean("image_Show",false);
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
        editor.putString("background",background);
    }

    public int getTheme_number() {
        return theme_number;
    }

    public boolean isNatureWeek() {
        return isNatureWeek;
    }

    public int getFirstweeknum() {
        return firstweeknum;
    }

    public int getWeeklength() {
        return weeklength;
    }

    public void setTheme_number(int theme_number) {
        this.theme_number = theme_number;
        editor.putInt("theme_number",theme_number);
    }

    public void setNatureWeek(boolean natureWeek) {
        isNatureWeek = natureWeek;
        editor.putBoolean("is_Nature_Week",natureWeek);
    }

    public void setFirstweeknum(int firstweeknum) {
        this.firstweeknum = firstweeknum;
        editor.putInt("first_week_number",firstweeknum);
    }

    public void setWeeklength(int weeklength) {
        this.weeklength = weeklength;
        editor.putInt("week_length",weeklength);
    }

    public void saveChange(){
        editor.commit();
    }

    public boolean isImageShow() {
        return imageShow;
    }

    public void setImageShow(boolean imageShow) {
        this.imageShow = imageShow;
        editor.putBoolean("image_Show",imageShow);
    }
}
