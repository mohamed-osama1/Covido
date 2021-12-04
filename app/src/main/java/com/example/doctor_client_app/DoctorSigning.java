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

public class DoctorSigning extends AppCompatActivity implements View.OnClickListener {
    Button Register;
    Button LogIn;
    Button ForgotPass;
    private FirebaseAuth mAuth;
    private EditText  editTextEmail ,editTextPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_welcome_screen);

        Register = (Button)findViewById(R.id.regDoctor);
        LogIn = (Button)findViewById(R.id.logDoc);
        Register.setOnClickListener(this::onClick);
        LogIn.setOnClickListener(this::onClick);

        ForgotPass = (Button)findViewById(R.id.docforgotpass);
        ForgotPass.setOnClickListener(this::onClick);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword= findViewById(R.id.editTextPassword);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.logDoc)
        {
            userLogin();
        }
        if (v.getId()==R.id.regDoctor)
        {
            Intent i = new Intent(getApplicationContext(),registerScreen.class);
            startActivity(i);
        }

        if (v.getId()==R.id.docforgotpass)
        {
            String email = editTextEmail.getText().toString().trim();
            if (email.isEmpty()) {
                editTextEmail.setError("Email required");
                editTextEmail.requestFocus();
                return;
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("provide valid email");
                editTextEmail.requestFocus();
                return;
            }
            else {
                Intent i = new Intent(getApplicationContext(),forgotPassword.class);
                startActivity(i);
            }
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
                    startActivity(new Intent(DoctorSigning.this, DoctorPage.class));
                } else {
                    Toast.makeText(DoctorSigning.this, "Failed to Login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
