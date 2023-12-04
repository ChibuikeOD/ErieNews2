package com.example.erienews2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventDescActivity extends AppCompatActivity
{
    EditText eventAddress;
    EditText eventDesc;

    Button backButton;
    Button nextButton;

    Event createdEvent;
    String eventName;
    String eventStart;
    String eventEnd;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_description);

        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        eventDesc = findViewById(R.id.eventDesc);
        eventAddress = findViewById(R.id.eventAddress);

        backButton = findViewById(R.id.button4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDescActivity.this, MainActivity.class);
                startActivity(intent);
                EventDescActivity.this.finish();
            }
        });

        nextButton = findViewById(R.id.button5);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent intent = new Intent(EventDescActivity.this, AddEventActivity.class);
                createdEvent = new Event();
                createdEvent.setDescription(eventDesc.getText().toString());
                createdEvent.setAddress(eventAddress.getText().toString());
                createdEvent.setName(getIntent().getStringExtra("keyName"));
                createdEvent.setStartTime((Date) getIntent().getSerializableExtra("keyStart"));
                createdEvent.setEndTime((Date) getIntent().getSerializableExtra("keyEnd"));

                //Set username
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String accountUsername = sharedPreferences.getString("username", null);

                createdEvent.setEventOrganizerUsername(accountUsername);

                mDatabase.child("Events").child(createdEvent.getUserID()).setValue(eventMap(createdEvent, createdEvent.getName(), createdEvent.getEventOrganizerUsername(), createdEvent.getAddress(), createdEvent.getStartTime(), createdEvent.getEndTime(), createdEvent.getDescription()));

                Intent intent = new Intent(EventDescActivity.this, MainActivity.class);
                startActivity(intent);
                EventDescActivity.this.finish();
            }


        });
    }

    public Map<String, Object> eventMap(Event event, String eName, String eventOrganizerUsername, String eAddress, Date eStart, Date eEnd, String eDesc) //function to change account object into a map, to be uploaded into the database
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eventName", eName);
        result.put("eventOrganizer", eventOrganizerUsername);
        result.put("eventAddress", eAddress);
        result.put("eventStart", eStart);
        result.put("eventEnd", eEnd);
        result.put("eventDesc", eDesc);
        return result;
    }
}