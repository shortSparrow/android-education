package com.example.movies.model;

public class Movie {
    private String title;
    private String year;
    private String posterUrl;
    private String imdbRating;
    private String movieId;



    public Movie(String title, String year, String posterUrl,String imdbRating, String movieId ) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.imdbRating = imdbRating;
        this.movieId = movieId;
    }

    public Movie() {

    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
