package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment.adapter.CriticsAdapter;
import com.example.assignment.adapter.RestaurantListAdapter;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUserName, tvEmail, tvUserRole;
    UserData userData;
    Button btnAddRestaurant, btnLogout;
    RecyclerView rvCritics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvEmail = findViewById(R.id.tvEmail);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserRole = findViewById(R.id.tvUserRole);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        btnLogout = findViewById(R.id.btnLogout);
        rvCritics = findViewById(R.id.rvCritics);
        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");

        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tvUserRole.setText(userData.getUserType());
        tvUserName.setText(userData.getFirstName()+" "+userData.getLastName());

        if(userData.getUserType().equals("Admin")){
            btnAddRestaurant.setVisibility(View.VISIBLE);
            rvCritics.setVisibility(View.VISIBLE);
        }else{
            btnAddRestaurant.setVisibility(View.GONE);
            rvCritics.setVisibility(View.GONE);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddRestaurantActivity.class);
                startActivity(intent);
            }
        });
        getData();

    }


    private void getData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereIn("userType", new ArrayList(Collections.singleton("Critic")))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<UserData> userList = task.getResult().toObjects(UserData.class);
                    CriticsAdapter criticsAdapter = new CriticsAdapter(userList);
                    rvCritics.setAdapter(criticsAdapter);

                }
            }
        });

    }
}