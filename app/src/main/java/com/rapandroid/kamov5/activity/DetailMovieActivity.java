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
import com.rapandroid.kamov5.database.MoviesHelper;
import com.rapandroid.kamov5.model.Movie;
import com.rapandroid.kamov5.widget.FavoriteWidget;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    Button btnAdd;
    private MoviesHelper moviesHelper;
    private boolean isFavorite = false;
    private Movie selected;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        selected = getIntent().getParcelableExtra(EXTRA_MOVIE);
        moviesHelper = MoviesHelper.getInstance(getApplicationContext());
        moviesHelper.open();
        btnAdd = findViewById(R.id.btn_add_movie_favorite);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    int b = selected.getId();
                    moviesHelper.deleteMovie(selected.getId());
                    moviesHelper.deleteAll(b);
                    updateWidget();
                    Toast.makeText(getApplicationContext(), R.string.success_info, Toast.LENGTH_SHORT).show();
                } else {
                    moviesHelper.insertMovies(selected);
                    moviesHelper.insertAll(selected);
                    updateWidget();
                    Toast.makeText(getApplicationContext(), R.string.success_insert_fav, Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;
                setBtn();
            }
        });
        if (selected != null) {
            final ImageView igPhoto = findViewById(R.id.ig_bg_detail_movies);
            final ImageView igPhotomini = findViewById(R.id.ig_detailmini_movies);
            final TextView tvTitle = findViewById(R.id.tv_title_detail_movies);
            final TextView tvVote = findViewById(R.id.tv_vote_detail_movies);
            final TextView tvDate = findViewById(R.id.tv_date_detail_movies);
            final TextView tvLanguage = findViewById(R.id.tv_language_detail_movies);
            final TextView tvOverview = findViewById(R.id.tv_overview_detail_movies);

            final TextView tvRate = findViewById(R.id.textView);
            final ProgressBar progressBar = findViewById(R.id.progressbarMovieDetail);
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
                            Movie movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
                            String url_img = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();

                            getSupportActionBar().setTitle(movies.getTitle());
                            tvRate.setText(getResources().getText(R.string.rate));
                            tvTitle.setText(movies.getTitle());
                            tvDate.setText(movies.getRelease());
                            tvLanguage.setText(movies.getLanguage());
                            tvVote.setText(Double.toString(movies.getVote_avg()));
                            tvOverview.setText(movies.getOverview());
                            Glide.with(DetailMovieActivity.this)
                                    .load(url_img)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(igPhoto);
                            Glide.with(DetailMovieActivity.this)
                                    .load(url_img)
                                    .apply(new RequestOptions().override(120, 180))
                                    .into(igPhotomini);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
        checkFavorite();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem mt){
        finish();
        return super.onOptionsItemSelected(mt);
    }
    private void checkFavorite() {
        ArrayList<Movie> movieInDatabase = moviesHelper.getAllMovies();
        int i;
//        Movie selected = getIntent().getParcelableExtra(EXTRA_MOVIE);
        for (i = 0; i < movieInDatabase.toArray().length; i++) {
            if (selected.getId().toString().matches(movieInDatabase.get(i).getId().toString())) {
                isFavorite = true;
            }
        }
        setBtn();
    }

    private void setBtn() {
        if (isFavorite) {
            btnAdd.setBackground(getDrawable(R.drawable.btn_merah));
            btnAdd.setTextColor(getResources().getColor(R.color.textWhite));
            btnAdd.setText(getResources().getText(R.string.delete_favorite));
        } else {
            btnAdd.setBackground(getDrawable(R.drawable.btn_white));
            btnAdd.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            btnAdd.setText(getResources().getText(R.string.add_favorite));
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
        moviesHelper.close();
    }
}
