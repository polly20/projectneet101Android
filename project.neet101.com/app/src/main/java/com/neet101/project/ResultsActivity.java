package com.neet101.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    TextView txt_a, txt_b, txt_c, txt_score;

    Button btnBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        get_subject(Helper.SubjectId);

        txt_score = (TextView) findViewById(R.id.txt_score);
        txt_a = (TextView) findViewById(R.id.txt_a);
        txt_b = (TextView) findViewById(R.id.txt_b);
        txt_c = (TextView) findViewById(R.id.txt_c);

        Integer total_question = Helper.Correct + Helper.Incorrect;

        txt_score.setText("SCORE: " + Helper.Correct + " of " + total_question);
        txt_a.setText(Helper.DontKnow.toString());
        txt_b.setText(Helper.Know.toString());
        txt_c.setText(Helper.SomewhatKnow.toString());

        btnBackToDashboard = (Button) findViewById(R.id.btnBackToDashboard);

        btnBackToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logs", "a");

                Intent i = new Intent(getBaseContext(), QuestionDashboardActivity.class);
                startActivity(i);

            }
        });
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
}
