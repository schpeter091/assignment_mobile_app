package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.adapter.RestaurantListAdapter;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Button buttonToProfile, btnStreetFood;
    UserData userData;
    TextView textView;
    RecyclerView rvRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.txtWelcome);


        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");
        textView.setText("Welcome " + userData.getFirstName());
        buttonToProfile = findViewById(R.id.btn_ToProfile);
        rvRestaurant = findViewById(R.id.rvRestaurant);
        btnStreetFood = findViewById(R.id.btnStreetFood);

        btnStreetFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StreetFoodActivity.class);
                intent.putExtra("USER_DATA", userData);
                startActivity(intent);
            }
        });
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("USER_DATA", userData);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurant")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<RestaurantData> restaurantDataList = task.getResult().toObjects(RestaurantData.class);
                    Collections.sort(restaurantDataList, new Comparator<RestaurantData>() {
                        @Override
                        public int compare(RestaurantData o1, RestaurantData o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                    RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(restaurantDataList, userData);

                    rvRestaurant.setAdapter(restaurantListAdapter);

                }
            }
        });

    }


}