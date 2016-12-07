package com.dm.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dm.myapp.base.BasicMultiTypeAdapter;
import com.dm.myapp.bean.VideoInfo;

import java.util.List;

/**
 * Created by ldm on 16/12/1.
 * 视频列表适配器
 */

public class VideoListViewAdapter extends BasicMultiTypeAdapter<VideoInfo> {


    public VideoListViewAdapter(Context context, List<VideoInfo> data) {
        super(context, data);
    }

    /**
     * 这个View代替了getView(...)方法
     *
     * @param position 位置
     * @param v        Convert View
     * @param parent   ViewGroup
     */
    @Override
    protected View buildView(LayoutInflater layoutInflater, int position, View v, ViewGroup parent) {

        VideoInfo bean = getItem(position);
        switch (getItem(position).getViewType()) {
            case VideoInfo.ITEM_TYPE_VIDEO:
                v = new VideoListVideoHolderVideo().buildView(layoutInflater, position, bean, parent, v);
                break;
            default:
                v = new VideoListVideoHolderImage().buildView(layoutInflater, position, bean, parent, v);
                break;
        }
        return v;
    }


}
