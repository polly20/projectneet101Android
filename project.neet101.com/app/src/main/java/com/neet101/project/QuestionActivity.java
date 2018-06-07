package com.neet101.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;

    TextView txt_a, txt_b, txt_c, txt_d, txt_question;

    RadioButton rbCache;

    public static Integer TotalQuestion;

    private mySQLite mysqlite;

    public void InitDB() {
        SQLiteDatabase sqlDB = openOrCreateDatabase(mySQLite.DB_NAME, Context.MODE_PRIVATE, null);
        mysqlite = new mySQLite(sqlDB, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Helper.SubjectId = 1;

        String total_question = Helper.Get(QuestionActivity.this, "total_question");

        TotalQuestion = Integer.parseInt(total_question);


        InitDB();

        Integer code = TotalQuestion - (TotalQuestion - 0);

        String question_key = Helper.SubjectId + "_" + code;

        String data = Helper.Get(QuestionActivity.this, question_key);

        Log.d("question_key", question_key);

        Log.d("data", data);


        String[] questions = data.split(";");

        Log.d("question_id", questions[2] + "");



        boolean isTaken = mysqlite.checkIfQuestionTaken(1);

        Log.d("isTaken", "isTaken" + "");

        txt_question = (TextView) findViewById(R.id.txt_question);
        txt_a = (TextView) findViewById(R.id.txt_a);
        txt_b = (TextView) findViewById(R.id.txt_b);
        txt_c = (TextView) findViewById(R.id.txt_c);
        txt_d = (TextView) findViewById(R.id.txt_d);


        txt_question.setText(questions[3]);
        txt_a.setText(questions[4]);
        txt_b.setText(questions[5]);
        txt_c.setText(questions[6]);
        txt_d.setText(questions[7]);

        rbt_a = (RadioButton) findViewById(R.id.rb_a);
        rbt_b = (RadioButton) findViewById(R.id.rb_b);
        rbt_c = (RadioButton) findViewById(R.id.rb_c);
        rbt_d = (RadioButton) findViewById(R.id.rb_d);

        rbt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        rbt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "b");

                rbt_a.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        rbt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "c");

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        rbt_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "d");

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
            }
        });

        Log.d("TotalQuestion", TotalQuestion + "");


        //http://cpanel.neet101.com/api/student/random_question?studentid=3&subj_id=1&qcount=5&reference_id=eYYWj2D9gS
    }
}
