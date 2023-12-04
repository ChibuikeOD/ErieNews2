package com.example.erienews2;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erienews2.Account;
import com.example.erienews2.FriendsViewHolder;
import com.example.erienews2.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    Context context;
    List<Account> friends;

    public MyAdapter(Context context, List<Account> friends)
    {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsViewHolder(LayoutInflater.from(context).inflate(R.layout.friends_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        if (position >= 0 && position < friends.size()) {
            holder.nameView.setText(friends.get(position).getUsername());
            // Handle other views if needed
        }
      //  holder.pfpView.setImageResource(friends.get(position).ge);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
