package com.neet101.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SettingsAActivity extends AppCompatActivity {


    public LinearLayout btnEnabledA, btnDisabledA;

    public LinearLayout btnEnabledB, btnDisabledB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_a);

//        getActionBar().setTitle("Target Settings");
        getSupportActionBar().setTitle("Target Settings");

        btnEnabledA = (LinearLayout) findViewById(R.id.btnEnabledA);
        btnDisabledA = (LinearLayout) findViewById(R.id.btnDisabledA);

        btnEnabledB = (LinearLayout) findViewById(R.id.btnEnabledB);
        btnDisabledB = (LinearLayout) findViewById(R.id.btnDisabledB);

        btnDisabledA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisabledA.setVisibility(View.GONE);
                btnEnabledA.setVisibility(View.VISIBLE);

                Log.d("Disabled", "YES");
            }
        });

        btnEnabledA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEnabledA.setVisibility(View.GONE);
                btnDisabledA.setVisibility(View.VISIBLE);

                Log.d("Enabled", "YES");
            }
        });

        btnDisabledB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisabledB.setVisibility(View.GONE);
                btnEnabledB.setVisibility(View.VISIBLE);

                Log.d("Disabled", "YES");
            }
        });

        btnEnabledB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEnabledB.setVisibility(View.GONE);
                btnDisabledB.setVisibility(View.VISIBLE);

                Log.d("Enabled", "YES");
            }
        });


    }
}
