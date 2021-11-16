package com.example.assignment.adapter;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.RestaurantDetailActivity;
import com.example.assignment.model.RestaurantData;
import com.example.assignment.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    List<RestaurantData> restaurantList;
    UserData userData;
    public RestaurantListAdapter(List<RestaurantData> list,UserData userData){
     this.restaurantList = list;
     this.userData = userData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant,parent,false);
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

        void bindData(int position){
            tvRestaurantName.setText(restaurantList.get(position).getName());
            Glide.with(imgRestaurant).load(restaurantList.get(position).getImageUrl()).into(imgRestaurant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), RestaurantDetailActivity.class);
                    intent.putExtra("REST_DATA", restaurantList.get(position));
                    intent.putExtra("USER_DATA", userData);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
