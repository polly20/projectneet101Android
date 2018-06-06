package com.neet101.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;

    RadioButton rbCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

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

        //http://cpanel.neet101.com/api/student/random_question?studentid=3&subj_id=1&qcount=5&reference_id=eYYWj2D9gS
    }
}
