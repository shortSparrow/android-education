package com.example.movies.model;

import android.widget.ImageView;

import java.util.ArrayList;

public class MovieFull {
    private String movieId;
    private String title;
    private String ratings;
    private String director;
    private String releaseDate;
    private String duration;
    private String imageUrl;
    private String description;
    private String companyCreated;
//    private ArrayList<MovieActor> actorList;


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyCreated() {
        return companyCreated;
    }

    public void setCompanyCreated(String companyCreated) {
        this.companyCreated = companyCreated;
    }
}
