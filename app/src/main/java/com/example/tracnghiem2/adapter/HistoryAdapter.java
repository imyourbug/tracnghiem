package com.example.tracnghiem2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tracnghiem2.R;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Score;
import com.example.tracnghiem2.model.User;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private ArrayList<Score> listScore;
    private ArrayList<Category> listCategories;

    public HistoryAdapter(ArrayList<Score> listScore, ArrayList<Category> listCategories) {
        this.listScore = listScore;
        this.listCategories = listCategories;
    }

    @Override
    public int getCount() {

        return listScore.size();
    }

    @Override
    public Object getItem(int position) {
        return listScore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listScore.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewScore;
        if (convertView == null) {
            viewScore = View.inflate(parent.getContext(), R.layout.history_view, null);
        } else viewScore = convertView;

        //Bind dữ liệu phần tử vào View
        Score score = (Score) getItem(position);
        ((TextView) viewScore.findViewById(R.id.rank)).setText(String.format("%d", position + 1));
        ((TextView) viewScore.findViewById(R.id.score)).setText(String.format("%d", score.getScore()));
        ((TextView) viewScore.findViewById(R.id.name_category)).setText(String.format("%s", getNameCategory(score.getId_category())));
        ((TextView) viewScore.findViewById(R.id.date)).setText(String.format("%s", score.getDate()));

        return viewScore;
    }

    private String getNameCategory(int id) {
        for (Category c : listCategories
        ) {
            if (c.getId() == id) return c.getName();
        }
        return "";
    }

}
