package com.rapandroid.favoritekamov;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rapandroid.favoritekamov.adapter.MovieAdapter;

import static com.rapandroid.favoritekamov.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private MovieAdapter adapter;
    private RecyclerView rvSearch;
    private LinearLayoutManager linearLayoutManager;
    private static final int CODE_MOVIE = 301;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSearch = findViewById(R.id.rv_search);
        adapter =new MovieAdapter(getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearch.setLayoutManager(linearLayoutManager);
        rvSearch.setAdapter(adapter);
        rvSearch.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(CODE_MOVIE, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getApplicationContext(), CONTENT_URI, null, null ,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.setAllData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == CODE_MOVIE){
        adapter.setAllData(null);
        }
    }
}
