package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment.model.ReviewData;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddReviewActivity extends AppCompatActivity {

    Button btnAddReview;
    EditText etReview;
    String restId;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        restId = getIntent().getStringExtra("REST_ID");
        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");

        btnAddReview = findViewById(R.id.btnAddReview);
        etReview = findViewById(R.id.etReview);

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                ReviewData reviewData = new ReviewData(restId, etReview.getText().toString(), userData.getFirstName() + " " + userData.getLastName());
                firebaseFirestore.collection("reviews").document()
                        .set(reviewData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddReviewActivity.this, "Review Added!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        });
    }
}