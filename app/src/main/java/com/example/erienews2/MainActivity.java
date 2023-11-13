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

    Account loggedUser;
    String loggedUname;
    TextView Uname;
    String loggedPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        loggedUname = getIntent().getStringExtra("username");

        mapImage = findViewById(R.id.mapImage);
        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });

        Uname = findViewById(R.id.textView15);
        Uname.setText("Welcome " + loggedUname);

        friendListButton = findViewById(R.id.friendListButton);
        friendListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FriendsListActivity.class);
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
    }
}