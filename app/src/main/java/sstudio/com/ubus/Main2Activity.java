package sstudio.com.ubus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ekalips.fancybuttonproj.FancyButton;


public class Main2Activity extends AppCompatActivity {
    FancyButton annonymus,authorized;
    CheckBox checkBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean rem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        preferences=getApplicationContext().getSharedPreferences("Remember",MODE_PRIVATE);
        editor=preferences.edit();
        annonymus=(FancyButton) findViewById(R.id.buttonannonymus);
        authorized=(FancyButton) findViewById(R.id.buttonauthorized);
        rem=preferences.getBoolean("Remember",false);
        if (rem){
            Intent intent=new Intent(Main2Activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        checkBox=(CheckBox)findViewById(R.id.remembermecheckbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Remember",b);
                editor.commit();
                editor.apply();
            }
        });
        annonymus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (annonymus.isExpanded()) {
                    annonymus.collapse();
                    //annonymus.showComplete();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            //annonymus.expand();
                            finish();
                        }
                    }, 1500);
                }
                //annonymus.expand();

            }
        });
        authorized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authorized.isExpanded()){
                authorized.collapse();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
                        startActivity(intent);
                        authorized.expand();
                    }
                }, 1500);
                }

            }
        });
    }
}
