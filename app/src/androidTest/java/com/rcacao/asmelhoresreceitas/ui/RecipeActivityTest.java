package com.rcacao.asmelhoresreceitas.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Ingredient;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.data.models.Step;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    private static final String RECIPE_NAME = "Brigadeiro";
    private static final String INGREDIENT_NAME = "Leite Condensado";
    private static final String INGREDIENT_QUANTITY = "1.0";
    private static final String INGREDIENT_MEASURE = "LATA";
    private static final String IMAGE_URL = "http://www.tudogostoso.com.br/images/recipes/000/000/114/75811/75811_original.jpg";
    private static final int STEP_ID = 0;
    private static final String STEP_SHORT_DESCRIPTION = "Início";
    private static final String STEP_DESCRIPTION = "Vamos iniciar a receita";

    @Rule
    public final ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>
            (RecipeActivity.class, false, false);

    private boolean isTwoPanel;
    private static Intent intentRecipe;
    private static Recipe recipe;

    @BeforeClass
    public static void setUp() {

        recipe = new Recipe();
        recipe.setName(RECIPE_NAME);
        recipe.setImage(IMAGE_URL);

        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient(INGREDIENT_NAME);
        ingredient.setQuantity(INGREDIENT_QUANTITY);
        ingredient.setMeasure(INGREDIENT_MEASURE);

        recipe.setIngredients(new Ingredient[]{ingredient});

        Step step = new Step();
        step.setId(STEP_ID);
        step.setShortDescription(STEP_SHORT_DESCRIPTION);
        step.setDescription(STEP_DESCRIPTION);

        recipe.setSteps(new Step[]{step});

        intentRecipe = new Intent();
        intentRecipe.putExtra(RecipeActivity.ARG_RECIPE,recipe);

    }

    @Test
    public void isTwoPanel_ShowVideoFragment() {

        mActivityTestRule.launchActivity(intentRecipe);
        isTwoPanel = mActivityTestRule.getActivity().isTwoPanel();

        if(isTwoPanel){
            onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        }
        else{
            onView(withId(R.id.fragment_container)).check(doesNotExist());
        }
    }

    @Test
    public void receiveRecipeImage_passUrlToItem() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(withId(R.id.imageViewGeneric),withTagValue(is((Object) IMAGE_URL))))
                    .check(matches(withTagValue(is((Object) IMAGE_URL))));
    }

    @Test
    public void receiveRecipe_showRecipeName() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(
                withText(RECIPE_NAME),
                withId(R.id.textViewTitle)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveIngredient_showIngredientName() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(
                withText(INGREDIENT_NAME),
                withId(R.id.textViewIngredient)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveIngredient_showIngredientMeasure() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(
                withText(INGREDIENT_MEASURE),
                withId(R.id.textViewMeasure)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveIngredient_showIngredientQuantity() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(
                withText(INGREDIENT_QUANTITY),
                withId(R.id.textViewQuantity)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveStep_showStepId() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(ViewMatchers.withId(R.id.recyclerViewRecipeItens))
                .perform(RecyclerViewActions.scrollToPosition(4));

        onView(allOf(
                withText(STEP_ID + "."),
                withId(R.id.textViewId)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveStep_showStepShortDescription() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(ViewMatchers.withId(R.id.recyclerViewRecipeItens))
                .perform(RecyclerViewActions.scrollToPosition(4));

        onView(allOf(
                withText(STEP_SHORT_DESCRIPTION),
                withId(R.id.textViewStep)
        )).check(matches(isDisplayed()));

    }

    @Test
    public void receiveStepWithoutVideo_notShowVideoIcon() {

        mActivityTestRule.launchActivity(intentRecipe);

        onView(withId(R.id.imageViewClip)).check(matches(not(isDisplayed())));

    }


    @Test
    public void receiveStepWithVideo_ShowVideoIcon() {

        recipe.getSteps()[0].setVideoURL("VIDEO//URL");
        intentRecipe.putExtra(RecipeActivity.ARG_RECIPE,recipe);
        mActivityTestRule.launchActivity(intentRecipe);

        onView(ViewMatchers.withId(R.id.recyclerViewRecipeItens))
                .perform(RecyclerViewActions.scrollToPosition(4));

        onView(withId(R.id.imageViewClip)).check(matches((isDisplayed())));

    }

    @Test
    public void receiveStepWithThumbnail_PassUrlToItem() {

        recipe.getSteps()[0].setThumbnailURL(IMAGE_URL);
        intentRecipe.putExtra(RecipeActivity.ARG_RECIPE,recipe);
        mActivityTestRule.launchActivity(intentRecipe);

        onView(allOf(withId(R.id.imageViewStep),withTagValue(is((Object) IMAGE_URL))))
                .check(matches(withTagValue(is((Object) IMAGE_URL))));

    }


    @Test
    public void clickRecyclerViewStepItem_SendAIntentOrOpenFrag() {


        mActivityTestRule.launchActivity(intentRecipe);
        isTwoPanel = mActivityTestRule.getActivity().isTwoPanel();

        Intents.init();

        //resposta
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(hasComponent(StepActivity.class.getName())).respondWith(result);

        onView(ViewMatchers.withId(R.id.recyclerViewRecipeItens))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));

        if (!isTwoPanel){
            intended(allOf(hasComponent(StepActivity.class.getName()),
                    hasExtraWithKey(StepActivity.ARG_STEPS)));

        }
        else{
            onView(allOf(withId(R.id.textViewDescription),withText(STEP_DESCRIPTION)))
                    .check(matches(isDisplayed()));

        }

        Intents.release();
    }

}