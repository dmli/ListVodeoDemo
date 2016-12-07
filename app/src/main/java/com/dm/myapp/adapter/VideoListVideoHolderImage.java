package com.dm.myapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dm.myapp.R;
import com.dm.myapp.base.BasicMultiTypeAdapter;
import com.dm.myapp.bean.VideoInfo;

/**
 * Created by ldm on 16/12/1.
 * 首页列表Image类型Item View
 */

public class VideoListVideoHolderImage implements BasicMultiTypeAdapter.BasicViewHolder<VideoInfo>, View.OnClickListener{

    private ImageView image1;


    @Override
    public View buildView(LayoutInflater layoutInflater, int position, VideoInfo t, ViewGroup parent, View convertView) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.video_list_item_view_image, parent, false);
        }
        image1 = (ImageView) convertView.findViewById(R.id.videoImage1);


        showImage(image1, t.getUrl());

        return convertView;
    }

    private void showImage(ImageView iv, String url) {
        iv.setImageResource(R.mipmap.image1);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "这是一张图片...", Toast.LENGTH_SHORT).show();
    }
}
