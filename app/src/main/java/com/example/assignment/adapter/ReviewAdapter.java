package com.example.assignment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.AddReviewActivity;
import com.example.assignment.R;
import com.example.assignment.model.ReviewData;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    List<ReviewData> list;
    public ReviewAdapter(List<ReviewData> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindData(int position){
            TextView tvReview = itemView.findViewById(R.id.tvReview);
            TextView tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
            Button btnEdit = itemView.findViewById(R.id.btnEdit);

            tvReview.setText("Review: "+ list.get(position).getReview());
            tvPostedBy.setText("Posted By:"+ list.get(position).getPostedBy());
            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(list.get(position).getUserId())){
                btnEdit.setVisibility(View.VISIBLE);
            }else{
                btnEdit.setVisibility(View.GONE);
            }

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), AddReviewActivity.class);
                    intent.putExtra("REVIEW", list.get(position).getReview());
                    intent.putExtra("ID", list.get(position).getId());

                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}
