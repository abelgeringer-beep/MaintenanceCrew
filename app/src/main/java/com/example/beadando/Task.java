package com.example.beadando;

import androidx.annotation.NonNull;

public class Task {
    public int taskId;
    public int reportId;
    public int assigned_user_id;
    public int maintenance_id;
    public String status;
    public String taskProgress;
    public String details;
    public String startedAt;
    public String finishedAt;
    public String statusChangedAt;

    public Task(int taskId, int reportId, int assigned_user_id,
                String statusChangedAt, int maintenance_id, String details,
                String taskProgress, String startedAt, String finishedAt) {
        this.taskId = taskId;
        this.reportId = reportId;
        this.assigned_user_id = assigned_user_id;
        this.statusChangedAt = statusChangedAt;
        this.maintenance_id = maintenance_id;
        this.details = details;
        this.taskProgress = taskProgress;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.status = taskProgress;
    }

    @NonNull
    public String toString() {
        return
            "taskId: " + taskId + "\n"
                + "assigned_user_id: " + assigned_user_id + "\n"
                + "reportId: " + reportId + "\n"
                + "statusChangedAt: " + statusChangedAt + "\n"
                + "maintenance_id: " + maintenance_id + "\n"
                + "details: " + details + "\n"
                + "taskProgress: " + taskProgress + "\n"
                + "startedAt: " + startedAt + "\n"
                + "finishedAt: " + finishedAt + "\n"
                + "status: " + status + "\n";
    }
}
