package com.prueba.movieapp.Interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.movieapp.Interfaces.Contract;
import com.prueba.movieapp.Utils.AdminSQLiteOpenHelper;
import com.prueba.movieapp.VO.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class MainActivityInteractor implements Contract.Interactor {

    private Contract.onOperationListener onOperationListener;
    public ArrayList<Movie> movies;
    public AdminSQLiteOpenHelper admin;
    public SQLiteDatabase db;

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
                admin = new AdminSQLiteOpenHelper(context, "moviesDB", null, 1);
                db = admin.getWritableDatabase();
                db.execSQL("DELETE FROM moviesTable");
                try {
                    for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                        Movie movie = new Movie();
                        String title = response.getJSONArray("results").getJSONObject(i).getString("title");
                        String date = response.getJSONArray("results").getJSONObject(i).getString("release_date");
                        String overview = response.getJSONArray("results").getJSONObject(i).getString("overview");
                        float voteAverage = (float) response.getJSONArray("results").getJSONObject(i).getDouble("vote_average");
                        String url = "https://image.tmdb.org/t/p/original" + response.getJSONArray("results").getJSONObject(i).getString("poster_path");
                        movie.setTitle(title);
                        movie.setDate(date);
                        movie.setOverview(overview);
                        movie.setVoteAverage(voteAverage);
                        movie.setUrl(url);
                        movies.add(movie);
                        registerSQLite(i, title, date, overview, voteAverage, url);
                    }
                    onOperationListener.onRead(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db.close();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                admin = new AdminSQLiteOpenHelper(context, "moviesDB", null, 1);
                db = admin.getReadableDatabase();
                long count = DatabaseUtils.queryNumEntries(db,"moviesTable");
                for(int i = 0; i<count; i++){
                    Movie movie = new Movie();
                    Cursor row = db.rawQuery("select title, date, overview, voteAverage, url from moviesTable where id ="+i, null);
                    if(row.moveToFirst()){
                        movie.setTitle(row.getString(0));
                        movie.setDate(row.getString(1));
                        movie.setOverview(row.getString(2));
                        movie.setVoteAverage(row.getFloat(3));
                        movie.setUrl(row.getString(4));
                        movies.add(movie);
                    }
                }
                onOperationListener.onRead(movies);
                db.close();
            }
        });

        onOperationListener.onLoad(jsonObjectRequest);
    }

    public void registerSQLite(int id, String title, String date, String overview, float voteAverage, String url) {
        ContentValues register = new ContentValues();
        register.put("id", id);
        register.put("url", url);
        register.put("title", title);
        register.put("date", date);
        register.put("overview", overview);
        register.put("voteAverage", voteAverage);
        db.insert("moviesTable", null, register);

    }

}
