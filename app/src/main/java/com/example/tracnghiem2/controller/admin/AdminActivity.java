package com.example.tracnghiem2.controller.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.ChangePassActivity;
import com.example.tracnghiem2.controller.LoginActivity;

public class AdminActivity extends AppCompatActivity {

    private TextView txtUserName;
    private TextView btnTaiKhoan;
    private TextView btnDangXuat;
    private TextView btnCauHoi;
    private TextView btnChuDe;
    private TextView btnDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //
        anhXa();
        //
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);
        //
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //
        btnCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MenuQuestionActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        //
        btnChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MenuCategoryActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        //
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ChangePassActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        //
        btnTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });


    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        btnCauHoi = findViewById(R.id.btnCauHoi);
        btnChuDe = findViewById(R.id.btnChuDe);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnTaiKhoan = findViewById(R.id.btnTaiKhoan);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
    }
}