package com.rcacao.asmelhoresreceitas.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.loaders.JsonAsyncLoader;
import com.rcacao.asmelhoresreceitas.data.models.models.Recipe;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private static final int ID_JSONLOADER = 11;
    private final String URL_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRecipes();

    }

    private void loadRecipes() {
        Bundle args = new Bundle();
        args.putString(JsonAsyncLoader.ARG_URL, URL_RECIPES);
        getSupportLoaderManager().restartLoader(ID_JSONLOADER,args,this);
    }


    @NonNull
    @Override
    public Loader<Recipe[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new JsonAsyncLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Recipe[]> loader, Recipe[] data) {
        Log.i("OK","OK");

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recipe[]> loader) {
    }
}


