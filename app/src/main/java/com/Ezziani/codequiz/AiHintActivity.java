package com.Ezziani.codequiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AiHintActivity extends AppCompatActivity {

    TextView tvCode, tvHint, tvQuestion;
    Button btnGetHint;
    ProgressBar progressBar;
    ScrollView scrollView;
    String codeSnippet, question;

    // ⚠️ Paste your Gemini API key here
    private static final String API_KEY = "AIzaSyC_bXc4Wjp0jzprsdRc2VjnCKyGgv3Xwoo";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/" +
                    "gemini-2.5-flash:generateContent?key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_hint);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("🤖 AI Hint");
        }

        tvQuestion  = findViewById(R.id.tvQuestion);
        tvCode      = findViewById(R.id.tvCode);
        tvHint      = findViewById(R.id.tvHint);
        btnGetHint  = findViewById(R.id.btnGetHint);
        progressBar = findViewById(R.id.progressBar);
        scrollView  = findViewById(R.id.scrollView);

        codeSnippet = getIntent().getStringExtra("code");
        question    = getIntent().getStringExtra("question");

        tvQuestion.setText(question);
        tvCode.setText(codeSnippet);

        btnGetHint.setOnClickListener(v -> getAiHint());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getAiHint() {
        progressBar.setVisibility(View.VISIBLE);
        btnGetHint.setEnabled(false);
        tvHint.setText("Asking AI...");

        String prompt = "You are a programming tutor. A student is looking at this code:\n\n"
                + codeSnippet
                + "\n\nThe question is: " + question
                + "\n\nGive a clear, educational hint to help the student understand "
                + "WITHOUT directly revealing the answer. "
                + "Keep it under 5 sentences. Be friendly and pedagogical.";

        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);

                // Build Gemini request body
                JSONObject body = new JSONObject();
                JSONArray contents = new JSONArray();
                JSONObject content = new JSONObject();
                JSONArray parts = new JSONArray();
                JSONObject part = new JSONObject();
                part.put("text", prompt);
                parts.put(part);
                content.put("parts", parts);
                contents.put(content);
                body.put("contents", contents);

                OutputStream os = conn.getOutputStream();
                os.write(body.toString().getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();

                if (responseCode == 200) {
                    JSONObject response = new JSONObject(sb.toString());
                    String hint = response
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text");

                    runOnUiThread(() -> {
                        tvHint.setText(hint);
                        progressBar.setVisibility(View.GONE);
                        btnGetHint.setEnabled(true);
                    });
                } else {
                    runOnUiThread(() -> {
                        tvHint.setText("Error: " + sb.toString());
                        progressBar.setVisibility(View.GONE);
                        btnGetHint.setEnabled(true);
                    });
                }

            } catch (Exception e) {
                runOnUiThread(() -> {
                    tvHint.setText("Could not get AI hint. Check your internet connection.");
                    progressBar.setVisibility(View.GONE);
                    btnGetHint.setEnabled(true);
                    Toast.makeText(this, "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}