package com.example.phishingdetector;

import android.content.Context;
import android.widget.Toast;

public class PhishingDetector {
    public static void analyzeLink(Context context, String url) {
        GeminiAPI.checkLink(url, new GeminiAPI.GeminiCallback() {
            @Override
            public void onResponse(boolean isPhishing) {
                if (isPhishing) {
                    Toast.makeText(context, "Phishing Link Detected by Gemini API!", Toast.LENGTH_LONG).show();
                } else {
                    SafeBrowsingAPI.checkLink(url, new SafeBrowsingAPI.SafeBrowsingCallback() {
                        @Override
                        public void onResponse(boolean isMalicious) {
                            if (isMalicious) {
                                Toast.makeText(context, "Malicious Link Detected by Google Safe Browsing!", Toast.LENGTH_LONG).show();
                            } else {
                                boolean isIDXVerified = IDXPlatform.validateIdentity(url);
                                if (!isIDXVerified) {
                                    Toast.makeText(context, "Identity Validation Failed by IDX Platform!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Link is Safe.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(context, "Safe Browsing API Error: " + error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(context, "Gemini API Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
