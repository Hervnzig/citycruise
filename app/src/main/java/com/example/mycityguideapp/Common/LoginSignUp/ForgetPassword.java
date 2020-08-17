package com.example.mycityguideapp.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.mycityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNbr;
    RelativeLayout progressBar;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phoneNbr = findViewById(R.id.forget_phone);
        countryCodePicker = findViewById(R.id.forget_country_code_picker);
        progressBar = findViewById(R.id.forget_progress_bar);
    }

    public void VerifyPhoneNumber(View view){

        progressBar.setVisibility(View.VISIBLE);
        String _phoneNbr = phoneNbr.getEditText().getText().toString().trim();

        if (_phoneNbr.charAt(0) == '0') {
            _phoneNbr = _phoneNbr.substring(1);
        }
        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNbr;

        // Verify in database
        Query verifyNumber = FirebaseDatabase.getInstance()
                .getReference("Users").orderByChild("phoneNo")
                .equalTo(_completePhoneNumber);

        verifyNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                if (snapshot.exists()){
                    phoneNbr.setError(null);
                    phoneNbr.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDo", "updateData");
                    startActivity(intent);
                    finish();

                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.GONE);
                    phoneNbr.setError("No such User!");
                    phoneNbr.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
