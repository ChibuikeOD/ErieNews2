package com.example.erienews2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.erienews2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    Button backButton;
    Button pickDateButton;
    Button pickTimeButton;

    Date chosenDateAndTime;

    ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Set back button, which takes user back to main activity
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
                MapsActivity.this.finish();
            }
        });

        //Use to get date and time
        final Calendar c = Calendar.getInstance();
        chosenDateAndTime = Calendar.getInstance().getTime();

        //Instantiate button and set text to current day
        pickDateButton = findViewById(R.id.pickDateButton);
        pickDateButton.setText((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));

        //On Click, bring up date listener, get new date, and reload event markers
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MapsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Set button text to selected day
                                pickDateButton.setText((month + 1) + "/" + dayOfMonth + "/" + year);

                                //Call function that selects all event markers for date and time
                                chosenDateAndTime.setDate(dayOfMonth);
                                chosenDateAndTime.setMonth(month);
                                chosenDateAndTime.setYear(year);

                                ReloadMarkers();
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        //Instantiate button and set text to current time
        pickTimeButton = findViewById(R.id.pickTimeButton);
        pickTimeButton.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", c.get(Calendar.MINUTE)));

        //On Click, bring up time listener, get new time, and reload event markers
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MapsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Set button text to selected time
                                pickTimeButton.setText(hourOfDay + ":" + String.format("%02d", minute));

                                //Call function that selects all event markers for date and time
                                chosenDateAndTime.setHours(hourOfDay);
                                chosenDateAndTime.setMinutes(minute);

                                ReloadMarkers();
                            }
                        },
                        hour, minute, false);

                timePickerDialog.show();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLngBounds erieBounds = new LatLngBounds(
                new LatLng(42.04548661463424, -80.16806429247939), new LatLng(42.18903873716287, -79.96800619322025));

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(erieBounds, 100));

                //Prevent zoom from being too great
                googleMap.setMaxZoomPreference(15.0f);

                mMap.setLatLngBoundsForCameraTarget(erieBounds);
            }
        });

        //Reload the Markers in the scene
        ReloadMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                //Get title from the marker
                String eventTitle = marker.getTitle();

                //Get event that that title corresponds to
                getEventIndex(eventTitle);

                int eventIndex = getEventIndex(eventTitle);

                Bundle bundle = new Bundle();
                bundle.putSerializable("event", eventList.get(eventIndex));

                //Start fragment
                MapEventDescActivityFragment eventDescFragment = new MapEventDescActivityFragment();
                eventDescFragment.setArguments(bundle);

                //Set screen information to the event information of title
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, eventDescFragment) // R.id.fragmentContainer is the container in your activity layout
                        .addToBackStack(null)
                        .commit();

                return false;
            }
        });
    }

    private void ReloadMarkers(){
        //Open path
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsReference = database.getReference("Events");

        //Get all settings
        //Time
        String selectedTime = pickTimeButton.getText().toString();
        //Date
        String selectedDate = pickDateButton.getText().toString();
        //Advanced (Not yet implemented)

        //Clear all markers that are on the map
        mMap.clear();

        //Get all events that fit the above criteria from the database and put them into lists we can reference from
        eventList = new ArrayList<Event>();

        //For every marker
        eventsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //For each event entry, create a local Event object then instantiate it
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    //Create instance of event
                    Event newEvent = eventSnapshot.getValue(Event.class);
                    newEvent.setDescription(eventSnapshot.child("eventDesc").getValue(String.class));
                    newEvent.setAddress(eventSnapshot.child("eventAddress").getValue(String.class));
                    newEvent.setName(eventSnapshot.child("eventName").getValue(String.class));
                    newEvent.setStartTime(eventSnapshot.child("eventStart").getValue(Date.class));
                    newEvent.setEndTime(eventSnapshot.child("eventEnd").getValue(Date.class));

                    if(chosenDateAndTime.after(newEvent.getStartTime()) && chosenDateAndTime.before(newEvent.getEndTime())){
                        //Get its LatLng from address
                        LatLng markerLatLng = getLocationFromAddress(null, eventSnapshot.child("eventAddress").getValue(String.class));

                        //Add it to map
                        Marker markerExample = mMap.addMarker(new MarkerOptions()
                                .position(markerLatLng)
                                .title(eventSnapshot.child("eventName").getValue(String.class)));

                        //Add instance to the list of events
                        eventList.add(newEvent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        });
            //Instantiate marker with event title at its location
    }

    //Get Address function
//https://stackoverflow.com/questions/42626735/geocoding-converting-an-address-in-string-form-into-latlng-in-googlemaps-jav
    public LatLng getLocationFromAddress(Context context, String inputtedAddress) {
        //Get the geocoder component
        Geocoder coder = new Geocoder(this, Locale.getDefault());
        //List of addresses that may be the correct location
        List<Address> address;
        //LatLng to be returned
        LatLng resLatLng = null;

        try {
            //Get addresses that
            address = coder.getFromLocationName(inputtedAddress, 5);

            //Return nothing if the geocoder couldn't find anything
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            //Get first (most likely) entry, and se the latitude and longitude to it
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Return the lat and long of this address
        return resLatLng;
    }

    public int getEventIndex(String eventName) {
        //Index of event in the event list
        int eventIndex = 0;
        //Get the event that we are using
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getName().equals(eventName)) {
                eventIndex = i;
            }
        }

        return (eventIndex);
    }
}
//https://www.geeksforgeeks.org/how-to-save-data-to-the-firebase-realtime-database-in-android/
//Query order by child
