package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;
    private Button loadMoviesAgain;

    private EditText searchFiled;
    private ShimmerFrameLayout mFrameLayout;
    private LinearLayout failedLoadDataWrapper;
    private TextView failedLoadDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = findViewById(R.id.shimmer_view_container);
        searchFiled = findViewById(R.id.searchMovie);
        loadMoviesAgain = findViewById(R.id.loadMoviesAgain);
        failedLoadDataWrapper = findViewById(R.id.failedLoadDataWrapper);
        failedLoadDataText = findViewById(R.id.failedLoadDataText);
        recyclerView = findViewById(R.id.movieRecyclerList);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<Movie>();
        requestQueue = Volley.newRequestQueue(this);

//        loadTestData();
        showShimmer();
        getMovies("https://imdb-api.com/en/API/Top250Movies/k_3si39c77/", "items");
    }

    private void loadTestData() {
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/300/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/300/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/200/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/310/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/312/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/201/300",
                "9.2",
                "tt0111161"
        ));

        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/202/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/203/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/204/300",
                "9.2",
                "tt0111161"
        ));


        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/205/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/206/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/207/300",
                "9.2",
                "tt0111161"
        ));

        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/208/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/209/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/2010/300",
                "9.2",
                "tt0111161"
        ));

        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/211/300",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/212/200",
                "9.2",
                "tt0111161"
        ));
        movies.add(new Movie(
                "The Shawshank Redemption",
                "1994",
                "https://picsum.photos/213/300",
                "9.2",
                "tt0111161"
        ));

        movieAdapter = new MovieAdapter(MainActivity.this, movies);
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

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

        mFrameLayout.startShimmer();
        mFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }



    private void getMovies(String url, String key) {
        ProgressDialog progress = new ProgressDialog(this);

        if (mFrameLayout.getVisibility() == View.GONE) {
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorMessage = response.getString("errorMessage");
                    Log.d("errorMessage", errorMessage);
                    if (errorMessage.length() > 0) {
                        showErrorMessage(errorMessage, url, key);
                        return;
                    }

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

                    stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);

                    if (failedLoadDataWrapper.getVisibility() == View.VISIBLE) {
                        failedLoadDataWrapper.setVisibility(View.GONE);
                    }

                } catch (JSONException err) {

                    if (err.getMessage() != null) {
                        showErrorMessage(err.getMessage(), url, key);
                    } else {
                        showErrorMessage("Some error happened!", url, key);
                    }

                    err.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Cant load the content", Toast.LENGTH_LONG).show();
                } finally {
                    progress.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage("Some error happened!", url, key);
                Toast.makeText(getApplicationContext(),"Cant load the content", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void showShimmer() {
        mFrameLayout.startShimmer();
    }

    public void stopShimmer() {
        mFrameLayout.stopShimmer();
        mFrameLayout.setVisibility(View.GONE);
    }


    public void showErrorMessage(String errorMessage, String url, String key) {
        stopShimmer();
        recyclerView.setVisibility(View.GONE);
        failedLoadDataWrapper.setVisibility(View.VISIBLE);
        failedLoadDataText.setText(errorMessage);

        loadMoviesAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("loadAGain", key);
                Log.d("loadAGain1", url);
                getMovies(url, key);
            }
        });
    }

    public void searchMovies(View view) {
        movies.clear();
        String searchValue = searchFiled.getText().toString().trim();
        Log.d("searchValue", searchValue);
        getMovies("https://imdb-api.com/en/API/Search/k_3si39c77/" + searchValue, "results");
    }

}