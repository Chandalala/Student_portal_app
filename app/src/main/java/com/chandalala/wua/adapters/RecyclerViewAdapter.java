package com.chandalala.wua.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List list;
    private final int rowLayout;
    private Activity activity;
    private String recycler;

    public RecyclerViewAdapter(List list, @LayoutRes int rowLayout, Activity activity, String recycler) {
        this.list =  list;
        this.rowLayout = rowLayout;
        this.activity=activity;
        this.recycler = recycler;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent,false);

        return new ViewHolder(view, recycler);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        if (recycler.equals("events")){
            viewHolder.getEventsViewHolder(viewHolder, position, activity, list);
        }
        else if (recycler.equals("timetable")){
            viewHolder.getTimetableViewHolder(viewHolder, position, activity, list);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




}
