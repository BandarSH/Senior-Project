package com.example.testingseniorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DestinationActivity extends AppCompatActivity {

    LatLng destinationLatLng;
    Button letsGoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        Toast.makeText(this, "Long click to select destination", Toast.LENGTH_SHORT).show();

        letsGoBtn = findViewById(R.id.letsGoBtn);
//---------------------------------------------------------------------------------------------------------------------
        // THIS BUTTON IS COLLECTING ALL THE DATA AND PARSING IT TO OTHER ACTIVITY
        letsGoBtn.setOnClickListener(v -> {
            startActivity(new Intent(DestinationActivity.this, NavigationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
                    .putExtra("lat", destinationLatLng.latitude)
                    .putExtra("long", destinationLatLng.longitude)
            );
        });
//---------------------------------------------------------------------------------------------------------------------
        SupportMapFragment mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(DestinationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DestinationActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // We are calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission.
                    ActivityCompat.requestPermissions(DestinationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // MOVING THE MAP TO OPEN USER LOCATION
                        LatLng myLatLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                });
//---------------------------------------------------------------------------------------------------------------------
                // HERE WE WILL HAVE A LONG CLICK LITENER SO THAT WE WILL GET USER DESTINATION LOCATION
                googleMap.setOnMapLongClickListener(latLng -> {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Destination selected"));
                    destinationLatLng = latLng;
                    letsGoBtn.setVisibility(View.VISIBLE);
                });
//---------------------------------------------------------------------------------------------------------------------

            }
        });

    }
}