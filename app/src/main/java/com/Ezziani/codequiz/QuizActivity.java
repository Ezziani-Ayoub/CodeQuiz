package com.Ezziani.codequiz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion, tvCode, tvQuestionNumber, tvScore, tvTimer;
    Button btnA, btnB, btnC, btnD, btnVoice, btnAiHint;
    ProgressBar progressBar, timerBar;

    ArrayList<Question> questions;
    int currentIndex = 0;
    int score = 0;
    String selectedLanguage, selectedDifficulty;
    CountDownTimer countDownTimer;
    static final int TIMER_SECONDS = 15;
    String currentCorrectAnswer;

    ActivityResultLauncher<Intent> voiceLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    List<String> results = result.getData()
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (results != null && !results.isEmpty()) {
                        handleVoiceResult(results.get(0).toUpperCase().trim());
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Code Quiz");
        }

        selectedLanguage = getIntent().getStringExtra("language");
        selectedDifficulty = getIntent().getStringExtra("difficulty");

        tvQuestion       = findViewById(R.id.tvQuestion);
        tvCode           = findViewById(R.id.tvCode);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvScore          = findViewById(R.id.tvScore);
        tvTimer          = findViewById(R.id.tvTimer);
        progressBar      = findViewById(R.id.progressBar);
        timerBar         = findViewById(R.id.timerBar);
        btnA             = findViewById(R.id.btnA);
        btnB             = findViewById(R.id.btnB);
        btnC             = findViewById(R.id.btnC);
        btnD             = findViewById(R.id.btnD);
        btnVoice         = findViewById(R.id.btnVoice);
        btnAiHint        = findViewById(R.id.btnAiHint);

        questions = QuestionBank.getQuestions(selectedLanguage, selectedDifficulty);
        Collections.shuffle(questions);
        progressBar.setMax(questions.size());

        btnVoice.setOnClickListener(v -> startVoiceRecognition());

        btnAiHint.setOnClickListener(v -> {
            Question current = questions.get(currentIndex);
            Intent intent = new Intent(QuizActivity.this, AiHintActivity.class);
            intent.putExtra("code", current.getCode());
            intent.putExtra("question", current.getQuestion());
            startActivity(intent);
        });

        showQuestion();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (countDownTimer != null) countDownTimer.cancel();
        finish();
        return true;
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say A, B, C or D");
        try {
            voiceLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Voice recognition not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleVoiceResult(String spoken) {
        String answer = null;
        if (spoken.contains("A") || spoken.contains("ALPHA"))   answer = "A";
        else if (spoken.contains("B") || spoken.contains("BRAVO"))  answer = "B";
        else if (spoken.contains("C") || spoken.contains("CHARLIE")) answer = "C";
        else if (spoken.contains("D") || spoken.contains("DELTA"))   answer = "D";

        if (answer != null) {
            Toast.makeText(this, "🎤 You said: " + answer,
                    Toast.LENGTH_SHORT).show();
            checkAnswer(answer);
        } else {
            Toast.makeText(this, "Could not understand. Say A, B, C or D",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            goToResult();
            return;
        }

        Question q = questions.get(currentIndex);
        tvQuestionNumber.setText("Question " + (currentIndex + 1) + "/" + questions.size());
        tvScore.setText("Score: " + score);
        tvQuestion.setText(q.getQuestion());
        tvCode.setText(q.getCode());

        ArrayList<String> options = new ArrayList<>();
        options.add(q.getOptionA());
        options.add(q.getOptionB());
        options.add(q.getOptionC());
        options.add(q.getOptionD());

        String correctText;
        switch (q.getCorrectAnswer()) {
            case "A": correctText = q.getOptionA(); break;
            case "B": correctText = q.getOptionB(); break;
            case "C": correctText = q.getOptionC(); break;
            default:  correctText = q.getOptionD(); break;
        }

        Collections.shuffle(options);
        btnA.setText(options.get(0));
        btnB.setText(options.get(1));
        btnC.setText(options.get(2));
        btnD.setText(options.get(3));

        if (options.get(0).equals(correctText))      currentCorrectAnswer = "A";
        else if (options.get(1).equals(correctText)) currentCorrectAnswer = "B";
        else if (options.get(2).equals(correctText)) currentCorrectAnswer = "C";
        else                                          currentCorrectAnswer = "D";

        ObjectAnimator.ofInt(progressBar, "progress", currentIndex)
                .setDuration(300).start();

        resetButtons();
        startTimer();
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timerBar.setMax(TIMER_SECONDS);
        timerBar.setProgress(TIMER_SECONDS);
        setTimerColor(R.color.correct);

        countDownTimer = new CountDownTimer(TIMER_SECONDS * 1000L, 1000) {
            @Override
            public void onTick(long ms) {
                int s = (int)(ms / 1000);
                tvTimer.setText("⏱ " + s);
                timerBar.setProgress(s);
                if (s <= 5) {
                    tvTimer.setTextColor(ContextCompat.getColor(
                            QuizActivity.this, R.color.wrong));
                    setTimerColor(R.color.wrong);
                } else {
                    tvTimer.setTextColor(ContextCompat.getColor(
                            QuizActivity.this, R.color.primary));
                    setTimerColor(R.color.correct);
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("⏱ 0");
                timerBar.setProgress(0);
                disableButtons();
                setButtonColor(getButton(currentCorrectAnswer), R.color.correct);
                tvCode.postDelayed(() -> {
                    currentIndex++;
                    showQuestion();
                }, 1200);
            }
        }.start();
    }

    private void checkAnswer(String selected) {
        if (countDownTimer != null) countDownTimer.cancel();
        disableButtons();
        if (selected.equals(currentCorrectAnswer)) {
            score++;
            setButtonColor(getButton(selected), R.color.correct);
        } else {
            setButtonColor(getButton(selected), R.color.wrong);
            setButtonColor(getButton(currentCorrectAnswer), R.color.correct);
        }
        tvScore.setText("Score: " + score);
        tvCode.postDelayed(() -> {
            currentIndex++;
            showQuestion();
        }, 1200);
    }

    private void setTimerColor(int colorRes) {
        timerBar.setProgressTintList(ColorStateList.valueOf(
                ContextCompat.getColor(this, colorRes)));
    }

    private void setButtonColor(Button btn, int colorRes) {
        btn.setBackgroundColor(ContextCompat.getColor(this, colorRes));
    }

    private Button getButton(String option) {
        switch (option) {
            case "A": return btnA;
            case "B": return btnB;
            case "C": return btnC;
            default:  return btnD;
        }
    }

    private void disableButtons() {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnVoice.setEnabled(false);
        btnAiHint.setEnabled(false);
    }

    private void resetButtons() {
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
        btnVoice.setEnabled(true);
        btnAiHint.setEnabled(true);
        setButtonColor(btnA, R.color.surface);
        setButtonColor(btnB, R.color.surface);
        setButtonColor(btnC, R.color.surface);
        setButtonColor(btnD, R.color.surface);
        tvTimer.setTextColor(ContextCompat.getColor(this, R.color.primary));
        btnA.setOnClickListener(v -> checkAnswer("A"));
        btnB.setOnClickListener(v -> checkAnswer("B"));
        btnC.setOnClickListener(v -> checkAnswer("C"));
        btnD.setOnClickListener(v -> checkAnswer("D"));
    }

    private void goToResult() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        intent.putExtra("language", selectedLanguage);
        intent.putExtra("difficulty", selectedDifficulty);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}