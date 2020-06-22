package com.example.mycityguideapp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycityguideapp.R;

public class SignUp extends AppCompatActivity {

    // Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_sign_up);

        // Hooks
        backBtn = findViewById(R.id.sign_up_back_btn);
        next = findViewById(R.id.signUp_next_btn);
        login = findViewById(R.id.sign_up_login_btn);
        titleText = findViewById(R.id.signUp_title_text);

        // back function
        // back press function
    }

    public void callNextSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        // Add transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_title_text");
        pairs[2] = new Pair<View, String>(login, "transition_signUp_next_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_signUp_login_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

}
