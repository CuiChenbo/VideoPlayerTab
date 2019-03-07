package choi.ccb.com.fragmenttabhostdemo.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import choi.ccb.com.fragmenttabhostdemo.R;


/**
 * ExoPlayer视频播放
 */
public class CartFragment extends Fragment {


    private ExoPlayer player;
    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;

    public CartFragment() {
        // Required empty public constructor
    }

    private PlayerView playerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exo, container, false);
        initializePlayer(v);
        return v;
    }

    private void initializePlayer(View v) {
        playerView = (v.findViewById(R.id.video_view));
        // 创建带宽
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
       // 创建轨道选择工厂
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        // 创建轨道选择实例
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // 创建播放器实例
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        playerView.setPlayer(player);
        // 创建加载数据的工厂
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "MyApplication"), null);
        // 创建资源
        Uri uri = Uri.parse("/storage/emulated/0/Download/APPNEWCARDEMO.mp4");
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        player.prepare(mediaSource, false, true);
        player.setPlayWhenReady(true);
    }

}
