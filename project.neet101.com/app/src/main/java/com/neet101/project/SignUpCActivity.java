package com.neet101.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpCActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView btnNext;

    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_c);
        getSupportActionBar().hide();

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpBActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Helper.IsEmpty(txtPassword)) {
                    Helper.MessageBoxOKShow(SignUpCActivity.this, "Alert Box", "Please enter your password.", 750);
                    return;
                }

                String p = txtPassword.getText().toString();
                Helper.Put(SignUpCActivity.this, "password", p);

                Intent i = new Intent(getApplicationContext(), SignUpDActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void Init() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        String p = Helper.Get(SignUpCActivity.this, "password");
        txtPassword.setText(p);
    }
}
