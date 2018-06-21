package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Step;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;

public class StepActivity extends AppCompatActivity  {

    public static final String ARG_STEP = "step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        Step step = null;

        if (intent !=null){
            step = getIntent().getParcelableExtra(ARG_STEP);
        }

        if (step==null){
            finish();
            return;
        }

        loadFragment(step.getVideoURL(), step.getDescription(), step.getTitle());

    }

    private void loadFragment(String videoUrl, String description, String title) {

        StepFragment stepFragment = new StepFragment();

        Bundle args = new Bundle();
        args.putString(StepFragment.ARG_VIDEO_URL,videoUrl);
        args.putString(StepFragment.ARG_DESCRIPTION,description);
        args.putString(StepFragment.ARG_TITLE,title);

        stepFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,stepFragment)
                .commit();
    }


}
