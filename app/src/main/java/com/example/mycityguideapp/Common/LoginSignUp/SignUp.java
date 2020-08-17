package com.example.mycityguideapp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    // Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);

        // Hooks for animation
        backBtn = findViewById(R.id.sign_up_back_btn);
        next = findViewById(R.id.signUp_next_btn);
        login = findViewById(R.id.sign_up_login_btn);
        titleText = findViewById(R.id.signUp_title_text);

        // Hooks for getting data
        fullName = findViewById(R.id.sign_up_full_name);
        username = findViewById(R.id.sign_up_user_name);
        email = findViewById(R.id.sign_up_email);
        password = findViewById(R.id.sign_up_password);
        // back press function
    }

    public void callNextSignUpScreen(View view) {

        // Data to pass to next screen
        String user_fullNames = fullName.getEditText().getText().toString().trim();
        String user_name = username.getEditText().getText().toString().trim();
        String user_email = email.getEditText().getText().toString().trim();
        String user_password = password.getEditText().getText().toString().trim();

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);


        // Fields going to the next screen
        intent.putExtra("full_name", user_fullNames);
        intent.putExtra("username", user_name);
        intent.putExtra("email", user_email);
        intent.putExtra("password", user_password);


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


    /*
    Validation functions
     */

    private boolean validateFullName() {
        String val = Objects.requireNonNull(fullName.getEditText()).getText().toString().trim();
//        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Exceeds 20 characters!");
            return false;
        } else if (!val.matches(checkSpaces)) {
            username.setError("No spaces allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid email format!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
//        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("At least 4 characters required!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}
