package com.rapandroid.kamov5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.activity.DetailMovieActivity;
import com.rapandroid.kamov5.model.Movie;
import com.rapandroid.kamov5.rest.ApiClient;
import com.rapandroid.kamov5.rest.ApiEndPoint;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private Context context;
    private ApiEndPoint mApi;
//    public  void setData(ArrayList<Movie> items){
//        listMovie.clear();
//        listMovie.addAll(items);
//        notifyDataSetChanged();
//    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public MovieAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies,
                viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieAdapterViewHolder holder, int position) {
        final Movie movie = getListMovie().get(position);
        String url_img = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();
        mApi = ApiClient.getClient().create(ApiEndPoint.class);
        Glide.with(context)
                .load(url_img)
                .apply(new RequestOptions().override(90,90))
                .into(holder.imgPhoto);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelease.setText(movie.getRelease());
        holder.tvOverview.setText(movie.getOverview());
        holder.cvMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvRelease,tvOverview;
        CardView cvMovies;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);


            imgPhoto = itemView.findViewById(R.id.ig_item_photo_movies);
            tvTitle = itemView.findViewById(R.id.tv_title_movies);
            tvRelease = itemView.findViewById(R.id.tv_release_movies);
            tvOverview = itemView.findViewById(R.id.tv_overview_movies);
            cvMovies = itemView.findViewById(R.id.item_movies);
        }
    }
}
