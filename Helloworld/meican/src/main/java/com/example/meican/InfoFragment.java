package com.example.meican;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.meican.utils.newsBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
public class InfoFragment extends ListFragment implements AbsListView.OnScrollListener{
    private ArrayList<HashMap<String, Object>> listItem;
    private newsBean nb;
    newsBean mnb;
    private ListView lv;
    PullRefreshLayout layout;
    public String url = "";
    ImageView mpic;
    View view;
    mAdapter adapter;
    SwipeRefreshLayout swipe;
    int totalItemCount;// 总数量；
    int lastVisibleItem;// 最后一个可见的item；
    boolean isLoading = false;// 正在加载
    View footer;// 底部布局；

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab03, container, false);

        lv = (ListView) view.findViewById(android.R.id.list);
        footer = inflater.inflate(R.layout.tab03_footer, null);
//        footer.findViewById(R.id.tab03_footer_load).setVisibility(View.GONE);
        footer.findViewById(R.id.tab03_footer_load).setVisibility(View.VISIBLE);
        lv.addFooterView(footer, null, false);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        View hv = inflater.inflate(R.layout.tab01_head, null, false);
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.orange, R.color.lawngreen, R.color.dodgerblue);
        lv.addHeaderView(hv, null, false);
        lv.setOnScrollListener(this);
//        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int totalItemCount;// 总数量；
//            int lastVisibleItem;// 最后一个可见的item；
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
////                toast("onScroolStateChanged");
//                if (totalItemCount == lastVisibleItem) {
//                    if (!isLoading) {
//
//                        // 加载更多
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                isLoading = true;
//                                footer.findViewById(R.id.tab03_footer_load).setVisibility(
//                                        View.VISIBLE);
//                                new Thread(runnable0).start();
//                                isLoading = false;
//                                footer.findViewById(R.id.tab03_footer_load).setVisibility(
//                                        View.GONE);
//                            }
//                        }, 2000);
//                    }
//                }
//            }
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                // TODO Auto-generated method stub
//                this.lastVisibleItem = firstVisibleItem + visibleItemCount;
//                this.totalItemCount = totalItemCount;
////                toast("onScrool");
//            }
//        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(true);
                        getData();
                        swipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        new Thread(runnable).start();
        return view;
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //
            // TODO: http request.
            //
//			gethtmlString();
            getData();
        }
    };
    Runnable runnable0 = new Runnable() {
        @Override
        public void run() {
            //
            // TODO: http request.
            //
//			gethtmlString();
            getData(0);
        }
    };
    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (totalItemCount == lastVisibleItem) {
            if (!isLoading) {
                // 加载更多
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = true;
                        footer.findViewById(R.id.tab03_footer_load).setVisibility(
                                View.VISIBLE);
                        new Thread(runnable0).start();
                        isLoading = false;
//                        footer.findViewById(R.id.tab03_footer_load).setVisibility(
//                                View.GONE);
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		new Thread(runnable).start();
    }
    public void getData() {
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
                        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
                        Gson gson = new Gson();
//                        System.out.println(response);
                        nb = gson.fromJson(response, newsBean.class);
//							textView.setText("errmsg:"+nb.getErrMsg());

                        if (nb.getErrNum() == 0) {
                            for (newsBean.RetDataBean newsDatas : nb.getRetData()) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("ItemTitle", newsDatas.getTitle());//加入图片
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
//						System.out.println("nb.getretData.."+nb.getRetData());
//						System.out.println("onresponse-tb----" + nb.getErrMsg());
//						SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), listItem,
//								R.layout.tab03_item, new String[]{"ItemTitle", "ItemCon","mpic"},
//								new int[]{R.id.tab03_item_title, R.id.tab03_item_con, R.id.tab03_item_img}
//						);
                        adapter = new mAdapter(getActivity(), listItem);
                        lv.setAdapter(adapter);
//						lv.setListAdapter(mSimpleAdapter);
                    }
                });
    }
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
                        System.out.println("listitem.size" + listItem.size());
                        Gson gson = new Gson();
                        mnb = gson.fromJson(response, newsBean.class);
//							textView.setText("errmsg:"+nb.getErrMsg());
//                        listItem.clear();
                        if (type == 1) {
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
                        System.out.println("listitem.size after" + listItem.size());
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
                        if (type == 1) {
                            adapter.notifyDataSetInvalidated();

                        } else {
                            adapter.notifyDataSetChanged();
//                            refreshView.startScroll(-200,3000);
                            // 滚动
//                            lv.smoothScrollBy(150,4000);
//                            lv.smoothScrollBy(250, 5000);
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
    public void gethtmlString() {
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
                        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
                        Gson gson = new Gson();
                        System.out.println(response);
                        nb = gson.fromJson(response, newsBean.class);
//							textView.setText("errmsg:"+nb.getErrMsg());

                        if (nb.getErrNum() == 0) {
                            for (newsBean.RetDataBean newsDatas : nb.getRetData()) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("ItemTitle", newsDatas.getTitle());//加入图片
                                map.put("ItemCon", newsDatas.getImage_url());
                                map.put("ItemUrl", newsDatas.getUrl());


//								setUrl(newsDatas.getImage_url());
//			map.put("ItemText", "这是第"+i+"行");
                                listItem.add(map);
                            }
                        } else {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("ItemTitle", "出错了..");//加入图片
                            map.put("ItemCon", "真的出错了..");
                            listItem.add(map);
                        }
                        System.out.println("nb toshring,," + nb.toString());
                        SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), listItem,
                                R.layout.tab03_item, new String[]{"ItemTitle", "ItemCon", "mpic"},
                                new int[]{R.id.tab03_item_title, R.id.tab03_item_con, R.id.tab03_item_img}
                        );
                        lv.setAdapter(mSimpleAdapter);
//						lv.setListAdapter(mSimpleAdapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent1 = new Intent(getActivity(), WebActivity.class);
                                intent1.putExtra("listItem", listItem);
                                System.out.println("" + listItem.get(position).get("ItemTitle"));
                                intent1.putExtra("position", position);
//								intent1.putExtra("url",getUrl());
                                startActivity(intent1);
                            }
                        });
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
    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
    }
    class mAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private ArrayList<HashMap<String, Object>> listItem;

        private mAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
            this.listItem = list;
        }

        @Override
        public int getCount() {
            return listItem.size();
        }

        @Override
        public Object getItem(int i) {
            if (listItem.size() > i) {
                return listItem.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.tab03_item, null);
                holder.img = (ImageView) convertView.findViewById(R.id.tab03_item_img);
                holder.title = (TextView) convertView.findViewById(R.id.tab03_item_title);
                holder.info = (TextView) convertView.findViewById(R.id.tab03_item_con);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (listItem.get(position).get("ItemCon").toString() != null && listItem.get(position).get("ItemCon").toString() != "") {
                Picasso.with(getActivity())
                        .load(listItem.get(position).get("ItemCon").toString())
//                    .placeholder(R.drawable.placeholder)
                        .error(R.drawable.env1)
//                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                        .resize(100, 100)
//                    .centerInside()
                        .tag(getActivity())
                        .into(holder.img);
            } else holder.img.setImageResource(R.drawable.food2);
            holder.title.setText((String) listItem.get(position).get("ItemTitle"));
            holder.info.setText((String) listItem.get(position).get("ItemCon"));
            return convertView;
        }
    }
}







