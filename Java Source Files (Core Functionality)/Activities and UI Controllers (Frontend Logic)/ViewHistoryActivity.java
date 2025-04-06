package com.example.phishingdetector;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button clearHistoryButton;
    private HistoryAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);
        db = new DatabaseHelper(this);

        List<UrlRecord> historyList = db.getAllRecords();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);

        clearHistoryButton.setOnClickListener(v -> {
            db.clearAllRecords();
            Toast.makeText(this, "History cleared!", Toast.LENGTH_SHORT).show();
            adapter.updateData(db.getAllRecords());
        });
    }
}
