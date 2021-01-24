package com.prueba.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prueba.movieapp.R;
import com.prueba.movieapp.VO.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    public ArrayList<Movie> movies;
    private Context context;

    public MovieAdapter(ArrayList<Movie> movies, Context context){
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, null);
        MovieHolder movieHolder = new MovieHolder(view);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.tvTitle.setText(movies.get(position).getTitle());
        holder.tvDate.setText(movies.get(position).getDate());
        holder.tvOverview.setText(movies.get(position).getOverview());
        holder.tvVoteAverage.setText(movies.get(position).getVoteAverage());
        Glide.with(context)
                .load(movies.get(position).getUrl())
                .fitCenter()
                .centerCrop()
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        public ImageView imgPoster;
        public TextView tvTitle, tvDate, tvOverview, tvVoteAverage;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average);
        }
    }
}
