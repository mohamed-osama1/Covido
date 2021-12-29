package com.example.doctor_client_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doctor_client_app.DoctorTabs.DoctorAdapter;
import com.example.doctor_client_app.DoctorTabs.DoctorHospitals;
import com.example.doctor_client_app.DoctorTabs.DoctorProfile;
import com.example.doctor_client_app.DoctorTabs.DoctorRequests;
import com.example.doctor_client_app.PatientTabs.PatientAdapter;
import com.example.doctor_client_app.PatientTabs.PatientHospitals;
import com.example.doctor_client_app.PatientTabs.PatientProfile;
import com.example.doctor_client_app.PatientTabs.PatientRequests;
import com.example.doctor_client_app.PatientTabs.Stats;
import com.google.android.material.tabs.TabLayout;

public class DoctorPage extends AppCompatActivity {
    private TabLayout doctorTabs;
    private ViewPager doctorViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_screen);

        doctorTabs=findViewById(R.id.doctorTabs);
        doctorViewPager=findViewById(R.id.doctorViewPager);

        doctorTabs.setupWithViewPager(doctorViewPager);

        DoctorAdapter doctorAdapter = new DoctorAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        doctorAdapter.addFragment(new DoctorRequests(),"Requests");
        doctorAdapter.addFragment(new DoctorHospitals(),"Hospitals");
        doctorAdapter.addFragment(new DoctorProfile(),"Profile");
        doctorViewPager.setAdapter(doctorAdapter);

    }
}