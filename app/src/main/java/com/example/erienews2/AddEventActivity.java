package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class AddEventActivity extends FragmentActivity {
    EditText eventName;

    EditText startTime;

    EditText endTime;

    Button backButton;
    Button nextButton;

    Event createdEvent;

    String typedName;
    String typedStart;
    String typedEnd;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        eventName = findViewById(R.id.edittext12);
        startTime = findViewById(R.id.edittext13);
        endTime = findViewById(R.id.edittext14);

        backButton = findViewById(R.id.button2);
        nextButton = findViewById(R.id.button3);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     Event newEvent = new Event();

            //    newEvent.setName(eventName.getText().toString());
           //     newEvent.setStartTime(startTime.getText().toString());
            //    newEvent.setEndTime(endTime.getText().toString());
           //     createdEvent = newEvent;
                typedName = eventName.getText().toString();
                typedStart = eventName.getText().toString();
                typedEnd = eventName.getText().toString();

                Intent intent = new Intent(AddEventActivity.this, EventDescActivity.class);
                intent.putExtra("keyName",typedName);
                intent.putExtra("keyStart", typedStart);
                intent.putExtra("keyEnd", typedEnd);
                startActivity(intent);


            //    intent.putExtra("keyEvent", (CharSequence) createdEvent);



            }
        });

    }
}


