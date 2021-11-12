package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.MovieAdapter;
import com.example.movies.data.RecyclerItemClickListener;
import com.example.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;

    private EditText searchFiled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFiled = findViewById(R.id.searchMovie);
        recyclerView = findViewById(R.id.movieRecyclerList);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<Movie>();
        requestQueue = Volley.newRequestQueue(this);

//        movies.add(new Movie(
//                "The Shawshank Redemption",
//                "1994",
//                "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
//                "9.2",
//                "tt0111161"
//        ));
//        movieAdapter = new MovieAdapter(MainActivity.this, movies);
//        recyclerView.setAdapter(movieAdapter);
//
//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Movie clickedMovie = movies.get(position);
//                        Log.d("clickedItem", clickedMovie.getMovieId());
//                        Intent intent = new Intent(MainActivity.this, MovieInfo.class);
//                        intent.putExtra("movieId", clickedMovie.getMovieId() + "");
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//
//                    }
//                })
//        );


        getMovies("https://imdb-api.com/en/API/Top250Movies/k_3si39c77", "items");
    }

    private void getMovies(String url, String key) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(key);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cycleFor", i + "");
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String movieId = jsonObject.getString("id").toString();
                        String title = jsonObject.getString("title").toString();
                        String posterUrl = jsonObject.getString("image").toString();

                        String year = "";
                        if (jsonObject.has("year")) {
                            year = jsonObject.getString("year");
                        }
                        String imdbRating = "";
                        if (jsonObject.has("imDbRating")) {
                            imdbRating = jsonObject.getString("imDbRating");
                        }

                        Movie movie = new Movie();
                        movie.setMovieId(movieId);
                        movie.setPosterUrl(posterUrl);
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setImdbRating(imdbRating);

                        movies.add(movie);
                    }

                    movieAdapter = new MovieAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(movieAdapter);

                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(MainActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                                @Override
                                public void onItemClick(View view, int position) {
                                    Movie clickedMovie = movies.get(position);
                                    Log.d("clickedItem", clickedMovie.getMovieId());
                                    Intent intent = new Intent(MainActivity.this, MovieInfo.class);
                                    intent.putExtra("movieId", clickedMovie.getMovieId() + "");
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }
                            })
                    );

                } catch (JSONException err) {
                    err.printStackTrace();
                } finally {
                    progress.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public void searchMovies(View view) {
        movies.clear();
        String searchValue = searchFiled.getText().toString().trim();
        Log.d("searchValue", searchValue);
        getMovies("https://imdb-api.com/en/API/Search/k_3si39c77/" + searchValue, "results");
    }
}