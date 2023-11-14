package com.example.tp_final_sauce_algerienne_proj_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming you have a user object or user data available.
        String username = "User"; // replace this with actual user data
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("Welcome, %s", username));

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(DateFormat.getDateInstance().format(new Date()));

        Button checkPreviousButton = findViewById(R.id.checkPreviousButton);
        checkPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BillsHistoricActivity.class);
                startActivity(intent);
            }
        });

        Button commandUberButton = findViewById(R.id.commandUberButton);
        commandUberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RideActivity.class);
                startActivity(intent);
            }
        });
    }
}