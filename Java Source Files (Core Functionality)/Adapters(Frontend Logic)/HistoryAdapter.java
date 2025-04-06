package com.example.phishingdetector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<UrlRecord> records;

    public HistoryAdapter(List<UrlRecord> records) {
        this.records = records;
    }

    public void updateData(List<UrlRecord> newRecords) {
        this.records = newRecords;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlText, statusText, timeText;

        public ViewHolder(View view) {
            super(view);
            urlText = view.findViewById(R.id.urlText);
            statusText = view.findViewById(R.id.statusText);
            timeText = view.findViewById(R.id.timeText);
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        UrlRecord record = records.get(position);
        holder.urlText.setText(record.getUrl());
        holder.statusText.setText(record.getStatus());
        holder.timeText.setText(record.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
