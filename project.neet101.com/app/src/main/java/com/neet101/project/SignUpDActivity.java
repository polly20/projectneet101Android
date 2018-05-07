package com.neet101.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpDActivity extends AppCompatActivity {

    private String TAG = SignUpDActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    public Context _context;

    ImageView btnBack;
    TextView btnNext;

    public EditText birthday;

    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_d);
        getSupportActionBar().hide();
        _context = getApplicationContext();

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);

        birthday = (EditText) findViewById(R.id.birthday);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpCActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), SettingsAActivity.class);
//                startActivity(i);
//                finish();

                String fname = Helper.Get(SignUpDActivity.this, "fname");
                String lname = Helper.Get(SignUpDActivity.this, "lname");
                String email = Helper.Get(SignUpDActivity.this, "email");
                String password = Helper.Get(SignUpDActivity.this, "password");
                String birthdate = birthday.getText().toString();

                data = "?first_name=" + fname;
                data += "&last_name=" + lname;
                data += "&email=" + email;
                data += "&password=" + password;
                data += "&fb_id=" + fname;
                data += "&fb_profile=" + fname;
                data += "&registration_type=" + fname;
                data += "&member_type=" + fname;
            }
        });
    }

}
