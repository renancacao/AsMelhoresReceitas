package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Step;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class StepActivityTest {

    private static final String STEP_VIDEO_0 = "";
    private static final int STEP_ID_0 = 0;
    private static final String STEP_SHORT_DESCRIPTION_0 = "Início";
    private static final String STEP_DESCRIPTION_0 = "Vamos iniciar a receita";

    private static final String STEP_VIDEO_1 = "video.mp4";
    private static final int STEP_ID_1 = 1;
    private static final String STEP_SHORT_DESCRIPTION_1 = "Passo 1";
    private static final String STEP_DESCRIPTION_1 = "Passo 1";

    private static final String STEP_VIDEO_2 = "";
    private static final int STEP_ID_2 = 0;
    private static final String STEP_SHORT_DESCRIPTION_2 = "Passo 2";
    private static final String STEP_DESCRIPTION_2 = "Passo 2";

    @Rule
    public final ActivityTestRule<StepActivity> mActivityTestRule = new ActivityTestRule<>
            (StepActivity.class, false, false);

    private static Intent intentSteps;

    @BeforeClass
    public static void setUp() {

        Step step0 = new Step();
        step0.setId(STEP_ID_0);
        step0.setVideoURL(STEP_VIDEO_0);
        step0.setShortDescription(STEP_SHORT_DESCRIPTION_0);
        step0.setDescription(STEP_DESCRIPTION_0);

        Step step1 = new Step();
        step1.setId(STEP_ID_1);
        step1.setVideoURL(STEP_VIDEO_1);
        step1.setShortDescription(STEP_SHORT_DESCRIPTION_1);
        step1.setDescription(STEP_DESCRIPTION_1);

        Step step2 = new Step();
        step2.setId(STEP_ID_2);
        step2.setVideoURL(STEP_VIDEO_2);
        step2.setShortDescription(STEP_SHORT_DESCRIPTION_2);
        step2.setDescription(STEP_DESCRIPTION_2);

        intentSteps = new Intent();
        intentSteps.putExtra(StepActivity.ARG_STEPS,new Step[]{step0,step1,step2});

    }

    @Test
    public void receiveVideo_ShowVideo() {

        intentSteps.putExtra(StepActivity.ARG_ID,1);

        mActivityTestRule.launchActivity(intentSteps);
        onView(allOf(
                withId(R.id.playerView),
                withClassName(is(SimpleExoPlayerView.class.getName())))).check(matches(isDisplayed()));

    }

    @Test
    public void notReceiveVideo_notShowVideo() {

        intentSteps.putExtra(StepActivity.ARG_ID,0);

        mActivityTestRule.launchActivity(intentSteps);
        onView(allOf(
                withId(R.id.playerView),
                withClassName(is(SimpleExoPlayerView.class.getName())))).check(matches(not(isDisplayed())));

    }

    @Test
    public void firstItem_notShowPrevius() {

        intentSteps.putExtra(StepActivity.ARG_ID,0);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.imageViewPrev)).check(matches(not(isDisplayed())));

    }

    @Test
    public void lastItem_notShowNext() {

        intentSteps.putExtra(StepActivity.ARG_ID,2);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.imageViewNext)).check(matches(not(isDisplayed())));

    }

    @Test
    public void midleItem_ShowNext() {

        intentSteps.putExtra(StepActivity.ARG_ID,1);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.imageViewNext)).check(matches(isDisplayed()));

    }

    @Test
    public void midleItem_ShowPrevious() {

        intentSteps.putExtra(StepActivity.ARG_ID,1);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.imageViewPrev)).check(matches(isDisplayed()));

    }

    @Test
    public void receiveStep_showShortDescrption() {

        intentSteps.putExtra(StepActivity.ARG_ID,0);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.textViewTitle)).check(matches(withText(STEP_ID_0 + ". " + STEP_SHORT_DESCRIPTION_0)));

    }

    @Test
    public void receiveStep_showDescrption() {

        intentSteps.putExtra(StepActivity.ARG_ID,0);

        mActivityTestRule.launchActivity(intentSteps);
        onView(withId(R.id.textViewDescription)).check(matches(withText(STEP_DESCRIPTION_0)));

    }



}