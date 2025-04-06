package com.example.phishingdetector;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class FirestoreHelper {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void savePhishingLink(String link) {
        Map<String, Object> data = new HashMap<>();
        data.put("link", link);
        data.put("timestamp", System.currentTimeMillis());

        db.collection("phishing_links").add(data);
    }
}
