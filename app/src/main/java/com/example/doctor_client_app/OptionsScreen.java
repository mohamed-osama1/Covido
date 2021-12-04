package com.example.doctor_client_app;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;


import android.widget.Button;


public class OptionsScreen extends AppCompatActivity {
    Button doctorPage;
    Button patientPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        doctorPage = (Button)findViewById(R.id.doctorPage);
        patientPage =(Button)findViewById(R.id.patientPage);

        doctorPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DoctorSigning.class);
                startActivity(i);

            }
        });

        patientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PatientSigning.class);
                startActivity(i);

            }
        });
    }




    }
