package com.Ezziani.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LanguageActivity extends AppCompatActivity {

    Button btnJava, btnPython, btnC, btnCpp, btnJavaScript, btnSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Choose Language");
        }

        btnJava = findViewById(R.id.btnJava);
        btnPython = findViewById(R.id.btnPython);
        btnC = findViewById(R.id.btnC);
        btnCpp = findViewById(R.id.btnCpp);
        btnJavaScript = findViewById(R.id.btnJavaScript);
        btnSQL = findViewById(R.id.btnSQL);

        btnJava.setOnClickListener(v -> goToDifficulty("Java"));
        btnPython.setOnClickListener(v -> goToDifficulty("Python"));
        btnC.setOnClickListener(v -> goToDifficulty("C"));
        btnCpp.setOnClickListener(v -> goToDifficulty("C++"));
        btnJavaScript.setOnClickListener(v -> goToDifficulty("JavaScript"));
        btnSQL.setOnClickListener(v -> goToDifficulty("SQL"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void goToDifficulty(String language) {
        Intent intent = new Intent(LanguageActivity.this, DifficultyActivity.class);
        intent.putExtra("language", language);
        startActivity(intent);
    }
}