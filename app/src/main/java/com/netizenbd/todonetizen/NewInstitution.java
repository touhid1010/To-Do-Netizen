package com.netizenbd.todonetizen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewInstitution extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper myDbHelper;

    EditText editText_instId,
            editText_instName,
            editText_instAddress,
            editText_instAuthorityName,
            editText_instAuthorityDesignation,
            editText_mobileNo,
            editText_studentAmount;

    Button button_saveInstitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_institution);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        myDbHelper = new MyDbHelper(this);

        editText_instId = (EditText) findViewById(R.id.editText_instId);
        editText_instName = (EditText) findViewById(R.id.editText_instName);
        editText_instAddress = (EditText) findViewById(R.id.editText_instAddress);
        editText_instAuthorityName = (EditText) findViewById(R.id.editText_instAuthorityName);
        editText_instAuthorityDesignation = (EditText) findViewById(R.id.editText_instAuthorityDesignation);
        editText_mobileNo = (EditText) findViewById(R.id.editText_mobileNo);
        editText_studentAmount = (EditText) findViewById(R.id.editText_studentAmount);

        button_saveInstitute = (Button) findViewById(R.id.button_saveInstitute);
        button_saveInstitute.setOnClickListener(this);


    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveInstitute:

                String instId = editText_instId.getText().toString();
                String name = editText_instName.getText().toString();
                String address = editText_instAddress.getText().toString();
                String authoName = editText_instAuthorityName.getText().toString();
                String desig = editText_instAuthorityDesignation.getText().toString();
                String mobile = editText_mobileNo.getText().toString();
                String amount = editText_studentAmount.getText().toString();

                if (instId.isEmpty() || name.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(NewInstitution.this, "ID, Name, Address and Mobile should not empty.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean b = myDbHelper.saveNewInstitutionData(instId, name, address, authoName, desig, mobile, amount);
                    if (b) {
                        Toast.makeText(NewInstitution.this, "Success!", Toast.LENGTH_SHORT).show();

                        // clear text from edit text
                        editText_instId.getText().clear();
                        editText_instName.getText().clear();
                        editText_instAddress.getText().clear();
                        editText_instAuthorityName.getText().clear();
                        editText_instAuthorityDesignation.getText().clear();
                        editText_mobileNo.getText().clear();
                        editText_studentAmount.getText().clear();

                    } else {
                        Toast.makeText(NewInstitution.this, "Data not inserted. ID may exists!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }


}
