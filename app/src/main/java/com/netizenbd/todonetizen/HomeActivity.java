package com.netizenbd.todonetizen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    MySessionManager sessionManager;


    Spinner spinner_instituteName;

    Button button_showTask,
    //            button_newTask,
    button_instDetails;

    FloatingActionButton fab_newInstitution,
            fab_logout;

    TextView textView_home_title,
            textView_followupListTitle;

    ScrollView scrollView_home_main_button,
            scrollView_table;

    MyDbHelper myDbHelper;

    MyAllAnimations myAllAnimations;

    public final static String HOME_TO_VIEWTASKLIST_KEY = "spinnerItem";
    public final static String STOP_RESTART_ANIMATION = "stopAnimation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fab_newInstitution = (FloatingActionButton) findViewById(R.id.fab_newInstitution);
//        fab_logout = (FloatingActionButton) findViewById(R.id.fab_logout);
        fab_newInstitution.setOnClickListener(this);
//        fab_logout.setOnClickListener(this);


        myDbHelper = new MyDbHelper(getApplicationContext());


        /**
         * session check
         */
        sessionManager = new MySessionManager(getApplicationContext());
//        sessionManager.

        spinner_instituteName = (Spinner) findViewById(R.id.spinner_instituteName);

        button_showTask = (Button) findViewById(R.id.button_showTask);
//        button_newTask = (Button) findViewById(R.id.button_newTask);
        button_instDetails = (Button) findViewById(R.id.button_instDetails);
        button_showTask.setOnClickListener(this);
//        button_newTask.setOnClickListener(this);
        button_instDetails.setOnClickListener(this);

        textView_home_title = (TextView) findViewById(R.id.textView_home_title);
        textView_followupListTitle = (TextView) findViewById(R.id.textView_followupListTitle);


        scrollView_home_main_button = (ScrollView) findViewById(R.id.scrollView_home_main_button);
        scrollView_table = (ScrollView) findViewById(R.id.scrollView_table);


//        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
//                R.array.emptyArray, android.R.layout.simple_spinner_item);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner_instituteName.setAdapter(dataAdapter);
//


        getFollowUpTimes();

        getAllInstName();


        // animations
        myAllAnimations = new MyAllAnimations();


        /**
         * passed intent extra from onRestart from this activity
         */
        if (getIntent().getStringExtra(STOP_RESTART_ANIMATION) == null) {
            // animation for appearance at first time open the activity
//            myAllAnimations.floatingActionButtonsAppearance_middleToTop(this, fab_logout);
//            myAllAnimations.floatingActionButtonsAppearance_middleToTop(this, fab_logout, false);
            myAllAnimations.floatingActionButtonsAppearance_middleToBottom(this, fab_newInstitution, textView_home_title);

        } else {
            // animation to highlight new institution
            if (textView_home_title.getVisibility() == View.VISIBLE) {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.animation_floationg_button_shake);
                fab_newInstitution.startAnimation(shake);
            }

        }










    } // end of onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_showTask:
                Intent i = new Intent(getApplicationContext(), ViewTaskList.class);
                i.putExtra(HOME_TO_VIEWTASKLIST_KEY, spinner_instituteName.getSelectedItem().toString());
                startActivity(i);
                break;

//            case R.id.button_newTask:
//                Intent in = new Intent(getApplicationContext(), AddTask.class);
//                in.putExtra(HOME_TO_VIEWTASKLIST_KEY, spinner_instituteName.getSelectedItem().toString());
//                startActivity(in);
//                break;

            case R.id.button_instDetails:

                Intent inte = new Intent(getApplicationContext(), ViewInstitutionDetails.class);
                inte.putExtra(HOME_TO_VIEWTASKLIST_KEY, spinner_instituteName.getSelectedItem().toString());
                startActivity(inte);

                break;

            case R.id.fab_newInstitution:

                startActivity(new Intent(this, NewInstitution.class));

                break;
