package com.example.scheduler.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduler.Model.DeadLine;
import com.example.scheduler.R;

import java.util.List;

public class DeadLineListAdpter extends RecyclerView.Adapter <DeadLineListAdpter.DeadLineListViewHolder>{
    private List<DeadLine> list;
    private DeadLineRepository repository;

    public DeadLineListAdpter(LifecycleOwner owner,int year, int month, int day) {
        repository=new DeadLineRepository();
        repository.SelectDeadLineByDate(year,month,day).observe(owner,new Observer<List<DeadLine>>() {
            @Override
            public void onChanged(List<DeadLine> deadLines) {
                list=deadLines;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public DeadLineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeadLineListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deadline_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeadLineListViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.time.setText(String.format("%02d:%02d",list.get(position).getHour(),list.get(position).getMinute()));
        holder.content.setText(list.get(position).getContent());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.Delete(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
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


     static class DeadLineListViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,content;
        ImageView button;


        public DeadLineListViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.deadline_cell_name);
            time=itemView.findViewById(R.id.deadline_ceel_time);
            content=itemView.findViewById(R.id.deadline_cell_content);
            button=itemView.findViewById(R.id.deadline_cell_checkbutton);

        }
    }
}
