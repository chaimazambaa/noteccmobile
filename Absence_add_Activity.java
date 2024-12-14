package com.example.mobileapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Absence_add_Activity extends AppCompatActivity {

    private EditText editTextFullName, editTextDate, editTextClass, editTextAgent;
    private Button saveButton;

    private FirebaseAuth authProfile;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_add);

        // Initialize Firebase Auth and Firestore
        authProfile = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Reference UI elements
        editTextFullName = findViewById(R.id.editText_full_name);
        editTextDate = findViewById(R.id.editText_date);
        editTextClass = findViewById(R.id.editText_class_ID);
        editTextAgent = findViewById(R.id.editText_agent);
        saveButton = findViewById(R.id.saveButton);

        // Date Picker for Date Field
        editTextDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, day1) -> {
                String selectedDate = day1 + "/" + (month1 + 1) + "/" + year1;
                editTextDate.setText(selectedDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Save button action
        saveButton.setOnClickListener(v -> saveDataToFirestore());
    }

    private void saveDataToFirestore() {
        String fullName = editTextFullName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String classId = editTextClass.getText().toString().trim();
        String agent = editTextAgent.getText().toString().trim();

        if (fullName.isEmpty() || date.isEmpty() || classId.isEmpty() || agent.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            Map<String, Object> absenceData = new HashMap<>();
            absenceData.put("fullName", fullName);
            absenceData.put("date", date);
            absenceData.put("classId", classId);
            absenceData.put("agent", agent);
            absenceData.put("userId", userId);

            firestore.collection("Absences")
                    .add(absenceData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Absence saved successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextFullName.setText("");
        editTextDate.setText("");
        editTextClass.setText("");
        editTextAgent.setText("");
    }
}
