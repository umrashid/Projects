package com.example.umair.employeemanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AdminAddEmp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_emp);

        //Opening Database Connection
        final SQLiteDatabase empDatabase = this.openOrCreateDatabase("test", MODE_PRIVATE, null);


        //Setting all variables
        final EditText empID = (EditText) findViewById(R.id.empID);
        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText wage = (EditText) findViewById(R.id.wage);
        final EditText streetNumber = (EditText) findViewById(R.id.streetNumber);
        final EditText streetName = (EditText) findViewById(R.id.streetName);
        final EditText city = (EditText) findViewById(R.id.city);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        final Button submit = (Button) findViewById(R.id.submit);




        //Setting The Spinner
        final Spinner province = (Spinner) findViewById(R.id.province);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.province, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(dataAdapter);

        //Log.i("PhoneCheck", returnPhoneNumber("6047112222"));
        //Locking Generated ID
        empID.setText("Generate Employee ID: " + String.valueOf(generateID(empDatabase)));
        empID.setEnabled(false);

        //Animating the Objects to show
        empID.setAlpha(0);
        firstName.setAlpha(0);
        lastName.setAlpha(0);
        email.setAlpha(0);
        wage.setAlpha(0);
        streetNumber.setAlpha(0);
        streetName.setAlpha(0);
        city.setAlpha(0);
        password.setAlpha(0);
        phoneNumber.setAlpha(0);
        submit.setAlpha(0);
        province.setAlpha(0);
        empID.animate().alpha(1).setDuration(2000);
        firstName.animate().alpha(1).setDuration(2000);
        lastName.animate().alpha(1).setDuration(2000);
        email.animate().alpha(1).setDuration(2000);
        wage.animate().alpha(1).setDuration(2000);
        streetNumber.animate().alpha(1).setDuration(2000);
        streetName.animate().alpha(1).setDuration(2000);
        city.animate().alpha(1).setDuration(2000);
        password.animate().alpha(1).setDuration(2000);
        phoneNumber.animate().alpha(1).setDuration(2000);
        submit.animate().alpha(1).setDuration(2000);
        province.animate().alpha(1).setDuration(2000);



        //Click function
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking the data and Validating it
                if(emptyData(firstName.getText().toString().trim(),
                        lastName.getText().toString().trim(),
                        email.getText().toString().trim(),
                        wage.getText().toString().trim(),
                        streetName.getText().toString().trim(), streetNumber.getText().toString().trim(), city.getText().toString().trim())){
                    Toast.makeText(AdminAddEmp.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                else if (!validEmail(email.getText().toString().trim())) {
                    Toast.makeText(AdminAddEmp.this, "Enter valid e-mail!", Toast.LENGTH_LONG).show();
                }else if(!validPhoneNumber(phoneNumber.getText().toString().trim())){
                    Toast.makeText(AdminAddEmp.this,"Phone Number can only be 10-digits!",Toast.LENGTH_LONG).show();
                }else if(!validPassword(password.getText().toString().trim())){
                    Toast.makeText(AdminAddEmp.this,"Password must be at least 8-digits",Toast.LENGTH_LONG).show();
                }else if(!validWage(wage.getText().toString().trim())){
                    Toast.makeText(AdminAddEmp.this,"Wage must be between 10 and 100",Toast.LENGTH_LONG).show();
                }else {

                    Toast.makeText(AdminAddEmp.this, "Employee Information Submitted", Toast.LENGTH_LONG).show();
                    try {
                        //Inserting the values
                        String insert = "INSERT INTO employees(empID, firstName, lastName, email, wage, imageDest, streetName, streetNumber, city, province, phoneNumber, password) VALUES (" + generateID(empDatabase) +
                                ",'" + firstName.getText().toString() +
                                "','" + lastName.getText().toString() +
                                "','" + email.getText().toString() +
                                "'," + Double.parseDouble(wage.getText().toString()) +
                                ",  NULL ,"+
                                "'" + streetName.getText().toString() +
                                "','" + streetNumber.getText().toString() +
                                "','" + city.getText().toString() +
                                "','" + province.getSelectedItem().toString() +
                                "','" + returnPhoneNumber(phoneNumber.getText().toString()) +
                                "','" + password.getText().toString() +
                                "')";
                        empDatabase.execSQL(insert);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("error", "Insert didn't work");
                    }
                    //Clear all the forms and generate a new ID
                    empID.setText("Generate Employee ID: " + String.valueOf(generateID(empDatabase)));
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    wage.setText("");
                    streetName.setText("");
                    streetNumber.setText("");
                    city.setText("");
                    phoneNumber.setText("");
                    password.setText("");

                    //Viewing Data
                    viewData(empDatabase);

                }
            }
        });



    }

    public void viewData(SQLiteDatabase empDatabase){
        Cursor data = empDatabase.rawQuery("SELECT * FROM employees", null);
        int empIDIndex = data.getColumnIndex("empID");
        int firstNameIndex = data.getColumnIndex("firstName");
        int lastNameIndex = data.getColumnIndex("lastName");
        int emailIndex = data.getColumnIndex("email");
        int wageIndex = data.getColumnIndex("wage");
        int imageDestIndex = data.getColumnIndex("imageDest");
        int streetNumberIndex = data.getColumnIndex("streetNumber");
        int streetNameIndex = data.getColumnIndex("streetName");
        int cityIndex = data.getColumnIndex("city");
        int provinceindex = data.getColumnIndex("province");
        int phoneNumberIndex = data.getColumnIndex("phoneNumber");
        int passwordIndex = data.getColumnIndex("password");

        int count = 1;
        Log.i("column count", String.valueOf(data.getColumnCount()));
        data.moveToFirst();
        Log.i("row count", String.valueOf(data.getCount()));
        for(int i = 0; i < data.getCount(); i++ ){
            Log.i("data " + count, data.getString(empIDIndex) + " "
                    + data.getString(firstNameIndex) + " "
                    + data.getString(lastNameIndex) + " "
                    + data.getString(emailIndex) + " "
                    + data.getString(wageIndex) + " "
                    + data.getString(imageDestIndex) + " "
                    + data.getString(streetNumberIndex) + " "
                    + data.getString(streetNameIndex) + " "
                    + data.getString(cityIndex) + " "
                    + data.getString(provinceindex) + " "
                    + data.getString(phoneNumberIndex) + " "
                    + data.getString(passwordIndex) + " "
            );
            Log.i("empID " + count, data.getString(empIDIndex));
            data.moveToNext();
            count++;
        }
    }

    public int generateID(SQLiteDatabase empDatabase){
        String query =  "SELECT max(empID) as empID FROM employees";
        Cursor getID = empDatabase.rawQuery(query, null);
        getID.moveToFirst();
        int idIndex = getID.getColumnIndex("empID");
        int generatedID = Integer.parseInt(getID.getString(idIndex)) + 1;
        return generatedID;
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
