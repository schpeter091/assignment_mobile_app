package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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
                }
            }
        });

    }


}