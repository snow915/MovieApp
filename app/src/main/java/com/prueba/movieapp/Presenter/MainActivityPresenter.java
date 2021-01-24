package com.prueba.movieapp.Presenter;

import android.content.Context;

import com.prueba.movieapp.Interactor.MainActivityInteractor;
import com.prueba.movieapp.Interfaces.Contract;
import com.prueba.movieapp.VO.Movie;

import java.util.ArrayList;

public class MainActivityPresenter implements Contract.Presenter, Contract.onOperationListener {

    private MainActivityInteractor mainActivityInteractor;
    private Contract.View view;

    public MainActivityPresenter(Contract.View view){
        this.view = view;
        mainActivityInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void readMovies(Context context) {
        mainActivityInteractor.performReadMovies(context);
    }

    @Override
    public void onRead(ArrayList<Movie> movies) {
        view.onMovieRead(movies);
    }
}
