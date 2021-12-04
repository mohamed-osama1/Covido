package com.example.doctor_client_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class PatientSigning extends AppCompatActivity implements View.OnClickListener {
    Button Register;
    Button LogIn;
    private FirebaseAuth mAuth;
    private EditText  editTextEmail ,editTextPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_welcome_screen);

        Register = (Button)findViewById(R.id.regPat);
        LogIn = (Button)findViewById(R.id.logPat);
        Register.setOnClickListener(this::onClick);
        LogIn.setOnClickListener(this::onClick);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword= findViewById(R.id.editTextPassword);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.logPat)
        {
            userLogin();
        }
        if (v.getId()==R.id.regPat)
        {
            Intent i = new Intent(getApplicationContext(),registerScreen.class);
            startActivity(i);
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(PatientSigning.this, PatientPage.class));
                } else {
                    Toast.makeText(PatientSigning.this, "Failed to Login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
