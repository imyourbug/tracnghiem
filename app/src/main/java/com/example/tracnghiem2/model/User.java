package com.example.tracnghiem2.model;

import android.os.Parcel;
import android.os.Parcelable;
//implements Parcelable
public class User {
    private int id, role;
    private String name, password;

    public User(int role, String name, String password) {
        this.role = role;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    protected User(Parcel in) {
//        this.id = in.readInt();
//        this.role = in.readInt();
//        this.name = in.readString();
//        this.password = in.readString();
//    }
//
//    public static final Creator<User> CREATOR = new Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeString(password);
//        dest.writeInt(role);
//    }
}
