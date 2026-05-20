package com.Ezziani.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnProfile, btnHistory;
    TextView tvWelcome, tvLevel;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnStart = findViewById(R.id.btnStart);
        btnProfile = findViewById(R.id.btnProfile);
        btnHistory = findViewById(R.id.btnHistory);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvLevel = findViewById(R.id.tvLevel);

        loadUserData();

        btnStart.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LanguageActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class)));

        Button btnAvatar = findViewById(R.id.btnAvatar);
        btnAvatar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AvatarActivity.class)));
    }

    private void loadUserData() {
        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        tvWelcome.setText("Hello, " + doc.getString("username") + "! 👋");
                        tvLevel.setText("Level: " + doc.getString("level"));
                    }
                });
    }
}