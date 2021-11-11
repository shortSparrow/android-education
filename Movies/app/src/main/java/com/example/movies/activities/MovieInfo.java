package com.example.movies.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.MovieActorAdapter;
import com.example.movies.model.Movie;
import com.example.movies.model.MovieActor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class MovieInfo extends AppCompatActivity {
    ImageView moviePoster;
    TextView movieTitle;
    TextView movieIMDBRating;
    TextView movieDirector;
    TextView movieReleaseDate;
    TextView movieDuration;
    TextView movieDescription;
    TextView movieCompany;
    GifImageView trailerThumbnail;
    RecyclerView movieActorsList;
    MovieActorAdapter movieActorAdapter;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        getSupportActionBar().hide();

        moviePoster = findViewById(R.id.moviePoster);
        movieTitle = findViewById(R.id.movieTitle);
        movieIMDBRating = findViewById(R.id.movieIMDBRating);
        movieDirector = findViewById(R.id.movieDirector);
        movieReleaseDate = findViewById(R.id.movieReleaseDate);
        movieDuration = findViewById(R.id.movieDuration);
        movieDescription = findViewById(R.id.movieDescription);
        movieCompany = findViewById(R.id.movieCompany);
        movieActorsList = findViewById(R.id.movieActorsList);
        trailerThumbnail = findViewById(R.id.trailerThumbnail);

        ImageView movieAnimateLine = findViewById(R.id.movieAnimateLine);
//        val animation = moviePoster.drawable as AnimationDrawable
//        animation.start()

        moviePoster.setBackgroundResource(R.drawable.animation_loader_content);
        AnimationDrawable animation = (AnimationDrawable) moviePoster.getBackground();


        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                movieAnimateLine.setTranslationY(-150*2);
                movieAnimateLine.setTranslationX(-150*2);
                movieAnimateLine.animate().translationY(200*2).translationX(200*2).setDuration(600);
            }
        },0,2000);



        movieActorsList.hasFixedSize();
        movieActorsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        requestQueue = Volley.newRequestQueue(this);
        String movieId = getIntent().getStringExtra("movieId");
        if (movieId != null) {
            loadMovieData(movieId);
        }
    }

    private void loadMovieData(String movieId) {
        String url = "https://imdb-api.com/API/Title/k_3si39c77/" + movieId;
        Log.d("url", url);

        JsonObjectRequest getMovieData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<MovieActor> actorList = new ArrayList();
                try {
                    String movieId = response.getString("id");
                    Log.d("movieIdSSSS", movieId);

                    String imageUrl = response.getString("image");
                    String title = response.getString("title");
                    String ratings = response.getString("imDbRating");
                    String director = response.getString("directors");
                    String releaseDate = response.getString("releaseDate");
                    String duration = response.getString("runtimeStr");
                    String description = response.getString("plot");
                    String companyCreated = response.getString("companies");

                    movieTitle.setText(title);
                    movieIMDBRating.setText(ratings);
                    movieDirector.setText(director);
                    movieReleaseDate.setText(releaseDate);
                    movieDuration.setText(duration);
                    movieDescription.setText(description);
                    movieCompany.setText(companyCreated);
//                    Picasso.get().load("https://i.ytimg.com/vi/1Ne1hqOXKKI/maxresdefault.jpg").into(moviePoster);


                    JSONArray actors = response.getJSONArray("actorList");
                    for(int i=0; i < actors.length(); i++) {
                        JSONObject actor = actors.getJSONObject(i);

                        String id = actor.getString("id");
                        String name = actor.getString("name");
                        String characterName = actor.getString("asCharacter");
                        String actorImageUrl = actor.getString("image");

                        MovieActor movieActor = new MovieActor(id, name, characterName, actorImageUrl);
                        actorList.add(movieActor);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                movieActorAdapter = new MovieActorAdapter(MovieInfo.this, actorList);
                movieActorsList.setAdapter(movieActorAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("loadMovieDataFailed",error.getMessage());
            }
        });


        String trailerUrl = "https://imdb-api.com/en/API/Trailer/k_3si39c77/" + movieId;
        Log.d("trailerUrl", trailerUrl);
        JsonObjectRequest getMovieTrailer = new JsonObjectRequest(Request.Method.GET, trailerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String thumbnailUrl = response.getString("thumbnailUrl");
                    Picasso.get().load("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*").noPlaceholder().into(trailerThumbnail);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("loadMovieDataFailed",error.getMessage());
            }
        });


        requestQueue.add(getMovieData);
        requestQueue.add(getMovieTrailer);
    }
}
