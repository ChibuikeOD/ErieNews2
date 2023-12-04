package com.example.erienews2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsViewHolder extends RecyclerView.ViewHolder
{
    public ImageView pfpView;
    public TextView nameView;

    public FriendsViewHolder(@NonNull View itemView) {
        super(itemView);
        pfpView = itemView.findViewById(R.id.pfp);
        nameView = itemView.findViewById(R.id.nameView);

    }
}
