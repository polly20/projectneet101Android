package com.neet101.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;



import android.support.v4.app.Fragment; import android.support.v4.app.FragmentActivity; import android.support.v4.app.FragmentManager;

public class SettingsAActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    public ToggleButton btnToggleA, btnToggleB;
    public Button btnSetDate;

    public Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_a);
        getSupportActionBar().setTitle("Target Settings");

        myContext = getApplication();

        btnToggleA = (ToggleButton) findViewById(R.id.btnToggleA);
        btnToggleA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked) {
                    buttonView.setTextColor(Color.WHITE);
                    buttonView.setBackground(ContextCompat.getDrawable(myContext, R.drawable.button_enable_pink));
                }
                else
                {
                    buttonView.setTextColor(Color.BLACK);
                    buttonView.setBackground(ContextCompat.getDrawable(myContext, R.drawable.button_enable));
                }
            }
        });

        btnToggleB = (ToggleButton) findViewById(R.id.btnToggleB);
        btnToggleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked) {
                    buttonView.setTextColor(Color.WHITE);
                    buttonView.setBackground(ContextCompat.getDrawable(myContext, R.drawable.button_enable_pink));
                }
                else
                {
                    buttonView.setTextColor(Color.BLACK);
                    buttonView.setBackground(ContextCompat.getDrawable(myContext, R.drawable.button_enable));
                }
            }
        });

        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    public void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
