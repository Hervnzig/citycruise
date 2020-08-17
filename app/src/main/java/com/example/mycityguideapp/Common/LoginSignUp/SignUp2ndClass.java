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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycityguideapp.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    // Variables
    ImageView backBtn;
    Button next, login;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    TextView titleText;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);

        // Hooks
        backBtn = findViewById(R.id.back_pressed);
        next = findViewById(R.id.sign_up_next_button);
        login = findViewById(R.id.sign_up_login_btn);
        titleText = findViewById(R.id.signUp_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }

    public void call3rdSignUpScreen(View view) {

        if (!validateGender() | !validateAge()){
            return;
        }

        // Fields from the previous activity
        String _user_fullNames = getIntent().getStringExtra("full_name");
        String _user_name = getIntent().getStringExtra("username");
        String _user_email = getIntent().getStringExtra("email");
        String _user_password = getIntent().getStringExtra("password");


        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day +"/"+month+"/"+year;


        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

        // Pass these data to the next activity
        intent.putExtra("full_name", _user_fullNames);
        intent.putExtra("username", _user_name);
        intent.putExtra("email", _user_email);
        intent.putExtra("password", _user_password);
        intent.putExtra("gender", gender);
        intent.putExtra("date", date);

        startActivity(intent);
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid <= 14) {
            Toast.makeText(this, "You are young", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
