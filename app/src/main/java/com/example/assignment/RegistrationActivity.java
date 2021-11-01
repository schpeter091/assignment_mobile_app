package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }


    private boolean validateData(){
        if(etEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            Toast.makeText(this, "Please enter valid email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(etFirstName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your first name!", Toast.LENGTH_LONG).show();
            return false;

        }
        if(etLastName.getText().toString().isEmpty()){

                Toast.makeText(this, "Please enter your last name!", Toast.LENGTH_LONG).show();
                return false;
        }

        if(etPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_LONG).show();
            return false;

        }
        if(etPassword.getText().length() < 6 ) {
            Toast.makeText(this, "Your password must be minimum 6 character long", Toast.LENGTH_LONG).show();

        }

        return true;
    }



}