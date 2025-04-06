package com.example.phishingdetector;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdBlocker extends WebViewClient {

    private static final String[] adHosts = {"ads.example.com", "tracker.example.net"};

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        for (String host : adHosts) {
            if (url.contains(host)) return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }
}
