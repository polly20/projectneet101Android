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

    private class register extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUpDActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://cpanel.neet101.com/api/registration", "POST", _context);

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
