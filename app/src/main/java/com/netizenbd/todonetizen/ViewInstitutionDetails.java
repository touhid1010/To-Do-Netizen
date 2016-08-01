package com.netizenbd.todonetizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewInstitutionDetails extends AppCompatActivity {

    TextView textView_instName_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_institution_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        textView_instName_details = (TextView) findViewById(R.id.textView_instName_details);


        // get intent message and set title inst. name // intent from HomeActivity.java
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, "");
        textView_instName_details.setText(s);


        // get intent message and set title inst. name // intent from ViewTaskList.java or HomeActivity.java both
        Bundle b = getIntent().getExtras();
        String s2 = b.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, "");
        textView_instName_details.setText(s2);



    } // end of onCreate

}
