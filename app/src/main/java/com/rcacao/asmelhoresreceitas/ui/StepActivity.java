package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Step;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;

import java.util.Arrays;

public class StepActivity extends AppCompatActivity implements StepFragment.OnNavigateClickListener {

    public static final String ARG_STEPS = "steps";
    public static final String ARG_ID = "id";

    private Step steps[];
    private int id;

    private StepFragment stepFragment = new StepFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();

        if (intent !=null){
            Parcelable parcels[] = intent.getParcelableArrayExtra(ARG_STEPS);
            if (parcels != null) {
                steps = Arrays.copyOf(parcels, parcels.length, Step[].class);
            }
            id = intent.getIntExtra(ARG_ID,0);
        }

        if (steps==null){
            finish();
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,stepFragment)
                .commit();

        loadStep(id);

    }

    private void loadStep(int idStep) {

        Bundle args = new Bundle();
        args.putParcelable(StepFragment.ARG_STEP, steps[idStep]);
        args.putInt(StepFragment.ARG_TOTAL,steps.length);
        args.putInt(StepFragment.ARG_ID,idStep);

        stepFragment.setArguments(args);
        stepFragment.loadStep();

    }


    @Override
    public void onClickNext(int actualId) {

        if (actualId < steps.length-1){
            loadStep(actualId+1);
        }

    }

    @Override
    public void onClickPrevious(int actualId) {

        if (actualId > 0){
            loadStep(actualId-1);
        }

    }
}
