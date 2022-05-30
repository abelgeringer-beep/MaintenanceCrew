package com.example.beadando;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APICalls {

    private static APICalls Instance;

    public User user;
    public String username, password;

    private APICalls() {
    }

    public static APICalls getInstance() {
        if (Instance == null)
            Instance = new APICalls();

        return Instance;
    }

    public String saveTaskChanges(int taskId) {
        final String[] msg = {""};
        Thread t = new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2/api/saveTaskChanges.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                String send = "&task_id=" + (taskId + 1)
                    + "&task_progress=" + APICalls.getInstance().user.tasks[taskId].status
                    + "&details=" + APICalls.getInstance().user.tasks[taskId].details
                    + "&status_changed_at=" + APICalls.getInstance().user.tasks[taskId].statusChangedAt
                    + "&started_at=" + APICalls.getInstance().user.tasks[taskId].startedAt
                    + "&finished_at=" + APICalls.getInstance().user.tasks[taskId].finishedAt;

                Log.d("SaveException", send);

                try (OutputStream outputStream = conn.getOutputStream()) {
                    byte[] input = send.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }

                conn.connect();

                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line.trim());
                    }
                }

                JSONObject json = new JSONObject(sb.toString());

                msg[0] = json.get("message").toString();

            } catch (Exception e) {
                if (e.getMessage() != null)
                    Log.d("SaveException", e.getMessage());
                msg[0] = "Something went wrong";
            }
        });

        t.start();
        return msg[0];
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login() {
        Thread t = new Thread(() -> {
            try {
                auth();
                fetchTasksForUser();
            } catch (Exception ex) {
                Log.e("api", "yourDataTask", ex);
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fetchTasksForUser() {
        Thread t = new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2/api/tasksForUser.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                String send = "&user_id=" + APICalls.getInstance().user.userId;

                try (OutputStream outputStream = conn.getOutputStream()) {
                    byte[] input = send.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }

                conn.connect();

                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line.trim());
                    }
                }

                JSONArray json = new JSONArray(sb.toString());

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    APICalls.getInstance().user.tasks = new Task[json.length()];

                    for (int i = 0; i < json.length(); ++i) {
                        JSONObject obj = json.getJSONObject(i).getJSONObject("data");
                        APICalls.getInstance().user.tasks[i] = new Task(
                            Integer.parseInt(obj.get("task_id").toString()),
                            Integer.parseInt(obj.get("report_id").toString()),
                            Integer.parseInt(obj.get("assigned_user_id").toString()),
                            obj.get("status_changed_at").toString(),
                            Integer.parseInt(obj.get("maintenance_id").toString()),
                            obj.get("details").toString(),
                            obj.get("task_progress").toString(),
                            obj.get("started_at").toString(),
                            obj.get("finished_at").toString()
                        );
                    }
                }
            } catch (Exception e) {
                Log.d("APIException", e.getMessage());
            }
        });

        t.start();
    }

    private void auth() throws IOException, JSONException {
        URL url = new URL("http://10.0.2.2/api/login.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String send = "&username=" + username + "&password=" + password;

        try (OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = send.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        conn.connect();

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
        }

        JSONObject json = new JSONObject(sb.toString());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            APICalls.getInstance().user = new User(
                Integer.parseInt(json.get("user_id").toString()),
                Integer.parseInt(json.get("qualification_id").toString()),
                json.get("username").toString(),
                json.get("status").toString(),
                json.get("registered_at").toString(),
                json.get("token").toString()
            );
        }

    }

}
