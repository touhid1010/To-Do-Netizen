package com.netizenbd.todonetizen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    MySessionManager sessionManager;

    Spinner spinner_instituteName;

    Button button_showTask,
            button_newTask;

    TextView textView_home_title;

    ScrollView scrollView_home_main_button;

    MyDbHelper myDbHelper;

    public final static String HOME_TO_VIEWTASKLIST_KEY = "spinnerItem";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab_newInstitution = (FloatingActionButton) findViewById(R.id.fab_newInstitution);
        FloatingActionButton fab_logout = (FloatingActionButton) findViewById(R.id.fab_logout);
        fab_newInstitution.setOnClickListener(this);
        fab_logout.setOnClickListener(this);


        myDbHelper = new MyDbHelper(getApplicationContext());


        /**
         * session check
         */
        sessionManager = new MySessionManager(getApplicationContext());
//        sessionManager.

        spinner_instituteName = (Spinner) findViewById(R.id.spinner_instituteName);

        button_showTask = (Button) findViewById(R.id.button_showTask);
        button_newTask = (Button) findViewById(R.id.button_newTask);
        button_showTask.setOnClickListener(this);
        button_newTask.setOnClickListener(this);

        textView_home_title = (TextView) findViewById(R.id.textView_home_title);

        scrollView_home_main_button = (ScrollView) findViewById(R.id.scrollView_home_main_button);


//        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
//                R.array.emptyArray, android.R.layout.simple_spinner_item);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner_instituteName.setAdapter(dataAdapter);
//


        getAllInstName();



    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_showTask:
                Intent i = new Intent(getApplicationContext(), ViewTaskList.class);
                i.putExtra(HOME_TO_VIEWTASKLIST_KEY, spinner_instituteName.getSelectedItem().toString());
                startActivity(i);
                break;

            case R.id.button_newTask:
                Intent in = new Intent(getApplicationContext(), AddTask.class);
                in.putExtra(HOME_TO_VIEWTASKLIST_KEY, spinner_instituteName.getSelectedItem().toString());
                startActivity(in);
                break;

            case R.id.fab_newInstitution:
                startActivity(new Intent(this, NewInstitution.class));
                break;

            case R.id.fab_logout:
                sessionManager.logoutUser();
                finish();
                break;

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
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        super.onRestart();
    }
}
