package com.neet101.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpAActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView btnNext;
    EditText txtFname, txtLname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_a);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(Helper.IsEmpty(txtFname)) {
                Helper.MessageBoxOKShow(SignUpAActivity.this, "Alert Box", "Please enter your first name.", 750);
                return;
            }

            if(Helper.IsEmpty(txtLname)) {
                Helper.MessageBoxOKShow(SignUpAActivity.this, "Alert Box", "Please enter your last name.", 750);
                return;
            }

            String f = txtFname.getText().toString();
            String l = txtLname.getText().toString();

            Helper.Put(SignUpAActivity.this, "fname", f);
            Helper.Put(SignUpAActivity.this, "lname", l);

            Intent i = new Intent(getApplicationContext(), SignUpBActivity.class);
            startActivity(i);
            finish();
            }
        });

    }

    public void Init() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);

        txtFname = (EditText) findViewById(R.id.txtFname);
        txtLname = (EditText) findViewById(R.id.txtLname);

        String fn = Helper.Get(SignUpAActivity.this, "fname");
        String ln = Helper.Get(SignUpAActivity.this, "lname");

        txtFname.setText(fn);
        txtLname.setText(ln);
    }

}
