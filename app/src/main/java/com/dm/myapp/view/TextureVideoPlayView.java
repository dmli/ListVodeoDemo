package com.dm.myapp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.File;
import java.io.IOException;

/**
 * Created by ldm on 15/12/30.
 * 用来播放列表视频的View
 */
public class TextureVideoPlayView extends TextureView implements TextureView.SurfaceTextureListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {


    private static TextureVideoPlayView nowPlayView;
    private OnStateListener onStateListener;

    private SurfaceTexture surfaceTexture;
    private MyMediaPlayer mediaPlayer;
    private String videoPath;

    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PREPARE = 3;
    private int currentState = STATE_IDLE;

    private static final int COLOR_IDLE = Color.WHITE;
    private static final int COLOR_PLAYING = Color.TRANSPARENT;

    public boolean isPlayAudio = true;


    public TextureVideoPlayView(Context context) {
        super(context);
        init();
    }

    public TextureVideoPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextureVideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    /**
     * 返回当前状态
     *
     * @return STATE_ERROR | STATE_IDLE | STATE_PLAYING | STATE_PAUSED | STATE_PREPARE
     */
    public int getCurrentState() {
        return currentState;
    }

    private void init() {
        //初始化MediaPlayer
        setKeepScreenOn(true);
        setSurfaceTextureListener(this);
        surfaceTexture = getSurfaceTexture();
    }

    @Override
    protected void onAttachedToWindow() {
        setBackgroundColor(COLOR_IDLE);
        setVisibility(VISIBLE);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        setVisibility(GONE);
        release();
        super.onDetachedFromWindow();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        currentState = STATE_IDLE;
        mp.seekTo(0);
        mp.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (onSizeChangeListener != null) {
            onSizeChangeListener.onSizeChange(this, mp.getVideoWidth(), mp.getVideoHeight());
        }
        mediaPlayer.start();
        currentState = STATE_PLAYING;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setBackgroundColor(COLOR_PLAYING);
            }
        }, 80);
    }

    /**
     * 恢复播放，暂停后可以用这个方法恢复
     */
    public void resumePlay() {
        if (isEffectiveVideoPath()) {
            stateControl.play = true;
            try {
                if (stateControl.surfaceState == 1) {
                    initMediaPlayer();
                    mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                    currentState = STATE_PREPARE;
                    stateControl.play = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 播放本地视频
     *
     * @param videoPath 本地地址
     */
    public void playAsync(String videoPath) {
        /**
         * 如果有播放中的内容，尝试释放
         */
        if (nowPlayView != null) {
            if (nowPlayView == this && currentState == STATE_PLAYING) {
                return;
            }
            nowPlayView.release();
            nowPlayView = null;
        }

        String gen = Environment.getExternalStorageDirectory().toString();
        this.videoPath = gen + "/VIDEO.mp4";//使用假数据测试
        stateControl.play = true;
        try {
            if (stateControl.surfaceState == 1) {
                initMediaPlayer();
                mediaPlayer.prepareAsync();
                currentState = STATE_PREPARE;
                stateControl.play = false;
            }
            nowPlayView = this;
            if (onStateListener != null) {
                onStateListener.onPlayingAfter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化MediaPlayer
     *
     * @throws IOException
     */
    private void initMediaPlayer() throws Exception {
        //初始化前先尝试释放
        stop();
        /**
         * 初始化MediaPlayer控件
         */
        mediaPlayer = new MyMediaPlayer();
        mediaPlayer.reset();
        if (!isPlayAudio) {
            mediaPlayer.setVolume(0, 0);
        }
        mediaPlayer.setDataSource(videoPath);
        mediaPlayer.setSurface(new Surface(surfaceTexture));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        Log.e("aaaaaaa", "initMediaPlayer = " + videoPath);
    }

    /**
     * 设置本地视频
     *
     * @param videoPath 视频地址
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    /**
     * 返回视频地址是否有效
     *
     * @return true/false
     */
    public boolean isEffectiveVideoPath() {
        return videoPath != null && new File(videoPath).exists();
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentState = STATE_PAUSED;
        }
    }

    /**
     * 停止播放，并释放资源
     */
    public void stop() {
        setBackgroundColor(COLOR_IDLE);
        try {
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(null);
                mediaPlayer.setOnPreparedListener(null);
                mediaPlayer.setOnErrorListener(null);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentState = STATE_IDLE;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (onStateListener != null) {
            onStateListener.onReleaseBefore();
        }
        stop();
        videoPath = null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        currentState = STATE_ERROR;
        setBackgroundColor(COLOR_IDLE);
        return true;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        surfaceTexture = surface;
        stateControl.surfaceState = 1;
        if (stateControl.play) {
            try {
                initMediaPlayer();
                mediaPlayer.prepareAsync();
                currentState = STATE_PREPARE;
            } catch (Exception e) {
                e.printStackTrace();
            }
            stateControl.play = false;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        surfaceTexture = surface;
        stateControl.surfaceState = 1;
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        stateControl.surfaceState = 0;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        surfaceTexture = surface;
        stateControl.surfaceState = 1;
    }


    public class MyMediaPlayer extends MediaPlayer {
        @Override
        protected void finalize() {
            try {
                super.finalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setVolume(float leftVolume, float rightVolume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(leftVolume, rightVolume);
        }
    }

    /**
     * 不播放音量
     */
    public void notAudio() {
        isPlayAudio = false;
    }

    private PlayState stateControl = new PlayState();

    class PlayState {
        int surfaceState;
        boolean play;
    }

    private OnSizeChangeListener onSizeChangeListener;

    public void setOnSizeChangeListener(OnSizeChangeListener onSizeChangeListener) {
        this.onSizeChangeListener = onSizeChangeListener;
    }

    /**
     * 录制大小视频大小发生变化时触发
     */
    public interface OnSizeChangeListener {
        void onSizeChange(View target, int width, int height);
    }

    public OnStateListener getOnStateListener() {
        return onStateListener;
    }

    public void setOnStateListener(OnStateListener onStateListener) {
        this.onStateListener = onStateListener;
    }

    /**
     * 状态发生变化时触发
     */
    public interface OnStateListener {

        /**
         * 方法在触发release方法前触发
         */
        void onReleaseBefore();

        /**
         * 方法在视频播放后触发
         */
        void onPlayingAfter();
    }
}
