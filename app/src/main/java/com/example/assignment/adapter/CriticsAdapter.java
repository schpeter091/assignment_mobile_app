package com.example.assignment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.HomeActivity;
import com.example.assignment.R;
import com.example.assignment.RegistrationActivity;
import com.example.assignment.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CriticsAdapter extends RecyclerView.Adapter<CriticsAdapter.ViewHolder> {
    List<UserData> userData;

    public CriticsAdapter(List<UserData> userData) {
        this.userData = userData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_critic_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindData(int position){
            TextView tvName = itemView.findViewById(R.id.tvName);
            Button btnApprove = itemView.findViewById(R.id.btnApprove);
            if(userData.get(position).isApproved()){
                btnApprove.setVisibility(View.GONE);
            }else{
                btnApprove.setVisibility(View.VISIBLE);
            }

            tvName.setText(userData.get(position).getFirstName()+" "+userData.get(position).getLastName());
            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(userData.get(position).getUserId())
                            .update("approved",true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(itemView.getContext(), "Critic Approved", Toast.LENGTH_LONG).show();
                                        userData.remove(position);
                                        notifyItemRemoved(position);
                                    }

                                }
                            });
                }
            });
        }
    }
}
