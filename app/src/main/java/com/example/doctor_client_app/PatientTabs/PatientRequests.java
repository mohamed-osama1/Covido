package com.example.doctor_client_app.PatientTabs;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doctor_client_app.PatientPage;
import com.example.doctor_client_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PatientRequests extends Fragment {
    Button btn;
    String mytext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_requests, container, false);
        FloatingActionButton   LogIn = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        LogIn.setOnClickListener(this::onF);
        return v;

    }
    public void onF(View v) {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(v.getContext());
        final EditText request = new EditText(v.getContext());
        request.setInputType(InputType.TYPE_CLASS_TEXT);
        alertbuilder.setView(request);
        alertbuilder.setTitle("Request alert")
                .setMessage("Do you want to add new request?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mytext = request.getText().toString();
                        Toast.makeText(v.getContext(),"Request Sent Successfully ",Toast.LENGTH_SHORT).show();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"Selected Option: No",Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog dialog  = alertbuilder.create();
        dialog.show();
    }
}