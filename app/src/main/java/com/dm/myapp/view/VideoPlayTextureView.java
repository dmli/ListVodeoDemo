package com.dm.myapp.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by ldm on 16/12/1.
 * 用来播放列表视频的View
 */

public class VideoPlayTextureView extends TextureView implements TextureView.SurfaceTextureListener {


    public VideoPlayTextureView(Context context) {
        super(context);
    }

    public VideoPlayTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
