package com.example.scheduler.Utils;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.scheduler.Model.MyDatabase;
import com.example.scheduler.Model.Todo;
import com.example.scheduler.Model.TodoDao;

import java.util.List;

public class TodoRepository {
    private TodoDao dao;

    public TodoRepository(){
        dao= MyDatabase.getDatabase().getTodoDao();
    }

    public void Insert(Todo... todos){
        new Insert(dao).execute(todos);
    }

    public void Update(Todo... todos){
        new Update(dao).execute(todos);
    }

    public void Delete(Todo... todos){
        new Delete(dao).execute(todos);
    }

    public void Clear(){
        new Clear(dao).execute();
    }

    public LiveData<List<Todo>> SelectTodo(String key){
        return dao.SelectTodo(key);
    }

    public LiveData<Todo> SelectTodoById(int id){
        return dao.SelectTodoById(id);
    }

    static class Insert extends AsyncTask<Todo,Void,Void> {
        private TodoDao dao;

        public Insert(TodoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            dao.Insert(todos);
            return null;
        }
    }

    static class Update extends AsyncTask<Todo,Void,Void>{
        private TodoDao dao;

        public Update(TodoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            dao.Update(todos);
            return null;
        }
    }

    static class Delete extends AsyncTask<Todo,Void,Void>{
        private TodoDao dao;

        public Delete(TodoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            dao.Delete(todos);
            return null;
        }
    }

    static class Clear extends AsyncTask<Void,Void,Void>{
        TodoDao dao;
        public Clear(TodoDao dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.Clear();
            return null;
        }
    }
}
