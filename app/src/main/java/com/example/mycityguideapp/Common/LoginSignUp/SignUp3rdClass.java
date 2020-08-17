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
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mycityguideapp.R;
import com.example.mycityguideapp.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {

    // Variables
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);

    }

    public void callVerifyOTPScreen(View view) {

        // Variable fields
//        if (!validPhoneNumber()){
//            return;
//        }

        // Get all the values passed from previous screens using intent
        String _user_fullNames = getIntent().getStringExtra("full_name");
        String _user_name = getIntent().getStringExtra("username");
        String _user_email = getIntent().getStringExtra("email");
        String _user_password = getIntent().getStringExtra("password");
        String _user_gender = getIntent().getStringExtra("gender");
        String _user_date = getIntent().getStringExtra("date");

        String _getUserEnteredPhoneNumber  = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        // Pass all the data from the previous 3 activities to the OTP activity
        intent.putExtra("full_name", _user_fullNames);
        intent.putExtra("username", _user_name);
        intent.putExtra("email", _user_email);
        intent.putExtra("password", _user_password);
        intent.putExtra("gender", _user_gender);
        intent.putExtra("date", _user_date);
        intent.putExtra("phoneNo", _phoneNo);

        // Add transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View , String>(scrollView, "transition_OTP_screen");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {

            startActivity(intent);
        }
    }

    public boolean validPhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
//        String checking = "^\\+(?:[0-9]?){6,14}[0-9]$";
        String checkPhone = "^" +                //  Assert position at the beginning of the string
                "\\+" +                         //  Match a literal "+" character.
                "(?:" +                         // Group but don’t capture:
                "[0-9]" +                       // Match a digit from 0-9
                "?)" +                          // between zero and one time.
                "{1,3}" +
                ".[0-9]" +
                "{4,14}" +
                "(?:x.+)?" +
                "$";

        if (val.isEmpty()) {
            phoneNumber.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPhone)) {
            phoneNumber.setError("Invalid phone number format");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

}
