package com.netizenbd.todonetizen;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewInstitutionDetails extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayout_viewPart,
            linearLayout_editPart;

    TextView textView_instName_details,
            textView_instId,
            textView_instName,
            textView_instAddress,
            textView_instAuthorityName,
            textView_instAuthorityDesignation,
            textView_mobileNo,
            textView_studentAmount;

    EditText editText_instId,
            editText_instName,
            editText_instAddress,
            editText_instAuthorityName,
            editText_instAuthorityDesignation,
            editText_mobileNo,
            editText_studentAmount;

    Button button_editInstitute,
            button_cancelInstitute,
            button_updateInstitute,
            button_deleteInstitute;

    MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_institution_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // LinearLayout
        linearLayout_viewPart = (LinearLayout) findViewById(R.id.linearLayout_viewPart);
        linearLayout_editPart = (LinearLayout) findViewById(R.id.linearLayout_editPart);

        // set default visibility of layout
        linearLayout_viewPart.setVisibility(View.VISIBLE);
        linearLayout_editPart.setVisibility(View.GONE);

        // TextView
        textView_instName_details = (TextView) findViewById(R.id.textView_instName_details);
        textView_instId = (TextView) findViewById(R.id.textView_instId);
        textView_instName = (TextView) findViewById(R.id.textView_instName);
        textView_instAddress = (TextView) findViewById(R.id.textView_instAddress);
        textView_instAuthorityName = (TextView) findViewById(R.id.textView_instAuthorityName);
        textView_instAuthorityDesignation = (TextView) findViewById(R.id.textView_instAuthorityDesignation);
        textView_mobileNo = (TextView) findViewById(R.id.textView_mobileNo);
        textView_studentAmount = (TextView) findViewById(R.id.textView_studentAmount);

        // EditText
        editText_instId = (EditText) findViewById(R.id.editText_instId);
        editText_instName = (EditText) findViewById(R.id.editText_instName);
        editText_instAddress = (EditText) findViewById(R.id.editText_instAddress);
        editText_instAuthorityName = (EditText) findViewById(R.id.editText_instAuthorityName);
        editText_instAuthorityDesignation = (EditText) findViewById(R.id.editText_instAuthorityDesignation);
        editText_mobileNo = (EditText) findViewById(R.id.editText_mobileNo);
        editText_studentAmount = (EditText) findViewById(R.id.editText_studentAmount);


        // Button
        button_editInstitute = (Button) findViewById(R.id.button_editInstitute);
        button_cancelInstitute = (Button) findViewById(R.id.button_cancelInstitute);
        button_updateInstitute = (Button) findViewById(R.id.button_updateInstitute);
        button_deleteInstitute = (Button) findViewById(R.id.button_deleteInstitute);

        button_editInstitute.setOnClickListener(this);
        button_cancelInstitute.setOnClickListener(this);
        button_updateInstitute.setOnClickListener(this);
        button_deleteInstitute.setOnClickListener(this);


        // get intent message and set title inst. name // intent from HomeActivity.java
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString(HomeActivity.HOME_TO_VIEWTASKLIST_KEY, "");
        textView_instName_details.setText(s);

        // set text to view
        myDbHelper = new MyDbHelper(this);
        Cursor cursorInstInfo = myDbHelper.getAllInstitution(textView_instName_details.getText().toString());


        // looping through all rows and adding to textView

        while (cursorInstInfo.moveToNext()) {
            int i = 0;
            textView_instId.setText(cursorInstInfo.getString(i));
            i++;
            textView_instName.setText(cursorInstInfo.getString(i));
            i++;
            textView_instAddress.setText(cursorInstInfo.getString(i));
            i++;
            textView_instAuthorityName.setText(cursorInstInfo.getString(i));
            i++;
            textView_instAuthorityDesignation.setText(cursorInstInfo.getString(i));
            i++;
            textView_mobileNo.setText(cursorInstInfo.getString(i));
            i++;
            textView_studentAmount.setText(cursorInstInfo.getString(i));

            break;
        }


    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_editInstitute:

                // set layout visibility
                linearLayout_viewPart.setVisibility(View.GONE);
                linearLayout_editPart.setVisibility(View.VISIBLE);

                // get from textView and set to EditText
                editText_instId.setText(textView_instId.getText());
                editText_instName.setText(textView_instName.getText());
                editText_instAddress.setText(textView_instAddress.getText());
                editText_instAuthorityName.setText(textView_instAuthorityName.getText());
                editText_instAuthorityDesignation.setText(textView_instAuthorityDesignation.getText());
                editText_mobileNo.setText(textView_mobileNo.getText());
                editText_studentAmount.setText(textView_studentAmount.getText());


                break;

            case R.id.button_cancelInstitute:

                linearLayout_viewPart.setVisibility(View.VISIBLE);
                linearLayout_editPart.setVisibility(View.GONE);

                break;

            case R.id.button_updateInstitute:

                String[] isntIdPrevious = {textView_instId.getText().toString()};
                String isntIdUpdate = editText_instId.getText().toString();
                String isntName = editText_instName.getText().toString();
                String isntAddress = editText_instAddress.getText().toString();
                String isntAuthority = editText_instAuthorityName.getText().toString();
                String isntDesig = editText_instAuthorityDesignation.getText().toString();
                String isntMobile = editText_mobileNo.getText().toString();
                String isntStuAmount = editText_studentAmount.getText().toString();

                long update = myDbHelper.updateInstitution(isntIdPrevious, isntIdUpdate, isntName, isntAddress, isntAuthority,
                        isntDesig, isntMobile, isntStuAmount); // passed previous id, db will change indtId from both table

                if (update > 0) {
                    Toast.makeText(ViewInstitutionDetails.this, "Successful update! " + update + " row.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ViewInstitutionDetails.this, "Error, Try another id or name.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.button_deleteInstitute:

                long deletedRow = myDbHelper.deleteInstitute(textView_instId.getText().toString());

                if (deletedRow > 0) {
                    Toast.makeText(ViewInstitutionDetails.this, "Deleted " + deletedRow + " row.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ViewInstitutionDetails.this, "Error.", Toast.LENGTH_LONG).show();
                }

                // finish activity after delete
                finish();

                break;
        }
    }


}
