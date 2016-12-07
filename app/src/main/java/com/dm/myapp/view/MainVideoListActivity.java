package com.dm.myapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.dm.myapp.R;
import com.dm.myapp.adapter.VideoListViewAdapter;
import com.dm.myapp.bean.VideoInfo;
import com.dm.myapp.ctrl.MainVideoListActivityCtrl;

import java.util.ArrayList;
import java.util.List;

public class MainVideoListActivity extends Activity implements IMainVideoListActivity {

    private MainVideoListActivityCtrl control;
    private VideoListViewAdapter videoListViewAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        control = new MainVideoListActivityCtrl(this);


    }

    private void initView() {

        listView = (ListView) findViewById(R.id.listView);
        /**
         * 创建空布局
         */
        videoListViewAdapter = new VideoListViewAdapter(this, new ArrayList<VideoInfo>());
        listView.setAdapter(videoListViewAdapter);



    }


    /**
     * 设置视频列表数据源，用来填充listView使用的数据
     *
     * @param data List<VideoInfo>
     */
    @Override
    public void setListViewDataSource(List<VideoInfo> data) {

        if (videoListViewAdapter == null) {
            videoListViewAdapter = new VideoListViewAdapter(this, data);
        } else {
            videoListViewAdapter.addAll(data);
        }
    }
}
