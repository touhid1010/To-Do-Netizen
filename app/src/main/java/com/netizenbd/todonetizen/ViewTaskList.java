package com.netizenbd.todonetizen;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewTaskList extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper myDbHelper;

    TextView textView_viewTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab_newTask = (FloatingActionButton) findViewById(R.id.fab_newTask);
        fab_newTask.setOnClickListener(this);

        textView_viewTask = (TextView) findViewById(R.id.textView_viewTask);

        // get Home spinner item text to get data
        Bundle extras = getIntent().getExtras();
        String msg = extras.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, "");
        textView_viewTask.setText(msg);

        myReportGenerate();

    } // end of onCreate

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_newTask:

                Intent i = new Intent(getApplicationContext(), AddTask.class);
                i.putExtra(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, textView_viewTask.getText());
                startActivity(i);

                break;
        }

    }


    @Override
    protected void onRestart() {
        myReportGenerate();
        super.onRestart();
    }

    private void myReportGenerate() {
        myDbHelper = new MyDbHelper(getApplicationContext());
        Cursor cursor = myDbHelper.getAllTask(textView_viewTask.getText().toString());

        // using table layout

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout_main);

        // clear previous views from linearLayout
        tableLayout.removeAllViews();

        TableRow tbrow0 = new TableRow(this);


        // Serial
        TextView tv0 = new TextView(this);
        tv0.setText("Sl.No. ");
        tv0.setPadding(10,10,10,10);
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);


        // visited Date and time
        TextView tv1 = new TextView(this);
        tv1.setText(" Visited\nDate & Time ");
        tv1.setPadding(10,10,10,10);
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);


        // note
        TextView tv2 = new TextView(this);
        tv2.setText(" Note ");
        tv2.setPadding(10,10,10,10);
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);


        // following date and time
        TextView tv3 = new TextView(this);
        tv3.setText(" Followup\nDate & Time ");
        tv3.setPadding(10,10,10,10);
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);

        tbrow0.setBackgroundColor(Color.GRAY);
        tableLayout.addView(tbrow0);

        boolean rowColor = false;
        int j = 1;
        while (cursor.moveToNext()) {
            int i = 0;

            TableRow tableRow = new TableRow(this);


            // Serial no.
            TextView textView_sn = new TextView(this);


            textView_sn.setText("" + j); // serial no is not related with database, it's just serial, here j is only for serial no
            j++;
            i++;
            textView_sn.setTextColor(Color.BLACK);
            textView_sn.setGravity(Gravity.CENTER);

            tableRow.addView(textView_sn);

            // visited Date and time
            TextView textView_name = new TextView(this);
            textView_name.setText(" " + cursor.getString(i) + " \n" + cursor.getString(i + 1) + " ");
            i++;
            i++;
            textView_name.setTextColor(Color.BLACK);
            textView_name.setGravity(Gravity.CENTER);

            tableRow.addView(textView_name);


            // note
            TextView textView_phone = new TextView(this);
            textView_phone.setText(" " + cursor.getString(i) + " ");
            i++;
            textView_phone.setTextColor(Color.BLACK);
            textView_phone.setGravity(Gravity.CENTER);

            tableRow.addView(textView_phone);


            // followup Date and time
            TextView textView_address = new TextView(this);
            textView_address.setText(" " + cursor.getString(i) + " \n" + cursor.getString(i + 1) + " ");
            i++;
            i++;
            textView_address.setTextColor(Color.BLACK);
            textView_address.setGravity(Gravity.CENTER);

            tableRow.addView(textView_address);

            // set color background for table row after each row, starts from 2nd row
            if (rowColor) {
                tableRow.setBackgroundColor(0xFFCCCCCC);
                rowColor = false;
            } else {

                rowColor = true;
            }

            // add the row on the table
            tableLayout.addView(tableRow);

        }


    }


}