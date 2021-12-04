package com.example.movies.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.movies.model.MovieActor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MovieInfo extends AppCompatActivity {
    String movieId;
    TextView movieTitle;
    TextView movieIMDBRating;
    TextView movieDirector;
    TextView movieReleaseDate;
    TextView movieDuration;
    TextView movieDescription;
    TextView movieCompany;
    GifImageView trailerThumbnail;
    MovieActorAdapter movieActorAdapter;
    RequestQueue requestQueue;


    LinearLayout failedLoadDataWrapper;
    TextView failedLoadDataText;



    ShimmerFrameLayout shimmerMovieShortInfo;
    LinearLayout shimmerMovieShortInfoWrapper;
    LinearLayout movieShortInfoWrapper;

    ShimmerFrameLayout shimmerMoviePoster;
    LinearLayout shimmerMoviePosterWrapper;
    ImageView moviePoster;

    ShimmerFrameLayout shimmerMovieInfo;
    ConstraintLayout shimmerMovieInfoWrapper;
    LinearLayout movieInfoWrapper;

    ShimmerFrameLayout shimmerActorList;
    RecyclerView movieActorsList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        getSupportActionBar().hide();


        shimmerMovieShortInfo = findViewById(R.id.shimmer_movie_short_info);
        shimmerMovieShortInfoWrapper = findViewById(R.id.shimmer_movie_short_info_wrapper);
        movieShortInfoWrapper = findViewById(R.id.movie_short_info_wrapper);

        shimmerMoviePoster = findViewById(R.id.shimmer_movie_poster);
        shimmerMoviePosterWrapper = findViewById(R.id.shimmer_movie_poster_wrapper);
        moviePoster = findViewById(R.id.movie_poster);

        shimmerMovieInfo = findViewById(R.id.shimmer_movie_info);
        shimmerMovieInfoWrapper = findViewById(R.id.shimmer_movie_info_container);
        movieInfoWrapper = findViewById(R.id.movie_info_wrapper);


        shimmerActorList = findViewById(R.id.shimmer_actor_list);
        movieActorsList = findViewById(R.id.movieActorsList);


        shimmerMovieInfo = findViewById(R.id.shimmer_movie_info);
        failedLoadDataWrapper = findViewById(R.id.failedLoadDataWrapper);
        failedLoadDataText = findViewById(R.id.failedLoadDataText);
        movieTitle = findViewById(R.id.movieTitle);
        movieIMDBRating = findViewById(R.id.movieIMDBRating);
        movieDirector = findViewById(R.id.movieDirector);
        movieReleaseDate = findViewById(R.id.movieReleaseDate);
        movieDuration = findViewById(R.id.movieDuration);
        movieDescription = findViewById(R.id.movieDescription);
        movieCompany = findViewById(R.id.movieCompany);
        trailerThumbnail = findViewById(R.id.trailerThumbnail);

        movieActorsList.hasFixedSize();
        movieActorsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        requestQueue = Volley.newRequestQueue(this);
        movieId = getIntent().getStringExtra("movieId");



        if (movieId != null) {
            loadMovieInfo(movieId);
            loadMovieTrailer(movieId);
        }
    }

    private void loadMovieInfo(String movieId) {
        showInfoShimmer();
        String url = "https://imdb-api.com/API/Title/k_3si39c77/" + movieId;
        Log.d("url", url);

        JsonObjectRequest getMovieData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorMessage = response.getString("errorMessage");
                    if (errorMessage.length() > 0 && errorMessage.contains("Maximum usage")) {
                        showErrorMessage(errorMessage);
                        return;
                    }

                    ArrayList<MovieActor> actorList = new ArrayList();

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

                    Picasso.get().load(imageUrl).into(moviePoster,new Callback() {
                        @Override
                        public void onSuccess() {
                            stopPosterShimmer();
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(R.mipmap.no_image).into(moviePoster);
                        }
                    });


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

                    movieActorAdapter = new MovieActorAdapter(MovieInfo.this, actorList);
                    movieActorsList.setAdapter(movieActorAdapter);

                    stopInfoShimmer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.d("getMessage", error.getMessage());
                    showErrorMessage(error.getMessage());
                } else {
                    showErrorMessage("Some error happened!");
                }
            }
        });

        requestQueue.add(getMovieData);
    }

    private void loadMovieTrailer(String movieId) {
        shimmerMoviePoster.startShimmer();

        String trailerUrl = "https://imdb-api.com/en/API/Trailer/k_3si39c77/" + movieId;
        Log.d("trailerUrl", trailerUrl);
        JsonObjectRequest getMovieTrailer = new JsonObjectRequest(Request.Method.GET, trailerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorMessage = response.getString("errorMessage");
                    if (errorMessage.length() > 0) {
//                        showErrorMessage(errorMessage);
                        Picasso.get().load(R.mipmap.no_image).into(trailerThumbnail);
                        return;
                    }

                    String thumbnailUrl = response.getString("thumbnailUrl");
                    Picasso.get().load(thumbnailUrl).noPlaceholder().into(trailerThumbnail);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Picasso.get().load(R.mipmap.no_image).into(trailerThumbnail);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Picasso.get().load(R.mipmap.no_image).into(trailerThumbnail);
                Log.e("loadMovieDataFailed",error.getMessage());
            }
        });

        requestQueue.add(getMovieTrailer);
    }



    public void stopInfoShimmer() {
        shimmerMovieShortInfo.stopShimmer();
        shimmerMovieInfo.stopShimmer();
        shimmerActorList.stopShimmer();

        shimmerMovieShortInfoWrapper.setVisibility(View.GONE);
        shimmerMovieInfoWrapper.setVisibility(View.GONE);
        shimmerActorList.setVisibility(View.GONE);

        movieShortInfoWrapper.setVisibility(View.VISIBLE);
        movieInfoWrapper.setVisibility(View.VISIBLE);
        movieActorsList.setVisibility(View.VISIBLE);
    }

    public void showInfoShimmer() {
        shimmerMovieShortInfo.startShimmer();
        shimmerMovieInfo.startShimmer();
        shimmerActorList.startShimmer();

        shimmerMovieShortInfoWrapper.setVisibility(View.VISIBLE);
        shimmerMovieInfoWrapper.setVisibility(View.VISIBLE);
        shimmerActorList.setVisibility(View.VISIBLE);

        movieShortInfoWrapper.setVisibility(View.GONE);
        movieInfoWrapper.setVisibility(View.GONE);
        movieActorsList.setVisibility(View.GONE);
    }

    public void stopPosterShimmer() {
        shimmerMoviePoster.stopShimmer();
        shimmerMoviePosterWrapper.setVisibility(View.GONE);
        moviePoster.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        Log.d("errorMessageX", errorMessage);
        stopInfoShimmer();
        stopPosterShimmer();

        movieShortInfoWrapper.setVisibility(View.GONE);
        movieInfoWrapper.setVisibility(View.GONE);
        movieActorsList.setVisibility(View.GONE);

        failedLoadDataWrapper.setVisibility(View.VISIBLE);
        failedLoadDataText.setText(errorMessage);
    }


    public void getMovieInfo(View view) {
        if (movieId != null) {
            failedLoadDataWrapper.setVisibility(View.GONE);
            loadMovieInfo(movieId);
        }
    }
}
