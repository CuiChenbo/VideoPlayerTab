package choi.ccb.com.fragmenttabhostdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import choi.ccb.com.fragmenttabhostdemo.R;
import choi.ccb.com.fragmenttabhostdemo.video.MyJzvdStd;
import choi.ccb.com.fragmenttabhostdemo.video.VideoPlayerView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


/**
 * JZVD 视频播放
 */
public class HotFragment extends Fragment {


    public HotFragment() {
        // Required empty public constructor
    }

private VideoPlayerView vv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vv, container, false);
       vv = (v.findViewById(R.id.vv));
       initData();
        return v;
    }

    private void initData() {
        vv.setUp("/storage/emulated/0/Download/APPNEWCARDEMO.mp4"
                , "enen", JzvdStd.SCREEN_WINDOW_NORMAL);
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);  //裁剪全屏
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(vv.thumbImageView);
        vv.startButton.performClick();
//        vv.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                , "饺子快长大", JzvdStd.SCREEN_WINDOW_NORMAL);
    }

}
