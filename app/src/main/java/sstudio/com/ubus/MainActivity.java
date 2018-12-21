package sstudio.com.ubus;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    GoogleMap gMap;
    Firebase update, bus1, bus2, bus3, bus4, bus5, bus6, bus7, bus8, bus9, bus10, bus11, bus12, bus13, bus14, bus15;
    Marker marker1, marker2, marker3, marker4, marker5, marker6, marker7,
            marker8, marker9, marker10, marker11, marker12, marker13, marker14, marker15,you;
    MapFragment map;
    MapView m;
    String updateDiagTitle, updateDiagMessage, url;
    LocationManager locationManager;
    int app_version=1,new_version;
    CoordinatorLayout mainLay;
    boolean eXit=false,load=true,cancellable=true;
    SharedPreferences preferencesMap;
    Dialog alertDialog;
    TextView onlineTextView;
    long onlinecount,onlinebuscount;
    TextView onlineBusTextView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLay=(CoordinatorLayout)findViewById(R.id.activity_main);
        Log.e("erg", "erg");
        Firebase presence= new Firebase("https://ubus-c5cd5.firebaseio.com/present");
        Firebase preseneRef1=presence.push();
        Firebase preseneRef=preseneRef1.push();
        preseneRef.onDisconnect().removeValue();
        preseneRef.setValue("hello");
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stylePopUp();
                RandomColor ran=new RandomColor();
                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ran.randomColor()));
                Log.e("color:","#"+ran.randomColor());
                floatingActionButton.setRippleColor(ran.randomColor());
            }
        });




           /* flowingMenuLayout=(FlowingMenuLayout)findViewById(R.id.menulayout);
            *//*TextView textView=(TextView)findViewById(R.id.textview1111);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                }
            });*//*
            mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
            mDrawer.setTouchMode(ElasticDrawer.FOCUSABLES_TOUCH_MODE);
            mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                @Override
                public void onDrawerStateChange(int oldState, int newState) {
                    if (newState == ElasticDrawer.STATE_CLOSED) {
                        Log.i("MainActivity", "Drawer STATE_CLOSED");
                    }
                }

                @Override
                public void onDrawerSlide(float openRatio, int offsetPixels) {
                    Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                }
            });*/

        Firebase busPresent= new Firebase("https://ubus-c5cd5.firebaseio.com/busonline");

        alertDialog=new Dialog(MainActivity.this);
        alertDialog.setContentView(R.layout.loading);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        preferencesMap=getApplicationContext().getSharedPreferences("MapStyle",MODE_PRIVATE);
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.mapf);
        Log.e("before try", "");
        update = new Firebase("https://ubus-c5cd5.firebaseio.com/Update");
        bus1 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus1");
        bus2 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus2");
        bus3 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus3");
        bus4 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus4");
        bus5 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus5");
        bus6 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus6");
        bus7 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus7");
        bus8 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus8");
        bus9 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus9");
        bus10 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus10");
        bus11 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus11");
        bus12 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus12");
        bus13 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus13");
        bus14 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus14");
        bus15 = new Firebase("https://ubus-c5cd5.firebaseio.com/UbusService/Bus15");
        presence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               onlinecount= dataSnapshot.getChildrenCount();
                Log.e("onlinecount",""+onlinecount);
                try{
                    onlineTextView.setText(""+onlinecount);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        busPresent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onlinebuscount=dataSnapshot.getChildrenCount();
                try{
                    onlineBusTextView.setText(""+onlinebuscount);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        update.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alertDialog.dismiss();
                new_version=dataSnapshot.child("newUpdate").getValue(int.class);
                if (new_version>app_version) {
                    updateDiagTitle = dataSnapshot.child("Title").getValue(String.class);
                    updateDiagMessage = dataSnapshot.child("Message").getValue(String.class);
                    url = dataSnapshot.child("url").getValue(String.class);
                    cancellable=dataSnapshot.child("cancellable").getValue(boolean.class);
                    updateDiag();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        map.getMapAsync(this);
        bus1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("before try","");
                try{
            if (marker1!=null){
             marker1.remove();

            }
                    marker1=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 1"));
                    marker1.showInfoWindow();
//Log.e("lat="+lat,"long="+lang);

                    Log.e("onDataChanged",""+dataSnapshot.child("longitude").getValue(double.class));
                }catch (Exception e){
                    Log.e("bus1 ondata: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bus2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("reading bus2 info","");
                try{
                    if (marker2!=null){
                        marker2.remove();
                    }
                    marker2=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 2"));
                    marker2.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus2: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bus3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker3!=null){
                        marker3.remove();
                    }
                    marker3=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 3"));
                    marker3.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus3: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bus4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker4!=null){
                        marker4.remove();
                    }
                    marker4=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 4"));
                    marker4.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus4: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bus5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{if (marker5!=null){
                    marker5.remove();
                }
                    marker5=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 5"));
                    marker5.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus5: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker6!=null){
                        marker6.remove();
                    }
                    marker6=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 6"));
                    marker6.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus6: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker7!=null){
                        marker7.remove();
                    }
                    marker7=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 7"));
                    marker7.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus7: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker8!=null){
                        marker8.remove();
                    }
                    marker8=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 8"));
                    marker8.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus8: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bus9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker9!=null){
                        marker9.remove();
                    }
                    marker9=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 9"));
                    marker9.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus9: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker10!=null){
                        marker10.remove();
                    }
                    marker10=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 10"));
                    marker10.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus10: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker11!=null){
                        marker11.remove();
                    }
                    marker11=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 11"));
                    marker11.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus11: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker12!=null){
                        marker12.remove();
                    }
                    marker12=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 12"));
                    marker12.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus12: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus13.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker13!=null){
                        marker13.remove();
                    }
                    marker13=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 13"));
                    marker13.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus13: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus14.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker14!=null){
                        marker14.remove();
                    }
                    marker14=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("longitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 14"));
                    marker14.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus14: ",""+e);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });bus15.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if (marker15!=null){
                        marker15.remove();
                    }
                    marker15=gMap.addMarker(new MarkerOptions().position(new LatLng
                            (dataSnapshot.child("latitude").getValue(double.class),
                                    dataSnapshot.child("latitude").getValue(double.class)))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Bus 15"));
                    marker15.showInfoWindow();
                }catch (Exception e){
                    Log.e("bus5: ",""+e);
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
            String [] req={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission
                    .ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(MainActivity.this,req,1);
        }else {
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);
        }
    }
    public void updateDiag(){
        try{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(updateDiagTitle);
            alertDialog.setMessage(updateDiagMessage);
            alertDialog.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri=Uri.parse(url);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            });
            alertDialog.setCancelable(cancellable);
            alertDialog.setNegativeButton("Later",null);
            AlertDialog dialog=alertDialog.create();
            dialog.show();
        }catch (Exception e){
            Toast.makeText(this, "UBus: "+e, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
        int map=preferencesMap.getInt("MapStyle",R.raw.standerd);
        try{
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,map));
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        Log.e("map ready.","");
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(26.1539,91.6622) , 14.0f) );
       // googleMap.addMarker(new MarkerOptions().position(new LatLng(0.0,0.0)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.onlinemenu);
        MenuItem item2=menu.findItem(R.id.onlinebus);

        try {
            onlineBusTextView=(TextView)item2.getActionView().findViewById(R.id.onlineBusText);
            onlineBusTextView.setText(""+onlinebuscount);
            onlineTextView=(TextView)item.getActionView().findViewById(R.id.onlineText);
            onlineTextView.setText(""+onlinecount);
            Log.e("onlinecount in menu",""+onlinecount);
        }catch (Exception e){

            Log.e("oncreate error",""+e);
        }

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("MainActivity:","location changed.");
        if(you!=null){
            you.remove();
        }
        you=gMap.addMarker(new MarkerOptions().position(new LatLng
                (location.getLatitude(),location.getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.self)).title("This is where you are."));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
       // Toast.makeText(this, "Status changed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Provider enabled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Provider disabled.", Toast.LENGTH_SHORT).show();
    }
    public void stylePopUp(){
        CharSequence colors[] = new CharSequence[] {"Standerd", "Green", "Sky blue", "Red Black","Night"};
        final SharedPreferences.Editor editorM= preferencesMap.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyCustomTheme);
        TextView textView=new TextView(this);
        textView.setText("Pick Map style");
        textView.setTextSize(25);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setGravity(17);
        builder.setCustomTitle(textView);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:{
                        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this,R.raw.standerd));
                        editorM.putInt("MapStyle",R.raw.standerd);
                        editorM.apply();
                        break;
                    }
                    case 1:{
                        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this,R.raw.green));
                        editorM.putInt("MapStyle",R.raw.green);
                        editorM.apply();
                        break;
                    }
                    case 2:{
                        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this,R.raw.lightblue));
                        editorM.putInt("MapStyle",R.raw.lightblue);
                        editorM.apply();
                        break;
                    }
                    case 3:{
                        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this,R.raw.red_black));
                        editorM.putInt("MapStyle",R.raw.red_black);
                        editorM.apply();
                        break;
                    }
                    case 4:{
                        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this,R.raw.mapstyle));
                        editorM.putInt("MapStyle",R.raw.mapstyle);
                        editorM.apply();
                        break;
                    }
                }
            }
        });
        builder.show();
    }
    public void about(){
       /* new AlertDialog(MainActivity.this)
                .setHeaderBackground(R.color.dialogheader)
                .setTitle("UBus",20)
                //.setHeaderLogo(R.drawable.logobus)
                .setPositive("OK")// You can pass also View.OnClickListener as second param
                .setMessage("UBus, locating your own bus at real time. \n-sstudio")
                .isCancelable(false)
                .show();*/
    }
    @Override
    public void onBackPressed() {
        Snackbar snackbar=Snackbar.make(mainLay, "Press back to exit.", Snackbar.LENGTH_LONG);
        View snackbarView=snackbar.getView();
        snackbarView.setBackgroundColor(R.color.snackbarbackground);
    if (eXit){
        finish();
    }else {
        snackbar.show();
        eXit=true;
        //super.onBackPressed();
    }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                eXit=false;
            }
        }, 2000);
    }
}
