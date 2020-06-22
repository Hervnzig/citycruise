package com.example.mycityguideapp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.mycityguideapp.R;

public class Login extends AppCompatActivity {

    ImageView loginBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        //Hooks
        loginBackBtn = findViewById(R.id.login_back_btn);

    }
}
