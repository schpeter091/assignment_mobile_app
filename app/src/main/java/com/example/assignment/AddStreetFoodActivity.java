package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.StreetFoodData;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddStreetFoodActivity extends AppCompatActivity {

    Button button;
    EditText streetName;
    EditText foodName;
    EditText postCode;
    ImageView picRestaurant;
    private Uri uri;
    RadioButton rbVeg,rbNonVeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_street_food);

        button = findViewById(R.id.button_addRestaurant);
        streetName = findViewById(R.id.etSteetName);
        foodName = findViewById(R.id.etStreetFoodName);
        postCode = findViewById(R.id.etPostCode);
        picRestaurant = findViewById(R.id.picRestaurant);
        rbVeg = findViewById(R.id.rbVeg);
        rbNonVeg = findViewById(R.id.rbNonVeg);

        picRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data.getData();

            // Use Uri object instead of File to avoid storage permissions
            picRestaurant.setImageURI(uri);
        }
    }



    private void saveData(){
        if(validateData()){
            FirebaseStorage storage =  FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Create a reference to "mountains.jpg"
            StorageReference restaurantsRef = storageRef.child("restarant_"+foodName.getText()+"-"+new Date() +".jpg");

            restaurantsRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        restaurantsRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String url = task.getResult().toString();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                StreetFoodData restaurantData =  new StreetFoodData();
                                restaurantData.setName(foodName.getText().toString());
                                restaurantData.setImageUrl(url);
                                restaurantData.setFoodType(rbNonVeg.isChecked()?"Non Vegetarian":"Vegetarian");
                                restaurantData.setPostcode(postCode.getText().toString());
                                restaurantData.setStreetName(streetName.getText().toString());
                                restaurantData.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                DocumentReference document = db.collection("streetFood").document();
                                restaurantData.setId(document.getId());

                                document.set(restaurantData)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(AddStreetFoodActivity.this, " Upload successful", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                }
            });

        }
    }


    private void selectImage() {
        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private boolean validateData() {
        if (foodName.getText().toString().isEmpty()) {
            Toast.makeText(AddStreetFoodActivity.this, "Please enter a street food name! ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (streetName.getText().toString().isEmpty()) {
            Toast.makeText(AddStreetFoodActivity.this, "Please enter a street name! ", Toast.LENGTH_LONG).show();
            return false;

        }
        if (postCode.getText().toString().isEmpty()) {
            Toast.makeText(AddStreetFoodActivity.this, "Enter a postcode! ", Toast.LENGTH_LONG).show();
            return false;
        }

        if(uri==null){
            Toast.makeText(AddStreetFoodActivity.this, "No photo selected ", Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }
}