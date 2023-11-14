package com.example.tp_final_sauce_algerienne_proj_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        // Assuming you have a user object or user data available.
        String username = "User"; // replace this with actual user data
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("%s, ready for a ride?", username));

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(String.format("Today is %s", DateFormat.getDateInstance().format(new Date())));

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