//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package choi.ccb.com.fragmenttabhostdemo.video.videoplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Handler.Callback;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import java.io.IOException;
import java.util.Map;

@TargetApi(14)
public class TextureVideoView extends TextureView implements SurfaceTextureListener, Callback, OnPreparedListener, OnVideoSizeChangedListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnBufferingUpdateListener {
    private static final String TAG = "TextureVideoView";
    private static final boolean SHOW_LOGS = false;
    private volatile int mCurrentState;
    private volatile int mTargetState;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int MSG_START = 1;
    private static final int MSG_PAUSE = 4;
    private static final int MSG_STOP = 6;
    private Uri mUri;
    private Context mContext;
    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private TextureVideoView.MediaPlayerCallback mMediaPlayerCallback;
    private Handler mHandler;
    private Handler mVideoHandler;
    private boolean mSoundMute;
    private boolean mHasAudio;
    private ScaleType mScaleType;
    private static final HandlerThread sThread = new HandlerThread("VideoPlayThread");

    public TextureVideoView(Context context) {
        this(context, (AttributeSet)null);
    }

    public TextureVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextureVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mScaleType = ScaleType.NONE;
        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, styleable.scaleStyle, 0, 0);
//            if (a != null) {
//                int scaleType = a.getInt(styleable.scaleStyle_scaleType, ScaleType.NONE.ordinal());
                int scaleType = ScaleType.NONE.ordinal();
//                a.recycle();
                this.mScaleType = ScaleType.values()[scaleType];
                this.init();
