package com.example.phishingdetector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyNotificationListenerService extends NotificationListenerService {

    private static final String PREFS_NAME = "PhishingDetectorPrefs";
    private static final String HISTORY_KEY = "scanned_urls";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Log.d("NotificationListener", "📲 Notification from: " + packageName);

        Bundle extras = sbn.getNotification().extras;
        CharSequence text = extras.getCharSequence("android.text");
        CharSequence bigText = extras.getCharSequence("android.bigText");
        CharSequence[] textLines = extras.getCharSequenceArray("android.textLines");

        StringBuilder fullText = new StringBuilder();

        if (text != null) {
            fullText.append(text).append("\n");
            Log.d("NotificationListener", "📨 Content: " + text.toString());
        }
        if (bigText != null) fullText.append(bigText).append("\n");
        if (textLines != null) {
            for (CharSequence line : textLines) {
                fullText.append(line).append("\n");
            }
        }

        String content = fullText.toString().trim();

        if (!content.isEmpty()) {
            String detectedUrl = extractUrl(content);
            if (detectedUrl != null) {
                Log.d("PhishingDetector", "🔍 Scanned: " + detectedUrl);
                saveToHistory(detectedUrl);
                PhishingChecker.checkLink(detectedUrl, result -> {
                    if (result) {
                        Log.w("PhishingDetector", "⚠️ Phishing Link Detected: " + detectedUrl);
                        showPopup("⚠️ Phishing link detected:\n" + detectedUrl);
                    } else {
                        Log.i("PhishingDetector", "✅ Safe Link: " + detectedUrl);
                    }
                });
            }
        }
    }

    private String extractUrl(String text) {
        Pattern pattern = Pattern.compile("(https?://[\\w\\-\\.\\?\\=\\&/:%#]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    private void showPopup(String message) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show()
        );
    }

    private void saveToHistory(String url) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String existing = prefs.getString(HISTORY_KEY, "");
        String updated = existing + url + "\n";
        prefs.edit().putString(HISTORY_KEY, updated).apply();
    }
}
