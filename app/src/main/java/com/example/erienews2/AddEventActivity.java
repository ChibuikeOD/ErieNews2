package com.example.erienews2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends FragmentActivity {
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

        eventName = findViewById(R.id.eventNameInput);

        //Use to get date and time
        final Calendar c = Calendar.getInstance();
        //Create start date at the current created day
        startDateTime = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0);
        //Create end date at the current created day plus one
        endDateTime = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 1, 0, 0);

        //Instantiate button and set text to current day
        pickStartDateButton = findViewById(R.id.pickStartDateButton);
        pickStartDateButton.setText((c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));

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
                        AddEventActivity.this,
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

        //Instantiate button and set text to current day
        pickStartTimeButton = findViewById(R.id.pickStartTimeButton);
        pickStartTimeButton.setText(0 + ":" + String.format("%02d", 0));

        //On Click, bring up time listener and get new start time for event
        pickStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the current date as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddEventActivity.this,
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

        //Instantiate button and set text to current day
        pickEndDateButton = findViewById(R.id.pickEndDateButton);
        pickEndDateButton.setText((c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH) + 1) + "/" + c.get(Calendar.YEAR));

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
                        AddEventActivity.this,
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
        pickEndTimeButton.setText(0 + ":" + String.format("%02d", 0));

        //On Click, bring up time listener and get new end time for event
        pickEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the 0:00 as the default date in the picker.
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddEventActivity.this,
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
                Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
                startActivity(intent);
                AddEventActivity.this.finish();
            }
        });

        //Set next button, which takes this information, packages it, and sends it and user to the Event Description Activity
        nextButton = findViewById(R.id.button3);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedName = eventName.getText().toString();

                Intent intent = new Intent(AddEventActivity.this, EventDescActivity.class);
                intent.putExtra("keyName",typedName);
                intent.putExtra("keyStart", startDateTime);
                intent.putExtra("keyEnd", endDateTime);
                startActivity(intent);
                AddEventActivity.this.finish();
            }
        });
    }
}


