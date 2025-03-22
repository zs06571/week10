package com.example.moviedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
private List<Movie> movieList;

public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

@NonNull
@Override
public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_menue, parent, false);
    return new MovieViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    Movie movie = movieList.get(position);
    holder.title.setText(movie.getTitle());
    holder.year.setText(String.valueOf(movie.getYear()));
    holder.genre.setText(movie.getGenre());
    holder.poster.setImageResource(movie.getPosterResourceID());
}

@Override
public int getItemCount() {
    return movieList.size();
}

static class MovieViewHolder extends RecyclerView.ViewHolder {
    TextView title, year, genre;
    ImageView poster;

    public MovieViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movieTitle);
        year = itemView.findViewById(R.id.movieYear);
        genre = itemView.findViewById(R.id.movieGenre);
        poster = itemView.findViewById(R.id.moviePoster);
    }
}
}
