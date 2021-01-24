package com.prueba.movieapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.movieapp.Adapters.MovieAdapter;
import com.prueba.movieapp.Interfaces.Contract;
import com.prueba.movieapp.Presenter.MainActivityPresenter;
import com.prueba.movieapp.R;
import com.prueba.movieapp.VO.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Contract.View {
    public RecyclerView recyclerView;
    public MovieAdapter movieAdapter;
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.readMovies(this);
    }

    @Override
    public void onMovieRead(ArrayList<Movie> movies) {
        movieAdapter = new MovieAdapter(movies, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onLoadData(JsonObjectRequest jsonObjectRequest) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}