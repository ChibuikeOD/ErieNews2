package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView mapImage;
    Button friendListButton;
    Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapImage = findViewById(R.id.mapImage);
        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });

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