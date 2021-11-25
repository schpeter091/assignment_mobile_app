package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    Button btnRegister, btnLogin;
    EditText etEmail, etFirstName, etLastName, etPassword;
    Spinner spinner;
    private FirebaseAuth mAuth;
    String[] userTypes =  new String[]{"Select the usertype","User","Admin","Critic"};//creating a list


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();//initializing the FB auth object

        etEmail = findViewById(R.id.etEmail); //initializing the view
        etFirstName = findViewById(R.id.etFirstName); //initializing the view
        etLastName = findViewById(R.id.etLastName); //initializing the view
        etPassword = findViewById(R.id.etPassword); //initializing the view
        btnRegister = findViewById(R.id.btnRegister);   // R means resource or res
        btnRegister = findViewById(R.id.btnRegister);   // R means resource or res
        btnLogin = findViewById(R.id.btnLogin);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,userTypes);//connect to our list with spinner
        spinner.setAdapter(arrayAdapter);

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
              boolean  isDataValid =  validateData();
              if(isDataValid){
                  registerUser();
              }
            }
        });

    }
    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                      saveData(task.getResult().getUser().getUid());
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    }

                }) ;


    }


    private boolean validateData() {
        if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            Toast.makeText(this, "Please enter valid email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your first name!", Toast.LENGTH_LONG).show();
            return false;

        }
        if (etLastName.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please enter your last name!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_LONG).show();
            return false;

        }
        if (etPassword.getText().length() < 6) {
            Toast.makeText(this, "Your password must be minimum 6 character long", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void saveData(String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UserData userData = new UserData();
        userData.setFirstName(etFirstName.getText().toString());
        userData.setLastName(etLastName.getText().toString());
        userData.setUserType(spinner.getSelectedItem().toString());
        userData.setApproved(false);
        userData.setUserId(userId);


        db.collection("users").document(userId)
                .set(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if(userData.getUserType().equals("Critic")){
                                Toast.makeText(RegistrationActivity.this, "Your critic account request has been sent for approval.", Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                            }else {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                intent.putExtra("USER_DATA", userData);
                                startActivity(intent);
                            }

                        }else{
                            Toast.makeText(RegistrationActivity.this, "registration unsuccessful",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }


}