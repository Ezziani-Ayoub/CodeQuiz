package com.Ezziani.codequiz;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    TextView tvEmpty;
    Button btnClear;
    DBHelper dbHelper;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(this);
        userEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getEmail()
                : "guest";

        listView = findViewById(R.id.listView);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnClear = findViewById(R.id.btnClear);

        loadHistory();

        btnClear.setOnClickListener(v -> {
            dbHelper.clearHistory(userEmail);
            Toast.makeText(this, "History cleared!", Toast.LENGTH_SHORT).show();
            loadHistory();
        });
    }

    private void loadHistory() {
        ArrayList<String> history = dbHelper.getHistory(userEmail);
        if (history.isEmpty()) {
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            listView.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            listView.setVisibility(android.view.View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, history);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }
}