package com.example.scheduler.Utils;

import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.scheduler.Model.MyDatabase;
import com.example.scheduler.Model.Time;
import com.example.scheduler.Model.TimeDao;

import java.util.List;

public class TimeRepository {
    private TimeDao dao;

    public TimeRepository(){
        this.dao = MyDatabase.getDatabase().getTimeDao();
    }

    public LiveData<List<Time>> GetTime(){
        return dao.GetTime();
    }

    public LiveData<Time> SelectTime(int id){
        return dao.SelectTime(id);
    }


    public void Insert(Time...times){
        new Insert(dao).execute(times);
    }

    public void Update(Time... times){
        new Update(dao).execute(times);
    }

    public void Delete(Time... times){
        new Delete(dao).execute(times);
    }

    public void Clear(){
        new Clear(dao).execute();
    }

    static class Insert extends AsyncTask<Time, Void, Void>{
        private TimeDao dao;

        public Insert(TimeDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            dao.Insert(times);
            return null;
        }
    }

    static class Update extends AsyncTask<Time, Void, Void>{
        private TimeDao dao;

        public Update(TimeDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            dao.Update(times);
            return null;
        }
    }

    static class Delete extends AsyncTask<Time, Void, Void>{
        private TimeDao dao;

        public Delete(TimeDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            dao.Delete(times);
            return null;
        }
    }

    static class Clear extends AsyncTask<Void, Void, Void>{
        private TimeDao  dao;

        public Clear(TimeDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.Clear();
            return null;
        }
    }
}
