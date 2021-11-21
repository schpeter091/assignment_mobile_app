package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    TextView tvRestaurant, tvAddress;
    ImageView imgRest;
    Button btnAddReview, btnBook;
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
        btnBook = findViewById(R.id.btnBook);

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

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookingCalendar();
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

    private void openBookingCalendar(){
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String url = "https://www.opentable.com/s/?covers=2&dateTime="+year+"-"+monthOfYear+"-"+dayOfMonth+"%"+year+"%3A00&metroId=72&regionIds=5316&pinnedRids%5B0%5D=87967&enableSimpleCuisines=true&includeTicketedAvailability=true&pageType=0";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }
}