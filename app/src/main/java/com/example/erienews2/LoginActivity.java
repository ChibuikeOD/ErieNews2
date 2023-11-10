package com.example.erienews2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    Button forgotPasswordButton;

    Account loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //Username and Password of user
        String trueUsername;
        String truePassword;


        //If user just registered, it will get the username and password from the Registration Activity. If not, it will use defaults
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trueUsername = extras.getString("usernameKey");
            truePassword = extras.getString("passwordKey");
        } else {
            trueUsername = "user";
            truePassword = "1234";
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        //If username and password entered match up to ours, user will be taken to Landing Page. if not, user will be told they failed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String typedName = username.getText().toString();
                String typedPass = password.getText().toString();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();//find username
                Query query = databaseReference.child("users").orderByChild("username").equalTo(typedName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Retrieve the user object based on the matched username
                           loggedUser = userSnapshot.getValue(Account.class);

                            // Do something with the user object
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", loggedUser.getUsername());

                        Toast.makeText(LoginActivity.this, loggedUser.getUsername(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
                    //   databaseReference.child("users").child(typedName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    //public void onComplete(@NonNull Task<DataSnapshot> task) {
                    //      if (!task.isSuccessful()) {
                    //            Log.e("firebase", "Error getting data", task.getException());
                    //          }
                    //            else {
                    //                  Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    //                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    //                      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //                        intent.putExtra("username", typedName);
                    //                          startActivity(intent);
//
                    //                          LoginActivity.this.finish();
//
                    //         }
                    //       }
                    //     });

                    //  query.addListenerForSingleValueEvent(new ValueEventListener() {
                    //        @Override
                    //        public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //username-password match
                    //            if (dataSnapshot.child("password").getValue(String.class).equals(typedPass)) {
                    //               Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                    //             }
                    //         }

                    //          @Override
                    //          public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                    //             Log.w("FirebaseError", "Failed to read value.", databaseError.toException());

                    // Show an error message to the user
                    //            Toast.makeText(getApplicationContext(), "Failed to retrieve data. Please try again later.", Toast.LENGTH_SHORT).show();
                    //         }


                    //  if (username.getText().toString().equals(trueUsername) && password.getText().toString().equals(truePassword)) {
                    //           Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();


                    //     } else {
                    //        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    //   }
                    //  }
                    //    });
                    //    }



                //Take user to Register Page
                registerButton = findViewById(R.id.registerButton);
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);

                        LoginActivity.this.finish();
                    }
                });

                //Take User to Reset Password Page
                forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
                forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                        intent.putExtra("usernameKey", trueUsername);
                        intent.putExtra("passwordKey", truePassword);
                        startActivity(intent);

                        LoginActivity.this.finish();
                    }
                });
            }
        });
    }
}