//
//            case R.id.fab_logout:
//
//                sessionManager.logoutUser();
//                finish();
//
//                break;

        }
    }

    /**
     * Function to load the spinner data from SQLite database if no db data load empty array from xml
     */
    private void getAllInstName() {

        // Spinner click listener
        spinner_instituteName.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> instData = myDbHelper.getAllInstName();

        // Creating adapter for spinner
        if (instData.isEmpty()) { // I did if empty an empty array from string will load; i faced error while empty value from db

            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                    R.array.emptyArray, android.R.layout.simple_spinner_item);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_instituteName.setAdapter(dataAdapter);

            // Change visibility of title message part
            textView_home_title.setVisibility(View.VISIBLE);
            scrollView_home_main_button.setVisibility(View.GONE);


        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, instData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_instituteName.setAdapter(dataAdapter);

            // Change visibility of title message part
            textView_home_title.setVisibility(View.GONE);
            scrollView_home_main_button.setVisibility(View.VISIBLE);


        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onRestart() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra(STOP_RESTART_ANIMATION, "stop_animate_again");
        startActivity(intent);

        super.onRestart();
    }

    // Go to previous page when pressing back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Want to exit?");

                    builder.setNegativeButton("Logout & Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sessionManager.logoutUser();
                            finish();
                        }
                    });
                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // end activity
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.show();
                    TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
                    messageText.setGravity(Gravity.CENTER);
                    dialog.show();
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void getFollowUpTimes() {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String date = df.format(Calendar.getInstance().getTime());
        // get today's tasks from today's date
        myDbHelper = new MyDbHelper(getApplicationContext());
        Cursor cursorTask = myDbHelper.getAllFollowupDateTime(date);

        if (cursorTask.getCount() > 0) {

            // set title first
            textView_followupListTitle.setText("Today's Schedule\n(" + date.toString() + ")");

            // using table layout
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout_todaysFollowupList);

            // clear previous views from linearLayout
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(this);

//        // Serial
//        TextView tv0 = new TextView(this);
//        tv0.setText("Sl.No. ");
//        tv0.setPadding(10, 10, 10, 10);
//        tv0.setTextColor(Color.WHITE);
//        tv0.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv0);


//            // INST NAME
//            TextView tv1 = new TextView(this);
//            tv1.setText(" Inst. Name ");
//            tv1.setPadding(10, 10, 10, 10);
//            tv1.setTextColor(Color.WHITE);
//            tv1.setGravity(Gravity.CENTER);
//            tbrow0.addView(tv1);


//            // note
//            TextView tv2 = new TextView(this);
//            tv2.setText(" Note ");
//            tv2.setPadding(10, 10, 10, 10);
//            tv2.setTextColor(Color.WHITE);
//            tv2.setGravity(Gravity.CENTER);
//            tbrow0.addView(tv2);


//            // following date and time
//            TextView tv3 = new TextView(this);
//            tv3.setText(" Followup Time ");
//            tv3.setPadding(10, 10, 10, 10);
//            tv3.setTextColor(Color.WHITE);
//            tv3.setGravity(Gravity.CENTER);
//            tbrow0.addView(tv3);

//            tbrow0.setBackgroundColor(Color.GRAY);
//            tableLayout.addView(tbrow0);

            boolean rowColor = false;
            int j = 1;
            while (cursorTask.moveToNext()) {
                int i = 0;

                TableRow tableRow = new TableRow(this);
//            // Serial no.
//            TextView textView_sn = new TextView(this);

//            textView_sn.setText("" + j); // serial no is not related with database, it's just serial, here j is only for serial no
//            j++;
////            i++;
//            textView_sn.setTextColor(Color.BLACK);
//            textView_sn.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_sn);

                // Inst. Name (get name from inst table)
                Cursor c = myDbHelper.getInstNameForFollowupDateTime(cursorTask.getString(i)); // passed id as argument
                String sName = "";
                while (c.moveToNext()) {
                    sName = c.getString(0);
                }
                TextView textView_name = new TextView(this);
                textView_name.setText(" " + sName + " ");
                i++;
                i++;
                textView_name.setTextColor(Color.BLACK);
                textView_name.setGravity(Gravity.CENTER);

                tableRow.addView(textView_name);

                // note
                TextView textView_phone = new TextView(this);
                textView_phone.setText(" " + cursorTask.getString(i + 1) + " ");
                i++;
                textView_phone.setTextColor(Color.BLACK);
                textView_phone.setGravity(Gravity.CENTER);

                tableRow.addView(textView_phone);

                // followup time
                TextView textView_address = new TextView(this);
                textView_address.setText(" " + cursorTask.getString(i + 2) + " ");
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

        } else {
            textView_followupListTitle.setText("Today is: " + date.toString() + "\nYou have no schedule today!");

            // to make text (textView_followupListTitle) center need to scrollview GONE
            scrollView_table.setVisibility(View.GONE);
        }


    }


}
