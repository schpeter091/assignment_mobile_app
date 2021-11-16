package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment.model.UserData;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUserName, tvEmail, tvUserRole;
    UserData userData;
    Button btnAddRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvEmail = findViewById(R.id.tvEmail);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserRole = findViewById(R.id.tvUserRole);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");

        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tvUserRole.setText(userData.getUserType());
        tvUserName.setText(userData.getFirstName()+" "+userData.getLastName());

        if(userData.getUserType().equals("Admin")){
            btnAddRestaurant.setVisibility(View.VISIBLE);
        }else{
            btnAddRestaurant.setVisibility(View.GONE);
        }

        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddRestaurantActivity.class);
                startActivity(intent);
            }
        });

    }
}