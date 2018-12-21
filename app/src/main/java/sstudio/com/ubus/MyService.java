package sstudio.com.ubus;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MyService extends Service implements android.location.LocationListener {
    public MyService() {
    }

    SharedPreferences preferences;
    String bus;
    LocationManager locationManager;
    Firebase busLoc;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service created", "");
        preferences = getApplicationContext().getSharedPreferences("Bus", MODE_PRIVATE);
        bus = preferences.getString("Bus", "BusX");
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();*/
        busLoc = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/" + preferences.getString("Bus", "BusX"));
        /*mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/
        if (ActivityCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("permission available", "");
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);
            // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10,  this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service called", "");
        preferences = getApplicationContext().getSharedPreferences("Bus", MODE_PRIVATE);
        bus = preferences.getString("Bus", "BusX");
        busLoc = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/" + preferences.getString("Bus", "BusX"));
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {
        //mLastLocation=location;
        busLoc.setValue(location);
        Log.e("service location called", "");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.e("status changed", "");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.e("provider enabled", "");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Grant permission to acesss location.", Toast.LENGTH_SHORT).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
