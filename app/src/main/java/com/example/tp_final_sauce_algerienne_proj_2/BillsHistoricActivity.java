package com.example.tp_final_sauce_algerienne_proj_2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_final_sauce_algerienne_proj_2.model.Bill;
import com.example.tp_final_sauce_algerienne_proj_2.remote.APIUtils;
import com.example.tp_final_sauce_algerienne_proj_2.remote.BillService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillsHistoricActivity extends AppCompatActivity {

    ListView listBillHistoric;

    BillService billService;

    List<Bill> list = new ArrayList<Bill>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billshistoric);

        listBillHistoric = findViewById(R.id.listBillHistoric);
        billService = APIUtils.getBillService();

        getUserBillsList();
    }

    public void getUserBillsList() {
        Call<List<Bill>> call = billService.getBills();
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences("userSaved", MODE_PRIVATE);
                    int savedUserId = sharedPreferences.getInt("id", -1);

                    List<Bill> filteredBills = new ArrayList<>();
                    for (Bill bill : list) {
                        if (bill.getUserId() == savedUserId) {
                            filteredBills.add(bill);
                        }
                    }
                    listBillHistoric.setAdapter(new BillAdapter(BillsHistoricActivity.this, R.layout.list_billhistoric, filteredBills));
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}
