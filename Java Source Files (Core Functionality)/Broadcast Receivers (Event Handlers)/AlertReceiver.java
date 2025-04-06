package com.example.phishingdetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.view.WindowManager;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setTitle("⚠️ Phishing Alert")
                .setMessage("Potential phishing link detected:\n" + url)
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        dialog.show();
    }
}
