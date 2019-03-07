package choi.ccb.com.fragmenttabhostdemo.fragment;


import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import choi.ccb.com.fragmenttabhostdemo.R;


/**
 * SurfaceView + MediaPlayer 视频播放
 */
public class MineFragment extends Fragment {


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
       initView(v);
//       initView2(v);
        ImageView imageView = v.findViewById(R.id.image);
        imageView.setVisibility(View.INVISIBLE);
//        Glide.with(getActivity()).load("/storage/emulated/0/Download/APPNEWCARDEMO.mp4").into(imageView);
        return v;
    }


    private TextureView textureView;
    private MediaPlayer player;
    private void initView(View v) {
        textureView = v.findViewById(R.id.textureView);
        String uri="/storage/emulated/0/Download/APPNEWCARDEMO.mp4";

        player = new MediaPlayer();

        try {
            player.setDataSource(uri);
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                    player.setSurface(new Surface(surfaceTexture));
                    try {
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            player.start();
                            player.setLooping(true);
                        }
                    });
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    return true;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private SurfaceView surfaceView;
//    private MediaPlayer player;
//    private SurfaceHolder holder;
//    private void initView2(View v) {
//        surfaceView = v.findViewById(R.id.surfaceView);
//        String uri="/storage/emulated/0/Download/APPNEWCARDEMO.mp4";
//
//        player = new MediaPlayer();
//
//        try {
//            player.setDataSource(uri);
//            holder=surfaceView.getHolder();
//            holder.addCallback(new MyCallBack());
//            try {
//                player.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    player.start();
//                    player.setLooping(true);
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private class MyCallBack implements SurfaceHolder.Callback {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            player.setDisplay(holder);
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//
//        }
//    }
}
