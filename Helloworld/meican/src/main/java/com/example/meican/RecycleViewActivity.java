package com.example.meican;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meican.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorry on 2016/7/31.
 */
public class RecycleViewActivity extends Activity {
    ArrayList<String> mDatas;
    private myRecycleAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        initData();
        recycleAdapter =new myRecycleAdapter(this ,mDatas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        //listview
        recyclerView.setLayoutManager(layoutManager);
        //gridview网格
//        recyclerView .setLayoutManager(new GridLayoutManager( this,4));
        //瀑布流
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerView.setAdapter( recycleAdapter);
        //设置分隔线
//        recyclerView.addItemDecoration( new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration( new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }
    private void initData() {
        mDatas = new ArrayList<String>();
        for ( int i=0; i < 100; i++) {
            mDatas.add( "item"+i);
        }
    }


    class myRecycleAdapter extends RecyclerView.Adapter<myRecycleAdapter.MyViewHolder> {
        private List<String> mDatas;
        private Context mContext;
        private LayoutInflater inflater;

        public myRecycleAdapter(Context mContext ,List<String> mDatas) {
            this.mDatas = mDatas;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText( mDatas.get(position));
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycle_item ,parent, false);
            MyViewHolder holder= new MyViewHolder(view);
            return holder;
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public MyViewHolder(View view) {
                super(view);
                tv=(TextView) view.findViewById(R.id.recycle_item_tx);
            }

        }
    }

}
