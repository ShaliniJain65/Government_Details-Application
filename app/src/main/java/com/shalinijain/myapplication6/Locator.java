package com.shalinijain.myapplication6;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Shalini on 01-04-2018.
 */

public class Locator {
    private int MY_PERM_REQ_CODE = 9090;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final String TAG = "Locator";
    private MainActivity owner;

    public Locator(MainActivity ma) {
        owner = ma;
        //Checks whether application has been given the Permission by user if yes setuplocation and detemrinelocation
        boolean havePermission = checkPermission();
        if (havePermission) {
            setUpLocationManager();
            determineLocation();
        }
        else
        {}
    }


    public void setUpLocationManager() {
        if (locationManager != null)
            return;

        if (!checkPermission())
            return;

        // Get the system's Location Manager service reference
        locationManager = (LocationManager) owner.getSystemService(LOCATION_SERVICE);

        //Create a location listener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //Toast.makeText(owner, "Update from " + location.getProvider(), Toast.LENGTH_SHORT).show();
                owner.doLocationWork(location.getLatitude(), location.getLongitude());

            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Nothing to do here
            }
            @Override
            public void onProviderEnabled(String provider) {
                // Nothing to do here
            }
            @Override
            public void onProviderDisabled(String provider) {
                // Nothing to do here
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(owner, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(owner, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERM_REQ_CODE);
            Log.d(TAG, "checkPermission: Waiting for PERMISSION");
            return false;
        } else {
            Log.d(TAG, "instance initializer: DO NOT Have Permission");
            return true;
        }
    }

    public void determineLocation() {

        if (!checkPermission())
            return;

        if (locationManager == null)
            setUpLocationManager();

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using" + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                owner.doLocationWork(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // If you get here, you got no location at all
        owner.noLocationAvailable();
        return;
    }
}
