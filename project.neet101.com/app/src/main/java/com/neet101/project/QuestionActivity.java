package com.neet101.project;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;

    TextView txt_a, txt_b, txt_c, txt_d, txt_question;

    RadioButton rbCache;

    Button btnSubmit, btnA, btnB, btnC, btnD;

    public static String Question, choicesA, choicesB, choicesC, choicesD;

    public static Integer TotalQuestion;

    public static Integer TotalExamTaken;

    private mySQLite mysqlite;

    public void InitDB() {
        SQLiteDatabase sqlDB = openOrCreateDatabase(mySQLite.DB_NAME, Context.MODE_PRIVATE, null);
        mysqlite = new mySQLite(sqlDB, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        String total_question = Helper.Get(QuestionActivity.this, "total_question");

        TotalQuestion = Integer.parseInt(total_question);

        InitDB();

        Integer code = TotalQuestion - (TotalQuestion - TotalExamTaken);

        String question_key = Helper.SubjectId + "kpa" + code;

        String data = Helper.Get(QuestionActivity.this, question_key);

        Log.d("question_key", question_key);

        Log.d("data", data);

        String[] questions = data.split(";");

        Log.d("question_id", questions[2] + "");

        boolean isTaken = mysqlite.checkIfQuestionTaken(1);

        Log.d("isTaken", "isTaken" + "");

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txt_question = (TextView) findViewById(R.id.txt_question);
        txt_a = (TextView) findViewById(R.id.txt_a);
        txt_b = (TextView) findViewById(R.id.txt_b);
        txt_c = (TextView) findViewById(R.id.txt_c);
        txt_d = (TextView) findViewById(R.id.txt_d);

        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);

        Question = questions[3];
        choicesA = questions[4];
        choicesB = questions[5];
        choicesC = questions[6];
        choicesD = questions[7];

        txt_question.setText(Question);
        btnA.setText(choicesA);
        btnB.setText(choicesB);
        btnC.setText(choicesC);
        btnD.setText(choicesD);

        AnswerViewActivity.RightAnswer = Integer.parseInt(questions[8]);

        Log.d("RightAnswer", questions[8]);

        rbt_a = (RadioButton) findViewById(R.id.rb_a);
        rbt_b = (RadioButton) findViewById(R.id.rb_b);
        rbt_c = (RadioButton) findViewById(R.id.rb_c);
        rbt_d = (RadioButton) findViewById(R.id.rb_d);

        rbt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                AnswerViewActivity.StudentAnswer = 1;

                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                AnswerViewActivity.StudentAnswer = 1;

                rbt_a.setChecked(true);
                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        rbt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "b");

                AnswerViewActivity.StudentAnswer = 2;

                rbt_a.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                AnswerViewActivity.StudentAnswer = 2;

                rbt_a.setChecked(false);
                rbt_b.setChecked(true);
                rbt_c.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        rbt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "c");

                AnswerViewActivity.StudentAnswer = 3;

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_d.setChecked(false);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                AnswerViewActivity.StudentAnswer = 3;

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_c.setChecked(true);
                rbt_d.setChecked(false);
            }
        });

        rbt_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "d");

                AnswerViewActivity.StudentAnswer = 4;

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                AnswerViewActivity.StudentAnswer = 4;

                rbt_a.setChecked(false);
                rbt_b.setChecked(false);
                rbt_c.setChecked(false);
                rbt_d.setChecked(true);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "d");

                Intent i = new Intent(getBaseContext(), AnswerViewActivity.class);
                startActivity(i);
            }
        });

        Log.d("TotalQuestion", TotalQuestion + "");


        //http://cpanel.neet101.com/api/student/random_question?studentid=3&subj_id=1&qcount=5&reference_id=eYYWj2D9gS
    }
}
