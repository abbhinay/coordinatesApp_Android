package com.abhinay.ambulanceapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 123;
    long MIN_TIME = 5000;
    float MIN_DISTANCE = 1000;

    String longitude;
    String latitude;

    TextView latitudeView;
    TextView longitudeView;


    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeView = (TextView) findViewById(R.id.latitudeInput);
        longitudeView = (TextView) findViewById(R.id.longitudeInput);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ambulanceApp", "onResume() called");
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("ambulanceApp", "onLocationChanges() callback recieved");

                longitude = String.valueOf(location.getLongitude());
                latitude = String.valueOf(location.getLatitude());

                //updateCoordinates(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("ambulanceApp", "onProviderDesabled() callback recieved");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("ambulanceApp", "onRequestPermissionResult(): Permission granted");
                getCurrentLocation();
            }else{
                Log.d("ambulanceApp", "Permission denied =( ");
            }
        }
    }

    public void updateCoordinates(View v){
        latitudeView.setText(latitude);
        longitudeView.setText(longitude);
    }
}
