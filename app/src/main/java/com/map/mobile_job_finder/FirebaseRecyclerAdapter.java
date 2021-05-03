package com.map.mobile_job_finder;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class FirebaseRecyclerAdapter <T, T1> extends RecyclerView.Adapter {
    public FirebaseRecyclerAdapter(Class<T> dataClass, int alljobpost, Class<T1> allJobPostViewHolderClass, DatabaseReference mAllJobPost) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    protected void populateViewHolder(AllJobActivity.AllJobPostViewHolder viewHolder, Data model, int position){
//        viewHolder.setJobTitle(model.getTitle());
//        viewHolder.setJobDate(model.getDate());
//        viewHolder.setJobDesc(model.getDescription());
//        viewHolder.setJobSkills(model.getSkills());
//        viewHolder.setJobSalary(model.getSalary());
//    }
//
//    protected abstract void populateViewHolder(PostJobActivity.MyViewHolder viewHolder, Data model, int position);
}
