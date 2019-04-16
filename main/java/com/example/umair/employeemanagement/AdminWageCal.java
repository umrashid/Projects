package com.example.umair.employeemanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdminWageCal extends AppCompatActivity {
    List<String> empID = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_wage_cal);
        //Setting Database
        final SQLiteDatabase empDatabase = this.openOrCreateDatabase("test", MODE_PRIVATE, null);
        //Setup Autocomeplete
        getID(empDatabase,empID);
        final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.empID);
        final ArrayAdapter<String> name = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, empID);
        autoComplete.setAdapter(name);
        //Setting all Texts
        final EditText hours = (EditText) findViewById(R.id.hours);
        final TextView disName = (TextView) findViewById(R.id.disName);
        final TextView disWage = (TextView) findViewById(R.id.disWage);
        final TextView disGrossPay = (TextView) findViewById(R.id.disGrossPay);
        final TextView disTaxes = (TextView) findViewById(R.id.disTaxes);
        final TextView disEI = (TextView) findViewById(R.id.disEI);
        final TextView disNetPay = (TextView) findViewById(R.id.disNetPay);
        final TextView nameEmp = (TextView) findViewById(R.id.name);
        final TextView wagePerHour = (TextView) findViewById(R.id.wagePerHour);
        final TextView grossPayAmt = (TextView) findViewById(R.id.grossPay);
        final TextView taxesAmt = (TextView) findViewById(R.id.taxes);
        final TextView eiAmt = (TextView) findViewById(R.id.ei);
        final TextView netPayAmt = (TextView) findViewById(R.id.netPay);


        //Hiding Fields


        //Setting up the button to calculate
        final Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double hour = Double.parseDouble(hours.getText().toString().trim());
                String id = autoComplete.getText().toString().trim();

                if(!validateHours(hours.getText().toString().trim())){
                    Toast.makeText(AdminWageCal.this, "Hours must be between 30 and 50", Toast.LENGTH_LONG).show();
                }else if(!validateID(autoComplete.getText().toString().trim(), empID)){
                    Toast.makeText(AdminWageCal.this, "Invalid ID!", Toast.LENGTH_LONG).show();
                }else{
                    ArrayList<String> results = getDataFromID(empDatabase,autoComplete.getText().toString().trim());
                    double wage = Double.parseDouble(results.get(4));
                    double grossPay = wage*hour;
                    grossPay = Math.round(grossPay*100.00)/100.00;
                    double taxes = grossPay*.20;
                    taxes = Math.round(taxes*100.00)/100.00;
                    double ei = Math.round(grossPay*0.05*100.0)/100.0;
                    double netPay = (grossPay - taxes - ei) * 1.00;
                    disName.setText(results.get(1) + " " + results.get(2));
                    disWage.setText("$" + results.get(4));
                    disGrossPay.setText("$" + String.valueOf(grossPay));
                    disTaxes.setText("$" + String.valueOf(taxes));
                    disEI.setText("$" + String.valueOf(ei));
                    disNetPay.setText("$" + String.valueOf(netPay));
                    //showing Fields
                    disName.animate().alpha(1).setDuration(2000);
                    disWage.animate().alpha(1).setDuration(2000);
                    disGrossPay.animate().alpha(1).setDuration(2000);
                    disTaxes.animate().alpha(1).setDuration(2000);
                    disEI.animate().alpha(1).setDuration(2000);
                    disNetPay.animate().alpha(1).setDuration(2000);
                    nameEmp.animate().alpha(1).setDuration(2000);
                    wagePerHour.animate().alpha(1).setDuration(2000);
                    grossPayAmt.animate().alpha(1).setDuration(2000);
                    taxesAmt.animate().alpha(1).setDuration(2000);
                    eiAmt.animate().alpha(1).setDuration(2000);
                    netPayAmt.animate().alpha(1).setDuration(2000);


                }
            }
        });



    }
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

    public boolean validateHours(String hours){
        if(hours.length() > 0){
            if(Double.parseDouble(hours) > 30 && Double.parseDouble(hours) < 50 ){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean validateID(String id, List<String> empID){
        if(id.length() == 4){
            for(int i = 0; i< empID.size(); i++){
                if(empID.get(i).equals(id)){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }
}
