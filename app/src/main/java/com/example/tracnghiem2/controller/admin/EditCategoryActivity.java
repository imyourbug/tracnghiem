package com.example.tracnghiem2.controller.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.LoginActivity;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Question;

import java.util.List;

public class EditCategoryActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView btnDangXuat;
    private TextView btnHome;
    private EditText edtNameCategory;
    private ImageButton btnEditCategory;
    private ImageButton btnBackCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        //
        anhXa();
        //load câu hỏi cần sửa và tên người đăng nhập
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user");
        int id_category = intent.getIntExtra("id_category", 0);
        txtUserName.setText("Xin chào " + user_name);
        //
        Category category = getCategoryById(id_category);
        edtNameCategory.setText(category.getName());
        //
        btnBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCategoryActivity.this, MenuCategoryActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCategoryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Category category = new Category();
                    category.setId(id_category);
                    category.setName(edtNameCategory.getText().toString().trim());
                    if (updateCategory(category)) {
                        Toast.makeText(EditCategoryActivity.this, "Cập nhật chủ đề thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(EditCategoryActivity.this, "Cập nhật chủ đề không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCategoryActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

    }

    private boolean updateCategory(Category category) {
        Database db = new Database(this);
        return db.updateCategory(category);
    }

    private Category getCategoryById(int id) {
        Database db = new Database(this);
        return db.getCategoryById(id);
    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnEditCategory = findViewById(R.id.btnEditCategory);
        edtNameCategory = findViewById(R.id.edtNameCategory);
        btnBackCategory = findViewById(R.id.btnBackCategory);
        btnHome = findViewById(R.id.btnHome);
    }

    private boolean validateInput() {
        if (edtNameCategory.getText().toString().trim().isEmpty()) {
            Toast.makeText(EditCategoryActivity.this, "Chưa nhập tên chủ đề!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
