package com.prueba.movieapp.Interactor;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.movieapp.Interfaces.Contract;
import com.prueba.movieapp.VO.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityInteractor implements Contract.Interactor {

    private Contract.onOperationListener onOperationListener;
    public ArrayList<Movie> movies;

    public MainActivityInteractor(Contract.onOperationListener onOperationListener) {
        this.onOperationListener = onOperationListener;
        movies = new ArrayList<>();
    }

    @Override
    public void performReadMovies(Context context) {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=cb7e8a89ebe8e55ca1cab90395234ad4&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                        Movie movie = new Movie();
                        movie.setTitle(response.getJSONArray("results").getJSONObject(i).getString("title"));
                        movie.setDate(response.getJSONArray("results").getJSONObject(i).getString("release_date"));
                        movie.setOverview(response.getJSONArray("results").getJSONObject(i).getString("overview"));
                        movie.setVoteAverage((float) response.getJSONArray("results").getJSONObject(i).getDouble("vote_average"));
                        movie.setUrl("https://image.tmdb.org/t/p/original" + response.getJSONArray("results").getJSONObject(i).getString("poster_path"));
                        movies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR = " + error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        onOperationListener.onRead(movies);
    }
}
