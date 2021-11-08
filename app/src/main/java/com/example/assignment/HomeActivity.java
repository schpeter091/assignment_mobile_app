package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment.model.UserData;

public class HomeActivity extends AppCompatActivity {
    Button buttonToHome;
    UserData userData;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.txtWelcome);


        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");
        textView.setText("Welcome " + userData.getFirstName());
        buttonToHome = findViewById(R.id.btn_ToHome);
        buttonToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddRestaurantActivity.class);
                        startActivity(intent);
            }
        });


    }


}