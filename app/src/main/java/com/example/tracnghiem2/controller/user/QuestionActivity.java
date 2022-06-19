package com.example.tracnghiem2.controller.user;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.LoginActivity;
import com.example.tracnghiem2.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;
    private TextView btnBackMainUser;
    private TextView btnHome;
    private TextView btnDangXuat;


    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    private TextView btnConfirmNext;
    private TextView txtClock;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> listQuestion;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;
    private Question currentQuestion;

    private int Score;
    private boolean answered;

    private String user_name;
    private int categoryID;
    private String categoryName;

    private MediaPlayer songTrue;
    private MediaPlayer songFalse;

    private int stateSound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        anhXa();

        Intent intent = getIntent();
        categoryID = intent.getIntExtra("categoryID", 0);
        categoryName = intent.getStringExtra("categoryName");
        user_name = intent.getStringExtra("user");

        textViewCategory.setText("Chủ đề: " + categoryName);

        Database db = new Database(this);
        listQuestion = db.getListQuestions(null, categoryID);

        questionSize = listQuestion.size();
        //đảo vị trí câu hỏi
        Collections.shuffle(listQuestion);
        // show câu hỏi + đáp án
        showNextQuestion();
        //
        btnConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() | rb2.isChecked() | rb3.isChecked() | rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuestionActivity.this, "Hãy chọn đáp án!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    stopSound();
                    showNextQuestion();
                }
            }
        });
        btnBackMainUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuestion();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuestion();
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createScore();
                stopSound();
                Intent intent = new Intent(QuestionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private void checkAnswer() {
        answered = true;
        RadioButton rdSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answer = rbGroup.indexOfChild(rdSelected) + 1;
        if (answer == currentQuestion.getAnswer() && answered == true) {
            Score += 10;
            textViewScore.setText("Điểm: " + Score);
            startSoundTrue();
        } else {
            startSoundFalse();
        }
        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getAnswer()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là: A");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là: B");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là: C");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là: D");
                break;
        }
        if (questionCounter < questionSize) {
            btnConfirmNext.setText("Câu tiếp theo");
        } else {
            btnConfirmNext.setText("Hoàn thành");
        }
        countDownTimer.cancel();
    }


    private void showNextQuestion() {
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
        //xoa chon
        rbGroup.clearCheck();
        // nếu còn câu hỏi
        if (questionCounter < questionSize) {
            currentQuestion = listQuestion.get(questionCounter);
            //
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;

            textViewQuestionCount.setText("Câu hỏi: " + questionCounter + "/" + questionSize);
            //
            answered = false;
            //
            btnConfirmNext.setText("Xác nhận");
            //
            timeLeftInMillis = 30000;
            //
            startCountDown();

        } else {
            DialogConfirm();
        }

    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMillis / 1000) / 60);
        int seconds = (int) ((timeLeftInMillis / 1000) % 60);
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
            blinkClock(0);
        } else {
            textViewCountDown.setTextColor(Color.BLACK);
            blinkClock(1);
        }
    }

    private void finishQuestion() {
        createScore();
        stopSound();
        Intent intentResult = new Intent(QuestionActivity.this, MainActivity.class);
        intentResult.putExtra("Score", Score);
        intentResult.putExtra("user", user_name);
        startActivity(intentResult);
    }

    @Override
    public void onBackPressed() {
        finishQuestion();
    }

    private void anhXa() {
        textViewCategory = findViewById(R.id.text_view_category);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewScore = findViewById(R.id.text_view_score);
        btnBackMainUser = findViewById(R.id.btnBackMainUser);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        txtClock = findViewById(R.id.txtClock);

        btnConfirmNext = findViewById(R.id.button_confirm_next);
        btnHome = findViewById(R.id.btnHome);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
    }

    private void createScore() {
        Database db = new Database(this);
        db.createScore(user_name, categoryID, Score);
    }

    private void startSoundTrue() {
        songTrue = MediaPlayer.create(QuestionActivity.this, R.raw.dung);
        songTrue.start();
    }

    private void startSoundFalse() {
        songFalse = MediaPlayer.create(QuestionActivity.this, R.raw.sai);
        songFalse.start();
    }

    private void stopSound() {
        countDownTimer.cancel();
        if (songTrue != null)
            songTrue.stop();
        if (songFalse != null)
            songFalse.stop();
    }

    private void DialogConfirm() {
        //tạo đối tượng dialog
        Dialog dialog = new Dialog(this);

        //nạp layout vào dialog
        dialog.setContentView(R.layout.dialog_score);

        //Tăt click ra ngoài chỉ click vào NO là đóng
        dialog.setCanceledOnTouchOutside(false);

        TextView btnReplay = dialog.findViewById(R.id.btnReplay);
        TextView btnBack = dialog.findViewById(R.id.btnBack);
        TextView txtTotalScore = dialog.findViewById(R.id.txtHighScore);

        //
        txtTotalScore.setText("Điểm cao " + Score);
        //set sự kiện click vào yes
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
                intent.putExtra("user", user_name);
                intent.putExtra("categoryName", categoryName);
                intent.putExtra("categoryID", categoryID);
                startActivity(intent);
            }
        });

        //set sự kiện click NO
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finishQuestion();
            }
        });
        //thực hiện dialog
        countDownTimer.cancel();
        dialog.show();
    }

    private void blinkClock(int state) {
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(300);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        if (state == 0) txtClock.startAnimation(mAnimation);
        if (state == 1) txtClock.clearAnimation();
    }
}