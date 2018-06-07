package com.neet101.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerViewActivity extends AppCompatActivity {

    TextView txt_status;

    public static Integer RightAnswer;
    public static Integer StudentAnswer;

    Button btnDontKnow, btnKnow, btnSomewhatKnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_view);

        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_status.setBackgroundResource(R.color.incorrect);


        if(RightAnswer != StudentAnswer) {
            txt_status.setBackgroundResource(R.color.incorrect);
        }
        else {
            txt_status.setBackgroundResource(R.color.correct);
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
}
