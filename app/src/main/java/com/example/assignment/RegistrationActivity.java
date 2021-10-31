package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {


    Button btnRegister, btnLogin;
    EditText etEmail, etFirstName, etLastName, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        etEmail = findViewById(R.id.etEmail); //initializing the view
        etFirstName = findViewById(R.id.etFirstName); //initializing the view
        etLastName = findViewById(R.id.etLastName); //initializing the view
        etPassword = findViewById(R.id.etPassword); //initializing the view
        btnRegister = findViewById(R.id.btnRegister);   // R means resource or res
        btnRegister = findViewById(R.id.btnRegister);   // R means resource or res
        btnLogin = findViewById(R.id.btnLogin);

        //context, activity lifecycle,intent

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }



}