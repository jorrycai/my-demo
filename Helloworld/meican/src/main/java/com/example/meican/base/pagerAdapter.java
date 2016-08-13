package com.example.meican.base;

/**
 * Created by jorry on 2016/7/31.
 */


import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class pagerAdapter extends PagerAdapter {

    private ArrayList<View> views;

    public pagerAdapter(ArrayList<View> views) {

        this.views = views;
    }

    @Override
    public int getCount() {
        return this.views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    //页面view
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

}
