package choi.ccb.com.fragmenttabhostdemo.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;

import choi.ccb.com.fragmenttabhostdemo.R;

/**
 * 占位图Video
 */

public class StandardVideoController extends GestureVideoController implements View.OnClickListener{
    private ImageView mThumb;

    public StandardVideoController(@NonNull Context context) {
        this(context, null);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.player_controller;
    }

    @Override
    protected void initView() {
        super.initView();

        mThumb = mControllerView.findViewById(R.id.thumb);
        mThumb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
       if (i == R.id.thumb) {
            doPauseResume();}

    }



    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                hide();
                mIsLocked = false;
                mMediaPlayer.setLock(false);
                mThumb.setVisibility(View.VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                mThumb.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                break;
            case IjkVideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                break;
            case IjkVideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mThumb.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mThumb.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_BUFFERED:
                mThumb.setVisibility(View.GONE);
                L.e("STATE_BUFFERED");
                break;
            case IjkVideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                mThumb.setVisibility(View.VISIBLE);
                mIsLocked = false;
                mMediaPlayer.setLock(false);
                break;
        }
    }

    public ImageView getThumb() {
        return mThumb;
    }
}
