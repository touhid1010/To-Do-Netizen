package com.netizenbd.todonetizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewTaskList extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab_newTask = (FloatingActionButton) findViewById(R.id.fab_newTask);
        fab_newTask.setOnClickListener(this);


    } // end of onCreate

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_newTask:
                startActivity(new Intent(this, AddTask.class));
                break;
        }

    }
}