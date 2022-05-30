package com.example.beadando;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TaskVH extends RecyclerView.ViewHolder implements Observer {

    public TextView description;
    public ImageView doneIV, pendingIV;
    public int currentCardId;
    private TasksAdapter adapter;

    public TaskVH(@NonNull View itemView) {
        super(itemView);
        observable.getInstance().addObserver(this);

        initialSetup();

        itemView.findViewById(R.id.TaskCV).setOnClickListener(view -> getPopUp(itemView));
        currentCardId = -1;

    }

    private void getPopUp(View view) {
        Intent i = new Intent(view.getContext(), Pop.class);
        currentCardId = getLayoutPosition();
        i.putExtra("pos", currentCardId);
        view.getContext().startActivity(i);
    }

    public TaskVH linkAdapter(TasksAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    private void initialSetup() {
        description = itemView.findViewById(R.id.TaskTV);
        doneIV = itemView.findViewById(R.id.DoneIV);
        pendingIV = itemView.findViewById(R.id.PendingIV);

        doneIV.setVisibility(View.INVISIBLE);
        pendingIV.setVisibility(View.INVISIBLE);
    }

    public void setStatusImages(String status, String position) {
        int pos = Integer.parseInt(position);
        if (currentCardId == pos)
            switch (status) {
                case "finished":
                    pendingIV.setVisibility(View.INVISIBLE);
                    doneIV.setVisibility(View.VISIBLE);
                    break;
                case "accepted":
                    pendingIV.setVisibility(View.VISIBLE);
                    doneIV.setVisibility(View.INVISIBLE);
                    break;
                case "":
                    pendingIV.setVisibility(View.INVISIBLE);
                    break;
                case "finish":
                    doneIV.setVisibility(View.INVISIBLE);
                    pendingIV.setVisibility(View.VISIBLE);
                    break;
                default:
                    Log.d("error", "something went wrong");
                    break;
            }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!(observable instanceof observable))
            return;

        List<String> status = Arrays.asList(o.toString().split(";"));

        if (status.get(0).equals("save")) {
            if (status.get(2).equals(currentCardId + ""))
                description.setText(status.get(1));
            return;
        }

        setStatusImages(status.get(0), status.get(1));
    }
}
