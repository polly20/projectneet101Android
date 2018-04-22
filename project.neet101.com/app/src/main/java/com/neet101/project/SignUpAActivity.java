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

    EditText fname, lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_a);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);

        String fn = Helper.Get(SignUpAActivity.this, "fname");
        String ln = Helper.Get(SignUpAActivity.this, "lname");

        fname.setText(fn);
        lname.setText(ln);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String f = fname.getText().toString();
            String l = lname.getText().toString();

            Log.d("f", f);

            if(f == "") {
                Helper.MessageBoxOKShow(SignUpAActivity.this, "Alert Box", "Please enter your first name.", 750);
                return;
            }

            if(l == "") {
                Helper.MessageBoxOKShow(SignUpAActivity.this, "Alert Box", "Please enter your last name.", 750);
                return;
            }

            Helper.Put(SignUpAActivity.this, "fname", f);
            Helper.Put(SignUpAActivity.this, "lname", l);

            Intent i = new Intent(getApplicationContext(), SignUpBActivity.class);
            startActivity(i);
            finish();
            }
        });

    }


}
