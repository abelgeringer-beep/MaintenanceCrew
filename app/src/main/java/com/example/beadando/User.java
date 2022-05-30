package com.example.beadando;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class User {
    public int userId;
    public int qualificationId;
    public String username;
    public String status;
    public String registeredAt;
    public String token;

    public Task[] tasks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected User(int userId, int qualificationId, String username,
                   String status, String registeredAt, String token) {
        this.userId = userId;
        this.qualificationId = qualificationId;
        this.username = username;
        this.status = status;
        this.registeredAt = registeredAt;
        this.token = token;
    }
}
