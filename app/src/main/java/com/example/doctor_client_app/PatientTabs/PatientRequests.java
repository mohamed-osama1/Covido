package com.example.doctor_client_app.PatientTabs;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doctor_client_app.PatientPage;
import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;
import com.example.doctor_client_app.User;
import com.example.doctor_client_app.registerScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;


public class PatientRequests extends Fragment {
    Button btn;
    String mytext;
    private FirebaseAuth mAuth;
    String patientId;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String[] name = new String[1];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                name[0] = String.valueOf(snapshot.child("name").getValue());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_requests, container, false);

        btn = (Button) v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(v.getContext());
                final EditText request = new EditText(v.getContext());
                request.setInputType(InputType.TYPE_CLASS_TEXT);

                alertbuilder.setView(request);
                alertbuilder.setTitle("Medical Issue")
                        .setMessage("What's wrong with you?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mytext = request.getText().toString();

                                patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                Request request1 = new Request(name[0],mytext);
                                String requestId= UUID.randomUUID().toString();
                                request1.uid=patientId;
                                request1.requestId=requestId;

                                DatabaseReference requests = FirebaseDatabase.getInstance().getReference("Requests");
                                        requests.child(patientId).child(requestId).setValue(request1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

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



        });
        return v;

    }
}