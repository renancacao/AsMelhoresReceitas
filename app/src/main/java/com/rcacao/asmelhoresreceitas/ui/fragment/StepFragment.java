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
import com.rcacao.asmelhoresreceitas.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment  {

    public static final String ARG_VIDEO_URL = "video_url";
    public static final String ARG_DESCRIPTION = "description";
    public static final String ARG_TITLE = "title";

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.textViewDescription)
    TextView textViewDescription;

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    SimpleExoPlayer exoPlayer;

    private String description = "";
    private String videoUrl = "";
    private String title = "";

    private long playbackPosition = C.TIME_UNSET ;
    private int currentWindow= 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args!=null){

            if (args.containsKey(ARG_VIDEO_URL)){
                videoUrl=args.getString(ARG_VIDEO_URL);
            }

            if (args.containsKey(ARG_DESCRIPTION)){
                description=args.getString(ARG_DESCRIPTION);
            }

            if (args.containsKey(ARG_TITLE)){
                title=args.getString(ARG_TITLE);
            }

        }

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        if (videoUrl.isEmpty()){
            playerView.setVisibility(View.GONE);
        }

        textViewDescription.setText(description);
        textViewTitle.setText(title);

        return rootView;
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
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer(){

        if (exoPlayer == null){

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

    private void releasePlayer(){
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }





}
