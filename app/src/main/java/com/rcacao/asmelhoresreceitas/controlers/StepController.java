package com.rcacao.asmelhoresreceitas.controlers;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.rcacao.asmelhoresreceitas.data.models.Step;
import com.rcacao.asmelhoresreceitas.ui.fragment.StepFragment;

public class StepController {

    public static final String STATE_ID = "id";
    public static final String STATE_PLAYBACK = "playback";
    public static final String STATE_WINDOW = "window";

    private long playbackPosition = C.TIME_UNSET ;
    private int currentWindow= 0;
    private Step steps[];

    public StepController(Step[] steps) {
        this.steps = steps;
    }

    public void loadStep(int idStep, @NonNull StepFragment fragment) {

        Bundle args = new Bundle();
        args.putParcelable(StepFragment.ARG_STEP, steps[idStep]);
        args.putInt(StepFragment.ARG_TOTAL,steps.length);
        args.putInt(StepFragment.ARG_ID,idStep);
        args.putLong(StepFragment.ARG_PLAYBACK,playbackPosition);
        args.putInt(StepFragment.ARG_WINDOW,currentWindow);

        fragment.setArguments(args);
        fragment.loadStep();

    }

    private void resetVideoPosition(){
        playbackPosition = C.TIME_UNSET ;
        currentWindow= 0;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public int loadNext(int actualId, StepFragment fragment){
        if (actualId < steps.length-1){
            resetVideoPosition();
            int id = actualId+1;
            loadStep(id, fragment );
            return id;
        }

        return 0;
    }

    public int loadPrevious(int actualId, StepFragment fragment){

        if (actualId > 0){
            resetVideoPosition();
            int id = actualId-1;
            loadStep(id, fragment );
            return id;
        }

        return 0;
    }

}
