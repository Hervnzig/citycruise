package com.example.mycityguideapp.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mycityguideapp.Databases.SessionManager;
import com.example.mycityguideapp.LocationOwner.RetailerDashboard;
import com.example.mycityguideapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    ImageView loginBackBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    CheckBox rememberMe;
    TextInputEditText login_password_ET, login_phone_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        //Hooks
        loginBackBtn = findViewById(R.id.login_back_btn);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.progress_bar);
        rememberMe = findViewById(R.id.remember_me);
        login_phone_ET = findViewById(R.id.login_phone_number_edit_text);
        login_password_ET = findViewById(R.id.login_password_edit_text);

        // check whether phone number and password is already saved in shared preferences or not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBER_ME_SESSION);
        if (sessionManager.checkLogin()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            login_phone_ET.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PHONE_NUMBER));
            login_password_ET.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PASSWORD));
        }

    }

    public void letUserLoggedIn(View view) {

        if (!isConnected(this)) {
            showCustomDialog();
        }

//        // Validate phone and password
//        if (!validateFields()) {
//            return;
//        }

        progressbar.setVisibility(View.VISIBLE);
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        if (rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBER_ME_SESSION);
            sessionManager.createRememberMeSession(_phoneNumber, _password);
        }

        // Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullName = dataSnapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _username = dataSnapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                        String _email = dataSnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _password = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        String _dob = dataSnapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                        String _gender = dataSnapshot.child(_completePhoneNumber).child("gender").getValue(String.class);


                        // Create a session
                        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USER_SESSION);
                        sessionManager.createLoginSession(_fullName, _username, _email, _phoneNo, _password, _gender, _dob);
                        startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));

                        Toast.makeText(Login.this, "Hello" + _fullName + "\n" + "\n" + _username +
                                _email + "\n" + _phoneNo + "\n" + _gender + "\n" + _dob, Toast.LENGTH_SHORT).show();


                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*
    check connection status
     */
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });
    }

    //  Validation
    private boolean validateFields() {
        String val = password.getEditText().getText().toString().trim();
        String phoneNbr = phoneNumber.getEditText().getText().toString().trim();

        if (phoneNbr.isEmpty()) {
            phoneNumber.setError("Field can not be empty");
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void callForgetPassword(View v){
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }
}
