package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.controlers.StepController;
import com.rcacao.asmelhoresreceitas.data.local.DbHelper;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.ui.fragment.ListFragment;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;
import com.rcacao.asmelhoresreceitas.utils.MyUtils;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements ListFragment.OnStepClickListener, StepFragment.OnNavigateClickListener {



    public static final String ARG_RECIPE = "recipe";
    public static final String PREF_FAV = "pref_fav";

    private Recipe recipe = null;
    private boolean twoPanel = false;
    private StepController controller = null;
    private int idStep = 0;
    private StepFragment stepFragment;
    private MenuItem mnuFav;
    private MenuItem mnuDesFav;


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

        setTitle(recipe.getName());
        
        passRecipeToFragment(recipe);

        twoPanel = findViewById(R.id.fragment_container) != null && recipe.getSteps() != null;

        if (twoPanel) {

            loadStepFragment(savedInstanceState);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe,menu);
        mnuFav = menu.findItem(R.id.mnuFav);
        mnuDesFav = menu.findItem(R.id.mnuDesFav);

        verifyMenu();
        return true;
    }

    private void verifyMenu() {

        String fav = PreferenceManager.
                getDefaultSharedPreferences(this).getString(PREF_FAV,"");

        if (fav.equals(recipe.getName())){
            mnuDesFav.setVisible(true);
            mnuFav.setVisible(false);
        }
        else{
            mnuDesFav.setVisible(false);
            mnuFav.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String title = "";

        if (item.getItemId()==R.id.mnuDesFav){
            new DbHelper(this).removeIngredients();
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString(PREF_FAV,"").apply();

            Toast.makeText(this, R.string.toast_desfav, Toast.LENGTH_SHORT).show();

        }
        else if (item.getItemId()==R.id.mnuFav){
            new DbHelper(this).changeIngredients(recipe.getIngredients());
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString(PREF_FAV,recipe.getName()).apply();

            Toast.makeText(this, R.string.toast_fav, Toast.LENGTH_SHORT).show();

            title = recipe.getName();
        }

        MyUtils.refreshWidget(title, this);

        verifyMenu();

        return super.onOptionsItemSelected(item);
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
            intent.putExtra(StepActivity.ARG_TITLE,recipe.getName());

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

    @VisibleForTesting
    public boolean isTwoPanel() {
        return twoPanel;
    }

}
