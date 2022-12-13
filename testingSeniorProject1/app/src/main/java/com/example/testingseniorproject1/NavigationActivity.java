package com.example.testingseniorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.concurrent.TimeUnit;

public class NavigationActivity extends AppCompatActivity {

    LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
//---------------------------------------------------------------------------------------------------------------------
        // HERE WE ARE GETTING DESTINATION LOCATION FROM GETINTENT()
        destination = new LatLng(getIntent().getDoubleExtra("lat", 0),
                getIntent().getDoubleExtra("long", 0));
//---------------------------------------------------------------------------------------------------------------------
        // HERE WE ARE GETTING THE TIME THAT USER HAS DECIDED AND THEN IT WILL START THE TIMER
        new CountDownTimer(TimeUnit.MINUTES.toMillis(getIntent().getIntExtra("time", 5)), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView timerTv = findViewById(R.id.timerTv);
                timerTv.setText("Remaining time: " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Toast.makeText(NavigationActivity.this, "Finished", Toast.LENGTH_SHORT).show();
            }
        }.start();
//---------------------------------------------------------------------------------------------------------------------
        SupportMapFragment mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(NavigationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(NavigationActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // We are calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission.
                    ActivityCompat.requestPermissions(NavigationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        LatLng myLatLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//---------------------------------------------------------------------------------------------------------------------
                        // HERE WE ARE ADDING THE LINE FOR THE DESTINATION AND THE USER CURRENT LOCATION
                        if (run){
                            run = false;
                            googleMap.addPolyline(new PolylineOptions()
                                    .add(myLatLng, destination)
                                    .geodesic(true));
                        }
//---------------------------------------------------------------------------------------------------------------------
                    }
                });

            }
        });

    }
    boolean run = true;
}