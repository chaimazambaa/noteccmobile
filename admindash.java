package com.example.projetmobile;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class AdminStat extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView absencesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activer les marges dynamiques
        setContentView(R.layout.activity_admin_stat);

        absencesTextView = findViewById(R.id.absencesTextView);

        // Appliquer les marges en fonction des insets du système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        fetchAbsences();
    }

    private void fetchAbsences() {
        CollectionReference absencesRef = db.collection("Absences");

        absencesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Integer> absencesCount = new HashMap<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String classId = document.getString("classId"); // Récupérer l'ID de classe
                    if (classId != null) {
                        absencesCount.put(classId, absencesCount.getOrDefault(classId, 0) + 1);
                    }
                }

                displayAbsences(absencesCount);
            } else {
                absencesTextView.setText("Erreur lors de la récupération des données.");
            }
        });
    }

    private void displayAbsences(Map<String, Integer> absencesCount) {
        StringBuilder absencesText = new StringBuilder();

        for (Map.Entry<String, Integer> entry : absencesCount.entrySet()) {
            absencesText.append("ID Classe ").append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append(" absences\n");
        }

        absencesTextView.setText(absencesText.toString());
    }
}
