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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionDashboardActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    public Context _context;

    private ProgressDialog pDialog;

    public Button btnBiology, btnChemistry, btnPhysics;

    public Integer StudentID = 3;

    public String ExamReference ;

//    public List<DashboardSubject> SubjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_dashboard);

        _context = getApplicationContext();

        btnBiology = (Button) findViewById(R.id.btnBiology) ;
        btnChemistry = (Button) findViewById(R.id.btnChemistry) ;
        btnPhysics = (Button) findViewById(R.id.btnPhysics) ;

        btnBiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnPhysics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });


        new checking_account().execute();
    }

    private class checking_account extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(QuestionDashboardActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            String[] defAccount = Helper.DefaultAccount(_context);

            HttpHandler sh = new HttpHandler(null, null);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://cpanel.neet101.com/api/student/question_dashboard?studentid=" + StudentID, "GET", _context);

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
                Integer student_id = jsonObj.getInt("student_id");
                ExamReference = jsonObj.getString("reference_id");

                if(student_id < 1) {
                    return;
                }

                List<DashboardSubject> SubjectList = new ArrayList<>();

                JSONArray subjects = jsonObj.getJSONArray("questions_per_subject");

                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject c = subjects.getJSONObject(i);
                    Log.d("c", c.toString());
                    DashboardSubject _subject = new DashboardSubject();
                    _subject.SubjectId = c.getInt("Id");
                    _subject.Subject = c.getString("Name");
                    _subject.Total_Exam = c.getInt("Count");
                    SubjectList.add(_subject);
                }

                Helper.SubjectList = SubjectList;

                renderSubjects(Helper.SubjectList);

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

    public void renderSubjects(List<DashboardSubject> d_subjects) {

        Integer ctr = 1;

        for (DashboardSubject s : d_subjects) {

            if(ctr == 1) {
//                Helper._Bio.SubjectId =
            }
            else if(ctr == 2) {

            }
            else {

            }

            Log.d("SubjectId", s.SubjectId + "");
            Log.d("Subject", s.Subject);
            Log.d("Total_Exam", s.Total_Exam + "");

            ctr++;
        }
        
    }
}
