package com.neet101.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

public class AnswerViewActivity extends AppCompatActivity {

    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;

    TextView txt_status, txt_question, txt_a, txt_b, txt_c, txt_d;

    public static Integer RightAnswer;
    public static Integer StudentAnswer;

    public TableRow tr_a, tr_b, tr_c, tr_d;

    Button btnDontKnow, btnKnow, btnSomewhatKnow;

    public static boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_view);

        rbt_a = (RadioButton) findViewById(R.id.rb_a);
        rbt_b = (RadioButton) findViewById(R.id.rb_b);
        rbt_c = (RadioButton) findViewById(R.id.rb_c);
        rbt_d = (RadioButton) findViewById(R.id.rb_d);

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

            setBackgroundColor(RightAnswer, true);

            setBackgroundColor(StudentAnswer, false);
        }
        else {
            txt_status.setBackgroundResource(R.color.correct);
            txt_status.setText("CORRECT");
            isCorrect = true;

            setBackgroundColor(StudentAnswer, true);
        }

        btnDontKnow = (Button) findViewById(R.id.btnDontKnow) ;
        btnKnow = (Button) findViewById(R.id.btnKnow) ;
        btnSomewhatKnow = (Button) findViewById(R.id.btnSomewhatKnow) ;

        btnDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuestionActivity.TotalExamTaken++;
                Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                startActivity(i);
            }
        });

        btnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuestionActivity.TotalExamTaken++;
                Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                startActivity(i);
            }
        });

        btnSomewhatKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuestionActivity.TotalExamTaken++;
                Intent i = new Intent(getBaseContext(), QuestionActivity.class);
                startActivity(i);
            }
        });


    }

    public void setBackgroundColor(Integer a, boolean correct) {

        tr_a = (TableRow) findViewById(R.id.tr_a);
        tr_b = (TableRow) findViewById(R.id.tr_b);
        tr_c = (TableRow) findViewById(R.id.tr_c);
        tr_d = (TableRow) findViewById(R.id.tr_d);

        switch (a) {
            case 1:

                if(correct) {
                    tr_a.setBackgroundResource(R.color.correct);
                }
                else {
                    rbt_a.setChecked(true);

                    tr_a.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 2:

                if(correct) {
                    tr_b.setBackgroundResource(R.color.correct);
                }
                else {
                    rbt_b.setChecked(true);

                    tr_b.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 3:

                if(correct) {
                    tr_c.setBackgroundResource(R.color.correct);
                }
                else {
                    rbt_c.setChecked(true);

                    tr_c.setBackgroundResource(R.color.incorrect);
                }

                break;

            case 4:

                if(correct) {
                    tr_d.setBackgroundResource(R.color.correct);
                }
                else {
                    rbt_d.setChecked(true);

                    tr_d.setBackgroundResource(R.color.incorrect);
                }

                break;

        }
    }
}
