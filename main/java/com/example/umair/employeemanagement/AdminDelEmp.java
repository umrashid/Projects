package com.example.umair.employeemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminDelEmp extends AppCompatActivity {
    //Initiating Lists for autocomplete
    List<String> empName = new ArrayList<String>();
    List<String> empID = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_del_emp);
        //Database
        final SQLiteDatabase empDatabase = this.openOrCreateDatabase("test", MODE_PRIVATE, null);

        //Hide all the TextViews
        final TextView empIDLabel = (TextView) findViewById(R.id.empID);
        final TextView empFirstLabel = (TextView) findViewById(R.id.firstName);
        final TextView empSecondLabel = (TextView) findViewById(R.id.lastName);
        final TextView empIDDis = (TextView) findViewById(R.id.disID);
        final TextView empFirstDis = (TextView) findViewById(R.id.disFirstName);
        final TextView empLastDis = (TextView) findViewById(R.id.disLastName);
        final Button delete = (Button) findViewById(R.id.del);
        final Button update = (Button) findViewById(R.id.update);
        final Button view = (Button) findViewById(R.id.view);
            empIDLabel.setVisibility(View.INVISIBLE);
            empFirstLabel.setVisibility(View.INVISIBLE);
            empSecondLabel.setVisibility(View.INVISIBLE);
            empIDDis.setVisibility(View.INVISIBLE);
            empFirstDis.setVisibility(View.INVISIBLE);
            empLastDis.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            update.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);





        //Storing all information in the Arrays
        getName(empDatabase,empName);
        getID(empDatabase, empID);

        //Creatin Spinner Items
        final Spinner option = (Spinner) findViewById(R.id.options);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.option, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        option.setAdapter(dataAdapter);

        //AutoComplete
        final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        final ArrayAdapter<String> name = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, empName);
        final ArrayAdapter<String> ID = new ArrayAdapter<String>(AdminDelEmp.this, android.R.layout.simple_list_item_1, empID);
        autoComplete.setAdapter(name);

        //Change the autocomplete hint text based on the Item Selected
        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(option.getSelectedItem().toString().trim().equals("Employee ID")){
                    autoComplete.setAdapter(ID);
                }else if(option.getSelectedItem().toString().trim().equals("Name")){
                    autoComplete.setAdapter(name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Setting the Click option
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminDelEmp.this,option.getSelectedItem().toString().trim(),Toast.LENGTH_LONG).show();
                if(option.getSelectedItem().toString().trim().equals("Employee ID")){
                    if(invalidID(option, autoComplete, empID)){
                        Toast.makeText(AdminDelEmp.this, "Invalid Employee ID. Please select a valid Employee ID from the Suggestion Box", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(AdminDelEmp.this, "Valid ID", Toast.LENGTH_LONG).show();
                        ArrayList<String> results = getData(autoComplete,empDatabase,"ID");
                        Log.i("ArrayLists", results.toString());
                        //Setting all of them to be visible
                        empIDLabel.setVisibility(View.VISIBLE);
                        empFirstLabel.setVisibility(View.VISIBLE);
                        empSecondLabel.setVisibility(View.VISIBLE);
                        empIDDis.setVisibility(View.VISIBLE);
                        empFirstDis.setVisibility(View.VISIBLE);
                        empLastDis.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        update.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        //Setting the results value to the visible text outputs
                        empIDDis.setText(results.get(0));
                        empFirstDis.setText(results.get(1));
                        empLastDis.setText(results.get(2));

                    };

                }else if(option.getSelectedItem().toString().trim().equals("Name")){
                    if(invalidName(option, autoComplete, empName)){
                        Toast.makeText(AdminDelEmp.this, "Please select a Employee Name from the Suggestion Box", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(AdminDelEmp.this, "Valid Name", Toast.LENGTH_LONG).show();
                        ArrayList<String> results = getData(autoComplete,empDatabase,"Name");
                        Log.i("ArrayLists", results.toString());
                        //Setting all of them to be visible
                        empIDLabel.setVisibility(View.VISIBLE);
                        empFirstLabel.setVisibility(View.VISIBLE);
                        empSecondLabel.setVisibility(View.VISIBLE);
                        empIDDis.setVisibility(View.VISIBLE);
                        empFirstDis.setVisibility(View.VISIBLE);
                        empLastDis.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        update.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        //Setting the results value to the visible text outputs
                        empIDDis.setText(results.get(0));
                        empFirstDis.setText(results.get(1));
                        empLastDis.setText(results.get(2));
                    };
                }
            }
        });

        //Setting the delete Button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int employeeID = Integer.parseInt(empIDDis.getText().toString().trim());
                String deleteQuery = "DELETE FROM employees where empID="+employeeID;
                empDatabase.execSQL(deleteQuery);
                //After Deleting Hiding all the views again
                empIDLabel.setVisibility(View.INVISIBLE);
                empFirstLabel.setVisibility(View.INVISIBLE);
                empSecondLabel.setVisibility(View.INVISIBLE);
                empIDDis.setVisibility(View.INVISIBLE);
                empFirstDis.setVisibility(View.INVISIBLE);
                empLastDis.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                update.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
                Toast.makeText(AdminDelEmp.this, "Deleted Employee ID: " + employeeID, Toast.LENGTH_LONG).show();
                //Restarting Activity to make sure autocomplete loads proeprly
                Intent intent = new Intent(AdminDelEmp.this, AdminDelEmp.class);
                startActivity(intent);
                finish();
            }
        });
        //Setting the update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = empIDDis.getText().toString().trim();
                ArrayList<String> results = getDataFromID(empDatabase, id);
                //Log.i("Results ARRAYLIST", results.toString());
                Intent intent = new Intent(AdminDelEmp.this, Update_View.class);
                intent.putExtra("results", results);
                intent.putExtra("updateOrView", "UPDATE");
                startActivity(intent);
                finish();

            }
        });
        //Setting the view button
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = empIDDis.getText().toString().trim();
                ArrayList<String> results = getDataFromID(empDatabase, id);
                //Log.i("Results ARRAYLIST", results.toString());
                Intent intent = new Intent(AdminDelEmp.this, Update_View.class);
                intent.putExtra("results", results);
                intent.putExtra("updateOrView", "VIEW");
                startActivity(intent);
                //finish();

            }
        });
    }




    /*------------------------------------Functions ------------------------------------------------*/
    public void getID(SQLiteDatabase empDatabase, List EMP_ID){
        Cursor data = empDatabase.rawQuery("SELECT empID FROM employees", null);
        int IDIndex = data.getColumnIndex("empID");
        data.moveToFirst();
        for(int i = 0; i < data.getCount(); i++ ){
            Log.i("empID ", data.getString(IDIndex));
            EMP_ID.add(data.getString(IDIndex));
            data.moveToNext();

        }
    }

    public void getName(SQLiteDatabase empDatabase, List EMP_NAME){
        Cursor data = empDatabase.rawQuery("SELECT empID,firstName,lastName FROM employees", null);
        int IDIndex = data.getColumnIndex("empID");
        int firstIndex = data.getColumnIndex("firstName");
        int lastIndex = data.getColumnIndex("lastName");
        data.moveToFirst();
        for(int i = 0; i < data.getCount(); i++ ){
            Log.i("empID ", data.getString(IDIndex));
            Log.i("firstName ", data.getString(firstIndex));
            Log.i("lastName ", data.getString(lastIndex));
            EMP_NAME.add(data.getString(IDIndex) + ": " + data.getString(firstIndex) + " " + data.getString(lastIndex));
            data.moveToNext();

        }
    }

    public boolean invalidID(Spinner option, AutoCompleteTextView autoComplete, List empID){
            boolean invalid = false;
            //Checking if its empty
            if(autoComplete.getText().toString().trim().length() >= 1) {
                //Checking if its an Integer
                try {
                    int employeeID = Integer.parseInt(autoComplete.getText().toString().trim());
                } catch (Exception e){
                    invalid = true;
                    return invalid;
                }
                //Checking if its equal to 4 digits
                if (invalid == false && autoComplete.getText().toString().trim().length() == 4) {
                    invalid = false;
                } else {
                    invalid = true;
                    return invalid;
                }
                //Checking its part of the autocomplete List
                for (int i = 0; i < empID.size(); i++) {
                    if (empID.get(i).equals(autoComplete.getText().toString().trim())) {
                        invalid = false;
                        break;
                    } else {
                        invalid = true;
                    }
                }
                return invalid;

            }else{
                invalid = true;
                return invalid;
            }
    }

    public boolean invalidName(Spinner option, AutoCompleteTextView autoComplete, List empName){
        boolean invalid = false;
        //Checking if its empty
        if(autoComplete.getText().toString().trim().length() >= 1) {
            //Checking its part of the autocomplete List
            for (int i = 0; i < empName.size(); i++) {
                if (empName.get(i).equals(autoComplete.getText().toString().trim())) {
                    invalid = false;
                    break;
                } else {
                    invalid = true;
                }
            }
            return invalid;

        }else{
            invalid = true;
            return invalid;
        }
    }

    public ArrayList<String> getData(AutoCompleteTextView autoComplete, SQLiteDatabase empDatabase, String location){
         int ID = 0;
         ArrayList<String> results = new ArrayList<String>();
        try {
            if(location.equals("ID")){
                ID = Integer.parseInt(autoComplete.getText().toString().trim());
                Log.i("ID", String.valueOf(ID));
            }else if(location.equals("Name")) {
                ID = Integer.parseInt(autoComplete.getText().toString().trim().substring(0, 4));
                Log.i("ID", String.valueOf(ID));
            }
            Cursor data = empDatabase.rawQuery("SELECT * FROM employees where empID =" +  ID, null);
            int empIDIndex = data.getColumnIndex("empID");
            int firstNameIndex = data.getColumnIndex("firstName");
            int lastNameIndex = data.getColumnIndex("lastName");
            int emailIndex = data.getColumnIndex("email");
            int wageIndex = data.getColumnIndex("wage");
            int imageDestIndex = data.getColumnIndex("imageDest");
            int streetNumberIndex = data.getColumnIndex("streetNumber");
            int streetNameIndex = data.getColumnIndex("streetName");
            int cityIndex = data.getColumnIndex("city");
            int provinceIndex = data.getColumnIndex("province");
            int passwordIndex = data.getColumnIndex("password");
            int count = 1;
            Log.i("column count", String.valueOf(data.getColumnCount()));
            data.moveToFirst();
            Log.i("row count", String.valueOf(data.getCount()));
            for(int i = 0; i < data.getCount(); i++ ){
                //Log.i("data " + count, data.getString(empIDIndex) + " " + data.getString(firstNameIndex) + " " + data.getString(lastNameIndex) + " " + data.getString(emailIndex) + " " + data.getString(wageIndex) );
                results.add(data.getString(empIDIndex)); //0
                results.add(data.getString(firstNameIndex)); //1
                results.add(data.getString(lastNameIndex)); //2
                results.add(data.getString(emailIndex)); //3
                results.add(data.getString(streetNumberIndex)); //4
                results.add(data.getString(streetNameIndex)); //5
                results.add(data.getString(cityIndex)); //6
                results.add(data.getString(provinceIndex)); //7
                results.add(data.getString(passwordIndex)); //8
                data.moveToNext();
                count++;
            }
        }catch( Exception e){
            Log.e("getDataFromName", "Failed");
        }
        return results;
    }

    public ArrayList<String> getDataFromID(SQLiteDatabase empDatabase, String empID){
        int ID = Integer.parseInt(empID);
        ArrayList<String> results = new ArrayList<String>();
        try {

            Cursor data = empDatabase.rawQuery("SELECT * FROM employees where empID =" +  ID, null);
            int empIDIndex = data.getColumnIndex("empID");
            int firstNameIndex = data.getColumnIndex("firstName");
            int lastNameIndex = data.getColumnIndex("lastName");
            int emailIndex = data.getColumnIndex("email");
            int wageIndex = data.getColumnIndex("wage");
            int imageDestIndex = data.getColumnIndex("imageDest");
            int streetNumberIndex = data.getColumnIndex("streetNumber");
            int streetNameIndex = data.getColumnIndex("streetName");
            int cityIndex = data.getColumnIndex("city");
            int provinceIndex = data.getColumnIndex("province");
            int passwordIndex = data.getColumnIndex("password");
            int phoneNumber = data.getColumnIndex("phoneNumber");
            int count = 1;
            Log.i("column count", String.valueOf(data.getColumnCount()));
            data.moveToFirst();
            Log.i("row count", String.valueOf(data.getCount()));
            for(int i = 0; i < data.getCount(); i++ ){
                //Log.i("data " + count, data.getString(empIDIndex) + " " + data.getString(firstNameIndex) + " " + data.getString(lastNameIndex) + " " + data.getString(emailIndex) + " " + data.getString(wageIndex) );
                results.add(data.getString(empIDIndex)); //0
                results.add(data.getString(firstNameIndex)); //1
                results.add(data.getString(lastNameIndex)); //2
                results.add(data.getString(emailIndex)); //3
                results.add(data.getString(wageIndex)); //4
                results.add(data.getString(streetNumberIndex)); //5
                results.add(data.getString(streetNameIndex)); //6
                results.add(data.getString(cityIndex)); //7
                results.add(data.getString(provinceIndex)); //8
                results.add(data.getString(passwordIndex)); //9
                results.add(data.getString(phoneNumber)); //10
                data.moveToNext();
                count++;
            }
        }catch( Exception e){
            Log.e("getDataFromName", "Failed");
        }
        return results;
    }



}
