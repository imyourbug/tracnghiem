package com.example.tracnghiem2.controller.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.adapter.ScoreAdapter;
import com.example.tracnghiem2.controller.LoginActivity;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Score;
import com.example.tracnghiem2.model.User;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    private TextView btnDangXuat;
    private TextView btnHome;
    private TextView txtUserName;
    private Spinner spCategory;
    private ListView lvScore;

    private ArrayList<Score> listScore;
    private ArrayList<Category> listCategories;
    private ArrayList<User> listUsers;

    private ScoreAdapter scoreAdapter;

    private String user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        //
        anhXa();
        //
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadRank();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, UserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnHome = findViewById(R.id.btnHome);
        txtUserName = findViewById(R.id.txtUserName);
        spCategory = findViewById(R.id.spCategory);
        lvScore = findViewById(R.id.lvScore);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText(user_name);

        loadCategories();
        loadRank();

    }

    private void loadRank() {
        Category category = (Category) spCategory.getSelectedItem();

        Database db = new Database(this);
        listScore = db.getListScore(category.getId(), null);
        listCategories = db.getListCategories(null);
        listUsers = db.getListUsers(null);

        scoreAdapter = new ScoreAdapter(listScore, listCategories, listUsers);
        lvScore.setAdapter(scoreAdapter);

    }

    private void loadCategories() {
        Database db = new Database(this);
        ArrayList<Category> listCategories = db.getListCategories(null);
        // tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategories);
        // bố cục
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn chủ đề lên spinner hiển thị
        spCategory.setAdapter(categoryArrayAdapter);
    }
}
