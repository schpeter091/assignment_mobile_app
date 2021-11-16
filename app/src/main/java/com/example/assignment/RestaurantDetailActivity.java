package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.UserData;

public class RestaurantDetailActivity extends AppCompatActivity {

    TextView tvRestaurant, tvAddress;
    ImageView imgRest;
    Button btnAddReview;
    RestaurantData restaurantData;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        tvRestaurant = findViewById(R.id.tvRestaurant);
        tvAddress = findViewById(R.id.tvAddress);
        imgRest = findViewById(R.id.imgRest);
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

    }
}