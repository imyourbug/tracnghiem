package com.example.tracnghiem2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tracnghiem2.Database;
import com.example.tracnghiem2.R;
import com.example.tracnghiem2.controller.admin.MenuCategoryActivity;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Question;

import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {

    final ArrayList<Question> listQuestion;private ArrayList<Category> listCategories;

    public QuestionAdapter(ArrayList<Question> listQuestion,ArrayList<Category> listCategories) {
        this.listQuestion = listQuestion;
        this.listCategories = listCategories;
    }

    @Override
    public int getCount() {
        return listQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return listQuestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listQuestion.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewQuestion;
        if (convertView == null) {
            viewQuestion = View.inflate(parent.getContext(), R.layout.question_view, null);
        } else viewQuestion = convertView;

        //Bind dữ liệu phần tử vào View
        Question question = (Question) getItem(position);
        ((TextView) viewQuestion.findViewById(R.id.id_question)).setText(String.format("%d", position + 1));
        ((TextView) viewQuestion.findViewById(R.id.name_question)).setText(String.format("%s", question.getQuestion()));
        ((TextView) viewQuestion.findViewById(R.id.name_category)).setText(String.format("%s", getNameCategory(question.getId_category())));

//        ((TextView) viewQuestion.findViewById(R.id.name_category)).setText(String.format("%s", getNameCategory(question.getId_category())));


        return viewQuestion;
    }
    private String getNameCategory(int id) {
        for (Category c : listCategories
        ) {
            if (c.getId() == id) return c.getName();
        }
        return "";
    }
}
