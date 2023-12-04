package com.example.erienews2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class EditEventListActivity extends AppCompatActivity {
    //List of events
    ArrayList<Event> eventList;

    //Create a string list of event names
    ArrayList<String> eventNames;

    ListView editEventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_list);

        //Username of user
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String accountUsername = sharedPreferences.getString("username", null);

        //Populate array with all the names of the events the user made
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsReference = database.getReference("Events");

        eventsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList = new ArrayList<Event>();
                eventNames = new ArrayList<String>();

                //For each event entry, create a local Event object then instantiate it
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    //Create instance of event
                    Event newEvent = new Event(eventSnapshot.getKey());
                    newEvent.setDescription(eventSnapshot.child("eventDesc").getValue(String.class));
                    newEvent.setAddress(eventSnapshot.child("eventAddress").getValue(String.class));
                    newEvent.setName(eventSnapshot.child("eventName").getValue(String.class));
                    newEvent.setStartTime(eventSnapshot.child("eventStart").getValue(Date.class));
                    newEvent.setEndTime(eventSnapshot.child("eventEnd").getValue(Date.class));
                    newEvent.setEventOrganizerUsername(eventSnapshot.child("eventOrganizer").getValue(String.class));

                    //If the account username matches with that of an event's add it to the array
                    if (newEvent.getEventOrganizerUsername().equals(accountUsername)){
                        eventList.add(newEvent);
                    }
                }

                for (Event event : eventList){
                    eventNames.add(event.getName());
                }
                //Set the names of the event list
                ArrayAdapter adapter = new ArrayAdapter<String>(EditEventListActivity.this, R.layout.edit_event_list_entry, eventNames);

                ListView listView = findViewById(R.id.event_list);
                listView.setAdapter(adapter);

                //Create listener for list view
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        Event selectedEvent = eventList.get(position);

                        Intent intent = new Intent(EditEventListActivity.this, EditEventActivity.class);
                        intent.putExtra("selectedEvent", selectedEvent);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
