package com.example.umair.employeemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        //Connecting to Database
        final SQLiteDatabase empDatabase = this.openOrCreateDatabase("test", MODE_PRIVATE, null);

        /*-------------------------------------------------------------------Creating Database-----------------------------------------------------------------------------*/
        try {
            //Create Employees Table if it doesn't exists
            String createEmpTable = "CREATE TABLE IF NOT EXISTS employees (empID INT(4) NOT NULL PRIMARY KEY, " +
                    "firstName VARCHAR NOT NULL, " +
                    "lastName VARCHAR NOT NULL, " +
                    "email VARCHAR NOT NULL, wage DOUBLE(2,2), " +
                    "imageDest VARCHAR DEFAULT NULL, " +
                    "streetNumber VARCHAR DEFAULT NULL, " +
                    "streetName VARCHAR DEFAULT NULL, " +
                    "city VARCHAR DEFAULT NULL, " +
                    "province VARCHAR DEFAULT NULL, " +
                    "phoneNumber VARCHAR DEFAULT NULL, " +
                    "password VARCHAR NOT NULL )";
            //empDatabase.execSQL("DROP TABLE IF EXISTS employees");
            empDatabase.execSQL(createEmpTable);
            //insertValuesEmployees(empDatabase);
            //Create Admin table of it doesn't Exist
            String createAdminTable = "CREATE TABLE IF NOT EXISTS admin( userID INT(9) NOT NULL PRIMARY KEY, password VARCHAR NOT NULL)";
            empDatabase.execSQL(createAdminTable);
            //insertValuesAdmin(empDatabase);


            // Viewing Data in Logs
            Cursor data = empDatabase.rawQuery("SELECT * FROM employees", null);
            int empIDIndex = data.getColumnIndex("empID");
            int firstNameIndex = data.getColumnIndex("firstName");
            int lastNameIndex = data.getColumnIndex("lastName");
            int emailIndex = data.getColumnIndex("email");
            int wageIndex = data.getColumnIndex("wage");
            int imageDestIndex = data.getColumnIndex("imageDest");
            int count = 1;
            //Log.i("column count", String.valueOf(data.getColumnCount()));
            data.moveToFirst();
            //Log.i("row count", String.valueOf(data.getCount()));
            //Log.i("Row 1 Column 1", data.getString(empIDIndex));
            for(int i = 0; i < data.getCount(); i++ ){
                //Log.i("data " + count, data.getString(empIDIndex) + " " + data.getString(firstNameIndex) + " " + data.getString(lastNameIndex) + " " + data.getString(emailIndex) + " " + data.getString(wageIndex) );
                //Log.i("empID " + count, data.getString(empIDIndex));
                data.moveToNext();
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        /*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


        final CheckBox forgotPassword = (CheckBox) findViewById(R.id.forgotPassword);
        final Button loginAdmin = (Button) findViewById(R.id.loginAdmin);
        final EditText adminUser = (EditText) findViewById(R.id.adminUsername);
        final EditText adminPass = (EditText) findViewById(R.id.adminPassword);

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgotPassword.isChecked()){
                    String username = adminUser.getText().toString().trim();
                    if (username.trim().length() == 9) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"tech@kpu.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Password Reset for User ID: " + username);
                        i.putExtra(Intent.EXTRA_TEXT   , "I have forgotten my password and I am unable to login. Please Reset the Password for me.");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(LoginAdmin.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(LoginAdmin.this, "Please Enter your valid User ID and then Click Forgot Password!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    String username = adminUser.getText().toString().trim();
                    String password = adminPass.getText().toString().trim();
                    if (username.trim().length() == 9) {
                        String data = getAdminPassFromUser(empDatabase, username);
                        if (data.equals("EMPTY")) {
                            Toast.makeText(LoginAdmin.this, "Invalid User Name!", Toast.LENGTH_LONG).show();
                        } else {
                            if (password.equals(data)) {
                                Toast.makeText(LoginAdmin.this, "Password Validated!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginAdmin.this, AdminMain.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginAdmin.this, "Invalid Password!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(LoginAdmin.this, "Invalid User Name!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        //Touch Listener
        loginAdmin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    loginAdmin.animate().alpha(1).scaleX(1.0f).scaleY(1.0f).setDuration(2000);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    loginAdmin.animate().alpha(1).scaleX(1.15f).scaleY(1.15f).setDuration(2000);
                }
                return false;
            }
        });
        //Checbox Listener
        forgotPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(forgotPassword.isChecked()){
                    adminPass.animate().alpha(0).setDuration(1000);
                    forgotPassword.animate().translationY(-175f).setDuration(2000);
                    loginAdmin.animate().translationY(-175f).setDuration(2000);
                    loginAdmin.setText("Forgot Password?");
                }else{
                    adminPass.animate().alpha(1).setDuration(2000);
                    forgotPassword.animate().translationY(0f).setDuration(2000);
                    loginAdmin.animate().translationY(0f).setDuration(2000);
                    loginAdmin.setText("Sign In?");
                }
            }
        });
        animateStart(loginAdmin, adminUser, adminPass);

    }
    public void animateStart(Button loginAdmin, EditText adminUser, EditText adminPass){
        loginAdmin.animate().alpha(1).scaleX(1.15f).scaleY(1.15f).setDuration(2000);
        adminUser.animate().alpha(1).scaleX(1.15f).scaleY(1.15f).setDuration(2000);
        adminPass.animate().alpha(1).scaleX(1.15f).scaleY(1.15f).setDuration(2000);
    }
    public String getAdminPassFromUser(SQLiteDatabase empDatabase, String userName){
        Cursor data = empDatabase.rawQuery("SELECT password FROM admin where userID=" + userName, null);
        int passwordIndex = data.getColumnIndex("password");
        data.moveToFirst();
        if(data.getCount() > 0){
            return data.getString(passwordIndex);
        }else{
            return "EMPTY";
        }

    }
    public void insertValuesEmployees(SQLiteDatabase empDatabase){
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1001, 'Shirlene', 'Letchmore', 'Shirlene.Letchmore@email.kpu.ca', 24.02,  null, '8656', 'Becker', 'Coquitlam', 'BC', '604-982-8406', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1002, 'Ferdinande', 'Avramchik', 'Ferdinande.Avramchik@email.kpu.ca', 21.22,  null, '1816', 'Golf View', 'Surrey', 'BC', '604-645-3013', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1003, 'Agnola', 'Passman', 'Agnola.Passman@email.kpu.ca', 29.20,  null, '44266', 'Badeau', 'Vancouver', 'BC', '604-195-0007', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1004, 'Myrna', 'Helks', 'Myrna.Helks@email.kpu.ca', 34.10,  null, '7', 'Utah', 'Richmond', 'BC', '604-631-0343', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1005, 'Ermentrude', 'Pfeffer', 'Ermentrude.Pfeffer@email.kpu.ca', 24.22, null, '12858', 'Bay', 'Richmond', 'BC', '604-334-0053', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1006, 'Hermie', 'Gambles', 'Hermie.Gambles@email.kpu.ca', 30.50,  null, '98', 'Ohio', 'White Rock', 'BC', '604-447-8761', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1007, 'Jean', 'Walkden', 'Jean.Walkden@email.kpu.ca', 20.22,  null, '32250', 'Mosinee', 'Burnaby', 'BC', '604-591-2949', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1008, 'Vanny', 'Maypowder', 'Vanny.Maypowder@email.kpu.ca', 14.22,  null, '79140', 'Center', 'Burnaby', 'BC', '604-020-7560', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1009, 'Charlot', 'Greet', 'Charlot.Greet@email.kpu.ca', 26.22,  null, '966', 'Lyons', 'Langley', 'BC', '604-987-9089', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1010, 'Davis', 'Dombrell', 'Davis.Dombrell@email.kpu.ca', 23.22,  null, '5023', 'Buena Vista', 'Langley', 'BC', '604-182-6256', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1011, 'Olly', 'Halfacre', 'Olly.Halfacre@email.kpu.ca', 24.50,  null, '0227', 'Esker', 'Coquitlam', 'BC', '604-863-7668', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1012, 'Ferguson', 'Scarth', 'Ferguson.Scarth@email.kpu.ca', 19.35,  null, '5', 'Dixon', 'White Rock', 'BC', '604-682-6169', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1013, 'Oralie', 'Esley', 'Oralie.Esley@email.kpu.ca', 40.00,  null, '8', 'Drewry', 'Langley', 'BC', '604-967-5263', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1014, 'Domenico', 'Caskey', 'Domenico.Caskey@email.kpu.ca', 32.22,  null, '2', 'Cody', 'Burnaby', 'BC', '604-328-6366', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1015, 'Baudoin', 'Sangra', 'Baudoin.Sangra@email.kpu.ca', 35.10,  null, '87450', 'Dunning', 'Langley', 'BC', '604-694-4804', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1016, 'Keeley', 'Goracci', 'Keeley.Goracci@email.kpu.ca', 36.47,  null, '14534', 'Oak', 'Surrey', 'BC', '604-152-1505', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1017, 'Denyse', 'Pinard', 'Denyse.Pinard@email.kpu.ca', 21.00,  null, '58140', 'Basil', 'Burnaby', 'BC', '604-305-5403', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1018, 'Georgia', 'Rigts', 'Georgia.Rigts@email.kpu.ca', 29.00,  null, '44435', 'Surrey', 'Burnaby', 'BC', '604-502-5099', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1019, 'Bernadina', 'Prisley', 'Bernadina.Prisley@email.kpu.ca', 24.00,  null, '99052', 'Packers', 'Burnaby', 'BC', '604-597-3206', 'default123')");
        empDatabase.execSQL("insert into employees (empID, firstName, lastName, email, wage, imageDest, streetNumber, streetName, city, province, phoneNumber, password) values (1020, 'Alie', 'Shirt', 'Alie.Shirt@email.kpu.ca', 16.00,  null, '6098', 'Manley', 'Burnaby', 'BC', '604-537-4932', 'default123')");
    }

    public void insertValuesAdmin(SQLiteDatabase empDatabase) {
        empDatabase.execSQL("insert into admin (userID,password) values (100312938, 'default123')");

    }


}
