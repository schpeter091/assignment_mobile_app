package com.example.assignment;

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
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class AddRestaurantActivity extends AppCompatActivity {

    Button button;
    EditText streetName;
    EditText restaurantName;
    EditText postCode;
    ImageView picRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        button = findViewById(R.id.button_addRestaurant);
        streetName = findViewById(R.id.etSteetName);
        restaurantName = findViewById(R.id.etRestaurantName);
        postCode = findViewById(R.id.etPostCode);
        picRestaurant = findViewById(R.id.picRestaurant);

        picRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }




    private void selectImage(){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri uri = data.getData();

            // Use Uri object instead of File to avoid storage permissions
            picRestaurant.setImageURI(uri);
        }
    }


    private boolean validateData(){
if(restaurantName.getText().toString().isEmpty()){
    Toast.makeText(AddRestaurantActivity.this,"Please enter a restaurant name! ",Toast.LENGTH_LONG).show();
    return false;
}
if(streetName.getText().toString().isEmpty()){
    Toast.makeText(AddRestaurantActivity.this, "Please enter a street name! ", Toast.LENGTH_LONG).show();
    return false;

}
if (postCode.getText().toString().isEmpty()){
    Toast.makeText(AddRestaurantActivity.this, "Enter a postcode! ", Toast.LENGTH_LONG).show();

}
    }
}