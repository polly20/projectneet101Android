package com.neet101.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpCActivity extends AppCompatActivity {

    private String TAG = SignUpDActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    public Context _context;

    ImageView btnBack;
    TextView btnNext;

    Button btnSubmit;

    EditText txtPassword;
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_c);
        getSupportActionBar().hide();

        Init();

        _context = getApplicationContext();

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

                Register();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Helper.IsEmpty(txtPassword)) {
                    Helper.MessageBoxOKShow(SignUpCActivity.this, "Alert Box", "Please enter your password.", 750);
                    return;
                }

                String p = txtPassword.getText().toString();

                Helper.Put(SignUpCActivity.this, "password", p);

                Register();

            }
        });
    }

    public void Init() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        String p = Helper.Get(SignUpCActivity.this, "password");
        txtPassword.setText(p);
    }

    public void Register() {

        String facebook_id = Helper.Get(SignUpCActivity.this, "facebook_id");
        String facebook_profile = Helper.Get(SignUpCActivity.this, "facebook_profile");

        String fname = Helper.Get(SignUpCActivity.this, "fname");
        String lname = Helper.Get(SignUpCActivity.this, "lname");
        String email = Helper.Get(SignUpCActivity.this, "email");
        String password = Helper.Get(SignUpCActivity.this, "password");

        Integer type = 1;
        if(facebook_id.length() > 0) {
            type = 2;
        }

        data = "first_name=" + fname + "&last_name=" + lname + "&email=" + email + "&password=" + password + "&fb_id=" + facebook_id + "&fb_profile=null&registration_type=" + type + "&member_type=1";

        Log.d("data", data);

        new registerAsync().execute();
    }

    private class registerAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUpCActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://cpanel.neet101.com/api/registration?" + data, "POST", _context);

            Log.e(TAG, "data: " + data);
            Log.e(TAG, "jsonStr: " + jsonStr);

            if (jsonStr != null) {
                Log.e(TAG, "Response from url: " + jsonStr);
                return jsonStr;
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JSON) {
            super.onPostExecute(JSON);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            try {
                JSONObject jsonObj = new JSONObject(JSON);

                // Getting JSON Array node
                String status = jsonObj.get("status").toString();

                String result = jsonObj.get("result").toString();

                Log.d("status", status);

                Log.d("result", result);

                if(Integer.parseInt(status) != 200) {
                    Toast.makeText(getApplicationContext(),
                            result,
                            Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(i);
                    finish();
                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        }

    }

}
