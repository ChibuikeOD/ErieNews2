package com.example.erienews2;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("users");



        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Intent intent1 = getIntent();
        loggedUser = (Account)intent1.getSerializableExtra("loggedUser");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        if(loggedUser == null)
        {
            Account blankUser = new Account();
            blankUser.setUsername("blank User");
            loggedUser = blankUser;
        }
        nameView.setText(loggedUser.getUsername());
       // DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference().child("users").child(loggedUser.getUsername());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                   friendName = FriendName.getText().toString();

                   Query query = friendsRef.orderByChild("username").equalTo(friendName);

                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                   newFriend = snapshot.getValue(Account.class);
                                   if (newFriend != null) {
                                   //    List<Account> currentList;
                                  //     currentList = loggedUser.getFriendsList(); //get current friend list
                                       loggedUser.addFriend(newFriend);

                                       mDatabase.child("users").child(loggedUser.getUserID()).setValue(toMap(loggedUser, loggedUser.getUsername(), loggedUser.getPassword(), loggedUser.getFriendsList()))
                                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                   @SuppressLint("RestrictedApi")
                                                   @Override
                                                   public void onSuccess(Void aVoid) {
                                                       Log.d(TAG, "Success");
                                                   }
                                               })
                                               .addOnFailureListener(new OnFailureListener() {
                                                   @SuppressLint("RestrictedApi")
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {
                                                       // Handle the error
                                                       Log.e(TAG, "Failure");
                                                   }
                                               });

                             //          currentList.add(newFriend);
                             //          loggedUser.setFriendsList(currentList);

                                       // friendsRef.setValue(loggedUser);
                                   }
                                //   else
                                //   {
                                 //      Toast.makeText(context, "User not found", duration).show();

                                //   }

                                   Toast.makeText(context, "User found: " + newFriend.getUsername(), duration).show();

                           }


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

    public Map<String, Object> toMap(Account acc, String uName, String pass, List<Account> friends) //function to change account object into a map, to be uploaded into the database
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", uName);
        result.put("password", pass);
        result.put("friendsList", friends);
        return result;
    }
}