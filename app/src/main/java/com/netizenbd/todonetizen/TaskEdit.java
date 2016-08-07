package com.netizenbd.todonetizen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class TaskEdit extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper myDbHelper;

    TextView textView_instNameTitle_edit;

    EditText editText_edit_visitedDate,
            editText_edit_visitedTime,
            editText_edit_note,
            editText_edit_nextFollowupDate,
            editText_edit_nextFollowupTime;

    Button button_edit_update,
            button_edit_delete;

    String stringTaskId;

    // picker
    Calendar calendar_Date;
    Calendar calendar_Date2;
    DatePickerDialog.OnDateSetListener dateSetListener_Date;
    DatePickerDialog.OnDateSetListener dateSetListener_Date2;


    Calendar mcurrentTime;
    Calendar mcurrentTime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        myDbHelper = new MyDbHelper(this);


        textView_instNameTitle_edit = (TextView) findViewById(R.id.textView_instNameTitle_edit);

        editText_edit_visitedDate = (EditText) findViewById(R.id.editText_edit_visitedDate);
        editText_edit_visitedDate.setOnClickListener(this);
        editText_edit_visitedTime = (EditText) findViewById(R.id.editText_edit_visitedTime);
        editText_edit_visitedTime.setOnClickListener(this);
        editText_edit_note = (EditText) findViewById(R.id.editText_edit_note);
        editText_edit_nextFollowupDate = (EditText) findViewById(R.id.editText_edit_nextFollowupDate);
        editText_edit_nextFollowupDate.setOnClickListener(this);
        editText_edit_nextFollowupTime = (EditText) findViewById(R.id.editText_edit_nextFollowupTime);
        editText_edit_nextFollowupTime.setOnClickListener(this);

        button_edit_update = (Button) findViewById(R.id.button_edit_update);
        button_edit_delete = (Button) findViewById(R.id.button_edit_delete);
        button_edit_update.setOnClickListener(this);
        button_edit_delete.setOnClickListener(this);


        // get task id from ViewTaskList, and inst name by intent
        Bundle extras = getIntent().getExtras();
//        Integer msgId = extras.getString(ViewTaskList.INTENT_MESSAGE_PASS_TASK_ID, "");
        Integer taskId = extras.getInt(ViewTaskList.INTENT_MESSAGE_PASS_TASK_ID);
        stringTaskId = String.valueOf(taskId); // to update and delete operation method argument
        String instName = extras.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY);
        //set title inst Name
        textView_instNameTitle_edit.setText(instName);

        Cursor cursorAllTask = myDbHelper.getAllTaskById(taskId);

        // set info from cursor
        while (cursorAllTask.moveToNext()) {
            int i = 2;
//            stringId = cursorAllTask.getString(i);

            editText_edit_visitedDate.setText(cursorAllTask.getString(i));
            i++;
            editText_edit_visitedTime.setText(cursorAllTask.getString(i));
            i++;
            editText_edit_note.setText(cursorAllTask.getString(i));
            i++;
            editText_edit_nextFollowupDate.setText(cursorAllTask.getString(i));
            i++;
            editText_edit_nextFollowupTime.setText(cursorAllTask.getString(i));

            break;
        }


        // time picker

        mcurrentTime = Calendar.getInstance();
        mcurrentTime2 = Calendar.getInstance();

    } // end of onCreate

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_edit_update:
                long updateMessage = myDbHelper.updateTask(stringTaskId, editText_edit_visitedDate.getText().toString(), editText_edit_visitedTime.getText().toString(),
                        editText_edit_note.getText().toString(), editText_edit_nextFollowupDate.getText().toString(), editText_edit_nextFollowupTime.getText().toString());


                if (updateMessage > 0) {
                    Toast.makeText(TaskEdit.this, "Success.", Toast.LENGTH_LONG).show();

                    // end the activity
                    finish();

                } else {
                    Toast.makeText(TaskEdit.this, "Error!", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.button_edit_delete:

                new AlertDialog.Builder(this)
                        .setMessage("Want to delete this task?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long deleteTask = myDbHelper.deleteTask(stringTaskId);
                                if (deleteTask > 0) {
                                    Toast.makeText(TaskEdit.this, "Success. " + deleteTask + " row deleted.", Toast.LENGTH_LONG).show();
                                    // end the activity
                                    finish();

                                } else {
                                    Toast.makeText(TaskEdit.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .create().show();


                break;

            case R.id.editText_edit_visitedDate:


                // pickers
                datePickerVisitedDate();


                break;

            case R.id.editText_edit_visitedTime:


                timePickerVisitedTime();


                break;


            case R.id.editText_edit_nextFollowupDate:

                datePickerFollowupTime();


                break;


            case R.id.editText_edit_nextFollowupTime:

                timePickerFollowupTime();

                break;


        }
    }


// my methods

    public void datePickerVisitedDate() {

        editText_edit_visitedDate = (EditText) findViewById(R.id.editText_edit_visitedDate);
        calendar_Date = Calendar.getInstance();
        dateSetListener_Date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.setDate(editText_edit_visitedDate, calendar_Date, year, monthOfYear, dayOfMonth);
            }
        };

        editText_edit_visitedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.clickToShowDatePicker(TaskEdit.this, dateSetListener_Date, calendar_Date);
            }
        });
    }

    public void timePickerVisitedTime() {
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TaskEdit.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                // set to calender
                mcurrentTime.set(mcurrentTime.HOUR_OF_DAY, selectedHour);
                mcurrentTime.set(mcurrentTime.MINUTE, selectedMinute);

                // Set Date
                String myFormat = "h:mm a"; //In which you need put here
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                editText_edit_visitedTime.setText(simpleDateFormat.format(mcurrentTime.getTime()));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Visited Time");
        mTimePicker.show();
    }

    public void datePickerFollowupTime() {

        editText_edit_nextFollowupDate = (EditText) findViewById(R.id.editText_edit_nextFollowupDate);
        calendar_Date2 = Calendar.getInstance();
        dateSetListener_Date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker2 = new MyDatePicker();
                myDatePicker2.setDate(editText_edit_nextFollowupDate, calendar_Date2, year, monthOfYear, dayOfMonth);
            }
        };

        editText_edit_nextFollowupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker2 = new MyDatePicker();
                myDatePicker2.clickToShowDatePicker(TaskEdit.this, dateSetListener_Date2, calendar_Date2);
            }
        });
    }

    public void timePickerFollowupTime() {

        int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
        int minute2 = mcurrentTime2.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker2;
        mTimePicker2 = new TimePickerDialog(TaskEdit.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                // set to calender
                mcurrentTime2.set(mcurrentTime.HOUR_OF_DAY, selectedHour);
                mcurrentTime2.set(mcurrentTime.MINUTE, selectedMinute);

                // Set Date
                String myFormat2 = "h:mm a"; //In which you need put here
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(myFormat2, Locale.US);
                editText_edit_nextFollowupTime.setText(simpleDateFormat2.format(mcurrentTime2.getTime()));
            }
        }, hour2, minute2, false);    //Yes 24 hour time
        mTimePicker2.setTitle("Select Followup Time");
        mTimePicker2.show();
    }


}
