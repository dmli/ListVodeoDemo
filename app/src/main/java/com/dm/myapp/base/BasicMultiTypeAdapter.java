package com.dm.myapp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldm on 14-3-5.
 * 基础的适配器模版BasicAdapter的共享版，使用外界的List<T>结果集地址，可以达到多适配器共享结果集的效果
 */
public abstract class BasicMultiTypeAdapter<T extends BasicMultiTypeAdapter.BasicMultiTypeBean> extends BaseAdapter {

    private Context context;
    private List<T> data;
    private BasicAdapterCacheManager cacheViewManager;

    public BasicMultiTypeAdapter(Context context, List<T> data) {
        this.data = data;
        this.context = context;
        this.cacheViewManager = new BasicAdapterCacheManager();
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cacheView = null;
        if (convertView == null) {
            convertView = new LinearLayout(context);
        } else {
            ViewGroup vg = ((ViewGroup) convertView);
            cacheView = vg.getChildAt(0);
            vg.removeAllViews();
        }

        /**
         * 查找缓存中是否有可用的View
         */
        T f = getItem(position);
        View v = cacheViewManager.findCacheView(cacheView, String.valueOf(f.getViewType()));

        /**
         * 编译View
         */
        v = buildView(getLayoutInflater(), position, v, parent);
        BasicAdapterCacheManager.syncTag(v, String.valueOf(f.getViewType()));

        /**
         * 将缓存加入到Item中
         */
        ((ViewGroup) convertView).addView(v);
        return convertView;
    }

    /**
     * 这个View代替了getView(...)方法
     *
     * @param position 位置
     * @param v        Convert View
     * @param parent   ViewGroup
     */
    protected abstract View buildView(LayoutInflater layoutInflater, int position, View v, ViewGroup parent);

    @Override
    public int getCount() {
        return this.data == null ? 0 : this.data.size();
    }

    /**
     * 获取数据集合
     *
     * @return List<T>
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 删除全部数据
     */
    public void removeAll() {
        if (this.data != null) {
            this.data.clear();
        }
    }

    /**
     * 根据位置删除数据中的数据项
     *
     * @param position 位置
     */
    public T remove(int position) {
        if (this.data != null && position >= 0 && position < this.data.size()) {
            return this.data.remove(position);
        }
        return null;
    }

    /**
     * 向adapter中增加数据
     *
     * @param data T
     */
    public void add(T data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
    }

    /**
     * 向adapter中增加数据
     *
     * @param data List<T>
     */
    public void addAll(List<? extends T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data.addAll(data);
    }

    @Override
    public T getItem(int position) {
        return (this.data == null || position < 0 || position >= this.data.size()) ? null : this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ViewHolder
     * 可以指定返回类型
     * TK extends TNCBasicMultiTypeAdapter.BasicMultiTypeBean
     */
    public interface BasicViewHolder<TK extends BasicMultiTypeBean> {

        View buildView(LayoutInflater layoutInflater, int position, TK t, ViewGroup parent, View convertView);
    }

    /**
     * 使用这个适配器
     */
    public interface BasicMultiTypeBean {

        /**
         * 用来区分当前List Item的类型
         *
         * @return type String
         */
        String getViewType();
    }
}
