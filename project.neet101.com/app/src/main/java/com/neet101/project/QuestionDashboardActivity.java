package com.neet101.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

    public String ExamReference ;

    private mySQLite mysqlite;

//    public List<DashboardSubject> SubjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_dashboard);

        Helper.StudentUid = 5;
        Helper.TotalExamTaken = 0;

        _context = getApplicationContext();

        btnBiology = (Button) findViewById(R.id.btnBiology) ;
        btnChemistry = (Button) findViewById(R.id.btnChemistry) ;
        btnPhysics = (Button) findViewById(R.id.btnPhysics) ;

        btnBiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validate(1)) {
                    Toast.makeText(_context,"Oops, You don't have question for today",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Helper.SubjectId = 1;
                    Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                    startActivity(i);
                }
            }
        });

        btnChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validate(2)) {
                    Toast.makeText(_context,"Oops, You don't have question for today",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Helper.SubjectId = 2;
                    Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                    startActivity(i);
                }
            }
        });

        btnPhysics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validate(3)) {
                    Toast.makeText(_context,"Oops, You don't have question for today",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Helper.SubjectId = 3;
                    Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                    startActivity(i);
                }
            }
        });

        mySQLite.isClearTable = true;
        CreatedDB();

        String isDownloaded = Helper.Get(QuestionDashboardActivity.this, "downloaded");
        if(isDownloaded.contains("NO")) {
            new checking_account().execute();
        }
        else {
            String BioMaxQuestions = Helper.Get(QuestionDashboardActivity.this, "BioMaxQuestions");
            String CheMaxQuestions = Helper.Get(QuestionDashboardActivity.this, "CheMaxQuestions");
            String PhyMaxQuestions = Helper.Get(QuestionDashboardActivity.this, "PhyMaxQuestions");

            Helper.BioMaxQuestions = Integer.parseInt(BioMaxQuestions);
            Helper.CheMaxQuestions = Integer.parseInt(CheMaxQuestions);
            Helper.PhyMaxQuestions = Integer.parseInt(PhyMaxQuestions);
        }
    }

    public boolean validate(Integer subject_id) {

        Helper.MaxQuestionsPerDay = get_subject(subject_id);

        Log.d("MaxQuestionsPerDay", Helper.MaxQuestionsPerDay.toString());

        Integer taken = get_exam_count(subject_id);

        Log.d("taken", taken.toString());

        if(taken >= Helper.MaxQuestionsPerDay) {
            return false;
        }
        return true;
    }

    public int get_subject(Integer subject_id) {

        int q = 0;

        switch (subject_id) {
            case 1 :
                q = Helper.BioMaxQuestions;
                break;
            case 2 :
                q = Helper.CheMaxQuestions;
                break;
            case 3 :
                q = Helper.PhyMaxQuestions;
                break;
        }

        return q;
    }

    public int get_exam_count(Integer subject_id) {

        Integer c = 0;

        if(subject_id == 1) {

           String BioExamCount = Helper.Get(QuestionDashboardActivity.this, "BioExamCount");

           Log.d("BioExamCount", BioExamCount);

           if(BioExamCount.length() > 0) {
               c = Integer.parseInt(BioExamCount);
           }
        }

        if(subject_id == 2) {

            String CheExamCount = Helper.Get(QuestionDashboardActivity.this, "CheExamCount");

            Log.d("CheExamCount", CheExamCount);

            if(CheExamCount.length() > 0) {
                c = Integer.parseInt(CheExamCount);
            }
        }

        if(subject_id == 3) {

            String PhyExamCount = Helper.Get(QuestionDashboardActivity.this, "PhyExamCount");

            Log.d("PhyExamCount", PhyExamCount);

            if(PhyExamCount.length() > 0) {
                c = Integer.parseInt(PhyExamCount);
            }
        }

        return c;
    }

    public void CreatedDB() {
        SQLiteDatabase sqlDB = openOrCreateDatabase(mySQLite.DB_NAME, Context.MODE_PRIVATE, null);
        mysqlite = new mySQLite(sqlDB, true);
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

            String url = Helper.Api_Url + "/api/student/v1/question_dashboard?studentid=" + Helper.StudentUid;

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, "GET", _context);

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

    public Integer ctr = 1;

    public void renderSubjects(List<DashboardSubject> d_subjects) {

        for (DashboardSubject s : d_subjects) {

            Log.d("SubjectId", s.SubjectId + "");
            Log.d("Subject", s.Subject);
            Log.d("Total_Exam", s.Total_Exam + "");

            if(s.SubjectId == 1) {
                Helper.BioMaxQuestions = s.Total_Exam;

                Helper.Put(QuestionDashboardActivity.this, "BioMaxQuestions", s.Total_Exam.toString());
            }

            if(s.SubjectId == 2) {
                Helper.CheMaxQuestions = s.Total_Exam;

                Helper.Put(QuestionDashboardActivity.this, "CheMaxQuestions", s.Total_Exam.toString());
            }

            if(s.SubjectId == 3) {
                Helper.PhyMaxQuestions = s.Total_Exam;

                Helper.Put(QuestionDashboardActivity.this, "PhyMaxQuestions", s.Total_Exam.toString());
            }

            Integer[] Subjects = new Integer[]
                    {
                            s.SubjectId,
                            s.Total_Exam
                    };
            new get_questions().execute(Subjects);

            ctr++;
        }
    }

    private class get_questions extends AsyncTask<Integer, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

