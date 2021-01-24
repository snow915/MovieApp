package com.prueba.movieapp.Interfaces;

import android.content.Context;

import com.prueba.movieapp.VO.Movie;

import java.util.ArrayList;

public interface Contract {

    interface View {
        void onMovieRead(ArrayList<Movie> movies);
    }

    interface Presenter {
        void readMovies(Context context);
    }

    interface Interactor {
        void performReadMovies(Context context);
    }

    interface onOperationListener {
        void onRead(ArrayList<Movie> movies);
    }

}
