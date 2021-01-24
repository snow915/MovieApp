package com.prueba.movieapp.VO;

import android.graphics.Bitmap;

public class Movie {
    private String url;
    private String title;
    private String date;
    private String overview;
    private float voteAverage;

    public Movie(){}

    public Movie(String url, String title, String date, String overview, float voteAverage) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return String.valueOf(voteAverage);
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }
}
