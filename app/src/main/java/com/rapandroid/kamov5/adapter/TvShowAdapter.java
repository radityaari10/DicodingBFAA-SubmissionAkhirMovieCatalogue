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
import com.rapandroid.kamov5.activity.DetailTvShowActivity;
import com.rapandroid.kamov5.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowAdapterViewHolder> {
    private ArrayList<TvShow> listTvshow = new ArrayList<>();
    private Context context;

//    public void setData(ArrayList<TvShow> items){
//        listTvshow.clear();
//        listTvshow.addAll(items);
//        notifyDataSetChanged();
//    }

    public ArrayList<TvShow> getListTvshow() {
        return listTvshow;
    }

    public void setListTvshow(ArrayList<TvShow> listTvshow) {
        this.listTvshow = listTvshow;
    }

    public TvShowAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tvshow,
                viewGroup, false);
        return new TvShowAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowAdapter.TvShowAdapterViewHolder holder, int position) {
        final TvShow tvShow = getListTvshow().get(position);
        String url_img = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();
        Glide.with(context)
                .load(url_img)
                .apply(new RequestOptions().override(90,90))
                .into(holder.imgPhoto);

        holder.tvTitle.setText(tvShow.getTitle());
        holder.tvRelease.setText(tvShow.getRelease());
        holder.tvOverview.setText(tvShow.getOverview());
        holder.cvTvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailTvShowActivity.class);
                i.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvshow.size();
    }

    public class TvShowAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvRelease,tvOverview;
        CardView cvTvshow;

        public TvShowAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.ig_item_photo_tvshow);
            tvTitle = itemView.findViewById(R.id.tv_title_tvshow);
            tvRelease = itemView.findViewById(R.id.tv_release_tvshow);
            tvOverview = itemView.findViewById(R.id.tv_overview_tvshow);
            cvTvshow = itemView.findViewById(R.id.item_tvshow);
        }
    }
}
