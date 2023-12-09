package com.example.erienews2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class EditEventActivity extends AppCompatActivity {
    TextView title;
    EditText eventName;
    Button pickStartTimeButton;
    Button pickStartDateButton;
    Button pickEndTimeButton;
    Button pickEndDateButton;

    Button backButton;
    Button nextButton;

    Event createdEvent;

    String typedName;
    Date startDateTime;
    Date endDateTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        //Edit the title of this activity
        title = findViewById(R.id.title);
        title.setText("Edit Event");

        //Get the event that was passed here
        Intent intent = getIntent();
        Event selectedEvent = (Event) intent.getSerializableExtra("selectedEvent");

        //Set the name of the event to the saved event
        eventName = findViewById(R.id.eventNameInput);
        eventName.setText(selectedEvent.getName());

        //Create start date at the current created day
        startDateTime = new Date(selectedEvent.getStartTime().getYear(), selectedEvent.getStartTime().getMonth(), selectedEvent.getStartTime().getDate(), selectedEvent.getStartTime().getHours(), selectedEvent.getStartTime().getMinutes());
        //Create end date at the current created day plus one
        endDateTime = new Date(selectedEvent.getEndTime().getYear(), selectedEvent.getEndTime().getMonth(), selectedEvent.getEndTime().getDate(), selectedEvent.getEndTime().getHours(), selectedEvent.getEndTime().getMinutes());

        //Instantiate button and set text to events starting day
        pickStartDateButton = findViewById(R.id.pickStartDateButton);
        pickStartDateButton.setText((startDateTime.getMonth() + 1) + "/" + startDateTime.getDate() + "/" + startDateTime.getYear());

        //On Click, bring up date listener and get new start date for event
        pickStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Set button text to selected day
                                pickStartDateButton.setText((month + 1) + "/" + dayOfMonth + "/" + year);

                                //Change startDateTime
                                startDateTime.setYear(year);
                                startDateTime.setMonth(month);
                                startDateTime.setDate(dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        //Instantiate button and set text to starting time of event
        pickStartTimeButton = findViewById(R.id.pickStartTimeButton);
        pickStartTimeButton.setText(startDateTime.getHours() + ":" + String.format("%02d", startDateTime.getMinutes()));

        //On Click, bring up time listener and get new start time for event
        pickStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Set button text to selected time
                                pickStartTimeButton.setText(hourOfDay + ":" + String.format("%02d", minute));

                                //Change startDateTime
                                startDateTime.setHours(hourOfDay);
                                startDateTime.setMinutes(minute);
                            }
                        },
                        hour, minute, false);

                timePickerDialog.show();
            }
        });

        //Instantiate button and set text to end date of selected event
        pickEndDateButton = findViewById(R.id.pickEndDateButton);
        pickEndDateButton.setText((endDateTime.getMonth() + 1) + "/" + endDateTime.getDate() + "/" + endDateTime.getYear());

        //On Click, bring up date listener and get new end date for event
        pickEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //Set button text to selected day
                                pickEndDateButton.setText((month + 1) + "/" + dayOfMonth + "/" + year);

                                //Change endDateTime
                                endDateTime.setYear(year);
                                endDateTime.setMonth(month);
                                endDateTime.setDate(dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        //Instantiate button and set text to current day
        pickEndTimeButton = findViewById(R.id.pickEndTimeButton);
        pickEndTimeButton.setText(endDateTime.getHours() + ":" + String.format("%02d", endDateTime.getMinutes()));

        //On Click, bring up time listener and get new end time for event
        pickEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the 0:00 as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Set button text to selected time
                                pickEndTimeButton.setText(hourOfDay + ":" + String.format("%02d", minute));

                                //Change endDateTime
                                endDateTime.setHours(hourOfDay);
                                endDateTime.setMinutes(minute);
                            }
                        },
                        hour, minute, false);

                timePickerDialog.show();
            }
        });

        //Set back button, which takes user back to main activity
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEventActivity.this, MainActivity.class);
                startActivity(intent);
                EditEventActivity.this.finish();
            }
        });

        //Set next button, which takes this information, packages it, and sends it and user to the Edit Event Description Activity
        nextButton = findViewById(R.id.button3);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedName = eventName.getText().toString();

                Intent intent = new Intent(EditEventActivity.this, EventDescEditActivity.class);
                intent.putExtra("keyName",typedName);
                intent.putExtra("keyStart", startDateTime);
                intent.putExtra("keyEnd", endDateTime);
                intent.putExtra("selectedEvent", selectedEvent);
                startActivity(intent);
                EditEventActivity.this.finish();
            }
        });
    }
}
