package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.controlers.StepController;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.ui.fragment.ListFragment;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements ListFragment.OnStepClickListener, StepFragment.OnNavigateClickListener {



    public static final String ARG_RECIPE = "recipe";

    private Recipe recipe = null;
    private boolean twoPanel = false;
    private StepController controller = null;
    private int idStep = 0;
    private StepFragment stepFragment;


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

        twoPanel = findViewById(R.id.fragment_container) != null && recipe.getSteps() != null;

        if (twoPanel) {

            loadStepFragment(savedInstanceState);

        }

    }

    private void loadStepFragment(Bundle savedInstanceState) {
        controller = new StepController(recipe.getSteps());
        stepFragment = new StepFragment();

        //verify saved instance
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(StepController.STATE_ID)) {
                idStep = savedInstanceState.getInt(StepController.STATE_ID);
            }
            if (savedInstanceState.containsKey(StepController.STATE_PLAYBACK)) {
                controller.setPlaybackPosition((long) savedInstanceState.getLong(StepController.STATE_PLAYBACK));
            }
            if (savedInstanceState.containsKey(StepController.STATE_WINDOW)) {
                controller.setCurrentWindow(savedInstanceState.getInt(StepController.STATE_WINDOW));
            }

        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,stepFragment)
                .commit();

        if (recipe.getSteps().length > 0) {
            controller.loadStep(idStep, stepFragment);
        }
    }


    private void passRecipeToFragment(Recipe recipe) {

        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        fragment.setRecipe(recipe);
        fragment.loadItens();

    }


    @Override
    public void onClickStep(int id) {

        if (!twoPanel){
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.ARG_STEPS, recipe.getSteps());
            intent.putExtra(StepActivity.ARG_ID,id);

            startActivity(intent);
        }
        else{
            controller.loadStep(id,stepFragment);
        }


    }

    @Override
    public void onClickNext(int actualId) {

        idStep = controller.loadNext(actualId,stepFragment);

    }

    @Override
    public void onClickPrevious(int actualId) {

        idStep = controller.loadNext(actualId,stepFragment);

    }
}
