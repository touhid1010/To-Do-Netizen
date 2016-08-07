package com.netizenbd.todonetizen;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewTaskList extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper myDbHelper;

    TextView textView_viewTask,
            textView_empty_task_message;

    FloatingActionButton fab_newTask;

    MyAllAnimations myAllAnimations;

    public final static String INTENT_MESSAGE_PASS_TASK_ID = "taskid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fab_newTask = (FloatingActionButton) findViewById(R.id.fab_newTask);
        fab_newTask.setOnClickListener(this);

        textView_viewTask = (TextView) findViewById(R.id.textView_viewTask);
        textView_empty_task_message = (TextView) findViewById(R.id.textView_empty_task_message);

        myAllAnimations = new MyAllAnimations();

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

            default: // work dynamic edit buttons
                Intent intent = new Intent(getApplicationContext(), TaskEdit.class);
                intent.putExtra(INTENT_MESSAGE_PASS_TASK_ID, v.getId());
                intent.putExtra(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, textView_viewTask.getText().toString());
                startActivity(intent);


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

        if (cursor.getCount() > 0) {

            textView_empty_task_message.setVisibility(View.GONE);

            myAllAnimations.floatingActionButtonShake(getApplicationContext(), fab_newTask, false);


            // clear previous views from linearLayout
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(this);


            // Serial
            TextView tv0 = new TextView(this);
            tv0.setText("Sl.No. ");
            tv0.setPadding(10, 10, 10, 10);
            tv0.setTextColor(0xC0C0C0C0);
            tv0.setTypeface(null, Typeface.BOLD);
            tv0.setGravity(Gravity.CENTER);
            tbrow0.addView(tv0);


            // visited Date and time
            TextView tv1 = new TextView(this);
            tv1.setText(" Visited\nDate & Time ");
            tv1.setPadding(10, 10, 10, 10);
            tv1.setTextColor(0xC0C0C0C0);
            tv1.setTypeface(null, Typeface.BOLD);
            tv1.setGravity(Gravity.CENTER);
            tbrow0.addView(tv1);


            // note
            TextView tv2 = new TextView(this);
            tv2.setText(" Note ");
            tv2.setPadding(10, 10, 10, 10);
            tv2.setTextColor(0xC0C0C0C0);
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setGravity(Gravity.CENTER);
            tbrow0.addView(tv2);


            // following date and time
            TextView tv3 = new TextView(this);
            tv3.setText(" Followup\nDate & Time ");
            tv3.setPadding(10, 10, 10, 10);
            tv3.setTextColor(0xC0C0C0C0);
            tv3.setTypeface(null, Typeface.BOLD);
            tv3.setGravity(Gravity.CENTER);
            tbrow0.addView(tv3);


            // edit column
            TextView tv4 = new TextView(this);
            tv4.setText("         ");
            tv4.setPadding(10, 10, 10, 40);
            tv4.setTextColor(0xC0C0C0C0);
            tv4.setBackgroundColor(getResources().getColor(R.color.colorBackgroundMain)); // to make this column invisible
            tv4.setTypeface(null, Typeface.BOLD);
            tv4.setGravity(Gravity.CENTER);
            tbrow0.addView(tv4);

            tbrow0.setBackgroundColor(0x292c89ff);

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
                i++;
                textView_sn.setTextColor(0xDCDCDCDC);
                textView_sn.setGravity(Gravity.CENTER);

                tableRow.addView(textView_sn);

                // visited Date and time
                TextView textView_name = new TextView(this);
                textView_name.setText(" " + cursor.getString(i) + " \n" + cursor.getString(i + 1) + " ");
                i++;
                i++;
                textView_name.setTextColor(0xDCDCDCDC);
                textView_name.setGravity(Gravity.CENTER);

                tableRow.addView(textView_name);


                // note
                TextView textView_phone = new TextView(this);
                textView_phone.setText(" " + cursor.getString(i) + " ");
                i++;
                textView_phone.setTextColor(0xDCDCDCDC);
                textView_phone.setGravity(Gravity.CENTER);

                tableRow.addView(textView_phone);


                // followup Date and time
                TextView textView_address = new TextView(this);
                textView_address.setText(" " + cursor.getString(i) + " \n" + cursor.getString(i + 1) + " ");
                i++;
                i++;
                textView_address.setTextColor(0xDCDCDCDC);
                textView_address.setGravity(Gravity.CENTER);

                tableRow.addView(textView_address);


                // edit button

                ImageButton buttonEdit = new ImageButton(this);
                buttonEdit.setImageResource(R.drawable.ic_action_edit);
                buttonEdit.setBackgroundColor(Color.TRANSPARENT);
                buttonEdit.setPadding(5, 5, 5, 5);
                buttonEdit.setId(Integer.parseInt(cursor.getString(0))); // string to int to set id
                buttonEdit.setOnClickListener(this);


//                TextView textView_text = new TextView(this);
//                textView_text.setText("   Edit ");
//                i++;
//                i++;
//                textView_text.setClickable(true);
//                textView_text.setTextColor(0x442cFFFF);
//                textView_text.setGravity(Gravity.CENTER);

//                tableRow.addView(textView_text);
                tableRow.addView(buttonEdit);

                // set color background for table row after each row, starts from 2nd row
                if (rowColor) {
                    tableRow.setBackgroundColor(0x292c8900);
                    rowColor = false;
                } else {
                    rowColor = true;
                }

                tableRow.setPadding(0, 10, 0, 10);

                // add the row on the table
                tableLayout.addView(tableRow);

            }
        } else {
            textView_empty_task_message.setVisibility(View.VISIBLE);

            // clear previous views from linearLayout
            tableLayout.removeAllViews(); //if all task deleted then it will remove all previous views
            myAllAnimations.floatingActionButtonShake(getApplicationContext(), fab_newTask, true);
        }


    }


}