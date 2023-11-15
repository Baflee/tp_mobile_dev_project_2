package com.example.tp_final_sauce_algerienne_proj_2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tp_final_sauce_algerienne_proj_2.model.Bill;
import com.example.tp_final_sauce_algerienne_proj_2.model.DirectionsResponse;
import com.example.tp_final_sauce_algerienne_proj_2.model.User;
import com.example.tp_final_sauce_algerienne_proj_2.remote.APIUtils;
import com.example.tp_final_sauce_algerienne_proj_2.remote.BillService;
import com.example.tp_final_sauce_algerienne_proj_2.remote.MapService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import android.Manifest;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapService mapService;
    BillService billService;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Polyline currentPolyline;
    TextView txtBillRideAmount;
    TextView txtBillRiderAmount;
    TextView txtBillDuration;
    TextView txtBillDistance;
    double totalDistanceKm;
    int totalDurationMinutes;
    double calculatedRideAmount;
    double calculatedRiderAmount;
    String startAddress;
    String endAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        mapService = APIUtils.getMapService();
        billService = APIUtils.getBillService();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setUpMap();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
        }

        txtBillRideAmount = (TextView) findViewById(R.id.txtBillRideAmount);
        txtBillRiderAmount = (TextView) findViewById(R.id.txtBillRiderAmount);
        txtBillDuration = (TextView) findViewById(R.id.txtBillDuration);
        txtBillDistance = (TextView) findViewById(R.id.txtBillDistance);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); // Replace R.id.map with your actual map fragment ID
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        EditText destinationInput = findViewById(R.id.edtDestination);
        //EditText originInput = findViewById(R.id.edtOrigin);
        Button submitRide = findViewById(R.id.btnSubmitRide);

        destinationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String destination = charSequence.toString().trim();
                if (!destination.isEmpty()) {
                    geocodeAndMoveCamera(destination);
                } else {
                    if (currentPolyline != null) {
                        currentPolyline.remove();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("userSaved", MODE_PRIVATE);
        int savedUserId = sharedPreferences.getInt("id", -1);

        submitRide.setOnClickListener(v -> {
            if(!startAddress.isEmpty() && !endAddress.isEmpty()) {
                attemptCreateBill();
            }
        });
    }

    private void attemptCreateBill(){
        SharedPreferences sharedPreferences = getSharedPreferences("userSaved", MODE_PRIVATE);
        int savedUserId = sharedPreferences.getInt("id", -1);

        Bill newBill = new Bill(savedUserId, getRandomRider() ,calculatedRideAmount, calculatedRiderAmount, totalDurationMinutes, actualDate(), startAddress, endAddress, totalDistanceKm);
        Call<Bill> call = billService.addBill(newBill);
        call.enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RideActivity.this, "Bill Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RideActivity.this, "Error requesting ride", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(RideActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRandomRider() {
        Random random = new Random();
        String[] names = {"Alex", "Jordan", "Chris", "Taylor", "Morgan", "Jamie", "Casey"};

        String name = names[random.nextInt(names.length)];
        return name;
    }

    private String actualDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        // Format the current date
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    setUpMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {
        if (mMap == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            // Optional: Get the current location and move the camera
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        public void onSuccess(Location location) {
                            if (location != null & mMap != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Origin"));
                            }
                        }

                        public void onFailure(Call<Location> call, Throwable t) {
                            Log.e("ERROR: ", t.getMessage());
                        }
                    });
        }
    }

    private void geocodeAndMoveCamera(String destination) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(destination, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                final LatLng destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Clear the map before adding new markers or polylines
                mMap.clear();

                // Use FusedLocationProviderClient to get the current location
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null && mMap != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                // Add origin marker
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Origin"));

                                getDirectionDatas(currentLocation, destinationLatLng);

                                // Add destination marker
                                mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDirectionDatas(LatLng originLatLng, LatLng destinationLatLng) {
        String origin = originLatLng.latitude + "," + originLatLng.longitude;
        String destination = destinationLatLng.latitude + "," + destinationLatLng.longitude;
        String apiKey = "";

        try {
            Bundle metaData = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;
            apiKey = metaData.getString("com.google.android.geo.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Make the API call
        Call<DirectionsResponse> call = mapService.getDirections(destination, origin, apiKey);
        call.enqueue(new Callback<DirectionsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DirectionsResponse.Route> routes = response.body().getRoutes();
                    if (!routes.isEmpty()) {
                        // Remove the previous polyline
                        if (currentPolyline != null) {
                            currentPolyline.remove();
                        }

                        DirectionsResponse.Route route = routes.get(0);
                        if (!route.getOverviewPolyline().getPoints().isEmpty()) {
                            // Decode the path
                            List<LatLng> decodedPath = PolyUtil.decode(route.getOverviewPolyline().getPoints());
                            //Show the path
                            currentPolyline = mMap.addPolyline(new PolylineOptions()
                                    .addAll(decodedPath)
                                    .width(10)
                                    .color(Color.BLACK)
                                    .geodesic(true));
                        }

                        long totalDistanceMeters = 0;
                        long totalDurationSeconds = 0;

                        // Get Every Data required to show
                        if (route.getLegs() != null) {
                            List<DirectionsResponse.Route.Leg> legs = route.getLegs();

                            if (legs.get(0).getDistance() != null) {
                                totalDistanceMeters = legs.get(0).getDistance().getValue(); // Distance of the ride
                            }
                            if (legs.get(0).getDuration() != null) {
                                totalDurationSeconds = legs.get(0).getDuration().getValue(); // Duration of the ride
                            }

                            if (legs.get(0).getStartAddress() != null && legs.get(0).getEndAddress() != null) {
                                startAddress = legs.get(0).getStartAddress();
                                endAddress = legs.get(0).getEndAddress();
                            }

                            totalDistanceKm = Double.parseDouble(String.format("%.1f", totalDistanceMeters / 1000.0));
                            totalDurationMinutes = (int) (totalDurationSeconds / 60.0);

                            txtBillDistance.setText("Distance : " + totalDistanceKm + " Km");

                            CalculatePrice(totalDurationMinutes, totalDistanceKm);

                            txtBillRideAmount.setText("Ride Fee : " + calculatedRideAmount + " Euros");

                            txtBillRiderAmount.setText("Service Fee : " + calculatedRiderAmount + " Euros");

                            txtBillDuration.setText("Duration : " + totalDurationMinutes + " Minutes");
                        }

                        // Move the camera to show the route
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                                LatLngBounds.builder().include(originLatLng).include(destinationLatLng).build(), 100));

                    }
                } else {
                    Log.e("ERROR: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Toast.makeText(RideActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void CalculatePrice(int estimatedTripDurationInMinutes, double estimatedDistanceKm) {
        double fuelPricePerLitre = 1.5;
        double hourlyRateDriver = 30.0;
        double tripDurationHours = estimatedTripDurationInMinutes / 60.0;
        calculatedRiderAmount = Double.parseDouble(String.format("%.1f",tripDurationHours * hourlyRateDriver));

        calculatedRideAmount = Double.parseDouble(String.format("%.1f",estimatedDistanceKm * fuelPricePerLitre));
    }


}