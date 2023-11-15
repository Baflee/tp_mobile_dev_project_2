package com.example.tp_final_sauce_algerienne_proj_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.tp_final_sauce_algerienne_proj_2.model.Bill;

import java.util.List;

public class BillAdapter extends ArrayAdapter<Bill> {
    private final Context context;
    private final List<Bill> bills;

    public BillAdapter(@NonNull Context context, int resource, @NonNull List<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bills = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_billhistoric, parent, false);

        TextView txtRider = (TextView) rowView.findViewById(R.id.txtRider);
        TextView txtBillAmount = (TextView) rowView.findViewById(R.id.txtBillAmount);
        TextView txtBillDueDate = (TextView) rowView.findViewById(R.id.txtBillDueDate);
        TextView txtBillDestination = (TextView) rowView.findViewById(R.id.txtBillDestination);

        double rideAmount = bills.get(pos).getRideAmount();
        double riderAmount = bills.get(pos).getRiderAmount();
        double totalAmount = rideAmount + riderAmount;

        txtRider.setText(String.format("Rider : %s", bills.get(pos).getRider()));
        txtBillAmount.setText(String.format("Total Cost : %.0f", totalAmount, " Euros"));
        txtBillDueDate.setText(String.format("Date : %s", bills.get(pos).getDueDate()));
        txtBillDestination.setText(String.format("Destination : %s", bills.get(pos).getDestination()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillActivity.class);
                intent.putExtra("rider", bills.get(pos).getRider());
                intent.putExtra("duration", String.valueOf(bills.get(pos).getDuration()));
                intent.putExtra("riderAmount", String.valueOf(bills.get(pos).getRiderAmount()));
                intent.putExtra("rideAmount", String.valueOf(bills.get(pos).getRideAmount()));
                intent.putExtra("dueDate", bills.get(pos).getDueDate());
                intent.putExtra("origin", bills.get(pos).getOrigin());
                intent.putExtra("destination", bills.get(pos).getDestination());
                intent.putExtra("distance", String.valueOf(bills.get(pos).getDistance()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
