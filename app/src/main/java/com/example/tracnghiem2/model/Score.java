package com.example.tracnghiem2.model;


public class Score {
    private int id;
    private int id_user;
    private int id_category;
    private int score;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Score() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score(int id, int id_user, int id_category, int score) {
        this.id = id;
        this.id_user = id_user;
        this.id_category = id_category;
        this.score = score;
    }

}
