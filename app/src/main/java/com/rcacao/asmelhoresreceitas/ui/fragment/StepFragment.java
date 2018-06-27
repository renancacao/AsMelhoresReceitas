package com.rcacao.asmelhoresreceitas.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.Step;
import com.rcacao.asmelhoresreceitas.utils.MyUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepFragment extends Fragment  {

    public static final String ARG_STEP = "step";
    public static final String ARG_TOTAL = "total";
    public static final String ARG_ID = "id";
    public static final String ARG_PLAYBACK = "playback";
    public static final String ARG_WINDOW = "window";

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.textViewDescription)
    TextView textViewDescription;

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @BindView(R.id.imageViewNext)
    ImageView imageViewNext;

    @BindView(R.id.imageViewPrev)
    ImageView imageViewPrev;

    private SimpleExoPlayer exoPlayer;

    private Step mStep;
    private int listID=-1;

    private String videoUrl = "";

    private long playbackPosition = C.TIME_UNSET ;
    private int currentWindow= 0;

    private OnNavigateClickListener mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadStep();
    }

    public void loadStep() {

        if (getView()==null){return;}

        Bundle args = getArguments();
        int totalSteps=0;

        if(args!=null){

            if (args.containsKey(ARG_STEP)){
                mStep=args.getParcelable(ARG_STEP);
            }

            if (args.containsKey(ARG_TOTAL)){
                totalSteps=args.getInt(ARG_TOTAL);
            }

            if (args.containsKey(ARG_ID)){
                listID=args.getInt(ARG_ID);
            }

            if (args.containsKey(ARG_PLAYBACK)){
                playbackPosition=args.getLong(ARG_PLAYBACK);
            }

            if (args.containsKey(ARG_WINDOW)){
                currentWindow=args.getInt(ARG_WINDOW);
            }

        }

        if (mStep != null){

            releasePlayer();
            if (mStep.getVideoURL() != null) {
                videoUrl = mStep.getVideoURL();
            }
            initializePlayer();

            playerView.setVisibility(videoUrl.isEmpty()? View.GONE : View.VISIBLE);

            textViewDescription.setText(mStep.getDescription());
            textViewTitle.setText(String.format(Locale.getDefault(),"%d. %s", mStep.getId(), mStep.getTitle()));

            imageViewPrev.setVisibility(listID > 0 ? View.VISIBLE : View.INVISIBLE);
            imageViewNext.setVisibility(listID < totalSteps-1 ? View.VISIBLE : View.INVISIBLE);

        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnNavigateClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " precisa implementar OnNavigateClickListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        saveTime();
        releasePlayer();
        super.onPause();

    }

    @Override
    public void onStop() {
        saveTime();
        releasePlayer();
        super.onStop();

    }


    private void initializePlayer(){

        if(videoUrl==null){return;}

        if (exoPlayer == null && !videoUrl.isEmpty()){
            Context context = getContext();
            if(context==null){
                return;
            }

            exoPlayer = ExoPlayerFactory.newSimpleInstance(context,
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            playerView.setPlayer(exoPlayer);

            String userAgent = MyUtils.getUserAgent(context, "AsMelhoresReceitas");

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);

            exoPlayer.prepare(mediaSource, true, false);
            exoPlayer.seekTo(currentWindow,playbackPosition);
            exoPlayer.setPlayWhenReady(true);

        }

    }

    private void saveTime(){
        if (exoPlayer != null){
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
        }
    }

    private void releasePlayer(){
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public long getVideoPlayback(){
        return playbackPosition;
    }

    public int getVideoWindow(){
        return currentWindow;
    }


    @OnClick(R.id.imageViewNext) public void onClickImageViewNext(){
        mCallback.onClickNext(listID);
    }

    @OnClick(R.id.imageViewPrev) public void onClickImageViewPrevious(){
        mCallback.onClickPrevious(listID);
    }

    public interface OnNavigateClickListener{
        void onClickNext(int actualId);
        void onClickPrevious(int actualId);
    }




}
