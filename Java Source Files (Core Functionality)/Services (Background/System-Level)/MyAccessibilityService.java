package com.example.phishingdetector;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {

            CharSequence content = event.getText().toString();
            if (content != null && content.length() > 0) {
                String detectedUrl = extractUrl(content.toString());
                if (detectedUrl != null) {
                    Log.d("PhishingDetector", "🔍 Accessibility scanned: " + detectedUrl);
                    PhishingChecker.checkLink(detectedUrl, result -> {
                        if (result) {
                            Log.w("PhishingDetector", "⚠️ Phishing Link Detected (Accessibility): " + detectedUrl);
                            Utils.showAlert(this, "Phishing Link Detected!", detectedUrl);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d("PhishingDetector", "AccessibilityService Interrupted");
    }

    private String extractUrl(String text) {
        Pattern pattern = Pattern.compile("(https?://[\\w\\-\\.\\?\\=\\&/:%#]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }
}
