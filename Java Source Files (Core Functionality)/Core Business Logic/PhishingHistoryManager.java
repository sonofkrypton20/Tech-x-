package com.example.phishingdetector;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class PhishingHistoryManager {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void logPhishingLink(String url) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("url", url);
        entry.put("timestamp", System.currentTimeMillis());

        db.collection("phishing_logs")
                .add(entry)
                .addOnSuccessListener(docRef -> {})
                .addOnFailureListener(e -> {});
    }
}
