package com.Ezziani.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DifficultyActivity extends AppCompatActivity {

    Button btnEasy, btnMedium, btnHard;
    TextView tvLanguage, tvMediumLock, tvHardLock;
    ProgressBar progressBar;
    String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Choose Difficulty");
        }

        selectedLanguage = getIntent().getStringExtra("language");

        tvLanguage = findViewById(R.id.tvLanguage);
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        tvMediumLock = findViewById(R.id.tvMediumLock);
        tvHardLock = findViewById(R.id.tvHardLock);
        progressBar = findViewById(R.id.progressBar);

        tvLanguage.setText("Language: " + selectedLanguage);

        // Lock all by default while loading
        btnMedium.setEnabled(false);
        btnHard.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Load unlock status from Firestore
        UserProgressManager.getProgress(selectedLanguage, (mediumUnlocked, hardUnlocked) -> {
            progressBar.setVisibility(View.GONE);

            if (mediumUnlocked) {
                btnMedium.setEnabled(true);
                tvMediumLock.setVisibility(View.GONE);
            } else {
                btnMedium.setEnabled(false);
                tvMediumLock.setVisibility(View.VISIBLE);
            }

            if (hardUnlocked) {
                btnHard.setEnabled(true);
                tvHardLock.setVisibility(View.GONE);
            } else {
                btnHard.setEnabled(false);
                tvHardLock.setVisibility(View.VISIBLE);
            }
        });

        btnEasy.setOnClickListener(v -> goToQuiz("Easy"));
        btnMedium.setOnClickListener(v -> goToQuiz("Medium"));
        btnHard.setOnClickListener(v -> goToQuiz("Hard"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void goToQuiz(String difficulty) {
        Intent intent = new Intent(DifficultyActivity.this, QuizActivity.class);
        intent.putExtra("language", selectedLanguage);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}