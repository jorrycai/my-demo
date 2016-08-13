package com.example.meican;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;
import com.example.meican.utils.newsBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


public class EvnFragment extends ListFragment {
    SimpleAdapter mSimpleAdapter;
    String TAG = "meican_Fragment_tab01";
    private ListView lv;
    private List<String> str_name = new ArrayList<String>();
    private XRefreshView refreshView;
    private ArrayList<HashMap<String, Object>> listItem;
    //	CommonAdapter<newsBean> mAdapter;
    CommonAdapter<HashMap<String, Object>> mAdapter;
    ArrayList<newsBean> anb;
    newsBean mnb;


    public static long lastRefreshTime;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            getData(1);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, container, false);
//        View headview =inflater.inflate(R.layout.tab01_head,container,false);
        lv = (ListView) view.findViewById(android.R.id.list);
//        lv.addHeaderView(headview);
        Log.i(TAG, "--------onCreateView");
        View hv = inflater.inflate(R.layout.tab01_head,null,false);
        lv.addHeaderView(hv,null,false);

//		listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
//		for (int i = 0; i < 20; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("ItemImage", R.drawable.env1);//加入图片
//			map.put("ItemTitle", "第" + i + "行");
//			map.put("ItemText", "这是第" + i + "行");
//			listItem.add(map);
//		}
//		anb = new ArrayList<newsBean>();
//		for (int i = 0; i < 20; i++) {
//		newsBean nb =new newsBean();
//		nb.setErrMsg("11124545"+i);
//		nb.setErrNum(440528+i);
//
//		anb.add(nb);
//		}
//		System.out.println("anb"+anb);
//		mSimpleAdapter = new SimpleAdapter(getActivity(), listItem,
//				R.layout.tab01_item, new String[]{"ItemImage", "ItemTitle", "ItemText"},
//				new int[]{R.id.ItemImage, R.id.ItemTitle, R.id.ItemText}
//		);
//		 mAdapter = new CommonAdapter<newsBean>(getActivity(),R.layout.tab01_item,anb){
//			@Override
//			protected void convert(ViewHolder viewHolder, newsBean item, int position) {
//				viewHolder.setText(R.id.ItemTitle,item.getErrMsg()).setText(R.id.ItemText,item.getErrNum()+"").setImageResource(R.id.ItemImage,R.drawable.food2);
//				ImageView imageView = (ImageView) viewHolder.getConvertView().findViewById(R.id.ItemImage);
//
//				Picasso.with(getActivity())
//						.load("http://img4.imgtn.bdimg.com/it/u=1142469417,341639617&fm=21&gp=0.jpg")
//						.placeholder(R.drawable.food1)   //加载失败时 显示的图片
//						.into(imageView);
//
//			}
//		};

        listItem=new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemTitle", "loading..");//加入图片
        map.put("ItemCon", "loading...");
        listItem.add(map);
        mAdapter = new CommonAdapter<HashMap<String, Object>>(getActivity(), R.layout.tab01_item, listItem) {
            @Override
            protected void convert(ViewHolder viewHolder, HashMap<String, Object> item, int position) {
//                               viewHolder.setText(R.id.tab01_item_title, item.get("ItemTitl").toString()).setText(R.id.tab01_item_con, item.get("ItemCon").toString());
                viewHolder.setText(R.id.tab01_item_title, item.get("ItemTitle") + "");
                viewHolder.setText(R.id.tab01_item_con, item.get("ItemCon") + "");
//                TextView thistv = (TextView) viewHolder.getConvertView().findViewById(R.id.tab03_item_title);
//                TextPaint tp = thistv.getPaint();
//                tp.setFakeBoldText(true);
//                thistv.setText(item.get("ItemTitle")+"");

                ImageView thisimageView = (ImageView) viewHolder.getConvertView().findViewById(R.id.tab01_item_img);
                if (item.get("ItemCon").toString() != null && item.get("ItemCon").toString() != "") {
                                    Picasso.with(getActivity())
                                            .load(item.get("ItemCon").toString())
                                            .placeholder(R.drawable.food1)   //加载失败时 显示的图片
                                            .into(thisimageView);
                                } else {
//                                    thisimageView.setImageResource(R.drawable.myinfo2);
                                }
            }
        };

        lv.setAdapter(mAdapter);
        new Thread(runnable).start();


//		lv.setAdapter(mAdapter);

        refreshView = (XRefreshView) view.findViewById(R.id.custom_view);
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
//		refreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        refreshView.setAutoRefresh(false);
        // 底部"加载完成" 固定时间
//        refreshView.setPinnedTime(1000);
//        refreshView.setPinnedContent(true);


        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        toast("刷新");
                        getData(1);

                        refreshView.stopRefresh();

                        lastRefreshTime = refreshView.getLastRefreshTime();

                    }
                }, 2000);
            }
            @Override
            public void onLoadMore(boolean isSlience) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        toast("加载更多");
                        getData(0);
                        // // 告诉它数据更新了
