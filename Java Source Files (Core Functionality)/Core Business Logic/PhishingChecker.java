package com.example.phishingdetector;

import android.content.Intent;

public class PhishingChecker {

    public interface PhishingCallback {
        void onResult(boolean isPhishing);
    }

    public static void checkLink(String url, PhishingCallback callback) {
        SafeBrowsingAPI.checkLink(url, new SafeBrowsingAPI.SafeBrowsingCallback() {
            @Override
            public void onResponse(boolean isMalicious) {
                if (isMalicious) {
                    handlePhishingDetected(url, callback);
                } else {
                    GeminiAPI.checkLink(url, new GeminiAPI.GeminiCallback() {
                        @Override
                        public void onResponse(boolean isPhishing) {
                            if (isPhishing) {
                                handlePhishingDetected(url, callback);
                            } else {
                                callback.onResult(false);
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            callback.onResult(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String error) {
                GeminiAPI.checkLink(url, new GeminiAPI.GeminiCallback() {
                    @Override
                    public void onResponse(boolean isPhishing) {
                        if (isPhishing) {
                            handlePhishingDetected(url, callback);
                        } else {
                            callback.onResult(false);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        callback.onResult(false);
                    }
                });
            }
        });
    }

    private static void handlePhishingDetected(String url, PhishingCallback callback) {
        Intent intent = new Intent("com.example.phishingdetector.PHISHING_ALERT");
        intent.putExtra("url", url);
        AppContext.getContext().sendBroadcast(intent);
        callback.onResult(true);
    }
}
