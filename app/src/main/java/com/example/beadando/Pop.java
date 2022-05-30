package com.example.beadando;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pop extends Activity {

    private TextView changedAt, finishedAt, startedAt;
    private EditText taskDescriptionET;
    private Button saveBtn, doneTaskBtn, acceptTaskBtn;

    private Task currentTask;

    private boolean accepted, done;
    private int taskPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskpopupwindow);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskPosition = extras.getInt("pos");
        }

        initialSetup();
        setupListeners();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.8));
    }

    private void setupListeners() {
        saveBtn.setOnClickListener(view -> saveTask());

        doneTaskBtn.setOnClickListener(view -> doneTask());

        acceptTaskBtn.setOnClickListener(view -> acceptTask());
    }

    private void initialSetup() {
        done = false;
        accepted = false;
        taskDescriptionET = findViewById(R.id.DescriptionMTL);
        saveBtn = findViewById(R.id.SaveBtn);
        doneTaskBtn = findViewById(R.id.doneTaskBtn);
        acceptTaskBtn = findViewById(R.id.acceptTaskBtn);
        changedAt = findViewById(R.id.ChangedAtTV);
        startedAt = findViewById(R.id.StartedAtTV);
        finishedAt = findViewById(R.id.FinishedAtTV);
        currentTask = APICalls.getInstance().user.tasks[taskPosition];

        taskDescriptionET.setText(currentTask.details);
        changedAt.setText(currentTask.statusChangedAt);
        finishedAt.setText(currentTask.startedAt);
        startedAt.setText(currentTask.finishedAt);
        doneTaskBtn.setText(currentTask.finishedAt.equals("") ? "finish" : "finished");
        acceptTaskBtn.setText(currentTask.status.equals("accepted") ? "accept" : "accepted");
        accepted = currentTask.status.equals("accepted") || currentTask.status.equals("finished");
        done = currentTask.status.equals("finished");

        setStatusStartedAt(accepted);
        setStatusFinishedAt(done);
        setStatusChangedAt(false);
    }

    @SuppressLint("SetTextI18n")
    private void acceptTask() {
        if (done)
            return;

        accepted = !accepted;
        doneTaskBtn.setActivated(accepted);

        currentTask.status = accepted ? "accepted" : "";
        acceptTaskBtn.setText(accepted ? "accepted" : "accept");

        setStatusChangedAt(true);
        setStatusStartedAt(accepted);

        observable.getInstance().sendData(currentTask.status + ";" + taskPosition);
    }

    private void doneTask() {
        if (!accepted)
            return;

        done = !done;
        acceptTaskBtn.setActivated(!done);

        currentTask.status = done ? "finished" : "accepted";
        doneTaskBtn.setText(done ? "finished" : "finish");

        setStatusFinishedAt(done);
        setStatusChangedAt(true);

        observable.getInstance().sendData(currentTask.status + ";" + taskPosition);
    }

    @SuppressLint("SetTextI18n")
    private void setStatusChangedAt(boolean changed) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String date = !changed ? currentTask.statusChangedAt : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            changedAt.setText("changed at: " + date);
            currentTask.statusChangedAt = date;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setStatusStartedAt(boolean started) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String date = started
                ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
                : currentTask.startedAt;
            startedAt.setText("started at: " + date);
            currentTask.startedAt = date;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setStatusFinishedAt(boolean finished) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String date = finished
                ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
                : "";
            finishedAt.setText("finished at: " + date);
            currentTask.finishedAt = date;
        }
    }

    private void saveTask() {
        observable.getInstance().sendData("save" + ";" + taskDescriptionET.getText() + ";" + taskPosition);
        currentTask.details = taskDescriptionET.getText().toString();
        Toast.makeText(this, APICalls.getInstance().saveTaskChanges(taskPosition), Toast.LENGTH_SHORT).show();
    }
}
