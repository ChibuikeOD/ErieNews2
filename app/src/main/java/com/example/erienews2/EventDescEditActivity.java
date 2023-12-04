package com.example.erienews2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventDescEditActivity extends AppCompatActivity {
    EditText eventAddress;
    EditText eventDesc;

    Button backButton;
    Button nextButton;

    Event createdEvent;
    String eventName;
    String eventStart;
    String eventEnd;
    private DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_description);

        //Get the event that was passed here
        Intent intent = getIntent();
        Event selectedEvent = (Event) intent.getSerializableExtra("selectedEvent");

        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        eventDesc = findViewById(R.id.eventDesc);
        eventDesc.setText(selectedEvent.getDescription());

        eventAddress = findViewById(R.id.eventAddress);
        eventAddress.setText(selectedEvent.getAddress());

        backButton = findViewById(R.id.button4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDescEditActivity.this, MainActivity.class);
                startActivity(intent);
                EventDescEditActivity.this.finish();
            }
        });

        nextButton = findViewById(R.id.button5);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                DatabaseReference eventRef = mDatabase.child("Events").child(selectedEvent.getUserID());

                eventRef.updateChildren(eventMap(createdEvent, createdEvent.getName(), createdEvent.getEventOrganizerUsername(), createdEvent.getAddress(), createdEvent.getStartTime(), createdEvent.getEndTime(), createdEvent.getDescription()));

                //Move to back to main activity
                Intent intent = new Intent(EventDescEditActivity.this, MainActivity.class);
                startActivity(intent);
                EventDescEditActivity.this.finish();
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
