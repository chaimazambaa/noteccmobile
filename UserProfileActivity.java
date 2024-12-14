package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    private Button buttonAddAbsence, buttonLesAbsences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Buttons
        buttonAddAbsence = findViewById(R.id.button_add_absence);
        buttonLesAbsences = findViewById(R.id.button_les_absences);

        // Add Absence Button Click Listener
        buttonAddAbsence.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, Absence_add_Activity.class);
            startActivity(intent);
        });

        // Les Absences Button Click Listener

        buttonLesAbsences.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, AllabsencesActivity.class);
            startActivity(intent);
        });
    }
}