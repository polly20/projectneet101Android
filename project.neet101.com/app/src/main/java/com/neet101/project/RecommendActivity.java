package com.neet101.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RecommendActivity extends AppCompatActivity {

    String TAG = "TAG";

    RadioButton rbt_20, rbt_30, rbt_40, rbt_50, rbt_60, rbt_70, rbt_80, rbt_90, rbt_100;

    Button btnSubmit;

    EditText txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        getSupportActionBar().hide();

        String target = Helper.Get(RecommendActivity.this, "target");

        String recommended = Helper.Get(RecommendActivity.this, "recommended");

        txtValue = (EditText) findViewById(R.id.txtValue);

        override_target(recommended);

        getRadionButton(Integer.parseInt(recommended));

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String value = ((RadioButton)findViewById(checkedId)).getText().toString();
                override_target(value);
            }
        });


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = txtValue.getText().toString();
                Log.d("Logs", value);

                Intent i = new Intent(getBaseContext(), QuestionDashboardActivity.class);
                startActivity(i);

            }
        });
    }

    public void override_target(String value) {
        txtValue.setText(value.toString());
    }

    public void getRadionButton(Integer val) {

        if(val >= 0 && val <= 29) {
            rbt_20 = (RadioButton)findViewById(R.id.rb_20);
            rbt_20.setChecked(true);
        }

        if(val >= 30 && val <= 39) {
            rbt_30 = (RadioButton)findViewById(R.id.rb_30);
            rbt_30.setChecked(true);
        }

        if(val >= 40 && val <= 49) {
            rbt_40 = (RadioButton)findViewById(R.id.rb_40);
            rbt_40.setChecked(true);
        }

        if(val >= 50 && val <= 59) {
            rbt_50 = (RadioButton)findViewById(R.id.rb_50);
            rbt_50.setChecked(true);
        }

        if(val >= 60 && val <= 69) {
            rbt_60 = (RadioButton)findViewById(R.id.rb_60);
            rbt_60.setChecked(true);
        }

        if(val >= 70 && val <= 79) {
            rbt_70 = (RadioButton)findViewById(R.id.rb_70);
            rbt_70.setChecked(true);
        }

        if(val >= 80 && val <= 89) {
            rbt_80 = (RadioButton)findViewById(R.id.rb_80);
            rbt_80.setChecked(true);
        }

        if(val >= 90 && val <= 99) {
            rbt_90 = (RadioButton)findViewById(R.id.rb_90);
            rbt_90.setChecked(true);
        }

        if(val >= 100) {
            rbt_100 = (RadioButton)findViewById(R.id.rb_100);
            rbt_100.setChecked(true);
        }
    }
}
