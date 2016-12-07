package com.dm.myapp.ctrl;

import com.dm.myapp.bean.VideoInfo;
import com.dm.myapp.view.IMainVideoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldm on 16/12/1.
 * 视频首页控制层 （control）
 */

public class MainVideoListActivityCtrl {

    private IMainVideoListActivity mainVideoListActivity;

    public MainVideoListActivityCtrl(IMainVideoListActivity mainVideoListActivity) {
        this.mainVideoListActivity = mainVideoListActivity;

        onCreate();
    }

    private void onCreate() {

        /**
         * 创建测试数据
         */
        List<VideoInfo> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoInfo video1 = new VideoInfo();
            video1.setType(i % 2 == 0 ? VideoInfo.ITEM_TYPE_IMAGE : VideoInfo.ITEM_TYPE_VIDEO);
            video1.setUrl("http://");
            data.add(video1);
        }

        mainVideoListActivity.setListViewDataSource(data);


    }


}
