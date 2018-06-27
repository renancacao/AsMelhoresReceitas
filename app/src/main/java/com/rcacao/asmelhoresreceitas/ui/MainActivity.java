package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.adapter.RecipeAdapter;
import com.rcacao.asmelhoresreceitas.data.loaders.JsonAsyncLoader;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.iddlingresource.SimpleIdlingResource;
import com.rcacao.asmelhoresreceitas.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe[]>,
        RecipeAdapter.RecipeAdapterClickHandler {

    @BindView(R.id.recyclerViewRecipes)
    RecyclerView recyclerViewRecipes;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.layoutError)
    LinearLayout layoutError;

    private static final int ID_JSONLOADER = 11;

    private Recipe[] recipes;
    private RecipeAdapter adapter;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.recipesNumCol),1);
        recyclerViewRecipes.setLayoutManager(layoutManager);

        adapter = new RecipeAdapter(recipes, this );
        recyclerViewRecipes.setAdapter(adapter);

        // Get the IdlingResource instance
        getIdlingResource();

        loadRecipes();

        MyUtils.refreshWidget(this);

    }

    private void loadRecipes() {

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        progressBar.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(ID_JSONLOADER,null,this);

    }


    @NonNull
    @Override
    public Loader<Recipe[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new JsonAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Recipe[]> loader, Recipe[] data) {

        recipes = data;
        adapter.setRecipes(recipes);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);


        if (recipes == null){
            layoutError.setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.INVISIBLE);
        }
        else{
            layoutError.setVisibility(View.INVISIBLE);
            recyclerViewRecipes.setVisibility(View.VISIBLE);
        }


        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recipe[]> loader) {
    }

    @Override
    public void clickRecipe(int id) {

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.ARG_RECIPE, recipes[id]);
        startActivity(intent);

    }

    public void OnClickTryAgain(View view) {

        loadRecipes();
    }


}


