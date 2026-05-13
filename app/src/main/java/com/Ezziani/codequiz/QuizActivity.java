package com.Ezziani.codequiz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion, tvCode, tvQuestionNumber, tvScore, tvTimer;
    Button btnA, btnB, btnC, btnD;
    ProgressBar progressBar, timerBar;

    ArrayList<Question> questions;
    int currentIndex = 0;
    int score = 0;
    String selectedLanguage, selectedDifficulty;
    CountDownTimer countDownTimer;
    static final int TIMER_SECONDS = 15;

    // Stores the correct answer letter after shuffling
    String currentCorrectAnswer;

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

        tvQuestion = findViewById(R.id.tvQuestion);
        tvCode = findViewById(R.id.tvCode);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        progressBar = findViewById(R.id.progressBar);
        timerBar = findViewById(R.id.timerBar);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        questions = QuestionBank.getQuestions(selectedLanguage, selectedDifficulty);
        Collections.shuffle(questions);

        progressBar.setMax(questions.size());

        showQuestion();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (countDownTimer != null) countDownTimer.cancel();
        finish();
        return true;
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

        // Build shuffled options list
        ArrayList<String> options = new ArrayList<>();
        options.add(q.getOptionA());
        options.add(q.getOptionB());
        options.add(q.getOptionC());
        options.add(q.getOptionD());

        // Remember the correct answer text before shuffling
        String correctText;
        switch (q.getCorrectAnswer()) {
            case "A": correctText = q.getOptionA(); break;
            case "B": correctText = q.getOptionB(); break;
            case "C": correctText = q.getOptionC(); break;
            default:  correctText = q.getOptionD(); break;
        }

        // Shuffle options
        Collections.shuffle(options);

        // Assign shuffled options to buttons
        btnA.setText(options.get(0));
        btnB.setText(options.get(1));
        btnC.setText(options.get(2));
        btnD.setText(options.get(3));

        // Find which button now has the correct answer
        if (options.get(0).equals(correctText)) currentCorrectAnswer = "A";
        else if (options.get(1).equals(correctText)) currentCorrectAnswer = "B";
        else if (options.get(2).equals(correctText)) currentCorrectAnswer = "C";
        else currentCorrectAnswer = "D";

        ObjectAnimator.ofInt(progressBar, "progress", currentIndex)
                .setDuration(300)
                .start();

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
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTimer.setText("⏱ " + secondsLeft);
                timerBar.setProgress(secondsLeft);

                if (secondsLeft <= 5) {
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
        int color = ContextCompat.getColor(this, colorRes);
        timerBar.setProgressTintList(ColorStateList.valueOf(color));
    }

    private void setButtonColor(Button btn, int colorRes) {
        btn.setBackgroundColor(ContextCompat.getColor(this, colorRes));
    }

    private Button getButton(String option) {
        switch (option) {
            case "A": return btnA;
            case "B": return btnB;
            case "C": return btnC;
            default: return btnD;
        }
    }

    private void disableButtons() {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
    }

    private void resetButtons() {
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
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