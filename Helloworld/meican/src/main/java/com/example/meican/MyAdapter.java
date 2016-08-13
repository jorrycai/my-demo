package com.example.meican;

import android.content.Context;

import com.example.meican.utils.newsBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by jorry on 2016/7/25.
 */
public class MyAdapter extends CommonAdapter<newsBean> {
    public MyAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, newsBean item, int position) {

    }
}
