package com.example.scheduler.Utils;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.Model.ActivityDao;
import com.example.scheduler.Model.MyDatabase;
import com.example.scheduler.Model.Time;

import java.util.List;

public class ActivityRepository {
    private ActivityDao dao;

    public ActivityRepository(){
        dao=MyDatabase.getDatabase().getActivityDao();
    }

    public void Insert(Activity... activities){
        new Insert(dao).execute(activities);
    }

    public void Update(Activity... activities){
        new Update(dao).execute(activities);
    }

    public void Delete(Activity... activities){
        new Delete(dao).execute(activities);
    }

    public void Clear(){
        new Clear(dao).execute();
    }

    public LiveData<List<Activity>> SelectActivityByWeek(int week){
        return dao.SelectActivityByWeek(week);
    }

    public LiveData<Activity> SelectActivityById(int id){
        return dao.SelectActivityById(id);
    }

    static class Insert extends AsyncTask<Activity,Void,Void>{
        private ActivityDao dao;

        public Insert(ActivityDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Activity... activities) {
            dao.Insert(activities);
            return null;
        }
    }

    static class Update extends AsyncTask<Activity,Void,Void>{
        private ActivityDao dao;

        public Update(ActivityDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Activity... activities) {
            dao.Update(activities);
            return null;
        }
    }

    static class Delete extends AsyncTask<Activity,Void,Void>{
        private ActivityDao dao;

        public Delete(ActivityDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Activity... activities) {
            dao.Delete(activities);
            return null;
        }
    }

    static class Clear extends AsyncTask<Void,Void,Void>{
        ActivityDao dao;
        public Clear(ActivityDao dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.Clear();
            return null;
        }
    }

}
