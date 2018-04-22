package com.neet101.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpBActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView btnNext;

    EditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_b);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpAActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Helper.IsEmailValid(txtEmail)) {
                    Helper.MessageBoxOKShow(SignUpBActivity.this, "Alert Box", "Please enter your valid email address.", 750);
                    return;
                }

                String e = txtEmail.getText().toString();
                Helper.Put(SignUpBActivity.this, "email", e);

                Intent i = new Intent(getApplicationContext(), SignUpCActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void Init() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        String e = Helper.Get(SignUpBActivity.this, "email");
        txtEmail.setText(e);
    }

}
