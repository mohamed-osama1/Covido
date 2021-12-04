package com.example.doctor_client_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class registerScreen extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText editTextName , editTextEmail ,editTextPassword ;
    private RadioButton makeDoctorUser;
    private RadioButton makePatientUser;
    private Button regUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        mAuth = FirebaseAuth.getInstance();

        regUser = findViewById(R.id.regFinalDoc);
        regUser.setOnClickListener(this);

        editTextName = findViewById(R.id.fullNameInReg);
        makeDoctorUser = findViewById(R.id.radioButton);
        makePatientUser = findViewById(R.id.radioButton2);
        editTextEmail = findViewById(R.id.emailInReg);
        editTextPassword= findViewById(R.id.passInReg);

    }


    @Override
    public void onClick(View v) {
       if (v.getId()==R.id.regFinalDoc)
        {
            registerUser();
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            editTextPassword.setError("password length should be greater than or equal 6");
            editTextPassword.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, pass);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registerScreen.this, "User has been registerd successfully", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(registerScreen.this, "Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(registerScreen.this, "Failed to register!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}