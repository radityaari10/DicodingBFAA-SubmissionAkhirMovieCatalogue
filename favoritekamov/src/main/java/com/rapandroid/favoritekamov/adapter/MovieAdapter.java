package com.rapandroid.favoritekamov.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapandroid.favoritekamov.R;
import com.rapandroid.favoritekamov.database.DatabaseContract;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private Context context;
    private Cursor mCursor;

    public void setAllData(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite,
                viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

//    public static Movie mapCursorToObject(Cursor notesCursor) {
//        notesCursor.moveToFirst();
//        int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
//        String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
//        String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
//        String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DATE));
//
//        return new Movie();
//    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieAdapterViewHolder holder, final int position) {

        mCursor.moveToPosition(position);
        String url_img = "https://image.tmdb.org/t/p/w185" + mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.POSTER));
        Glide.with(context)
                .load(url_img)
                .apply(new RequestOptions().override(90,90))
                .into(holder.imgPhoto);

        holder.tvTitle.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.TITLE)));
        holder.tvOverview.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.OVERVIEW)));
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(context, DetailActivity.class);
//                i.putExtra("detail_data", mCursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.ID));
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvOverview;
//        CardView cardView;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo_prov);
            tvTitle = itemView.findViewById(R.id.tv_item_title_prov);
            tvOverview = itemView.findViewById(R.id.tv_item_desc_prov);
//            cardView = itemView.findViewById(R.id.item_prov);
            imgPhoto.setClipToOutline(true);
        }
    }
}
