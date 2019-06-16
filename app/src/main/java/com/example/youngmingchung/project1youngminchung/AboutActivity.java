package com.example.youngmingchung.project1youngminchung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class AboutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    Button buttonDate;
    TextView textViewDate;
    TextView textViewTime;
    TimePicker tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonDate = findViewById(R.id.buttonDate);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);


        findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
//                showTimePickerDialog();
            }
        });

        //Get a new instance of Calendar
        final Calendar c= Calendar.getInstance();
        int hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        int minute = c.get(c.MINUTE); //Current Minute
        final int second = c.get(c.SECOND); //Current Second

        //Get the widgets reference from XML layout
        TimePicker tp = (TimePicker) findViewById(R.id.tp);

        //Display the TimePicker initial time
        textViewTime.setText(hourOfDay + ":" + minute + ":" + second);

        //Set a TimeChangedListener for TimePicker widget
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                textViewTime.setText(hourOfDay + ":" + minute);
            }
        });
    }

    // show Date using DatePickerDialog
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "" + (month+1) + "/" + dayOfMonth + "/" + year;
        textViewDate.setText(date);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("report",textViewDate.getText().toString());
        editor.putString("time",textViewTime.getText().toString());

        // write shared pref to file
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //restore our shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        textViewDate.setText(settings.getString("report",""));

    }

    public void onClickHome(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeIntent);
    }

    public void onClickAdd(View view) {
        Intent addIntent = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(addIntent);
    }

    public void onClickShelf(View view) {
        Intent reportIntent = new Intent(getApplicationContext(), ReportActivity.class);
        startActivity(reportIntent);
    }

    public void onClickSetting(View view) {
        Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(aboutIntent);
    }
}
