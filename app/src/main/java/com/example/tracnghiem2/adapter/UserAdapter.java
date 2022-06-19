package com.example.tracnghiem2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tracnghiem2.R;
import com.example.tracnghiem2.model.Question;
import com.example.tracnghiem2.model.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private ArrayList<User> listUser;

    public UserAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listUser.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewUser;
        if (convertView == null) {
            viewUser = View.inflate(parent.getContext(), R.layout.user_view, null);
        } else viewUser = convertView;

        //Bind dữ liệu phần tử vào View
        User user = (User) getItem(position);
        ((TextView) viewUser.findViewById(R.id.img_user)).setText(String.format("%s", getFirstLetterName(user.getName())));
        ((TextView) viewUser.findViewById(R.id.user_name)).setText(String.format("%s", user.getName()));
        ((TextView) viewUser.findViewById(R.id.password)).setText(String.format("%s", user.getPassword()));
        ((TextView) viewUser.findViewById(R.id.role)).setText(String.format("%s", getNameRole(user.getRole())));

        return viewUser;
    }

    private String getNameRole(int role) {
        if (role == 1)
            return "Admin";
        return "Người dùng";
    }

    private String getFirstLetterName(String user_name) {
        String[] rs = user_name.split("");
        return rs[0].toUpperCase();
    }
}
