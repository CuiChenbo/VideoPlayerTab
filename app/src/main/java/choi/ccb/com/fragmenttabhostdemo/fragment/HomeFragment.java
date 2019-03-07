package choi.ccb.com.fragmenttabhostdemo.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import choi.ccb.com.fragmenttabhostdemo.R;
import choi.ccb.com.fragmenttabhostdemo.video.videoplayer.ScaleType;
import choi.ccb.com.fragmenttabhostdemo.video.videoplayer.TextureVideoView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nice, container, false);
        initView(v);
        return v;
    }

    private ImageView ivC ;
    String uri="/storage/emulated/0/Download/APPNEWCARDEMO.mp4";
    private void initView(View v) {
        ivC = v.findViewById(R.id.ivC);
        TextureVideoView videoView = v.findViewById(R.id.video_view);
        videoView.setVideoPath(uri);
        videoView.setScaleType(ScaleType.LEFT_CENTER_CROP);
        videoView.start();
        videoView.setMediaPlayerCallback(new TextureVideoView.MediaPlayerCallback() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                videoView.start();
            }

            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                ivC.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                ivC.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

}
