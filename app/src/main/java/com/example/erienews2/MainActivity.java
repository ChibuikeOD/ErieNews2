package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView mapImage;
    Button friendListButton;
    Button createEventButton;
    Button editEventButton;

    Account loggedUser;
    String loggedUname;
    TextView Uname;
    String loggedPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        loggedUser = (Account)intent.getSerializableExtra("loggedUser");
        Uname = findViewById(R.id.textView15);
        if(loggedUser == null)
        {
            Account blankUser = new Account();
            blankUser.setUsername("blank User");
            loggedUser = blankUser;
            Uname.setVisibility(View.GONE);
        }

        Account def = new Account();
        def.setUsername("default");
        loggedUser.addFriend(def);

        mapImage = findViewById(R.id.mapImage);
        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });






        Uname.setText("Welcome " + loggedUser.getUsername());

        friendListButton = findViewById(R.id.friendListButton);
        friendListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FriendsListActivity.class);
                intent.putExtra("loggedUser", loggedUser);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });

        createEventButton = findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });

        editEventButton = findViewById(R.id.editEventButton);
        editEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, EditEventListActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });
    }
}