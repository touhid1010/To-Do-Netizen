package com.netizenbd.todonetizen;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper myDbHelper;

    TextView textView_instName_addTask;

    EditText editText_visitedDate,
            editText_visitedTime,
            editText_note,
            editText_nextFollowupDate,
            editText_nextFollowupTime;

    Button button_save_addTask;

    Calendar calendar_Date;
    Calendar calendar_Date2;
    OnDateSetListener dateSetListener_Date;
    OnDateSetListener dateSetListener_Date2;


    Calendar mcurrentTime;
    Calendar mcurrentTime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        myDbHelper = new MyDbHelper(getApplicationContext());

        textView_instName_addTask = (TextView) findViewById(R.id.textView_instName_addTask);

        editText_visitedDate = (EditText) findViewById(R.id.editText_visitedDate);
        editText_visitedTime = (EditText) findViewById(R.id.editText_visitedTime);
        editText_note = (EditText) findViewById(R.id.editText_note);
        editText_nextFollowupDate = (EditText) findViewById(R.id.editText_nextFollowupDate);
        editText_nextFollowupTime = (EditText) findViewById(R.id.editText_nextFollowupTime);

        editText_visitedDate.setOnClickListener(this);
        editText_visitedTime.setOnClickListener(this);
        editText_nextFollowupDate.setOnClickListener(this);
        editText_nextFollowupTime.setOnClickListener(this);


        button_save_addTask = (Button) findViewById(R.id.button_save_addTask);
        button_save_addTask.setOnClickListener(this);

        // get intent message and set title inst. name // intent from ViewTaskList.java or HomeActivity.java both
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, "");
        textView_instName_addTask.setText(s);


        // time picker

        mcurrentTime = Calendar.getInstance();
        mcurrentTime2 = Calendar.getInstance();


    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save_addTask:

                String instName = textView_instName_addTask.getText().toString();

                String vDate = editText_visitedDate.getText().toString();
                String vTime = editText_visitedTime.getText().toString();
                String note = editText_note.getText().toString();
                String fDate = editText_nextFollowupDate.getText().toString();
                String fTime = editText_nextFollowupTime.getText().toString();

                if (vDate.isEmpty() || vTime.isEmpty() || note.isEmpty() || fDate.isEmpty() || fTime.isEmpty()) {
                    Toast.makeText(AddTask.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean b = myDbHelper.saveTaskData(instName, vDate, vTime, note, fDate, fTime);
                    if (b) {
                        Toast.makeText(AddTask.this, "Success!", Toast.LENGTH_SHORT).show();
                        // clear text from editText
                        editText_visitedDate.getText().clear();
                        editText_visitedTime.getText().clear();
                        editText_note.getText().clear();
                        editText_nextFollowupDate.getText().clear();
                        editText_nextFollowupTime.getText().clear();
                    } else {
                        Toast.makeText(AddTask.this, "Data not inserted.", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.editText_visitedDate:

                datePickerVisitedDate();

                break;

            case R.id.editText_visitedTime:

                timePickerVisitedTime();

                break;

            case R.id.editText_nextFollowupDate:

                datePickerFollowupTime();

                break;

            case R.id.editText_nextFollowupTime:

                timePickerFollowupTime();

                break;

        }
    }


    public void datePickerVisitedDate() {

        editText_visitedDate = (EditText) findViewById(R.id.editText_visitedDate);
        calendar_Date = Calendar.getInstance();
        dateSetListener_Date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.setDate(editText_visitedDate, calendar_Date, year, monthOfYear, dayOfMonth);
            }
        };

        editText_visitedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.clickToShowDatePicker(AddTask.this, dateSetListener_Date, calendar_Date);
            }
        });
    }

    public void datePickerFollowupTime() {

        editText_nextFollowupDate = (EditText) findViewById(R.id.editText_nextFollowupDate);
        calendar_Date2 = Calendar.getInstance();
        dateSetListener_Date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker2 = new MyDatePicker();
                myDatePicker2.setDate(editText_nextFollowupDate, calendar_Date2, year, monthOfYear, dayOfMonth);
            }
        };

        editText_nextFollowupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker2 = new MyDatePicker();
                myDatePicker2.clickToShowDatePicker(AddTask.this, dateSetListener_Date2, calendar_Date2);
            }
        });
    }

    public void timePickerVisitedTime() {
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                // set to calender
                mcurrentTime.set(mcurrentTime.HOUR_OF_DAY, selectedHour);
                mcurrentTime.set(mcurrentTime.MINUTE, selectedMinute);

                // Set Date
                String myFormat = "h:mm a"; //In which you need put here
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                editText_visitedTime.setText(simpleDateFormat.format(mcurrentTime.getTime()));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Visited Time");
        mTimePicker.show();
    }

    public void timePickerFollowupTime() {

        int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
        int minute2 = mcurrentTime2.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker2;
        mTimePicker2 = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                // set to calender
                mcurrentTime2.set(mcurrentTime.HOUR_OF_DAY, selectedHour);
                mcurrentTime2.set(mcurrentTime.MINUTE, selectedMinute);

                // Set Date
                String myFormat2 = "h:mm a"; //In which you need put here
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(myFormat2, Locale.US);
                editText_nextFollowupTime.setText(simpleDateFormat2.format(mcurrentTime2.getTime()));
            }
        }, hour2, minute2, false);    //Yes 24 hour time
        mTimePicker2.setTitle("Select Followup Time");
        mTimePicker2.show();
    }


}
