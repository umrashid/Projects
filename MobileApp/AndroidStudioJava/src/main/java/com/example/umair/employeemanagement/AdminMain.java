package com.example.umair.employeemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        final Button addEmp = (Button)findViewById(R.id.addEmp);
        addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, AdminAddEmp.class);
                startActivity(intent);
            }
        });
        final Button delEmp = (Button) findViewById(R.id.deleteupdateview);
        delEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, AdminDelEmp.class);
                startActivity(intent);
            }
        });
        final Button wageCal = (Button)findViewById(R.id.wageCalculator);
        wageCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, AdminWageCal.class);
                startActivity(intent);

            }
        });
        animateButtons(addEmp, delEmp, wageCal);

    }
    public void animateButtons(Button button1, Button button2, Button button3){
        button1.animate().alpha(1).scaleX(1.25f).scaleY(1.25f).setDuration(2000);
        button2.animate().alpha(1).scaleX(1.25f).scaleY(1.25f).setDuration(2000);
        button3.animate().alpha(1).scaleX(1.25f).scaleY(1.25f).setDuration(2000);
    }
}
