package choi.ccb.com.fragmenttabhostdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;

//import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
//import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
//import org.yczbj.ycvideoplayerlib.inter.listener.OnCompletedListener;
//import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import choi.ccb.com.fragmenttabhostdemo.R;
import choi.ccb.com.fragmenttabhostdemo.video.StandardVideoController;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    private IjkVideoView ijkVideoView;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_videoview, container, false);
//        initView(v);
        initView2(v);
        initView3(v);
        return v;
    }

    private void initView3(View v) {
        ijkVideoView = v.findViewById(R.id.player);
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        ijkVideoView.setUrl(uri); //设置视频地址
        ijkVideoView.setMute(true);
//        ijkVideoView.setTitle("网易公开课-如何掌控你的自由时间"); //设置视频标题
        StandardVideoController controller = new StandardVideoController(getActivity());
        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        controller.getThumb().setImageResource(R.mipmap.ic_launcher_round);

        //高级设置（可选，须在start()之前调用方可生效）
        PlayerConfig playerConfig = new PlayerConfig.Builder()
//                .autoRotate() //启用重力感应自动进入/退出全屏功能
//                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
//                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
//                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭AudioFocusChange监听
                .setLooping() //循环播放当前正在播放的视频
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);

        ijkVideoView.start(); //开始播放，不调用则不自动播放
    }

    String uri="/storage/emulated/0/Download/APPNEWCARDEMO.mp4";
    private void initView2(View v) {
//        VideoPlayer videoPlayer = v.findViewById(R.id.video_player);
//        //设置播放类型
//// IjkPlayer or MediaPlayer
//        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
////设置视频地址和请求头部
//        videoPlayer.setUp(uri, null);
////创建视频控制器
//        VideoPlayerController controller = new VideoPlayerController(getActivity());
//        controller.setTitle("自定义视频播放器可以播放视频拉");
////设置视频控制器
//        videoPlayer.setController(controller);
//        videoPlayer.start();

    }

    private void initView(View v) {
        VideoView videoView = v.findViewById(R.id.vv);
        String uri="/storage/emulated/0/Download/APPNEWCARDEMO.mp4";
        videoView.setVideoPath(uri);
        videoView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }
}
