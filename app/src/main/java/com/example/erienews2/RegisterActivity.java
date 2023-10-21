package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    EditText passwordReEntry;
    Button backButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

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
