package com.example.erienews2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity {

    EditText FriendName;
    Button nextButton;
    Button backButton;

    String friendName;

    Account newFriend;

    Account loggedUser;

    TextView nameView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_friend);

        FriendName = findViewById(R.id.edittext20);
        nextButton = findViewById(R.id.button6);
        backButton = findViewById(R.id.backButton);
        nameView = findViewById(R.id.nameView);


        FirebaseApp.initializeApp(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Intent intent1 = getIntent();
        loggedUser = (Account)intent1.getSerializableExtra("loggedUser");


        if(loggedUser == null)
        {
            Account blankUser = new Account();
            blankUser.setUsername("blank User");
            loggedUser = blankUser;
        }
        nameView.setText(loggedUser.getUsername());
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference().child("users").child(loggedUser.getUsername());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                   friendName = FriendName.getText().toString();

                   Query query = databaseReference.orderByChild("username").equalTo(friendName);

                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                   newFriend = snapshot.getValue(Account.class);
                                   if (newFriend != null) {
                                       loggedUser.addFriend(newFriend);
                                       List<Account> currentList;
                                       currentList = loggedUser.getFriendsList(); //get current friend list
                                       currentList.add(newFriend);
                                       loggedUser.setFriendsList(currentList);

                                       accountRef.setValue(loggedUser);
                                   }
                                //   else
                                //   {
                                 //      Toast.makeText(context, "User not found", duration).show();

                                //   }

                                   Toast.makeText(context, "User found: " + newFriend.getUsername(), duration).show();

                           }
                               Toast.makeText(context, "Snapshot doesn't exist", duration).show();

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(context, "User not found", duration).show();

                       }


                   });


                Intent intent2 = new Intent(AddFriendActivity.this, FriendsListActivity.class);
                intent2.putExtra("loggedUser", loggedUser);

                startActivity(intent2);
                AddFriendActivity.this.finish();

            }






        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(AddFriendActivity.this, FriendsListActivity.class);
                intent2.putExtra("loggedUser", loggedUser);
                startActivity(intent2);

               AddFriendActivity.this.finish();


            }
        });

    }
}