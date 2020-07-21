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
import com.rapandroid.kamov5.activity.DetailTvShowActivity;
import com.rapandroid.kamov5.model.TvShow;

import java.util.ArrayList;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.ViewHolder>{
    private ArrayList<TvShow> listMovie = new ArrayList<>();
    private Context context;

    public  void setData(ArrayList<TvShow> items){
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }
    public TvShowFavoriteAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public TvShowFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite_tvshow,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowFavoriteAdapter.ViewHolder holder, int position) {
        final TvShow tvShow = listMovie.get(position);
        String url_img = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();
        Glide.with(context)
                .load(url_img)
                .apply(new RequestOptions().override(120,160))
                .into(holder.imgPhoto);

        holder.tvTitle.setText(tvShow.getTitle());
        holder.tvVote.setText(Double.toString(tvShow.getVote_avg()));
        holder.tvOverview.setText(tvShow.getOverview());
        holder.cvMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailTvShowActivity.class);
                i.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow);
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
//                        TvShowHelper moviesHelper = TvShowHelper.getInstance(holder.itemView.getContext());
//                        moviesHelper.deleteTvshow(tvShow.getId());
//                        notifyDataSetChanged();
//                        setData(listMovie);
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

            imgPhoto = itemView.findViewById(R.id.ig_item_tvshow_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_tvshow_favorite);
            tvVote = itemView.findViewById(R.id.tv_rate_tvshow_favorite);
            tvOverview = itemView.findViewById(R.id.tv_overview_tvshow_favorite);
            cvMovies = itemView.findViewById(R.id.item_favorite_tvshow);
//            btnDelete = itemView.findViewById(R.id.btn_delete_tvshow_favorite);
        }
    }
}
