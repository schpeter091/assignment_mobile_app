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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddReviewActivity extends AppCompatActivity {

    Button btnAddReview;
    EditText etReview;
    String restId;
    private UserData userData;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        btnAddReview = findViewById(R.id.btnAddReview);
        etReview = findViewById(R.id.etReview);

        restId = getIntent().getStringExtra("REST_ID");
        userData = (UserData) getIntent().getSerializableExtra("USER_DATA");
        boolean isEdit = getIntent().hasExtra("REVIEW");

        if(isEdit){
            etReview.setText(getIntent().getStringExtra("REVIEW"));
            id = getIntent().getStringExtra("ID");
        }




        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                if(isEdit){
                    DocumentReference document = firebaseFirestore.collection("reviews").document(id);
                    document.update("review",etReview.getText().toString() ).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddReviewActivity.this, "Review Updated!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }else {
                    ReviewData reviewData = new ReviewData(restId, etReview.getText().toString(), userData.getFirstName() + " " + userData.getLastName(), FirebaseAuth.getInstance().getCurrentUser().getUid());

                    DocumentReference document = firebaseFirestore.collection("reviews").document();
                    reviewData.setId(document.getId());
                    document.set(reviewData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddReviewActivity.this, "Review Added!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }

            }
        });
    }
}