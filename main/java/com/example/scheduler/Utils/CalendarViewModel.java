package com.example.scheduler.Utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.scheduler.Model.Activity;
import com.example.scheduler.Model.DeadLine;

import java.util.List;

public class CalendarViewModel extends ViewModel {
    private ActivityRepository activity_repo=new ActivityRepository();
    private LiveData<List<Activity>> activities=new LiveData<List<Activity>>() {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Activity>> observer) {
            super.observe(owner, observer);
        }
    };
    private DeadLineRepository deadline_repo=new DeadLineRepository();

    public void Load(int week){
        activities=activity_repo.SelectActivityByWeek(week);
    }

    public LiveData<List<Activity>> getActivity(){
        return activities;
    }

    public LiveData<List<DeadLine>> getDeadLine(int year,int month,int day){
        return deadline_repo.SelectDeadLineByDate(year, month, day);
    }

    public void ClearActivity(){
        activity_repo.Clear();
    }

    public void ClearDeadLine(){
        deadline_repo.Clear();
    }
}
