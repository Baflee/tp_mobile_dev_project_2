package com.example.tp_final_sauce_algerienne_proj_2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BillActivity extends AppCompatActivity {
    TextView txtRider;
    TextView txtBillRideAmount;
    TextView txtBillRiderAmount;
    TextView txtBillDuration;
    TextView txtBillDestination;
    TextView txtBillDistance;
    TextView txtBillOrigin;
    TextView txtBillDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRider = (TextView) findViewById(R.id.txtRider);
        txtBillRideAmount = (TextView) findViewById(R.id.txtBillRideAmount);
        txtBillRiderAmount = (TextView) findViewById(R.id.txtBillRiderAmount);
        txtBillDuration = (TextView) findViewById(R.id.txtBillDuration);
        txtBillDestination = (TextView) findViewById(R.id.txtBillDestination);
        txtBillDistance = (TextView) findViewById(R.id.txtBillDistance);
        txtBillOrigin = (TextView) findViewById(R.id.txtBillOrigin);
        txtBillDueDate = (TextView) findViewById(R.id.txtBillDueDate);

        Bundle extras = getIntent().getExtras();
        String rider = extras.getString("rider");
        String rideAmount = extras.getString("rideAmount");
        String riderAmount = extras.getString("riderAmount");
        String duration = extras.getString("duration");
        String dueDate = extras.getString("dueDate");
        String origin = extras.getString("origin");
        String destination = extras.getString("destination");
        String distance = extras.getString("distance");

        txtRider.setText("Rider: " + rider);
        txtBillRideAmount.setText("Ride Fee: " + rideAmount + " Euros");
        txtBillRiderAmount.setText("Service Fee : " + riderAmount + " Euros");
        txtBillDuration.setText("Duration: " + duration + " Minutes");
        txtBillDestination.setText("Destination: " + destination);
        txtBillDistance.setText("Distance: " + distance + " Km");
        txtBillOrigin.setText("Origin: " + origin);
        txtBillDueDate.setText("DueDate: " + dueDate);

        setTitle(dueDate + " - " + rider);
    }

}
