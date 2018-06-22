package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.ui.fragment.ListFragment;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements ListFragment.OnStepClickListener {



    public static final String ARG_RECIPE = "recipe";

    private Recipe recipe = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent !=null){
            recipe = getIntent().getParcelableExtra(ARG_RECIPE);
        }

        if (recipe==null){
            finish();
        }

        passRecipeToFragment(recipe);

    }

    private void passRecipeToFragment(Recipe recipe) {

        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        fragment.setRecipe(recipe);
        fragment.loadItens();

    }


    @Override
    public void onClickStep(int id) {

        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra(StepActivity.ARG_STEPS, recipe.getSteps());
        intent.putExtra(StepActivity.ARG_ID,id);

        startActivity(intent);

    }
}
