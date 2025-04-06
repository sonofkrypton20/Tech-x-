package com.example.phishingdetector;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class SafeBrowsingAPI {

    private static final String SAFE_BROWSING_API_URL = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyAhh7ZR_fx8VdJsseIhr1BGG9sVzLz56cA";

    public interface SafeBrowsingCallback {
        void onResponse(boolean isMalicious);
        void onFailure(String error);
    }

    public static void checkLink(String url, SafeBrowsingCallback callback) {
        OkHttpClient client = new OkHttpClient();

        JSONObject requestJson = new JSONObject();
        try {
            requestJson.put("client", new JSONObject()
                    .put("clientId", "yourcompanyname")
                    .put("clientVersion", "1.0"));
            requestJson.put("threatInfo", new JSONObject()
                    .put("threatTypes", new String[]{"MALWARE", "SOCIAL_ENGINEERING"})
                    .put("platformTypes", new String[]{"ANDROID"})
                    .put("threatEntryTypes", new String[]{"URL"})
                    .put("threatEntries", new org.json.JSONArray()
                            .put(new JSONObject().put("url", url))));
        } catch (Exception e) {
            callback.onFailure("Invalid JSON");
            return;
        }

        RequestBody body = RequestBody.create(requestJson.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(SAFE_BROWSING_API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseBody = response.body().string();
                    callback.onResponse(!new JSONObject(responseBody).isNull("matches"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Network Error");
            }
        });
    }
}


