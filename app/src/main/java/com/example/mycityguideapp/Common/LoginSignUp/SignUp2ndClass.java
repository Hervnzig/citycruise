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

public class SignUp2ndClass extends AppCompatActivity {

    // Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd_class);

        // Hooks
        backBtn = findViewById(R.id.back_pressed);
        next = findViewById(R.id.signUp_next_btn);
        login = findViewById(R.id.sign_up_login_btn);
        titleText = findViewById(R.id.signUp_title_text);

    }

    public void callNextSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

        // Add transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_title_text");
        pairs[2] = new Pair<View, String>(login, "transition_signUp_next_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_signUp_login_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
        finish();
    }
}
