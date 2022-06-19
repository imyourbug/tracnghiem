package com.example.tracnghiem2.controller.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.adapter.CategoryAdapter;
import com.example.tracnghiem2.adapter.QuestionAdapter;
import com.example.tracnghiem2.controller.LoginActivity;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView btnDangXuat;
    private TextView btnHome;
    private ImageButton btnAddCategory;
    private TextView btnClear;
    private EditText edtSearch;
    // khai báo listView
    private ListView lvCategory;
    private ArrayList<Category> listCategories;
    private CategoryAdapter categoryAdapter;
    private String user_name;

    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_menu_category);
        //
        anhXa();
        loadCategories(null);
        //
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);


        //
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCategoryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCategoryActivity.this, AddCategoryActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        //sự kiện nhấn vào item listView
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currTime = System.currentTimeMillis();
                if (currTime - mLastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
                    onItemDoubleClick(parent, view, position, id);
                }
                mLastClickTime = currTime;
            }

            public void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                int id_category = listCategories.get(position).getId();
                Intent intent = new Intent(MenuCategoryActivity.this, EditCategoryActivity.class);
                intent.putExtra("user", user_name);
                intent.putExtra("id_category", id_category);
                startActivity(intent);
            }
        });
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogDelete(position);
                return false;
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCategoryActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = edtSearch.getText().toString().trim();
                if (!search.equalsIgnoreCase("")) {
                    loadCategories(search);
                } else {
                    loadCategories(null);
                }
                if (edtSearch.getText().toString().equalsIgnoreCase("")) {
                    btnClear.setBackgroundResource(R.drawable.search);
                } else {
                    btnClear.setBackgroundResource(R.drawable.clear);
                    btnClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtSearch.setText("");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void DialogDelete(int position) {
        //tạo đối tượng dialog
        Dialog dialog = new Dialog(this);

        //nạp layout vào dialog
        dialog.setContentView(R.layout.dialog_delete);

        //Tăt click ra ngoài chỉ click vào NO là đóng
        dialog.setCanceledOnTouchOutside(false);

        TextView btnYes = dialog.findViewById(R.id.btnYes);
        TextView btnNo = dialog.findViewById(R.id.btnNo);

        //set sự kiện click vào yes
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_category = listCategories.get(position).getId();
                //xóa dữ liệu
                if (deleteCategory(id_category)) {
                    loadCategories(null);
                    dialog.cancel();
                    Toast.makeText(MenuCategoryActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MenuCategoryActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        //set sự kiện click NO
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //thực hiện dialog
        dialog.show();
    }


    private boolean deleteCategory(int id_category) {
        Database db = new Database(this);
        return db.deleteCategory(id_category);
    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        lvCategory = findViewById(R.id.lvCategory);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnHome = findViewById(R.id.btnHome);
        btnClear = findViewById(R.id.btnClear);

        edtSearch = findViewById(R.id.edtSearch);
    }

    private void loadCategories(String key) {
        Database db = new Database(this);
        listCategories = db.getListCategories(key);

        categoryAdapter = new CategoryAdapter(listCategories);

        lvCategory.setAdapter(categoryAdapter);

    }

}
