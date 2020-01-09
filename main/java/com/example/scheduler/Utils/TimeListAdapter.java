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

import com.example.scheduler.Model.Time;
import com.example.scheduler.R;
import com.example.scheduler.View.AddTime;

import java.util.List;

public class TimeListAdapter extends RecyclerView.Adapter <TimeListAdapter.TimeListViewHolder> {
    private TimeRepository repository;
    private Context context;
    private List<Time> list;

    public TimeListAdapter(LifecycleOwner owner, Context context){
        this.context = context;

        repository = new TimeRepository();
        repository.GetTime().observe(owner, new Observer<List<Time>>() {
            @Override
            public void onChanged(List<Time> times) {
                list = times;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public TimeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_time_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeListViewHolder holder, final int position) {
        StringBuilder time=new StringBuilder("");
        time.append(list.get(position).getStart_hour());
        time.append(":");
        time.append(String.format("%02d",list.get(position).getStart_minute()));
        time.append("--");
        time.append(list.get(position).getEnd_hour());
        time.append(":");
        time.append(String.format("%02d",list.get(position).getEnd_minute()));

        holder.label.setText(String.format("时间段 %d", list.get(position).getLabel()));
        holder.time.setText(time);

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
                Intent intent=new Intent(context, AddTime.class);
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

    public void Clear(){
        repository.Clear();
    }

    static class TimeListViewHolder extends RecyclerView.ViewHolder{
        private TextView label, time;
        private Button delete, edit;


        public TimeListViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.time_cell_label);
            time = itemView.findViewById(R.id.time_cell_time);
            delete = itemView.findViewById(R.id.time_cell_delete_btn);
            edit = itemView.findViewById(R.id.time_cell_edit_btn);
        }
    }
}
