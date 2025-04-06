package com.example.phishingdetector;

public class UrlRecord {
    private int id;
    private String url;
    private String status;
    private String timestamp;

    public UrlRecord() {}

    public UrlRecord(String url, String status, String timestamp) {
        this.url = url;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
