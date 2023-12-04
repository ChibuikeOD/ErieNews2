package com.example.erienews2;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //Username and Password of user
        String trueUsername;
        String truePassword;

        //If user just registered, it will get the username and password from the Registration Activity. If not, it will use defaults
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            trueUsername = extras.getString("usernameKey");
            truePassword = extras.getString("passwordKey");
        }
        else{
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


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");//find username
                    Query query = databaseReference.orderByChild("username").equalTo(typedName);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                // Retrieve the user object based on the matched username
                                loggedUser = userSnapshot.getValue(Account.class);

                                // Do something with the user object

                            }
                            //Save the username to shared preferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("username", typedName);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("loggedUser", loggedUser);

                            if(loggedUser == null)
                            {
                                Account blankUser = new Account();
                                blankUser.setUsername("blank User");
                            }

                            Toast.makeText(LoginActivity.this, loggedUser.getUsername(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            LoginActivity.this.finish();

                        }

                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });

               //     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
              //      startActivity(intent);

                //    LoginActivity.this.finish();

            }
        });

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
}
