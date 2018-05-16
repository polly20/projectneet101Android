package com.neet101.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String value = ((RadioButton)findViewById(checkedId)).getText().toString();

                override_target(value);
            }
        });
    }

    public void override_target(String value) {
        txtValue = (EditText) findViewById(R.id.txtValue);
        txtValue.setText(value.toString());
    }

    public String getRadionButton(Integer val) {

        if(val > 0 && val <= 20) {

        }

        return null;
    }
}
