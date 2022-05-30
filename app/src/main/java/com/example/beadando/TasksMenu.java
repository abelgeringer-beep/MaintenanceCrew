package com.example.beadando;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.LinkedList;

public class TasksMenu extends AppCompatActivity {

    private RecyclerView tasksRV;
    private TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tasksRV = findViewById(R.id.TasksRV);
        LinkedList<Task> items = new LinkedList<>();

        tasksRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(items);
        tasksRV.setAdapter(adapter);

        if (APICalls.getInstance().user.tasks.length > 0)
            Collections.addAll(items, APICalls.getInstance().user.tasks);

        adapter.notifyItemInserted(items.size() - 1);

    }
}
