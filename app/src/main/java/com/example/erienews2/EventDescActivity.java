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
                createdEvent.setAddress(eventAddress.getText().toString());
                createdEvent.setName(getIntent().getStringExtra("keyName"));
                createdEvent.setStartTime((Date) getIntent().getSerializableExtra("keyStart"));
                createdEvent.setEndTime((Date) getIntent().getSerializableExtra("keyEnd"));

                mDatabase.child("Events").child(createdEvent.getUserID()).setValue(eventMap(createdEvent, createdEvent.getName(), createdEvent.getAddress(), createdEvent.getStartTime(), createdEvent.getEndTime(), createdEvent.getDesription()));

                Intent intent = new Intent(EventDescActivity.this, MainActivity.class);
                startActivity(intent);
                EventDescActivity.this.finish();
            }


        });
    }






    public Map<String, Object> eventMap(Event event, String eName, String eAddress, Date eStart, Date eEnd, String eDesc) //function to change account object into a map, to be uploaded into the database
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eventName", eName);
        result.put("eventAddress", eAddress);
        result.put("eventStart", eStart);
        result.put("eventEnd", eEnd);
        result.put("eventDesc", eDesc);
        return result;
    }






}