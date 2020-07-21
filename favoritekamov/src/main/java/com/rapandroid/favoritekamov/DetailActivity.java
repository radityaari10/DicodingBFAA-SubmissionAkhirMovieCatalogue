package com.rapandroid.favoritekamov;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
//    public static final String EXTRA_MOVIE = "extra_movie";
//    private Movie ;
//    private int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
//        selected = getIntent().getIntExtra("detail_data", 0);
//
////        if (selected != null) {
//            final ImageView igPhoto = findViewById(R.id.ig_bg_detail_prov);
//            final ImageView igPhotomini = findViewById(R.id.ig_detailmini_prov);
//            final TextView tvTitle = findViewById(R.id.tv_title_detail_prov);
//            final TextView tvVote = findViewById(R.id.tv_vote_detail_prov);
//            final TextView tvDate = findViewById(R.id.tv_date_detail_prov);
//            final TextView tvLanguage = findViewById(R.id.tv_language_detail_prov);
//            final TextView tvOverview = findViewById(R.id.tv_overview_detail_prov);
//
//            final TextView tvRate = findViewById(R.id.textView);
//            igPhoto.setClipToOutline(true);
//            igPhotomini.setClipToOutline(true);}
////            final Handler handler = new Handler();
//
////            new Thread(new Runnable() {
////                public void run() {
////                    handler.post(new Runnable() {
////                        public void run() {
//    public void setLayout{
//                            Movie movies = getIntent().getIntExtra();
//                            String url_img = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();
//
//                            getSupportActionBar().setTitle(movies.getTitle());
//                            tvRate.setText(getResources().getText(R.string.rate));
//                            tvTitle.setText(movies.getTitle());
//                            tvDate.setText(movies.getRelease());
//                            tvLanguage.setText(movies.getLanguage());
//                            tvVote.setText(Double.toString(movies.getVote_avg()));
//                            tvOverview.setText(movies.getOverview());
//                            Glide.with(DetailActivity.this)
//                                    .load(url_img)
//                                    .apply(new RequestOptions().centerCrop())
//                                    .into(igPhoto);
//                            Glide.with(DetailActivity.this)
//                                    .load(url_img)
//                                    .apply(new RequestOptions().override(120, 180))
//                                    .into(igPhotomini);
////                            progressBar.setVisibility(View.INVISIBLE);
////                        }
////                    });
////                }
////            }).start();
////        }
//    }
}
