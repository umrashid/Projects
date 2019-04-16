package com.example.umair.employeemanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Update_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__view);
        //Setting Database Connection
        final SQLiteDatabase empDatabase = this.openOrCreateDatabase("test", MODE_PRIVATE, null);


        //Setting Spinner
        final Spinner province = (Spinner) findViewById(R.id.disProvince);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.province, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(dataAdapter);
        province.setSelection(1);

        //Setting all Edit Text and Button
        final EditText firstName = (EditText) findViewById(R.id.disFirstName);
        final EditText lastName = (EditText) findViewById(R.id.disLastName);
        final EditText email = (EditText) findViewById(R.id.disEmail);
        final EditText wage = (EditText) findViewById(R.id.disWage);
        final EditText streetNumber = (EditText) findViewById(R.id.disStreetNumber);
        final EditText streetName = (EditText) findViewById(R.id.disStreetName);
        final EditText city = (EditText) findViewById(R.id.disCity);
        final EditText phoneNumber = (EditText) findViewById(R.id.disPhoneNumber);
        final EditText password = (EditText) findViewById(R.id.disPassword);
        final Button update = (Button) findViewById(R.id.update);

        //Setting button to be invisible
        update.setVisibility(View.INVISIBLE);


        //Getting the result from an activity and placing it in the text views
        final ArrayList<String> results = (ArrayList<String>) getIntent().getSerializableExtra("results");
        Log.i("Results Array", results.toString());
        firstName.setText(results.get(1));
        lastName.setText(results.get(2));
        email.setText(results.get(3));
        wage.setText(results.get(4));
        streetNumber.setText(results.get(5));
        streetName.setText(results.get(6));
        city.setText(results.get(7));
        phoneNumber.setText(results.get(10).substring(0,3) + results.get(10).substring(4,7) + results.get(10).substring(8,12));
        password.setText(results.get(9));

        String whichbutton = getIntent().getExtras().getString("updateOrView");
        Log.i("updateorView", getIntent().getExtras().getString("updateOrView"));
        if(whichbutton.equals("UPDATE")){
            update.setVisibility(View.VISIBLE);
        }else{
            //Set all disabled for view only
            firstName.setEnabled(false);
            lastName.setEnabled(false);
            email.setEnabled(false);
            wage.setEnabled(false);
            streetName.setEnabled(false);
            streetNumber.setEnabled(false);
            city.setEnabled(false);
            phoneNumber.setEnabled(false);
            password.setEnabled(false);
            province.setEnabled(false);

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emptyData(firstName.getText().toString().trim(),
                        lastName.getText().toString().trim(),
                        email.getText().toString().trim(),
                        wage.getText().toString().trim(),
                        streetName.getText().toString().trim(), streetNumber.getText().toString().trim(), city.getText().toString().trim())){
                    Toast.makeText(Update_View.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                else if (!validEmail(email.getText().toString().trim())) {
                    Toast.makeText(Update_View.this, "Enter valid e-mail!", Toast.LENGTH_LONG).show();
                }else if(!validPhoneNumber(phoneNumber.getText().toString().trim())){
                    Toast.makeText(Update_View.this,"Phone Number can only be 10-digits!",Toast.LENGTH_LONG).show();
                }else if(!validPassword(password.getText().toString().trim())){
                    Toast.makeText(Update_View.this,"Password must be at least 8-digits",Toast.LENGTH_LONG).show();
                }else if(!validWage(wage.getText().toString().trim())){
                    Toast.makeText(Update_View.this,"Wage must be between 10 and 100",Toast.LENGTH_LONG).show();
                }else {

                    Toast.makeText(Update_View.this, "Employee Information Updated", Toast.LENGTH_LONG).show();
                    try {

                        //Updating
                        String update = "UPDATE employees " +
                                "SET firstName =" + "'" + firstName.getText().toString().trim() + "'" + "," +
                                "lastName =" + "'" + lastName.getText().toString().trim() + "'" + "," +
                                "email =" + "'" + email.getText().toString().trim() + "'" + "," +
                                "wage =" + wage.getText().toString().trim() + "," +
                                "streetName =" + "'" + streetName.getText().toString().trim() + "'" + "," +
                                "streetNumber =" + "'" + streetNumber.getText().toString().trim() + "'" + "," +
                                "city =" + "'" + city.getText().toString().trim() + "'" + "," +
                                "phoneNumber =" + "'" + returnPhoneNumber(phoneNumber.getText().toString().trim()) + "'" + "," +
                                "password =" + "'" + password.getText().toString().trim() + "'" + "," +
                                "province =" + "'" + province.getSelectedItem().toString().trim() + "'" + " " +
                                "WHERE empID = "+ results.get(0) + ";";
                        empDatabase.execSQL(update);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("error", "Update didn't work");
                    }
                    Intent intent = new Intent(Update_View.this, AdminDelEmp.class);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }
    public boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public String returnPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            phoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6,10);
            return phoneNumber;
        }else{
            return phoneNumber;

        }
    }
    public boolean validWage(String Wage){
        if(Double.parseDouble(Wage) > 100 || Double.parseDouble(Wage) < 10){
            return false;
        }else{
            return true;
        }

    }
    public boolean validPhoneNumber(String phoneNumber){
        if(phoneNumber.length() == 10){
            return true;
        }else{
            return false;
        }
    }

    public boolean validPassword(String password){
        if(password.length() >= 8){
            return true;
        }else{
            return false;
        }
    }

    public boolean emptyData(String firstname, String lastName, String email, String wage, String streetName, String streetNumber, String city ){
        if( firstname.length() < 1 || lastName.length() < 1 || email.length() < 1 || wage.length() < 1 || streetName.length() < 1 || streetNumber.length() < 1 || city.length() < 1){
            return true;
        }else{
            return false;
        }
    }
}
