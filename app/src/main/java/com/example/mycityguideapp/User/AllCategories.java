package com.example.mycityguideapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mycityguideapp.R;

public class AllCategories extends AppCompatActivity {

    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        // Hooks
        back_btn = findViewById(R.id.back_pressed);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategories.super.onBackPressed();
            }
        });
    }
}
