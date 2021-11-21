package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment.adapter.RestaurantListAdapter;
import com.example.assignment.adapter.StreetFoodAdapter;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.StreetFoodData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class StreetFoodActivity extends AppCompatActivity {

    Button btnAddStreetFood;
    RecyclerView rvStreetFood;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food);
        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");

        btnAddStreetFood = findViewById(R.id.btnAddStreetFood);
        rvStreetFood = findViewById(R.id.rvStreetFood);

        btnAddStreetFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreetFoodActivity.this, AddStreetFoodActivity.class);
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
        db.collection("streetFood")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<StreetFoodData> restaurantDataList = task.getResult().toObjects(StreetFoodData.class);
                    StreetFoodAdapter restaurantListAdapter = new StreetFoodAdapter(restaurantDataList, userData);
                    rvStreetFood.setAdapter(restaurantListAdapter);

                }
            }
        });

    }
}