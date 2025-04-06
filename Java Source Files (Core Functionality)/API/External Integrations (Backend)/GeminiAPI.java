package com.example.phishingdetector;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class GeminiAPI {
    private static final String GEMINI_API_URL = "https://api.gemini.com/v1/phishing/check";
    private static final String API_KEY = "AIzaSyA0DzPGnXX7Kar7-BIZiYgSMm0waJPjGhA";

    public interface GeminiCallback {
        void onResponse(boolean isPhishing);
        void onFailure(String error);
    }

    public static void checkLink(String url, GeminiCallback callback) {
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("url", url);
        } catch (Exception e) {
            callback.onFailure("Invalid JSON Request");
            return;
        }

        RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(GEMINI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    boolean isPhishing = false;
                    try {
                        isPhishing = new JSONObject(responseBody).getBoolean("phishing");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onResponse(isPhishing);
                } else {
                    callback.onFailure("Request Failed");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Network Error");
            }
        });
    }
}


