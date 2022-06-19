package com.example.tracnghiem2.controller.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.ChangePassActivity;
import com.example.tracnghiem2.controller.LoginActivity;

public class UserActivity extends AppCompatActivity {
    private TextView btnReady;
    private TextView btnGuide;
    private TextView btnHistory;
    private TextView btnRank;
    private TextView btnDoiMatKhau;
    private TextView txtUserName;
    private TextView btnDangXuat;


    private String user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //
        anhXa();
        //
        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ChangePassActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RankActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, HistoryActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btnReady = findViewById(R.id.btnReady);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnGuide = findViewById(R.id.btnGuide);
        btnHistory = findViewById(R.id.btnHistory);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnRank = findViewById(R.id.btnRank);
        txtUserName = findViewById(R.id.txtUserName);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText(user_name);

        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(300);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        btnReady.startAnimation(mAnimation);
    }
}
