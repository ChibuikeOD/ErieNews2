package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {
    Button backButton;
    Button AddFriendButton;
    Account loggedUser;

    ListView friendView;

    TextView userText;

    List<Account> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        Intent intent = getIntent();
        loggedUser = (Account)intent.getSerializableExtra("loggedUser");
        backButton = findViewById(R.id.backButt);
        AddFriendButton = findViewById(R.id.addFriendButton);
       // userText = findViewById(R.id.textView7);
       // userText.setText(loggedUser.getUsername());
        friendsList = new ArrayList<>();
        List<Account> currentFriends = new ArrayList<>();


        FirebaseApp.initializeApp(this);
        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("users").child(loggedUser.getUserID()).child("friendsList");


        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               friendsList = new ArrayList<>();

                for (DataSnapshot friendSnapshot : dataSnapshot.getChildren()) {
                   Account friend = friendSnapshot.getValue(Account.class);
                    friendsList.add(friend);
                }
             //   List<Account> currentFriends = friendsList;

                // Now you have the friendsList in your app
                // You can use it as needed, for example, display it in a ListView or RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        currentFriends = loggedUser.getFriendsList();
        if(currentFriends == null)
        {
            Account def = new Account();
            def.setUsername("default");
            currentFriends.add(def);

        }

        List<String> friendNames = new ArrayList<String>();

        for(int i = 0; i <= currentFriends.size() - 1; i++)
        {

            friendNames.add(currentFriends.get(i).getUsername());
        }

        friendView = findViewById(R.id.lView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendNames);
        friendView.setAdapter(adapter);





        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FriendsListActivity.this, MainActivity.class);
                intent1.putExtra("loggedUser", loggedUser);
                startActivity(intent1);

                FriendsListActivity.this.finish();
            }
        });

        AddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(FriendsListActivity.this, AddFriendActivity.class);
                intent2.putExtra("loggedUser", loggedUser);
                startActivity(intent2);

                FriendsListActivity.this.finish();
            }
        });
    }
}
