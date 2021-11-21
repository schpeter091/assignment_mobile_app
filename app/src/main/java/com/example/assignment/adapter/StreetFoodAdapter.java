package com.example.assignment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.RestaurantDetailActivity;
import com.example.assignment.StreetFoodDetailActivity;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.StreetFoodData;
import com.example.assignment.model.UserData;

import java.util.List;

public class StreetFoodAdapter extends RecyclerView.Adapter<StreetFoodAdapter.ViewHolder>{

    List<StreetFoodData> restaurantList;
    UserData userData;
    public StreetFoodAdapter(List<StreetFoodData> list,UserData userData){
        this.restaurantList = list;
        this.userData = userData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_street_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tvRestaurantName = itemView.findViewById(R.id.textViewRestaurantName);
        ImageView imgRestaurant = itemView.findViewById(R.id.photoRestaurant);
        Button btnView = itemView.findViewById(R.id.btnView);

        void bindData(int position){
            tvRestaurantName.setText(restaurantList.get(position).getName());
            Glide.with(imgRestaurant).load(restaurantList.get(position).getImageUrl()).into(imgRestaurant);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), StreetFoodDetailActivity.class);
                    intent.putExtra("STREET_FOOD_DATA", restaurantList.get(position));
                    intent.putExtra("USER_DATA", userData);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
