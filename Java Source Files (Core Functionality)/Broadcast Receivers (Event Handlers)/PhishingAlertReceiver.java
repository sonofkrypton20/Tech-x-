package com.example.phishingdetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PhishingAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        Toast.makeText(context, "⚠️ Phishing Link Detected: " + url, Toast.LENGTH_LONG).show();
    }
}
