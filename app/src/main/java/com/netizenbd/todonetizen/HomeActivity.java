package com.netizenbd.todonetizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner_instituteName;

    Button button_showTask,
            button_newTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab_newInstitution = (FloatingActionButton) findViewById(R.id.fab_newInstitution);
        fab_newInstitution.setOnClickListener(this);

        spinner_instituteName = (Spinner) findViewById(R.id.spinner_instituteName);

        button_showTask = (Button) findViewById(R.id.button_showTask);
        button_newTask = (Button) findViewById(R.id.button_newTask);
        button_showTask.setOnClickListener(this);
        button_newTask.setOnClickListener(this);


    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_showTask:
                Toast.makeText(HomeActivity.this, "show", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ViewTaskList.class));
                break;

            case R.id.button_newTask:
                Toast.makeText(HomeActivity.this, "new", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AddTask.class));
                break;

            case R.id.fab_newInstitution:
                startActivity(new Intent(this, NewInstitution.class));
                break;

        }
    }


}
