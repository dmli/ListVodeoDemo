package com.dm.myapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.dm.myapp.R;
import com.dm.myapp.base.BasicMultiTypeAdapter;
import com.dm.myapp.bean.VideoInfo;
import com.dm.myapp.view.TextureVideoPlayView;

/**
 * Created by ldm on 16/12/1.
 * 首页列表Image类型Item View
 */

public class VideoListVideoHolderVideo implements BasicMultiTypeAdapter.BasicViewHolder<VideoInfo>, View.OnClickListener, TextureVideoPlayView.OnStateListener {

    private TextureVideoPlayView playView;
    private View placeholderView;
    private VideoInfo videoInfo;

    @Override
    public View buildView(LayoutInflater layoutInflater, int position, VideoInfo t, ViewGroup parent, View convertView) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.video_list_item_view_video, parent, false);
        }
        videoInfo = t;
        placeholderView = convertView.findViewById(R.id.placeholderView);
        placeholderView.setVisibility(View.VISIBLE);
        playView = (TextureVideoPlayView) convertView.findViewById(R.id.video1);
        playView.setOnStateListener(this);
        playView.setOnClickListener(this);

        return convertView;
    }

    private void showVideo(TextureVideoPlayView iv, String url) {
        iv.playAsync(url);
    }

    @Override
    public void onClick(View view) {
        showVideo(playView, videoInfo.getUrl());
    }

    /**
     * 方法在触发release方法前触发
     */
    @Override
    public void onReleaseBefore() {
        placeholderView.setVisibility(View.VISIBLE);
    }

    /**
     * 方法在视频播放后触发
     */
    @Override
    public void onPlayingAfter() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                placeholderView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        placeholderView.startAnimation(alphaAnimation);
    }
}
