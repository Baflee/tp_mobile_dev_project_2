package com.example.tp_final_sauce_algerienne_proj_2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BillActivity extends AppCompatActivity {
    TextView txtRider;
    TextView txtBillAmount;
    TextView txtBillDestination;
    TextView txtBillDistance;
    TextView txtBillOrigin;
    TextView txtBillDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        setTitle("Bills");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRider = (TextView) findViewById(R.id.txtRider);
        txtBillAmount = (TextView) findViewById(R.id.txtBillAmount);
        txtBillDestination = (TextView) findViewById(R.id.txtBillDestination);
        txtBillDistance = (TextView) findViewById(R.id.txtBillDistance);
        txtBillOrigin = (TextView) findViewById(R.id.txtBillOrigin);
        txtBillDueDate = (TextView) findViewById(R.id.txtBillDueDate);

        Bundle extras = getIntent().getExtras();
        String rider = extras.getString("rider");
        String amount = extras.getString("amount");
        String dueDate = extras.getString("dueDate");
        String origin = extras.getString("origin");
        String destination = extras.getString("destination");
        String distance = extras.getString("distance");

        txtRider.setText("Rider: " + rider);
        txtBillAmount.setText("Price: " + amount);
        txtBillDestination.setText("Destination: " + destination);
        txtBillDistance.setText("Distance: " + distance);
        txtBillOrigin.setText("Origin: " + origin);
        txtBillDueDate.setText("DueDate: " + dueDate);
    }

}
