package com.example.phishingdetector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PhishingDetectorPrefs";
    private static final String HISTORY_KEY = "scanned_urls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        setContentView(textView);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String history = prefs.getString(HISTORY_KEY, "No scanned URLs yet.");
        textView.setText(history);
    }
}
