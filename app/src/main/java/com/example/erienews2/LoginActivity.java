package com.example.erienews2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

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
                if (username.getText().toString().equals(trueUsername) && password.getText().toString().equals(truePassword)) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
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
