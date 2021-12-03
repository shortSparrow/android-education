package com.example.movies.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ShimmerFrameLayout fullMoviePosterShimmer;
    private ShimmerFrameLayout shimmerActorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        getSupportActionBar().hide();

        fullMoviePosterShimmer = findViewById(R.id.fullMoviePosterShimmer);
        shimmerActorList = findViewById(R.id.shimmer_actor_list);
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

        movieActorsList.hasFixedSize();
        movieActorsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        requestQueue = Volley.newRequestQueue(this);
        String movieId = getIntent().getStringExtra("movieId");

        fullMoviePosterShimmer.startShimmer();
        shimmerActorList.startShimmer();
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

                    Picasso.get().load(imageUrl).into(moviePoster,new Callback() {
                        @Override
                        public void onSuccess() {
                            fullMoviePosterShimmer.stopShimmer();
                            fullMoviePosterShimmer.hideShimmer();
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                movieActorAdapter = new MovieActorAdapter(MovieInfo.this, actorList);
                movieActorsList.setAdapter(movieActorAdapter);

                shimmerActorList.stopShimmer();
                shimmerActorList.hideShimmer();
                shimmerActorList.setVisibility(View.GONE);
                movieActorsList.setVisibility(View.VISIBLE);
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
                    Picasso.get().load(thumbnailUrl).noPlaceholder().into(trailerThumbnail);

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


//
//package com.example.movies.activities;
//
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//
//        import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.android.volley.Request;
//        import com.android.volley.RequestQueue;
//        import com.android.volley.Response;
//        import com.android.volley.VolleyError;
//        import com.android.volley.toolbox.JsonObjectRequest;
//        import com.android.volley.toolbox.Volley;
//        import com.example.movies.R;
//        import com.example.movies.data.MovieActorAdapter;
//        import com.example.movies.model.MovieActor;
//        import com.facebook.shimmer.ShimmerFrameLayout;
//        import com.squareup.picasso.Callback;
//        import com.squareup.picasso.Picasso;
//
//        import org.json.JSONArray;
//        import org.json.JSONException;
//        import org.json.JSONObject;
//
//        import java.util.ArrayList;
//
//        import pl.droidsonroids.gif.GifImageView;
//
//public class MovieInfo extends AppCompatActivity {
//    ImageView moviePoster;
//    TextView movieTitle;
//    TextView movieIMDBRating;
//    TextView movieDirector;
//    TextView movieReleaseDate;
//    TextView movieDuration;
//    TextView movieDescription;
//    TextView movieCompany;
//    GifImageView trailerThumbnail;
//    RecyclerView movieActorsList;
//    MovieActorAdapter movieActorAdapter;
//    RequestQueue requestQueue;
//    private ShimmerFrameLayout fullMoviePosterShimmer;
//    private ShimmerFrameLayout shimmerActorList;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_movie_info);
//        getSupportActionBar().hide();
//        fullMoviePosterShimmer = findViewById(R.id.fullMoviePosterShimmer);
//        shimmerActorList = findViewById(R.id.shimmer_actor_list);
//        moviePoster = findViewById(R.id.moviePoster);
//        movieTitle = findViewById(R.id.movieTitle);
//        movieIMDBRating = findViewById(R.id.movieIMDBRating);
//        movieDirector = findViewById(R.id.movieDirector);
//        movieReleaseDate = findViewById(R.id.movieReleaseDate);
//        movieDuration = findViewById(R.id.movieDuration);
//        movieDescription = findViewById(R.id.movieDescription);
//        movieCompany = findViewById(R.id.movieCompany);
//        movieActorsList = findViewById(R.id.movieActorsList);
//        trailerThumbnail = findViewById(R.id.trailerThumbnail);
//
//        moviePoster.setBackgroundResource(R.drawable.animation_loader_content);
//
//        movieActorsList.hasFixedSize();
//        movieActorsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        requestQueue = Volley.newRequestQueue(this);
//        String movieId = getIntent().getStringExtra("movieId");
//        if (movieId != null) {
//            loadMovieData(movieId);
//        }
//    }
//
//    private void loadMovieData(String movieId) {
//        String url = "https://google.com";
//        Log.d("url", url);
//
//        ArrayList<MovieActor> actorList = new ArrayList();
//        try {
//            movieTitle.setText("inception 2010");
//            movieIMDBRating.setText("8.1");
//            movieDirector.setText("XYZZ");
//            movieReleaseDate.setText("12/10/21");
//            movieDuration.setText("180 min");
//            movieDescription.setText("LALALALALAL SLSLSLSLS");
//            movieCompany.setText("BRMO");
//            Picasso.get().load("https://media.wired.co.uk/photos/60c8730fa81eb7f50b44037e/3:2/w_3329,h_2219,c_limit/1521-WIRED-Cat.jpeg").into(moviePoster,new Callback() {
//                @Override
//                public void onSuccess() {
//                    fullMoviePosterShimmer.stopShimmer();
//                    fullMoviePosterShimmer.hideShimmer();
//                    moviePoster.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onError(Exception e) {
//
//                }
//            });
//
//
//            JSONObject sampleObject1 = new JSONObject();
//            sampleObject1.put("image", "https://imdb-api.com/images/original/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg");
//            JSONObject sampleObject2 = new JSONObject();
//            sampleObject2.put("image", "https://imdb-api.com/images/original/MV5BMTc3NTM4MzQ5MV5BMl5BanBnXkFtZTcwOTE4MDczNw@@._V1_Ratio0.7273_AL_.jpg");
//            JSONObject sampleObject3 = new JSONObject();
//            sampleObject3.put("image", "https://imdb-api.com/images/original/MV5BMTQ3ODE3Mjg1NV5BMl5BanBnXkFtZTcwNzA4ODcxNA@@._V1_Ratio0.7273_AL_.jpg");
//            JSONObject sampleObject4 = new JSONObject();
//            sampleObject4.put("image", "https://imdb-api.com/images/original/MV5BNTMxMjYzNzk5Nl5BMl5BanBnXkFtZTcwNzU4NDgwMw@@._V1_Ratio0.9545_AL_.jpg");
//            JSONObject sampleObject5 = new JSONObject();
//            sampleObject5.put("image", "https://imdb-api.com/images/original/MV5BMTQ4MDMyODEyMF5BMl5BanBnXkFtZTgwNDI4OTQ1MjE@._V1_Ratio1.3182_AL_.jpg");
//
//            JSONArray actors = new JSONArray();
//            actors.put(sampleObject1);
//            actors.put(sampleObject2);
//            actors.put(sampleObject3);
//            actors.put(sampleObject4);
//            actors.put(sampleObject5);
//
//            for(int i=0; i < actors.length(); i++) {
//                JSONObject actor = actors.getJSONObject(i);
//                String actorImageUrl = actor.getString("image");
//                MovieActor movieActor = new MovieActor("name", "name", "characterName", actorImageUrl);
//                actorList.add(movieActor);
//            }
//
//            shimmerActorList.stopShimmer();
//            shimmerActorList.hideShimmer();
//            shimmerActorList.setVisibility(View.GONE);
//            movieActorsList.setVisibility(View.VISIBLE);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        movieActorAdapter = new MovieActorAdapter(MovieInfo.this, actorList);
//        movieActorsList.setAdapter(movieActorAdapter);
//
//        String trailerUrl = "https://cdn.vox-cdn.com/thumbor/WDYEciOUWFz_PvWt-LyhaYeSEyo=/0x0:1548x1024/1400x1050/filters:focal(693x458:939x704):no_upscale()/cdn.vox-cdn.com/uploads/chorus_image/image/65936299/cats4.0.jpg";
//
//        Picasso.get().load(trailerUrl).noPlaceholder().into(trailerThumbnail);
//    }
//
//
//
//}
