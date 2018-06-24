package com.neet101.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class Answer2Activity extends AppCompatActivity  {


    ImageView img_a, img_b, img_c, img_d;

    TextView txt_status, txt_question, txt_a, txt_b, txt_c, txt_d;

    public static Integer RightAnswer;
    public static Integer StudentAnswer;

    public TableRow tr_a, tr_b, tr_c, tr_d;

    Button btnDontKnow, btnKnow, btnSomewhatKnow;

    public static boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer2);

        get_subject(Helper.SubjectId);

        img_a = (ImageView) findViewById(R.id.img_a);
        img_b = (ImageView) findViewById(R.id.img_b);
        img_c = (ImageView) findViewById(R.id.img_c);
        img_d = (ImageView) findViewById(R.id.img_d);

        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_status.setBackgroundResource(R.color.incorrect);

        txt_question = (TextView) findViewById(R.id.txt_question);
        txt_a = (TextView) findViewById(R.id.txt_a);
        txt_b = (TextView) findViewById(R.id.txt_b);
        txt_c = (TextView) findViewById(R.id.txt_c);
        txt_d = (TextView) findViewById(R.id.txt_d);

        txt_question.setText(QuestionActivity.Question);
        txt_a.setText(QuestionActivity.choicesA);
        txt_b.setText(QuestionActivity.choicesB);
        txt_c.setText(QuestionActivity.choicesC);
        txt_d.setText(QuestionActivity.choicesD);

        if(RightAnswer != StudentAnswer) {
            txt_status.setBackgroundResource(R.color.incorrect);
            txt_status.setText("INCORRECT");
            isCorrect = false;

            setBackgroundColor(RightAnswer, true, false);

            setBackgroundColor(StudentAnswer, false, false);
        }
        else {
            txt_status.setBackgroundResource(R.color.correct);
            txt_status.setText("CORRECT");
            isCorrect = true;

            setBackgroundColor(StudentAnswer, true, true);
        }

        btnDontKnow = (Button) findViewById(R.id.btnDontKnow) ;
        btnKnow = (Button) findViewById(R.id.btnKnow) ;
        btnSomewhatKnow = (Button) findViewById(R.id.btnSomewhatKnow) ;

        btnDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.TotalExamTaken++;

                Helper.DontKnow++;

                update_status();
            }
        });

        btnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.TotalExamTaken++;

                Helper.Know++;

                update_status();
            }
        });

        btnSomewhatKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.TotalExamTaken++;

                Helper.SomewhatKnow++;

                update_status();
            }
        });

    }

    public  void update_status() {
        if(isCorrect) {
            Helper.Correct++;
        }
        else {
            Helper.Incorrect++;
        }

        Log.d("TotalExamTaken", Helper.TotalExamTaken + "");

        Log.d("TotalQuestion", Helper.TotalQuestion + "");

        if(Helper.TotalExamTaken >= Helper.TotalQuestion) {
            update_exam_count(Helper.TotalExamTaken);

            Intent i = new Intent(getBaseContext(), ResultsActivity.class);
            startActivity(i);

            finish();
        }
        else {
            Intent i = new Intent(getBaseContext(), QuestionActivity.class);
            startActivity(i);

            finish();
        }
    }

    public void get_subject(Integer subject_id) {
        switch (subject_id) {
            case 1 :
                this.setTitle("Neet101 - Biology");
                break;
            case 2 :
                this.setTitle("Neet101 - Chemistry");
                break;
            case 3 :
                this.setTitle("Neet101 - Physics");
                break;
        }
    }

    public void update_exam_count(Integer exam_count) {
        if(Helper.SubjectId == 1) {

            Helper.Put(Answer2Activity.this, "BioExamCount", exam_count.toString());
        }

        if(Helper.SubjectId == 2) {

            Helper.Put(Answer2Activity.this, "CheExamCount", exam_count.toString());
        }

        if(Helper.SubjectId == 3) {

            Helper.Put(Answer2Activity.this, "PhyExamCount", exam_count.toString());
        }
    }

    public void setBackgroundColor(Integer a, boolean correct, boolean correct_only) {

        tr_a = (TableRow) findViewById(R.id.tr_a);
        tr_b = (TableRow) findViewById(R.id.tr_b);
        tr_c = (TableRow) findViewById(R.id.tr_c);
        tr_d = (TableRow) findViewById(R.id.tr_d);

        switch (a) {
            case 1:

                if(correct) {
                    tr_a.setBackgroundResource(R.color.correct);

                    if(correct_only) {
                        img_a.setImageResource(R.mipmap.selected_icon);
                    }
                }
                else {
                    img_a.setImageResource(R.mipmap.selected_icon);

                    tr_a.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 2:

                if(correct) {
                    tr_b.setBackgroundResource(R.color.correct);

                    if(correct_only) {
                        img_b.setImageResource(R.mipmap.selected_icon);
                    }
                }
                else {
                    img_b.setImageResource(R.mipmap.selected_icon);

                    tr_b.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 3:

                if(correct) {
                    tr_c.setBackgroundResource(R.color.correct);

                    if(correct_only) {
                        img_c.setImageResource(R.mipmap.selected_icon);
                    }
                }
                else {
                    img_c.setImageResource(R.mipmap.selected_icon);

                    tr_c.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 4:

                if(correct) {
                    tr_d.setBackgroundResource(R.color.correct);

                    if(correct_only) {
                        img_d.setImageResource(R.mipmap.selected_icon);
                    }
                }
                else {
                    img_d.setImageResource(R.mipmap.selected_icon);

                    tr_d.setBackgroundResource(R.color.incorrect);
                }

                break;

        }
    }

}

