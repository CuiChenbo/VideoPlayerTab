package choi.ccb.com.fragmenttabhostdemo.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;


import choi.ccb.com.fragmenttabhostdemo.R;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JzvdStd;

public class VideoPlayerView extends JzvdStd {
    public VideoPlayerView(Context context) {
        super(context);
        init();
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    //自定义UI，隐藏播放图片和底部进度条
    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_std;
    }

    private void init() {
//        findViewById(R.id.bottom_seek_progress)
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo(); //重复播放
    }

    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
        } else {
            JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
        }
    }

    /**
     * 进入全屏模式的时候关闭静音模式
     */
    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
        JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
    }

    /**
     * 退出全屏模式的时候也关闭静音模式
     */
    @Override
    public void playOnThisJzvd() {
        super.playOnThisJzvd();
        JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
    }

    /**
     * 重写播放按钮和加载中按钮的大小，设置为最小，使其隐藏；
     * 重写底部进度条一系列控件的大小，设置为最小，使其隐藏；
     * @param size 把size设置为最小即可
     */
    @Override
    public void changeStartButtonSize(int size) {
        size = 1;
        ViewGroup.LayoutParams lp = startButton.getLayoutParams();
        lp.height = size;
        lp.width = size;
        lp = loadingProgressBar.getLayoutParams();
        lp.height = size;
        lp.width = size;
        lp = bottomContainer.getLayoutParams();
        lp.height = size;
        lp.width = size;
    }
}