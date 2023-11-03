package com.example.erienews2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EventDescActivity extends AppCompatActivity
{

    EditText eventDate;
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
        eventDate = findViewById(R.id.eventDate);
        backButton = findViewById(R.id.button4);
        nextButton = findViewById(R.id.button5);

        //If user just registered, it will get the username and password from the Registration Activity. If not, it will use defaults
        //    Bundle extras = getIntent().getExtras();
        //  if(extras != null){
        ///    eventName = extras.getString("keyName");
        //eventStart= extras.getString("keyStart");
        // eventEnd= extras.getString("keyEnd");
        //    createdEvent.setName(eventName);
        //  createdEvent.setStartTime(eventStart);
        //createdEvent.setEndTime(eventEnd);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent intent = new Intent(EventDescActivity.this, AddEventActivity.class);
                createdEvent = new Event();
                createdEvent.setDesription(eventDesc.getText().toString());
                createdEvent.setEventDate(eventDate.getText().toString());
                createdEvent.setName(getIntent().getStringExtra("keyName"));
                createdEvent.setStartTime(getIntent().getStringExtra("keyStart"));
                createdEvent.setEndTime(getIntent().getStringExtra("keyEnd"));

                mDatabase.child("Events").child(createdEvent.getUserID()).setValue(eventMap(createdEvent, createdEvent.getName(), createdEvent.getEventDate(), createdEvent.getStartTime(), createdEvent.getEndTime(), createdEvent.getDesription()));
                EventDescActivity.this.finish();
            }


        });
    }






    public Map<String, Object> eventMap(Event event, String eName, String eDate, String eStart, String eEnd, String eDesc) //function to change account object into a map, to be uploaded into the database
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eventName", eName);
        result.put("eventDate", eDate);
        result.put("eventStart", eStart);
        result.put("eventEnd", eEnd);
        result.put("eventDesc", eDesc);
        return result;
    }






}