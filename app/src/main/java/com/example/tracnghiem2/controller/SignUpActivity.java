package com.example.tracnghiem2.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.admin.AddUserActivity;
import com.example.tracnghiem2.model.User;

public class SignUpActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView btnDangKy;
    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private EditText edtNhapLaiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //
        anhXa();
        //
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    User user = new User();
                    user.setName(edtTaiKhoan.getText().toString());
                    user.setPassword(edtMatKhau.getText().toString());
                    user.setRole(0);
//                    Toast.makeText(SignUpActivity.this, "TK" + , Toast.LENGTH_SHORT).show();
                    if (signUp(user)) {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SignUpActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean signUp(User user) {
        Database db = new Database(this);
        return db.signUp(user);
    }

    private void anhXa() {
        btnBack = findViewById(R.id.btnBack);
        btnDangKy = findViewById(R.id.btnDangKy);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNhapLaiMatKhau = findViewById(R.id.edtNhapLaiMatKhau);
    }

    private boolean validateInput() {
        if (edtTaiKhoan.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtNhapLaiMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edtNhapLaiMatKhau.getText().toString().trim().equalsIgnoreCase(edtMatKhau.getText().toString().trim())) {
            Toast.makeText(SignUpActivity.this, "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkExistUser(edtTaiKhoan.getText().toString().trim())) {
            Toast.makeText(SignUpActivity.this, "Tên tài khoản đã được sử dụng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkExistUser(String name) {
        Database db = new Database(this);
        if (db.getUserByName(name).getName() != null)
            return false;
        return true;
    }

}