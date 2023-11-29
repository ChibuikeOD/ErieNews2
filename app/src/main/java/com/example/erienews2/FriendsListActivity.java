package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FriendsListActivity extends AppCompatActivity {
    Button backButton;
    Button AddFriendButton;
    Account loggedUser;

    TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        Intent intent = getIntent();
        loggedUser = (Account)intent.getSerializableExtra("loggedUser");
        backButton = findViewById(R.id.backButt);
        AddFriendButton = findViewById(R.id.addFriendButton);
        userText = findViewById(R.id.textView7);
        userText.setText(loggedUser.getUsername());
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
