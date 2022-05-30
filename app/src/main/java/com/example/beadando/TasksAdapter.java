package com.example.beadando;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TaskVH> {

    public List<Task> tasks;

    public TasksAdapter(LinkedList<Task> items) {
        tasks = items;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskVH(v).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {
        holder.description.setText(tasks.get(position).details);
        String status = tasks.get(position).status;
        if (status.equals("finished")) {
            holder.doneIV.setVisibility(View.VISIBLE);
        } else if (status.equals("accepted")) {
            holder.pendingIV.setVisibility(View.VISIBLE);
        } else {
            holder.pendingIV.setVisibility(View.INVISIBLE);
            holder.doneIV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
