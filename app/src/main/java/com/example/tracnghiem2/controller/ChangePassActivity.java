package com.example.tracnghiem2.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.admin.AdminActivity;
import com.example.tracnghiem2.controller.user.UserActivity;
import com.example.tracnghiem2.model.User;

public class ChangePassActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView btnDoiMatKhau;

    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private EditText edtNhapLaiMatKhauMoi;
    private EditText edtMatKhauMoi;

    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        //
        anhXa();
        //
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        edtTaiKhoan.setText(user_name);
        //
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    String pass = edtMatKhauMoi.getText().toString().trim();

                    if (changePass(user_name, pass)) {
                        Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUserByName(user_name).getRole() == 1){
                    Intent intent = new Intent(ChangePassActivity.this, AdminActivity.class);
                    intent.putExtra("user", user_name);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ChangePassActivity.this, UserActivity.class);
                    intent.putExtra("user", user_name);
                    startActivity(intent);
                }
            }
        });

    }

    private void anhXa() {
        btnBack = findViewById(R.id.btnBack);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = findViewById(R.id.edtNhapLaiMatKhauMoi);
    }

    private User getUserByName(String user_name){
        Database db = new Database(this);
        return db.getUserByName(user_name);
    }

    private boolean changePass(String user_name, String pass){
        Database db = new Database(this);
        return db.changePass(user_name, pass);
    }
    private boolean validateInput() {
        Database db = new Database(this);
        User user = db.getUserByNameAndPass(edtTaiKhoan.getText().toString().trim(), edtMatKhau.getText().toString().trim());
        if (edtTaiKhoan.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "Chưa nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMatKhauMoi.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "Chưa nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtNhapLaiMatKhauMoi.getText().toString().trim().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "Chưa nhập lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edtNhapLaiMatKhauMoi.getText().toString().trim().equalsIgnoreCase(edtMatKhauMoi.getText().toString().trim())) {
            Toast.makeText(ChangePassActivity.this, "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user.getName() == null) {
            Toast.makeText(ChangePassActivity.this, "Tài khoản hoặc mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}