//            }
        }
    }

    public void setMediaPlayerCallback(TextureVideoView.MediaPlayerCallback mediaPlayerCallback) {
        this.mMediaPlayerCallback = mediaPlayerCallback;
        if (mediaPlayerCallback == null) {
            this.mHandler.removeCallbacksAndMessages((Object)null);
        }

    }

    public int getCurrentPosition() {
        return this.isInPlaybackState() ? this.mMediaPlayer.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return this.isInPlaybackState() ? this.mMediaPlayer.getDuration() : -1;
    }

    public int getVideoHeight() {
        return this.mMediaPlayer != null ? this.mMediaPlayer.getVideoHeight() : 0;
    }

    public int getVideoWidth() {
        return this.mMediaPlayer != null ? this.mMediaPlayer.getVideoWidth() : 0;
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        this.scaleVideoSize(this.getVideoWidth(), this.getVideoHeight());
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    private void scaleVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth != 0 && videoHeight != 0) {
            Size viewSize = new Size(this.getWidth(), this.getHeight());
            Size videoSize = new Size(videoWidth, videoHeight);
            ScaleManager scaleManager = new ScaleManager(viewSize, videoSize);
            final Matrix matrix = scaleManager.getScaleMatrix(this.mScaleType);
            if (matrix != null) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    this.setTransform(matrix);
                } else {
                    this.mHandler.postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            TextureVideoView.this.setTransform(matrix);
                        }
                    });
                }

            }
        }
    }

    public boolean handleMessage(Message msg) {
        Class var2 = TextureVideoView.class;
        synchronized(TextureVideoView.class) {
            switch(msg.what) {
            case 1:
                this.openVideo();
                break;
            case 4:
                if (this.mMediaPlayer != null) {
                    this.mMediaPlayer.pause();
                }

                this.mCurrentState = 4;
                break;
            case 6:
                this.release(true);
            }

            return true;
        }
    }

    private void init() {
        if (!this.isInEditMode()) {
            this.mContext = this.getContext();
            this.mCurrentState = 0;
            this.mTargetState = 0;
            this.mHandler = new Handler();
            this.mVideoHandler = new Handler(sThread.getLooper(), this);
            this.setSurfaceTextureListener(this);
        }

    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
        }

    }

    private void openVideo() {
        if (this.mUri != null && this.mSurface != null && this.mTargetState == 3) {
            this.mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
            this.mAudioManager.requestAudioFocus((OnAudioFocusChangeListener)null, 3, 1);
            this.release(false);

            try {
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setOnPreparedListener(this);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this);
                this.mMediaPlayer.setOnCompletionListener(this);
                this.mMediaPlayer.setOnErrorListener(this);
                this.mMediaPlayer.setOnInfoListener(this);
                this.mMediaPlayer.setOnBufferingUpdateListener(this);
                this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
                this.mMediaPlayer.setSurface(this.mSurface);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setLooping(true);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                this.mTargetState = 1;
                this.mHasAudio = true;
                if (VERSION.SDK_INT >= 16) {
                    try {
                        MediaExtractor mediaExtractor = new MediaExtractor();
                        mediaExtractor.setDataSource(this.mContext, this.mUri, (Map)null);

                        for(int i = 0; i < mediaExtractor.getTrackCount(); ++i) {
                            MediaFormat format = mediaExtractor.getTrackFormat(i);
                            String mime = format.getString("mime");
                            if (mime.startsWith("audio/")) {
                                this.mHasAudio = true;
                                break;
                            }
                        }
                    } catch (Exception var5) {
                        ;
                    }
                }
            } catch (IllegalArgumentException | IOException var6) {
                this.mCurrentState = -1;
                this.mTargetState = -1;
                if (this.mMediaPlayerCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            if (TextureVideoView.this.mMediaPlayerCallback != null) {
                                TextureVideoView.this.mMediaPlayerCallback.onError(TextureVideoView.this.mMediaPlayer, 1, 0);
                            }

                        }
                    });
                }
            }

        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.mSurface = new Surface(surface);
        if (this.mTargetState == 3) {
            this.start();
        }

    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.mSurface = null;
        this.stop();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void setVideoPath(String path) {
        this.setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        this.mUri = uri;
    }

    public void start() {
        this.mTargetState = 3;
        if (this.isInPlaybackState()) {
            this.mVideoHandler.obtainMessage(6).sendToTarget();
        }

        if (this.mUri != null && this.mSurface != null) {
            this.mVideoHandler.obtainMessage(1).sendToTarget();
        }

    }

    public void pause() {
        this.mTargetState = 4;
        if (this.isPlaying()) {
            this.mVideoHandler.obtainMessage(4).sendToTarget();
        }

    }

    public void resume() {
        this.mTargetState = 3;
        if (!this.isPlaying()) {
            this.mVideoHandler.obtainMessage(1).sendToTarget();
        }

    }

    public void stop() {
        this.mTargetState = 5;
        if (this.isInPlaybackState()) {
            this.mVideoHandler.obtainMessage(6).sendToTarget();
        }

    }

    public boolean isPlaying() {
        return this.isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public void mute() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(0.0F, 0.0F);
            this.mSoundMute = true;
        }

    }

    public void unMute() {
        if (this.mAudioManager != null && this.mMediaPlayer != null) {
            int max = 100;
            int audioVolume = 100;
            double numerator = max - audioVolume > 0 ? Math.log((double)(max - audioVolume)) : 0.0D;
            float volume = (float)(1.0D - numerator / Math.log((double)max));
            this.mMediaPlayer.setVolume(volume, volume);
            this.mSoundMute = false;
        }

    }

    public boolean isMute() {
        return this.mSoundMute;
    }

    public boolean isHasAudio() {
        return this.mHasAudio;
    }

    private boolean isInPlaybackState() {
        return this.mMediaPlayer != null && this.mCurrentState != -1 && this.mCurrentState != 0 && this.mCurrentState != 1;
    }

    public void onCompletion(final MediaPlayer mp) {
        this.mCurrentState = 5;
        this.mTargetState = 5;
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onCompletion(mp);
                    }

                }
            });
        }

    }

    public boolean onError(final MediaPlayer mp, final int what, final int extra) {
        this.mCurrentState = -1;
        this.mTargetState = -1;
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onError(mp, what, extra);
                    }

                }
            });
        }

        return true;
    }

    public void onPrepared(final MediaPlayer mp) {
        if (this.mTargetState == 1 && this.mCurrentState == 1) {
            this.mCurrentState = 2;
            if (this.isInPlaybackState()) {
                this.mMediaPlayer.start();
                this.mCurrentState = 3;
                this.mTargetState = 3;
            }

            if (this.mMediaPlayerCallback != null) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        if (TextureVideoView.this.mMediaPlayerCallback != null) {
                            TextureVideoView.this.mMediaPlayerCallback.onPrepared(mp);
                        }

                    }
                });
            }

        }
    }

    public void onVideoSizeChanged(final MediaPlayer mp, final int width, final int height) {
        this.scaleVideoSize(width, height);
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onVideoSizeChanged(mp, width, height);
                    }

                }
            });
        }

    }

    public void onBufferingUpdate(final MediaPlayer mp, final int percent) {
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onBufferingUpdate(mp, percent);
                    }

                }
            });
        }

    }

    public boolean onInfo(final MediaPlayer mp, final int what, final int extra) {
        if (this.mMediaPlayerCallback != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (TextureVideoView.this.mMediaPlayerCallback != null) {
                        TextureVideoView.this.mMediaPlayerCallback.onInfo(mp, what, extra);
                    }

                }
            });
        }

        return true;
    }

    static {
        sThread.start();
    }

    public interface MediaPlayerCallback {
        void onPrepared(MediaPlayer var1);

        void onCompletion(MediaPlayer var1);

        void onBufferingUpdate(MediaPlayer var1, int var2);

        void onVideoSizeChanged(MediaPlayer var1, int var2, int var3);

        boolean onInfo(MediaPlayer var1, int var2, int var3);

        boolean onError(MediaPlayer var1, int var2, int var3);
    }
}
