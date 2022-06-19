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
import com.example.tracnghiem2.model.User;

import java.util.List;

public class EditQuestionActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView btnDangXuat;
    private TextView btnHome;
    private Spinner spCategory;
    private ImageButton btnEditQuestion;
    private ImageButton btnBackQuestion;
    private EditText edtNameQuestion;
    private EditText edtOption1;
    private EditText edtOption2;
    private EditText edtOption3;
    private EditText edtOption4;
    private EditText edtAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        //
        anhXa();
        loadCategories();
        //load câu hỏi cần sửa và tên người đăng nhập
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user");
        int id_question = intent.getIntExtra("id_question", 0);
        txtUserName.setText("Xin chào " + user_name);
        Question q = getQuestionById(id_question);
        //
        edtNameQuestion.setText(q.getQuestion());
        edtAnswer.setText(String.valueOf(q.getAnswer()));
        edtOption1.setText(removeLetter(q.getOption1()));
        edtOption2.setText(removeLetter(q.getOption2()));
        edtOption3.setText(removeLetter(q.getOption3()));
        edtOption4.setText(removeLetter(q.getOption4()));
//
        int id_cate = q.getId_category();
        for (int i = 0; i < spCategory.getCount(); i++) {
            Category category = (Category) spCategory.getItemAtPosition(i);
            if (id_cate == category.getId()) {
                spCategory.setSelection(i);
            }
        }
        //
        btnBackQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditQuestionActivity.this, MenuQuestionActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditQuestionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnEditQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Category cate = (Category) spCategory.getSelectedItem();
                    Question q = new Question();
                    q.setId(id_question);
                    q.setQuestion(edtNameQuestion.getText().toString().trim());
                    q.setOption1("A. " + edtOption1.getText().toString().trim());
                    q.setOption2("B. " + edtOption2.getText().toString().trim());
                    q.setOption3("C. " + edtOption3.getText().toString().trim());
                    q.setOption4("D. " + edtOption4.getText().toString().trim());
                    q.setAnswer(Integer.parseInt(edtAnswer.getText().toString()));
                    q.setId_category(cate.getId());
                    if (updateQuestion(q)) {
                        Toast.makeText(EditQuestionActivity.this, "Cập nhật câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(EditQuestionActivity.this, "Cập nhật câu hỏi không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditQuestionActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

    }

    private boolean updateQuestion(Question q) {
        Database db = new Database(this);
        return db.updateQuestion(q);
    }

    private Question getQuestionById(int id) {
        Database db = new Database(this);
        return db.getQuestionById(id);
    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        spCategory = findViewById(R.id.spCategory);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnEditQuestion = findViewById(R.id.btnEditQuestion);
        btnBackQuestion = findViewById(R.id.btnBackQuestion);
        btnHome = findViewById(R.id.btnHome);
        edtNameQuestion = findViewById(R.id.edtNameQuestion);
        edtOption1 = findViewById(R.id.edtOption1);
        edtOption2 = findViewById(R.id.edtOption2);
        edtOption3 = findViewById(R.id.edtOption3);
        edtOption4 = findViewById(R.id.edtOption4);
        edtAnswer = findViewById(R.id.edtAnswer);
    }

    private void loadCategories() {
        Database db = new Database(this);
        List<Category> listCategories = db.getListCategories(null);
        // tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategories);
        // bố cục
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn chủ đề lên spinner hiển thị
        spCategory.setAdapter(categoryArrayAdapter);
    }

    private boolean validateInput() {
        if (edtNameQuestion.getText().toString().trim().isEmpty()) {
            Toast.makeText(EditQuestionActivity.this, "Chưa nhập câu hỏi!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtOption1.getText().toString().trim().isEmpty() | edtOption2.getText().toString().trim().isEmpty()
                | edtOption3.getText().toString().trim().isEmpty() | edtOption4.getText().toString().trim().isEmpty()) {
            Toast.makeText(EditQuestionActivity.this, "Chưa nhập đủ đáp án!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtAnswer.getText().toString().trim().isEmpty()) {
            Toast.makeText(EditQuestionActivity.this, "Chưa nhập đáp án đúng!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (Integer.parseInt(edtAnswer.getText().toString().trim()) < 1 | Integer.parseInt(edtAnswer.getText().toString().trim()) > 4) {
            Toast.makeText(EditQuestionActivity.this, "Đáp án chỉ từ 1 đến 4!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private String removeLetter(String option){
        return option.substring(3, option.length());
    }
}
