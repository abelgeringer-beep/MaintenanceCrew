package com.example.beadando;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText userNameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialSetup();
        setListeners();
    }

    private void setListeners() {
        loginBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, TasksMenu.class);

            APICalls.getInstance().username = userNameET.getText().toString();
            APICalls.getInstance().password = passwordET.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                APICalls.getInstance().login();
            }

            startActivity(i);
        });
    }

    private void initialSetup() {
        userNameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
    }
}
