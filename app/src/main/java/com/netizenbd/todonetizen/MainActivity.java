package com.netizenbd.todonetizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Session Manager Class
    MySessionManager session;

    EditText editText_userName,
            editText_password;

    Button button_logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Session Manager
        session = new MySessionManager(getApplicationContext());

        // if previous session exists - redirect to home page(only for this page, other page will call only session.checkLogin();)
        if (session.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        editText_userName = (EditText) findViewById(R.id.editText_userName);
        editText_password = (EditText) findViewById(R.id.editText_password);

        button_logIn = (Button) findViewById(R.id.button_logIn);
        button_logIn.setOnClickListener(this);




    } // end of onCreate

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_logIn:

                // Get username, password from EditText
                String username = editText_userName.getText().toString();
                String password = editText_password.getText().toString();

                // Check if username, password is filled
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    // For testing purpose username, password is checked with sample data
                    // username = 1
                    // password = 1
                    if (username.equals("1") && password.equals("1")) {

                        // Creating user login session
                        // For testing i am storing name, password as follow
                        // Use user real data
                        session.createLoginSession(username, password);

                        // Staring HomeActivity
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                        finish(); // finish login activity (here MainActivity)

                    } else {

                        // username / password doesn't match
                        Toast.makeText(MainActivity.this, "Username or Password don't match.", Toast.LENGTH_LONG).show();

                    }

                } else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    Toast.makeText(MainActivity.this, "Login failed. Please enter username and password", Toast.LENGTH_LONG).show();

                }

                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
