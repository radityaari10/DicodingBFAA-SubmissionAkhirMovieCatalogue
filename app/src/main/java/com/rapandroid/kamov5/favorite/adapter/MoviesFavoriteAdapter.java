package com.rapandroid.kamov5.favorite.adapter;

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

import java.util.ArrayList;
import java.util.List;

public class MoviesFavoriteAdapter extends RecyclerView.Adapter<MoviesFavoriteAdapter.ViewHolder> {
    private List<Movie> listMovie = new ArrayList<>();
    private Context context;

    public  void setData(List<Movie> items){
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }
    public MoviesFavoriteAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite_movies,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesFavoriteAdapter.ViewHolder holder, final int position) {
        final Movie movie = listMovie.get(position);
        String url_img = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();
        Glide.with(context)
                .load(url_img)
                .apply(new RequestOptions().override(120,160))
                .into(holder.imgPhoto);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvVote.setText(Double.toString(movie.getVote_avg()));
        holder.tvOverview.setText(movie.getOverview());
        holder.cvMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                context.startActivity(i);
            }
        });
//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext(), R.style.MyDialogTheme);
//                builder.setTitle(R.string.confirm_delete);
//                builder.setMessage(R.string.confirm_message);
//                builder.setCancelable(false);
//                builder.setPositiveButton(R.string.yes_btn, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        MoviesHelper moviesHelper = MoviesHelper.getInstance(holder.itemView.getContext());
//                        moviesHelper.deleteMovie(movie.getId());
//                        notifyDataSetChanged();
//                        Toast.makeText(context, R.string.success_info, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                builder.setNegativeButton(R.string.no_btn, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        builder.setCancelable(true);
//                    }
//                });
//                builder.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        Button btnDelete;
        ImageView imgPhoto;
        TextView tvTitle, tvVote,tvOverview;
        CardView cvMovies;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.ig_item_movie_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_movie_favorite);
            tvVote = itemView.findViewById(R.id.tv_rate_movie_favorite);
            tvOverview = itemView.findViewById(R.id.tv_overview_movie_favorite);
            cvMovies = itemView.findViewById(R.id.item_favorite_movie);
//            btnDelete = itemView.findViewById(R.id.btn_delete_movie_favorite);
        }
    }
}
