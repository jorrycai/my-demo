package com.example.meican;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;

	private LinearLayout mEvn;
	private LinearLayout mFood;
	private LinearLayout mInfo;
	private ImageButton mImgEvn;
	private ImageButton mImgFood;
	private ImageButton mImgInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
		initEvent();

		setSelect(1);
	}

	private void initEvent()
	{
		mEvn.setOnClickListener(this);
		mFood.setOnClickListener(this);
		mInfo.setOnClickListener(this);
	}

	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mEvn = (LinearLayout) findViewById(R.id.id_tab_evn);
		mFood = (LinearLayout) findViewById(R.id.id_tab_food);
		mInfo = (LinearLayout) findViewById(R.id.id_tab_info);
		mImgEvn = (ImageButton) findViewById(R.id.id_tab_evn_img);
		mImgFood = (ImageButton) findViewById(R.id.id_tab_food_img);
		mImgInfo = (ImageButton) findViewById(R.id.id_tab_info_img);
		mFragments = new ArrayList<Fragment>();
		Fragment mTab01 = new EvnFragment();
		Fragment mTab02 = new FoodFragment();
		Fragment mTab03 = new InfoFragment();
		mFragments.add(mTab01);
		mFragments.add(mTab02);
		mFragments.add(mTab03);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{

				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int arg0)
			{
				int currentItem = mViewPager.getCurrentItem();
				setTab(currentItem);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.id_tab_evn:
				setSelect(0);
				break;
			case R.id.id_tab_food:
				setSelect(1);
				break;
			case R.id.id_tab_info:
				setSelect(2);
				break;


			default:
				break;
		}
	}

	private void setSelect(int i)
	{
		setTab(i);
//		mViewPager.setCurrentItem(i);
		mViewPager.setCurrentItem(i,true);
	}

	private void setTab(int i)
	{
		resetImgs();
		// 重置图片
		// 选中图片
		switch (i)
		{
			case 0:
				mImgEvn.setImageResource(R.drawable.env2);
				break;
			case 1:
				mImgFood.setImageResource(R.drawable.food2);
				break;
			case 2:
				mImgInfo.setImageResource(R.drawable.myinfo2);
				break;
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{

		mImgEvn.setImageResource(R.drawable.env1);
		mImgFood.setImageResource(R.drawable.food1);
		mImgInfo.setImageResource(R.drawable.myinfo1);

	}

}
