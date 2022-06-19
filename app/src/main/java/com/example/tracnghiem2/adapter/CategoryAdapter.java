package com.example.tracnghiem2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tracnghiem2.R;
import com.example.tracnghiem2.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    final ArrayList<Category> lisCategory;


    public CategoryAdapter(ArrayList<Category> lisCategory) {
        this.lisCategory = lisCategory;
    }

    @Override
    public int getCount() {
        return lisCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return lisCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lisCategory.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCategory;
        if (convertView == null) {
            viewCategory = View.inflate(parent.getContext(), R.layout.category_view, null);
        } else viewCategory = convertView;

        //Bind dữ liệu phần tử vào View
        Category category = (Category) getItem(position);
        ((TextView) viewCategory.findViewById(R.id.id_category)).setText(String.format("%d", category.getId()));
        ((TextView) viewCategory.findViewById(R.id.name_category)).setText(String.format("%s", category.getName()));


        return viewCategory;
    }
}
