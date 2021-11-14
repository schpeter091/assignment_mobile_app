package com.example.assignment.adapter;

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
import com.example.assignment.model.RestaurantData;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    List<RestaurantData> restaurantList;
    public RestaurantListAdapter(List<RestaurantData> list){
     this.restaurantList = list;
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
        }
    }
}