//            if(ctr == 1) {
//                pDialog = new ProgressDialog(QuestionDashboardActivity.this);
//                pDialog.setMessage("Please wait...");
//                pDialog.setCancelable(false);
//                pDialog.show();
//            }
        }

        @Override
        protected String[] doInBackground(Integer... arg0) {

            Integer Subject_Id = arg0[0];
            Integer Subject_Count = arg0[1];

            Log.d("Subject_Id", Subject_Id + "");

            String[] defAccount = Helper.DefaultAccount(_context);

            HttpHandler sh = new HttpHandler(null, null);

            String url = Helper.Api_Url + "/api/student/v1/random_question?studentid="+ Helper.StudentUid +"&subj_id="+ Subject_Id +"&qcount="+ Subject_Count +"&reference_id=" + ExamReference;

//            String url_ = "http://cpanel.neet101.com/api/student/v1/random_question?studentid="+ Helper.StudentUid +"&subj_id="+ Subject_Id +"&qcount="+ Subject_Count +"&reference_id=" + ExamReference;

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, "GET", _context);

            if (jsonStr != null) {
                Log.e(TAG, "Response url: " + url);

                Log.e(TAG, "Response from url: " + jsonStr);

                String[] stringList = new String[] {
                        jsonStr,
                        Subject_Id.toString()
                };
                return stringList;
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
        protected void onPostExecute(String[] stringList) {

            super.onPostExecute(stringList);

            String JSON = stringList[0];
            String SUB_ID = stringList[1];

            try {

                JSONObject jsonObj = new JSONObject(JSON);
                Integer status = jsonObj.getInt("status");
                String reference_id = jsonObj.getString("reference_id");
                Integer batch_id = jsonObj.getInt("batch_id");

                if(status != 200) {
                    return;
                }

                mysqlite.insert(mySQLite.tblStudent, Helper.StudentUid + ", '" + reference_id + "', " + batch_id);

                JSONArray results = jsonObj.getJSONArray("result");

                Log.d("results", results + "");

                Helper.Put(QuestionDashboardActivity.this, "downloaded", "NO");

                Integer total_questions = results.length();

                Helper.Put(QuestionDashboardActivity.this, "total_question", total_questions + "");

                for(Integer i = 0; i < total_questions; i++) {

                    JSONObject q = results.getJSONObject(i);
                    Integer qid = q.getInt("id");
                    String question = q.getString("question");
                    JSONObject choices = q.getJSONObject("choices");
                    String choice_1 = choices.getString("choice_1");
                    String choice_2 = choices.getString("choice_2");
                    String choice_3 = choices.getString("choice_3");
                    String choice_4 = choices.getString("choice_4");
                    Integer correct_answer = q.getInt("correct_answer");
                    String correct_answer_explanation = q.getString("correct_answer_explanation");

                    String strQuery = Helper.StudentUid + ";" + batch_id + ";";
                    strQuery += qid + ";" + question + ";" + choice_1 + ";" + choice_2 + ";" + choice_3 + ";" + choice_4 + ";";
                    strQuery += correct_answer + ";" + correct_answer_explanation;
                    Log.d("COUNT " + i , strQuery + "");

                    String keyname = SUB_ID + "kpa" + i;

                    Log.d("keyname" , keyname);

                    Helper.Put(QuestionDashboardActivity.this, keyname, strQuery);
                }

                Log.d("SUB_ID" , SUB_ID + "");
                Log.d("results" , results + "");

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

            if(ctr > 2) {
                done();
            }

        }

    }

    public void done() {

        Helper.Put(QuestionDashboardActivity.this, "downloaded", "YES");

        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
