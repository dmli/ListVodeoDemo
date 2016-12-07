package com.dm.myapp.bean;

import com.dm.myapp.base.BasicMultiTypeAdapter;

/**
 * Created by ldm on 16/12/1.
 * 视频数据数据Bean
 */

public class VideoInfo implements BasicMultiTypeAdapter.BasicMultiTypeBean {


    public static final String ITEM_TYPE_IMAGE = "image";
    public static final String ITEM_TYPE_VIDEO = "video";

    private String url;
    private String type;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getViewType() {
        return type;
    }
}
