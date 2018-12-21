package sstudio.com.ubus;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main4Activity extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences preferences;
    Firebase ref;
    LatLng latLng;
    GoogleMap gmap;
    MapFragment map;
    Marker marker;
    SharedPreferences preferencesMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        preferencesMap=getApplicationContext().getSharedPreferences("MapStyleD",MODE_PRIVATE);
        Intent service=new Intent(Main4Activity.this,MyService.class);
        startService(service);
        map=(MapFragment)getFragmentManager().findFragmentById(R.id.map4);
        map.getMapAsync(this);
        preferences=getApplicationContext().getSharedPreferences("Bus",MODE_PRIVATE);
        Firebase busPresent= new Firebase("https://ubus-c5cd5.firebaseio.com/busonline");
        Firebase buspresenrref1=busPresent.push();
        Firebase buspresenrref=buspresenrref1.push();
        buspresenrref.onDisconnect().removeValue();
        buspresenrref.setValue("online");
        ref=new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/"+preferences.getString("Bus","BusX"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                latLng=new LatLng(dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class));
                try{
                    if (marker!=null){
                        marker.remove();
                    }
                    marker=gmap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .title(preferences.getString("Bus","BusX")));
                    marker.showInfoWindow();
                    gmap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 14.0f) );
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                            latLng, 15);
                    //gmap.animateCamera(location);

                }catch (Exception e){
                    Log.e("Exception indatachange:",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String [] req={android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission
                    .ACCESS_COARSE_LOCATION, android.Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(Main4Activity.this,req,1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.drivermenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapstyle:
                stylePopUp();
                break;
            case R.id.about:
                about();
                break;
            case R.id.exit:
                stopService(new Intent(this, MyService.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        int map=preferencesMap.getInt("MapStyleD",R.raw.standerd);
        try{
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,map));
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }
    public void stylePopUp(){
        CharSequence colors[] = new CharSequence[] {"Standerd", "Green", "Sky blue", "Red Black","Night"};
        final SharedPreferences.Editor editorM= preferencesMap.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:{
                        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Main4Activity.this,R.raw.standerd));
                        editorM.putInt("MapStyleD",R.raw.standerd);
                        editorM.apply();
                        break;
                    }
                    case 1:{
                        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Main4Activity.this,R.raw.green));
                        editorM.putInt("MapStyleD",R.raw.green);
                        editorM.apply();
                        break;
                    }
                    case 2:{
                        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Main4Activity.this,R.raw.lightblue));
                        editorM.putInt("MapStyleD",R.raw.lightblue);
                        editorM.apply();
                        break;
                    }
                    case 3:{
                        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Main4Activity.this,R.raw.red_black));
                        editorM.putInt("MapStyleD",R.raw.red_black);
                        editorM.apply();
                        break;
                    }
                    case 4:{
                        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Main4Activity.this,R.raw.mapstyle));
                        editorM.putInt("MapStyleD",R.raw.mapstyle);
                        editorM.apply();
                        break;
                    }
                }
            }
        });
        builder.show();
    }
    public void about(){
       /* new PanterDialog(this)
                .setHeaderBackground(R.color.dialogheader)
                .setTitle("UBus",20)
                //.setHeaderLogo(R.drawable.logobus)
                .setPositive("OK")// You can pass also View.OnClickListener as second param
                .setMessage("UBus, locating your own bus at real time. \n-sstudio")
                .isCancelable(false)
                .show();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
