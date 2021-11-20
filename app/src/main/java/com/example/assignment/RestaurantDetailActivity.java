package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assignment.adapter.ReviewAdapter;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.ReviewData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    TextView tvRestaurant, tvAddress;
    ImageView imgRest;
    Button btnAddReview;
    RestaurantData restaurantData;
    UserData userData;
    RecyclerView rvReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        tvRestaurant = findViewById(R.id.tvRestaurant);
        tvAddress = findViewById(R.id.tvAddress);
        imgRest = findViewById(R.id.imgRest);
        rvReviews = findViewById(R.id.rvReview);
        btnAddReview = findViewById(R.id.btnAddReview);

        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");
        restaurantData = (RestaurantData) getIntent().getSerializableExtra("REST_DATA");
        tvRestaurant.setText(restaurantData.getName());
        tvAddress.setText(restaurantData.getStreetName()+", "+restaurantData.getPostcode());
        Glide.with(imgRest).load(restaurantData.getImageUrl()).into(imgRest);

        if(userData.getUserType().equals("Critic")){
            btnAddReview.setVisibility(View.VISIBLE);
        }else{
            btnAddReview.setVisibility(View.GONE);
        }

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailActivity.this, AddReviewActivity.class);
                intent.putExtra("USER_DATA",userData);
                intent.putExtra("REST_ID", restaurantData.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("reviews").whereIn("restaurantId", new ArrayList(Collections.singleton(restaurantData.getId())))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<ReviewData> reviewDataList = task.getResult().toObjects(ReviewData.class);
                    rvReviews.setAdapter(new ReviewAdapter(reviewDataList));
                }
            }
        });
    }
}