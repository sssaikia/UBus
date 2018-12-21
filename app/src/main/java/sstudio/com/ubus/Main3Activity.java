package sstudio.com.ubus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ekalips.fancybuttonproj.FancyButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Main3Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,ValueEventListener {
    Spinner spinner;
    FancyButton button;
    EditText email,password;
    SharedPreferences preferences,spreferences;
    SharedPreferences.Editor editor,seditor;
    String busId="BusX";
    Firebase firebase;
    DataSnapshot dataSnapshot1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        spinner=(Spinner)findViewById(R.id.spinnerBusList);
        button=(FancyButton) findViewById(R.id.buttonLogin);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        preferences=getApplicationContext().getSharedPreferences("Bus",MODE_PRIVATE);
        spreferences=getApplicationContext().getSharedPreferences("Busid",MODE_PRIVATE);
        busId=preferences.getString("Bus","BusX");
        //busId=spinner.getSelectedItem().toString();
        firebase=new Firebase("https://ubus-c5cd5.firebaseio.com/authId/"+busId+"/");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot1=dataSnapshot;
                        Log.e("single value listened","");
                        openA();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
        firebase.addValueEventListener(this);
       /* firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              *//* dataSnapshot1=dataSnapshot;
                Log.e("in datachaged.","");*//*
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.busId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("select Bus");
        spinner.setSelection(spreferences.getInt("Busid",0));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        busId= adapterView.getSelectedItem().toString();
        email.setText(busId);
        seditor=spreferences.edit();
        seditor.putInt("spId",i);
        seditor.apply();
        editor=preferences.edit();
        editor.putString("Bus",busId);
        editor.apply();
        //Toast.makeText(this, "busId: "+busId+" selected.", Toast.LENGTH_SHORT).show();
        firebase=new Firebase("https://ubus-c5cd5.firebaseio.com/authId/"+busId+"/");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
       // spinner.setSelection(spreferences.getInt("Busid",0));
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot1=dataSnapshot;
        Log.e("in datachaged.","");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
    public void openA(){
        Log.e("firebase=",""+firebase.toString());
        Log.e("firebase=",""+dataSnapshot1);
        Log.e("datasnapshot= pass="+dataSnapshot1.child("pass").getValue(String.class),"id="+dataSnapshot1.child("id").getValue(String.class));
        Log.e("email="+email.getText().toString(),"pass"+password.getText().toString());
        try{
            String id,pass;
            id=dataSnapshot1.child("id").getValue(String.class);
            pass=dataSnapshot1.child("pass").getValue(String.class);
            if (id.equals(email.getText().toString())&&pass.equals(password.getText().toString())){
                button.collapse();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        button.expand();
                        Intent i=new Intent(Main3Activity.this,Main4Activity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }, 1000);
            }else {
               button.collapse();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                       button.expand();
                    }
                }, 1000);
                Toast.makeText(this, "Incorrect ID or Password.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(Main3Activity.this, "Exception "+e, Toast.LENGTH_SHORT).show();
        }
    }
}