//                        mAdapter.notifyDataSetChanged();
                        // 停止动画
                        mAdapter.notifyDataSetChanged();
//                        lv.setSelection(listItem.size());
                        refreshView.stopLoadMore();

//                        refreshView.startScroll(100,100);

                    }
                }, 1000);

            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                if (direction > 0) {
                    toast("下拉");
                } else {
                    toast("上拉");
                }
            }
        });
        refreshView.setOnAbsListViewScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.i("onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                LogUtils.i("onScroll");
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--------onCreate");

    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    //	@SuppressWarnings("unchecked")
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//		newsBean nb = (newsBean) l.getItemAtPosition(position);
//		System.out.println(nb.getErrMsg()+"+++++++++getErrMsg()");
//		Toast.makeText(getActivity(), TAG+l.getItemIdAtPosition(position)+"getErrMsg()"+nb.getErrMsg(), Toast.LENGTH_LONG).show();
//		System.out.println(position);
//	}
    // 参数 1为初始化, 0为加载更多
    public void getData(final int type) {
        String url = "http://apis.baidu.com/songshuxiansheng/news/news";
        Map<String, String> map2 = new HashMap<>();
        map2.put("apikey", "69423f293c4e6e6ea93e1ff05da05c9a");
        OkHttpUtils
                .get()
                .url(url)
                .headers(map2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "出错了....", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
                       System.out.println("listitem.size" +listItem.size());
                        Gson gson = new Gson();
                        mnb = gson.fromJson(response, newsBean.class);
//							textView.setText("errmsg:"+nb.getErrMsg());
//                        listItem.clear();
                        if(type==1){
                            listItem.clear();
                        }
                        if (mnb.getErrNum() == 0) {
                            for (newsBean.RetDataBean newsDatas : mnb.getRetData()) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("ItemTitle", newsDatas.getTitle());
                                map.put("ItemCon", newsDatas.getImage_url());
                                map.put("ItemUrl", newsDatas.getUrl());
                                listItem.add(map);
                            }
                        } else {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("ItemTitle", "出错了..");//加入图片
                            map.put("ItemCon", "真的出错了..");
                            listItem.add(map);
                        }
                        System.out.println("listitem.size after" +listItem.size());
//                        mAdapter = new CommonAdapter<HashMap<String, Object>>(getActivity(), R.layout.tab01_item, listItem) {
//                            @Override
//                            protected void convert(ViewHolder viewHolder, HashMap<String, Object> item, int position) {
////                               viewHolder.setText(R.id.tab01_item_title, item.get("ItemTitl").toString()).setText(R.id.tab01_item_con, item.get("ItemCon").toString());
//                                viewHolder.setText(R.id.tab01_item_title, item.get("ItemTitle") + "");
//                                viewHolder.setText(R.id.tab01_item_con, item.get("ItemCon") + "");
//                                ImageView thisimageView = (ImageView) viewHolder.getConvertView().findViewById(R.id.tab01_item_img);
//                                if (item.get("ItemCon").toString() != null && item.get("ItemCon").toString() != "") {
//                                    Picasso.with(getActivity())
//                                            .load(item.get("ItemCon").toString())
//                                            .placeholder(R.drawable.food1)   //加载失败时 显示的图片
//                                            .into(thisimageView);
//                                } else {
//                                    thisimageView.setImageResource(R.drawable.myinfo1);
//                                }
//                            }
//                        };
                        if(type==1){
                            mAdapter.notifyDataSetInvalidated();

                        }else{
                            mAdapter.notifyDataSetChanged();
//                            refreshView.startScroll(-200,3000);
                            // 滚动
//                            lv.smoothScrollBy(150,4000);
//                            lv.smoothScrollBy(250,5000);

                        }

//                        mAdapter.notifyDataSetChanged();
//                        lv.setAdapter(mAdapter);
//						lv.setListAdapter(mAdapter);
//                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                                Intent intent1 = new Intent(getActivity(), WebActivity.class);
//                                intent1.putExtra("listItem", listItem);
//                                System.out.println("" + listItem.get(position).get("ItemTitle"));
//                                intent1.putExtra("position", position);
////								intent1.putExtra("url",getUrl());
//                                startActivity(intent1);
//                            }
//                        });
                    }
                });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent1 = new Intent(getActivity(), WebActivity.class);
                                intent1.putExtra("listItem", listItem);
                                System.out.println("title" + listItem.get(position-lv.getHeaderViewsCount()).get("ItemTitle"));
                                intent1.putExtra("position", position-lv.getHeaderViewsCount());
//								intent1.putExtra("url",getUrl());
                                startActivity(intent1);
    }
}

