package com.neet101.project;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Slide1Activity extends AppCompatActivity {

    public static Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide1);
        getSupportActionBar().hide();

        String is_logged = Helper.Get(Slide1Activity.this, "is_logged");
        if(is_logged.contains("YES")) {
            Intent i = new Intent(getBaseContext(), DashboardActivity.class);
            startActivity(i);
            finish();
        }

        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
//                i.putExtra("PersonID", personID);
                startActivity(i);
                finish();
            }
        });


        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        SliderLayoutAdapter adapterView = new SliderLayoutAdapter(this);
        mViewPager.setAdapter(adapterView);
    }
}
