package com.example.scheduler.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduler.Model.Todo;
import com.example.scheduler.R;
import com.example.scheduler.View.AddTodo;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter <TodoListAdapter.TodoListViewHolder> {
    private TodoRepository repository;
    private List<Todo>  list;
    private LifecycleOwner owner;
    private Context context;

    public TodoListAdapter(LifecycleOwner owner, Context context) {
        this.owner=owner;
        this.context=context;
        repository=new TodoRepository();
        repository.SelectTodo("%%").observe(owner, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                list=todos;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todo_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, final int position) {
        holder.time.setText(list.get(position).getSetup_time());
        holder.name.setText(list.get(position).getName());
        holder.content.setText(list.get(position).getContent());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.Delete(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddTodo.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public void Load(String key){
        repository.SelectTodo("%"+key+"%").observe(owner, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                list=todos;
                notifyDataSetChanged();
            }
        });
    }

    public void Clear(){
        repository.Clear();
    }

    static class TodoListViewHolder extends RecyclerView.ViewHolder{
        private TextView time,name,content;
        private Button delete,edit;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time_cell_start_time);
            name=itemView.findViewById(R.id.time_cell_end_time);
            content=itemView.findViewById(R.id.todo_cell_content);
            delete=itemView.findViewById(R.id.time_cell_delete_btn);
            edit=itemView.findViewById(R.id.time_cell_edit_btn);
        }
    }
}
