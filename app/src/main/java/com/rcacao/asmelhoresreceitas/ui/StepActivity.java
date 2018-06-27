package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.controlers.StepController;
import com.rcacao.asmelhoresreceitas.data.models.Step;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;

import java.util.Arrays;

public class StepActivity extends AppCompatActivity implements StepFragment.OnNavigateClickListener {

    public static final String ARG_STEPS = "steps";
    public static final String ARG_ID = "id";
    public static final String ARG_TITLE = "title";

    private int id;

    private StepController controller;

    private final StepFragment stepFragment = new StepFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        Step[] steps = null;
        String title = "";

        if (intent !=null){
            Parcelable parcels[] = intent.getParcelableArrayExtra(ARG_STEPS);
            if (parcels != null) {
                steps = Arrays.copyOf(parcels, parcels.length, Step[].class);
            }
            id = intent.getIntExtra(ARG_ID,0);
            title = intent.getStringExtra(ARG_TITLE);
        }

        if (steps==null){
            finish();
            return;
        }

        setTitle(title);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,stepFragment)
                .commit();

        controller = new StepController(steps);

        //verify saved instance
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(StepController.STATE_ID)) {
                id = savedInstanceState.getInt(StepController.STATE_ID);
            }
            if (savedInstanceState.containsKey(StepController.STATE_PLAYBACK)) {
                controller.setPlaybackPosition((long) savedInstanceState.getLong(StepController.STATE_PLAYBACK));
            }
            if (savedInstanceState.containsKey(StepController.STATE_WINDOW)) {
                controller.setCurrentWindow(savedInstanceState.getInt(StepController.STATE_WINDOW));
            }
        }

        controller.loadStep(id,stepFragment);


    }




    @Override
    public void onClickNext(int actualId) {

       id = controller.loadNext(actualId,stepFragment);

    }

    @Override
    public void onClickPrevious(int actualId) {

        id = controller.loadPrevious(actualId,stepFragment);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(StepController.STATE_ID,id);
        if (stepFragment != null){
            outState.putLong(StepController.STATE_PLAYBACK,(long) stepFragment.getVideoPlayback());
            outState.putInt(StepController.STATE_WINDOW,stepFragment.getVideoWindow());
        }


        super.onSaveInstanceState(outState);
    }
}
