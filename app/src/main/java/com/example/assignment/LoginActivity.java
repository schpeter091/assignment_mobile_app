package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etPassword;
    EditText etEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isDataValid = validateData();
               if (isDataValid){
                   loginUser();

               }
            }
        });

    }
   private void loginUser(){

        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           getData(task.getResult().getUser().getUid());//
                    }else{
                        Toast.makeText(LoginActivity.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();
                        }
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
        return false;
        }


        return true;
    }

    private void getData(String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    UserData userData = task.getResult().toObject(UserData.class);//getting result from Firabase and converting to userdata type
                    Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        })

    }

}