package com.example.moviedatabase;

public class Movie {
    private String title;
    private int year;
    private String genre;
    private int posterResourceId;

    // 构造函数
    public Movie(String title, int year, String genre, int posterResourceId) {

        this.title = title;
        this.year = year;
        this.genre = genre;
        this.posterResourceId = posterResourceId;
    }

    // Getter 方法
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }

    public int getPosterResourceID() { return posterResourceId;}
}

