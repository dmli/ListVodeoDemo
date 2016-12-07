package com.dm.myapp.view;

import com.dm.myapp.bean.VideoInfo;

import java.util.List;

/**
 * Created by ldm on 16/12/1.
 * 视频列表View接口
 */

public interface IMainVideoListActivity {

    /**
     * 设置视频列表数据源，用来填充listView使用的数据
     *
     * @param data List<VideoInfo>
     */
    void setListViewDataSource(List<VideoInfo> data);

}
