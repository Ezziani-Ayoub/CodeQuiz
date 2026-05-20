package com.Ezziani.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class AvatarActivity extends AppCompatActivity {

    AvatarView avatarView;
    TextView tvSubtitle, tvResponse;
    Button btnSpeak;
    ProgressBar progressBar;
    TextToSpeech tts;
    boolean ttsReady = false;
    Handler blinkHandler = new Handler();

    // ⚠️ Paste your Gemini API key here
    private static final String API_KEY = "AIzaSyC_bXc4Wjp0jzprsdRc2VjnCKyGgv3Xwoo";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/" +
                    "gemini-2.5-flash:generateContent?key=" + API_KEY;

    ActivityResultLauncher<Intent> voiceLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    List<String> results = result.getData()
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (results != null && !results.isEmpty()) {
                        String spoken = results.get(0);
                        tvSubtitle.setText("You: " + spoken);
                        askAI(spoken);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("AI Assistant");
        }

        avatarView  = findViewById(R.id.avatarView);
        tvSubtitle  = findViewById(R.id.tvSubtitle);
        tvResponse  = findViewById(R.id.tvResponse);
        btnSpeak    = findViewById(R.id.btnSpeak);
        progressBar = findViewById(R.id.progressBar);

        // Init Text-to-Speech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
                ttsReady = true;
            }
        });

        startBlinking();

        btnSpeak.setOnClickListener(v -> startListening());

        tvResponse.setText("Hi! I'm CODY, your AI programming assistant. " +
                "Tap the mic and ask me anything about code!");
        avatarView.setMouthOpen(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Ask CODY anything about programming...");
        try {
            voiceLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Voice not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void askAI(String userMessage) {
        progressBar.setVisibility(View.VISIBLE);
        btnSpeak.setEnabled(false);
        avatarView.setThinking(true);
        tvResponse.setText("Thinking...");

        String prompt = "You are CODY, a friendly AI programming assistant "
                + "with a cheerful personality. Answer concisely in 2-3 sentences max. "
                + "Be encouraging and use simple language. "
                + "Question: " + userMessage;

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
                    br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                } else {
                    br = new BufferedReader(
                            new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();

                if (responseCode == 200) {
                    JSONObject response = new JSONObject(sb.toString());
                    String reply = response
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text");

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnSpeak.setEnabled(true);
                        avatarView.setThinking(false);
                        tvResponse.setText(reply);
                        tvSubtitle.setText("CODY says:");
                        speakResponse(reply);
                    });
                } else {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnSpeak.setEnabled(true);
                        avatarView.setThinking(false);
                        tvResponse.setText("Oops! Could not connect. Try again.");
                    });
                }

            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnSpeak.setEnabled(true);
                    avatarView.setThinking(false);
                    tvResponse.setText("Connection error. Check your internet.");
                    Toast.makeText(this, "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void speakResponse(String text) {
        if (!ttsReady) return;

        avatarView.setMouthOpen(true);
        avatarView.startTalking();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance");
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override public void onStart(String id) {}
            @Override public void onDone(String id) {
                runOnUiThread(() -> {
                    avatarView.stopTalking();
                    avatarView.setMouthOpen(false);
                });
            }
            @Override public void onError(String id) {
                runOnUiThread(() -> {
                    avatarView.stopTalking();
                    avatarView.setMouthOpen(false);
                });
            }
        });
    }

    private void startBlinking() {
        blinkHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                avatarView.blink();
                long delay = 2000 + (long)(Math.random() * 3000);
                blinkHandler.postDelayed(this, delay);
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        blinkHandler.removeCallbacksAndMessages(null);
    }
}