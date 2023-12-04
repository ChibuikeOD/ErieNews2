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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    EditText passwordReEntry;
    Button backButton;
    Button registerButton;

    private DatabaseReference mDatabase;

    private static final String TAG = "RegisterActivity"; //tag to access console
    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.register_page);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Fields to enter email, username, and password
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordReEntry = findViewById(R.id.passwordReEntry);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();



                Account newAccount = new Account(); //create new account
                newAccount.setUsername( username.getText().toString());
                newAccount.setPassword( password.getText().toString());


                mDatabase.child("users").child(newAccount.getUserID()).setValue(toMap(newAccount, newAccount.getUsername(), newAccount.getPassword(), newAccount.getFriendsList()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                             Log.d(TAG, "Success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the error
                                Log.e(TAG, "Failure");
                            }
                        });


            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("usernameKey", username.getText().toString());
                intent.putExtra("passwordKey", password.getText().toString());
                startActivity(intent);

                RegisterActivity.this.finish();
            }
        });
    }

    //Check if a field is empty or not. Return true or false
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public Map<String, Object> toMap(Account acc, String uName, String pass, List<Account> friends) //function to change account object into a map, to be uploaded into the database
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", uName);
        result.put("password", pass);
        result.put("friendsList", friends);
        return result;
    }

    //Checks to make sure every field has something in it and that the password and password re-entry match up.
    //If so, user is taken back to Login. If not, proper warnings are displayed.
    //username and password are passed back using "usernameKey" and "passwordKey"
    void checkDataEntered(){
        boolean error = false;

        if (isEmpty(username)){
            Toast.makeText(RegisterActivity.this, "Username is required", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (isEmpty(email)){
            Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (isEmpty(passwordReEntry)){
            Toast.makeText(RegisterActivity.this, "Re-enter your password", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (!password.getText().toString().equals(passwordReEntry.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (!error){


            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("usernameKey", username.getText().toString());
            intent.putExtra("passwordKey", password.getText().toString());
            startActivity(intent);

            RegisterActivity.this.finish();
        }
    }
}
