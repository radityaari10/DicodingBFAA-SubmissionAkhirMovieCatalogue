package com.rapandroid.kamov5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.database.TvShowHelper;
import com.rapandroid.kamov5.model.TvShow;
import com.rapandroid.kamov5.widget.FavoriteWidget;

import java.util.ArrayList;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    Button btnAdd;
    private TvShowHelper tvShowHelper;
    private boolean isFavorite = false;
    private TvShow selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        selected = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();
        btnAdd = findViewById(R.id.btn_add_tvshow_favorite);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    int b = selected.getId();
                    tvShowHelper.deleteTvshow(selected.getId());
                    tvShowHelper.deleteAll(b);
                    updateWidget();
                    Toast.makeText(getApplicationContext(), R.string.success_info, Toast.LENGTH_SHORT).show();

                } else {
                    tvShowHelper.insertTvShow(selected);
                    tvShowHelper.insertAll(selected);
                    updateWidget();
                    Toast.makeText(getApplicationContext(), R.string.success_insert_fav, Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;
                setBtn();
            }
        });
        if (selected!=null){
            final ImageView igPhoto = findViewById(R.id.ig_bg_detail_tvshow);
            final ImageView igPhotomini = findViewById(R.id.ig_detailmini_tvshow);
            final TextView tvTitle = findViewById(R.id.tv_title_detail_tvshow);
            final TextView tvVote = findViewById(R.id.tv_vote_detail_tvshow);
            final TextView tvDate = findViewById(R.id.tv_date_detail_tvshow);
            final TextView tvLanguage = findViewById(R.id.tv_language_detail_tvshow);
            final TextView tvOverview = findViewById(R.id.tv_overview_detail_tvshow);
            final TextView tvRate = findViewById(R.id.textView);
            final ProgressBar progressBar = findViewById(R.id.progressbarTvshowDetail);

            igPhoto.setClipToOutline(true);
            igPhotomini.setClipToOutline(true);

            final Handler handler = new Handler();

            new Thread(new Runnable() {
                public void run() {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                    }

                    handler.post(new Runnable() {
                        public void run() {
                            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
                            String url_img = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();

                            getSupportActionBar().setTitle(tvShow.getTitle());
                            tvRate.setText(getResources().getText(R.string.rate));
                            tvTitle.setText(tvShow.getTitle());
                            tvDate.setText(tvShow.getRelease());
                            tvLanguage.setText(tvShow.getLanguage());
                            tvVote.setText(Double.toString(tvShow.getVote_avg()));
                            tvOverview.setText(tvShow.getOverview());
                            Glide.with(DetailTvShowActivity.this)
                                    .load(url_img)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(igPhoto);
                            Glide.with(DetailTvShowActivity.this)
                                    .load(url_img)
                                    .apply(new RequestOptions().override(120,180))
                                    .into(igPhotomini);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
        checkFaforite();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem mt){
        finish();
        return super.onOptionsItemSelected(mt);
    }
    private void checkFaforite() {
        ArrayList<TvShow> movieInDatabase = tvShowHelper.getAllTvShow();
        int i;
//        TvShow selected = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        for (i = 0; i < movieInDatabase.toArray().length; i++) {
            if (selected.getId().toString().matches(movieInDatabase.get(i).getId().toString())) {
                isFavorite = true;
            }
        }
        setBtn();
    }

    private void setBtn() {
        if (isFavorite) {
            btnAdd.setText(getResources().getText(R.string.delete_favorite));
            btnAdd.setBackground(getDrawable(R.drawable.btn_merah));
            btnAdd.setTextColor(getResources().getColor(R.color.textWhite));
        } else {
            btnAdd.setText(getResources().getText(R.string.add_favorite));
            btnAdd.setBackground(getDrawable(R.drawable.btn_white));
            btnAdd.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void updateWidget() {
        Intent widgetUpdateIntent = new Intent(this, FavoriteWidget.class);
        widgetUpdateIntent.setAction(FavoriteWidget.TOAST_ACTION);
        sendBroadcast(widgetUpdateIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }
}
