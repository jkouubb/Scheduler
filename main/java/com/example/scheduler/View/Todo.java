package com.example.scheduler.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.scheduler.R;
import com.example.scheduler.Utils.TodoListAdapter;

public class Todo extends Fragment {

    private View view;
    private SearchView search;
    private Button add,clear;
    private RecyclerView todo_list;
    private TodoListAdapter adapter;


    public static Todo newInstance() {
        return new Todo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_todo, container, false);
        search=view.findViewById(R.id.todo_search);
        add=view.findViewById(R.id.todo_add_btn);
        clear=view.findViewById(R.id.todo_clear_btn);
        todo_list=view.findViewById(R.id.todo_list);

        adapter=new TodoListAdapter(getViewLifecycleOwner(),getContext());
        todo_list.setLayoutManager(new LinearLayoutManager(getContext()));
        todo_list.setAdapter(adapter);
        todo_list.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddTodo.class);
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.Clear();
            }
        });

        search.setIconifiedByDefault(false);
        search.setImeOptions(3);
        search.setQueryHint("输入关键字");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.Load(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    adapter.Load(newText);
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
