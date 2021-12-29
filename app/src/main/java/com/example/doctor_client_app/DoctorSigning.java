package com.example.doctor_client_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class DoctorSigning extends AppCompatActivity implements View.OnClickListener {
    Button Register;
    Button LogIn;
    private FirebaseAuth mAuth;
    private EditText  editTextEmail ,editTextPassword ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_welcome_screen);

        Register = (Button)findViewById(R.id.regDoctor);
        LogIn = (Button)findViewById(R.id.logDoc);
        Register.setOnClickListener(this);
        LogIn.setOnClickListener(this);


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



        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(DoctorSigning.this, DoctorPage.class));
            } else {
                Toast.makeText(DoctorSigning.this, "Failed to Login!", Toast.LENGTH_LONG).show();
            }
        });
    }


}
