package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etPassword;
    EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }
    private boolean validateData(){
        if(etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            Toast.makeText(this, "Please enter valid email address.", Toast.LENGTH_LONG).show();
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