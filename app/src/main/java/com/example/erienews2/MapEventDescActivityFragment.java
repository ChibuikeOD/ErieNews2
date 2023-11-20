package com.example.erienews2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class MapEventDescActivityFragment extends Fragment {
    View view;
    Button eventBackButton;
    Button addEventButton;
    public MapEventDescActivityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_map_event_description_fragment, container, false);

        //Set parts of the event page to the class given
        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("event");

            //Title
            TextView eventNameText = view.findViewById(R.id.eventNameText);
            eventNameText.setText(event.getName());

            //Address
            TextView eventAddress = view.findViewById(R.id.eventAddress);
            eventAddress.setText(event.getAddress());

            //Description
            TextView eventDescription = view.findViewById(R.id.eventDescription);
            eventDescription.setText(event.getDesription());

            //Time
            String startDateTime = ((event.getStartTime().getMonth() + 1) + "/" + event.getStartTime().getDate() + "/" + event.getStartTime().getYear() + " " + event.getStartTime().getHours() + ":" + String.format("%02d", event.getStartTime().getMinutes()));
            String endDateTime = ((event.getEndTime().getMonth() + 1) + "/" + event.getEndTime().getDate() + "/" + event.getEndTime().getYear() + " " + event.getEndTime().getHours() + ":" + String.format("%02d", event.getEndTime().getMinutes()));

            TextView eventTimeRange = view.findViewById(R.id.eventTimeRange);
            eventTimeRange.setText(startDateTime + " - " + endDateTime);
        }

        eventBackButton = (Button) view.findViewById(R.id.eventBackButton);
        eventBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        Button addEventButton = (Button) view.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });

        return view;
    }

    private void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
