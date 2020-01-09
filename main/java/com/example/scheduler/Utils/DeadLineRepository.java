package com.example.scheduler.Utils;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.DeadLine;
import com.example.scheduler.Model.DeadLineDao;
import com.example.scheduler.Model.MyDatabase;

import java.util.List;

public class DeadLineRepository {
    private DeadLineDao dao;

    public DeadLineRepository(){
        dao= MyDatabase.getDatabase().getDeadLineDao();
    }

    public void Insert(DeadLine... deadLines){
        new Insert(dao).execute(deadLines);
    }

    public void Update(DeadLine... deadLines){
        new Update(dao).execute(deadLines);
    }

    public void Delete(DeadLine... deadLines){
        new Delete(dao).execute(deadLines);
    }

    public void Clear(){
        new Clear(dao).execute();
    }

    public LiveData<List<DeadLine>> SelectDeadLineByDate(int year,int month,int day){
        return dao.SelectDeadLineByDate(year,month,day);
    }

    static class Insert extends AsyncTask<DeadLine,Void,Void> {
        private DeadLineDao dao;

        public Insert(DeadLineDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DeadLine... deadLines) {
            dao.Insert(deadLines);
            return null;
        }
    }

    static class Update extends AsyncTask<DeadLine,Void,Void>{
        private DeadLineDao dao;

        public Update(DeadLineDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DeadLine... deadLines) {
            dao.Update(deadLines);
            return null;
        }
    }

    static class Delete extends AsyncTask<DeadLine,Void,Void>{
        private DeadLineDao dao;

        public Delete(DeadLineDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DeadLine... deadLines) {
            dao.Delete(deadLines);
            return null;
        }
    }

    static class Clear extends AsyncTask<Void,Void,Void>{
        DeadLineDao dao;
        public Clear(DeadLineDao dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.Clear();
            return null;
        }
    }
}
