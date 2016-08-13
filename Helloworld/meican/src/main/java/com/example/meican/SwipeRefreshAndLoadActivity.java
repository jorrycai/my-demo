package com.example.meican;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jorry on 2016/8/8.
 */
public class SwipeRefreshAndLoadActivity extends Activity implements AbsListView.OnScrollListener{
    int totalItemCount;// 总数量；
    int lastVisibleItem;// 最后一个可见的item；
    boolean isLoading;// 正在加载
    View footer;// 底部布局；
    ArrayList<HashMap<String, Object>> listItem;
    SimpleAdapter mSimpleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LayoutInflater mLi = LayoutInflater.from(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        //设置下拉刷新 小圈圈颜色 ,最多可设置4个颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.dodgerblue, R.color.lawngreen, R.color.colorAccent, R.color.orange);
        ListView lv = (ListView) findViewById(R.id.list);
        //配置底部 加载更多 的布局
        footer = mLi.inflate(R.layout.footer, null);
        footer.findViewById(R.id.footer_load).setVisibility(View.VISIBLE);
        lv.addFooterView(footer, null, false);
        //模拟数据
        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemContent", "这是第" + i + "行");
            listItem.add(map);
        }
        //构造适配器
        mSimpleAdapter = new SimpleAdapter(this, listItem,
                R.layout.item, new String[]{"ItemTitle", "ItemContent"},
                new int[]{R.id.item_title, R.id.item_content}
        );
        lv.setAdapter(mSimpleAdapter);
        //设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SwipeRefreshAndLoadActivity.this, listItem.get(i).get("ItemTitle").toString(), Toast.LENGTH_LONG).show();
            }
        });
        //设置下拉刷新监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRefreshDatas();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //设置滚动事件监听, 实现 上拉加载更多
        lv.setOnScrollListener(this);
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//        System.out.println("hehe"+isLoading);
        if (totalItemCount == lastVisibleItem&& scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 加载更多
                        footer.findViewById(R.id.footer_load).setVisibility(View.VISIBLE);
                        doLoadMoreDatas();
                        isLoading = false;
                    }
                }, 2000);

            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }
    //刷新数据, 并通知adapter更新
    public void doRefreshDatas(){
        listItem.clear();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行new");
            map.put("ItemContent", "这是第" + i + "行new");
            listItem.add(map);
        }
        mSimpleAdapter.notifyDataSetChanged();
    }
    //加载更多数据,并通知adapter更新
    public void doLoadMoreDatas(){
        int j =listItem.size()+10;
        for (int i =listItem.size(); i<=j; i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemContent", "这是第" + i + "行");
            listItem.add(map);
        }
        mSimpleAdapter.notifyDataSetChanged();
    }
}