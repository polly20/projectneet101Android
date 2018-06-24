package com.neet101.project;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {

//    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;

    ImageView img_a, img_b, img_c, img_d;

    TextView txt_a, txt_b, txt_c, txt_d, txt_question, txt_exam_count;

    RadioButton rbCache;

    Button btnSubmit, btnA, btnB, btnC, btnD;


    public static String Question, choicesA, choicesB, choicesC, choicesD;



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

        get_subject(Helper.SubjectId);

        Helper.TotalQuestion = Helper.MaxQuestionsPerDay; // Integer.parseInt(total_question);

        Helper.TotalQuestionOnCount = Integer.parseInt(total_question) - 1;

        InitDB();

        Integer code = Helper.TotalQuestionOnCount - (Helper.TotalQuestionOnCount - Helper.TotalExamTaken);

        String question_key = Helper.SubjectId + "kpa" + code;

        String data = Helper.Get(QuestionActivity.this, question_key);

        Log.d("question_key", question_key);

        Log.d("data", data);

        String[] questions = data.split(";");

        Log.d("question_id", questions[2] + "");

        boolean isTaken = mysqlite.checkIfQuestionTaken(1);

        Log.d("isTaken", "isTaken" + "");

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txt_exam_count = (TextView) findViewById(R.id.txt_exam_count);
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


        String exam_counter = Helper.TotalExamTaken + " of " + Helper.TotalQuestion;
        txt_exam_count.setText(exam_counter);
        txt_question.setText(Question);
        btnA.setText(choicesA);
        btnB.setText(choicesB);
        btnC.setText(choicesC);
        btnD.setText(choicesD);

        Log.d("RightAnswer", questions[8]);

        Answer2Activity.RightAnswer = Integer.parseInt(questions[8]);

        img_a = (ImageView) findViewById(R.id.img_a);
        img_b = (ImageView) findViewById(R.id.img_b);
        img_c = (ImageView) findViewById(R.id.img_c);
        img_d = (ImageView) findViewById(R.id.img_d);

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                Answer2Activity.StudentAnswer = 1;

                img_a.setImageResource(R.mipmap.selected_icon);
                img_b.setImageResource(R.mipmap.not_selected_icon);
                img_c.setImageResource(R.mipmap.not_selected_icon);
                img_d.setImageResource(R.mipmap.not_selected_icon);

            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                Answer2Activity.StudentAnswer = 2;

                img_a.setImageResource(R.mipmap.not_selected_icon);
                img_b.setImageResource(R.mipmap.selected_icon);
                img_c.setImageResource(R.mipmap.not_selected_icon);
                img_d.setImageResource(R.mipmap.not_selected_icon);

            }
        });


        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                Answer2Activity.StudentAnswer = 3;

                img_a.setImageResource(R.mipmap.not_selected_icon);
                img_b.setImageResource(R.mipmap.not_selected_icon);
                img_c.setImageResource(R.mipmap.selected_icon);
                img_d.setImageResource(R.mipmap.not_selected_icon);

            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                Answer2Activity.StudentAnswer = 4;

                img_a.setImageResource(R.mipmap.not_selected_icon);
                img_b.setImageResource(R.mipmap.not_selected_icon);
                img_c.setImageResource(R.mipmap.not_selected_icon);
                img_d.setImageResource(R.mipmap.selected_icon);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "d");

                Intent i = new Intent(getBaseContext(), Answer2Activity.class);
                startActivity(i);

                finish();
            }
        });

        Log.d("TotalQuestion", Helper.TotalQuestion + "");
    }

    public void get_subject(Integer subject_id) {
        switch (subject_id) {
            case 1 :
                Helper.MaxQuestionsPerDay = Helper.BioMaxQuestions;
                this.setTitle("Neet101 - Biology");
                break;
            case 2 :
                Helper.MaxQuestionsPerDay = Helper.CheMaxQuestions;
                this.setTitle("Neet101 - Chemistry");
                break;
            case 3 :
                Helper.MaxQuestionsPerDay = Helper.PhyMaxQuestions;
                this.setTitle("Neet101 - Physics");
                break;
        }
    }
}
