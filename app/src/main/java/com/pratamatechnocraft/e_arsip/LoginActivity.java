package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText eTxtUsername, eTxtPassword;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.buttonLogin);
        eTxtUsername = findViewById(R.id.editTxtUsernameLogin);
        eTxtPassword = findViewById(R.id.editTxtPasswordLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=String.valueOf(eTxtUsername.getText());
                password=String.valueOf(eTxtPassword.getText());
                if (username.equals("user") && password.equals("pass")){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Username Atau Password Tidak Valid!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
