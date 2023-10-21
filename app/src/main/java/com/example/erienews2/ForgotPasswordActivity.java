package com.example.erienews2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText newPassword;
    EditText newPasswordConfirm;
    Button resetButton;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        //Get the username from the Login screen
        Bundle extras = getIntent().getExtras();

        String currentUsername = extras.getString("usernameKey");
        String currentPassword = extras.getString("passwordKey");

        newPassword = findViewById(R.id.password);
        newPasswordConfirm = findViewById(R.id.passwordReEntry);
        resetButton = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //If the passwords match up, send them to the MainActivity Page
                if (newPassword.getText().toString().equals(newPasswordConfirm.getText().toString())){
                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    intent.putExtra("usernameKey", currentUsername);
                    intent.putExtra("passwordKey", newPassword.getText().toString());
                    startActivity(intent);

                    ForgotPasswordActivity.this.finish();
                }
                //Otherwise give warning message
                else{
                    Toast.makeText(ForgotPasswordActivity.this, "Change Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                intent.putExtra("usernameKey", currentUsername);
                intent.putExtra("passwordKey", currentPassword);
                startActivity(intent);

                ForgotPasswordActivity.this.finish();
            }
        });

    }
}
