package com.example.erienews2;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.erienews2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    Button backButton;
    Button pickDateButton;
    Button pickTimeButton;

    CardView eventCardView;
    Button eventBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        eventCardView = findViewById(R.id.eventCardView);
        eventCardView.setVisibility(View.GONE);

        eventBackButton = findViewById(R.id.eventBackButton);
        eventBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventCardView.setVisibility(View.GONE);
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Use to get date and time
        final Calendar c = Calendar.getInstance();

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
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        //Instantiate button and set text to current day
        pickTimeButton = findViewById(R.id.pickTimeButton);
        pickTimeButton.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", c.get(Calendar.MINUTE)));

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

        //Reload events based on date and time
        String selectedTime = pickTimeButton.getText().toString();
        String selectedDate = pickDateButton.getText().toString();

        //Use selected time and date to get events from database on that day past that time
        ArrayList<String> eventNameList;
        //ArrayList<Info> eventInfoList;
        ArrayList<LatLng> eventLatLngList;

        //Example marker to be instantiated
        Marker markerExample = mMap.addMarker(new MarkerOptions()
                .position(erieBounds.getCenter())
                .title("Example Event"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                //Get title from the marker
                String eventTitle = marker.getTitle();

                //Use eventTitle to get the rest of the information of the event from the database
                //Set screen information to the event information of title

                //Pull up screen showing the information of the selected event
                eventCardView.setVisibility(View.VISIBLE);

                return false;
            }
        });
    }
}