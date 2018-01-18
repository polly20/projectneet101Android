package com.neet101.project;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Slide1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide1);
        getSupportActionBar().hide();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        SliderLayoutAdapter adapterView = new SliderLayoutAdapter(this);
        mViewPager.setAdapter(adapterView);
    }
}
