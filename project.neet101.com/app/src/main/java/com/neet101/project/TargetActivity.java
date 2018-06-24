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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TargetActivity extends AppCompatActivity {

    String TAG = "TAG";

    private ProgressDialog pDialog;

    public Context _context;

    Button btnSubmit;

    String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        getSupportActionBar().hide();

        _context = getApplicationContext();

        Helper.Put(TargetActivity.this, "downloaded", "NO");

        String UID_ = Helper.Get(TargetActivity.this, "UID");

        Log.d("StudentUid", UID_ + "");

        Integer StudentUid = Integer.parseInt(UID_);

        Helper.StudentUid = StudentUid;

        target = Helper.Get(TargetActivity.this, "target");

        if(target.length() > 0) {
            Intent i = new Intent(getBaseContext(), QuestionDashboardActivity.class);
            startActivity(i);

            finish();
        }

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                target = ((RadioButton)findViewById(checkedId)).getText().toString();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new set_target().execute();
            }
        });

    }

    private class set_target extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(TargetActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Integer... arg0) {

            String[] defAccount = Helper.DefaultAccount(_context);

            HttpHandler sh = new HttpHandler(null, null);

            String url = Helper.Api_Url + "/api/student/v1/recommended_question?sid="+ Helper.StudentUid +"&target="+ target +"&override=0&q_overide=0&uid=0";

            String jsonStr = sh.makeServiceCall(url, "GET", _context);

            if (jsonStr != null) {
                Log.e(TAG, "Response url: " + url);

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
        protected void onPostExecute(String stringList) {
            super.onPostExecute(stringList);


            try {

                JSONObject jsonObj = new JSONObject(stringList);
                Integer status = jsonObj.getInt("status");
                Integer uid = jsonObj.getInt("uid");
                Integer recommended = jsonObj.getInt("recommended_questions_per_day");

                Log.d("status", status.toString());
                Log.d("uid", uid.toString());
                Log.d("recommended", recommended.toString());

                Helper.Put(TargetActivity.this, "target", target.toString());

                Helper.Put(TargetActivity.this, "recommended", recommended.toString());

                done();

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

    public void done() {
        if (pDialog.isShowing())
            pDialog.dismiss();

        Intent i = new Intent(getBaseContext(), RecommendActivity.class);
        startActivity(i);

        finish();
    }
}
