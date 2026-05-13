package com.Ezziani.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView tvEmoji, tvResult, tvScore, tvMessage, tvPercent, tvLevelUp;
    Button btnRetry, btnHome, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 10);
        String language = getIntent().getStringExtra("language");
        String difficulty = getIntent().getStringExtra("difficulty");

        tvEmoji = findViewById(R.id.tvEmoji);
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        tvPercent = findViewById(R.id.tvPercent);
        tvLevelUp = findViewById(R.id.tvLevelUp);
        btnRetry = findViewById(R.id.btnRetry);
        btnHome = findViewById(R.id.btnHome);
        btnHistory = findViewById(R.id.btnHistory);

        // Save to SQLite
        String userEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getEmail()
                : "guest";
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm",
                Locale.getDefault()).format(new Date());
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.insertHistory(userEmail, language, difficulty, score, total, date);

        // Save to Firestore and update level
        UserProgressManager.saveProgress(language, difficulty, score, newLevel -> {
            runOnUiThread(() -> {
                tvLevelUp.setVisibility(View.VISIBLE);
                tvLevelUp.setText("🏅 Your level: " + newLevel);
            });
        });

        // Calculate percentage
        int percent = (score * 100) / total;
        String grade;
        if (percent == 100) grade = "A+";
        else if (percent >= 80) grade = "A";
        else if (percent >= 70) grade = "B";
        else if (percent >= 60) grade = "C";
        else grade = "F";

        tvScore.setText(score + " / " + total);
        tvPercent.setText(percent + "% — Grade: " + grade);

        if (score >= 8) {
            tvEmoji.setText("🏆");
            tvResult.setText(score == 10 ? "Perfect!" : "Unlocked Next Level!");
            tvMessage.setText(score == 10
                    ? "Flawless victory! You're a coding master!"
                    : "Great job! You unlocked the next difficulty! 🔓");
        } else if (percent >= 60) {
            tvEmoji.setText("👍");
            tvResult.setText("Good Job!");
            tvMessage.setText("Score 8/10 to unlock the next difficulty!");
        } else {
            tvEmoji.setText("😅");
            tvResult.setText("Keep Trying!");
            tvMessage.setText("Review the concepts and try again!");
        }

        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            intent.putExtra("language", language);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(ResultActivity.this, HistoryActivity.class)));
    }
}