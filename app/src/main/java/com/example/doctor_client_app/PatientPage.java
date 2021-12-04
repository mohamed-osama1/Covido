package com.example.doctor_client_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.doctor_client_app.PatientTabs.PatientHospitals;
import com.example.doctor_client_app.PatientTabs.PatientAdapter;
import com.example.doctor_client_app.PatientTabs.PatientProfile;
import com.example.doctor_client_app.PatientTabs.PatientRequests;
import com.example.doctor_client_app.PatientTabs.Stats;
import com.google.android.material.tabs.TabLayout;

public class PatientPage extends AppCompatActivity {

    private TabLayout patientTabs;
    private ViewPager patientViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);


        patientTabs=findViewById(R.id.patientTabs);
        patientViewPager=findViewById(R.id.patientViewPager);

        patientTabs.setupWithViewPager(patientViewPager);

        PatientAdapter patientAdapter = new PatientAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        patientAdapter.addFragment(new Stats(),"Stats");
        patientAdapter.addFragment(new PatientRequests(),"Requests");
        patientAdapter.addFragment(new PatientHospitals(),"Hospitals");
        patientAdapter.addFragment(new PatientProfile(),"Profile");
        patientViewPager.setAdapter(patientAdapter);

    }
}




