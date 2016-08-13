package com.example.meican;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meican.welcome.WelcomeActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.Request;

import okhttp3.Request;
import okhttp3.Response;

public class FoodFragment extends Fragment
{
	private ImageView imageView;
	private TextView text;
	String s ="";
	String TAG="meican_FoodFragment_tab02";
	Response re;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		int[] imgs = {
				R.drawable.roll_1,
				R.drawable.roll_2,
				R.drawable.roll_3,
				R.drawable.roll_4,
		};
//		ImageView i0=new ImageView(getActivity())
//		,i1=new ImageView(container.getContext()),i2=new ImageView(container.getContext()),i3=new ImageView(container.getContext());

//		Picasso.with(getActivity()).load("http://ww2.sinaimg.cn/mw690/70dfbeb7jw1f6akanxul2j215o1hjajt.jpg").placeholder(R.drawable.food1).into(i0);  //加载失败时 显示的图片
//		Picasso.with(getActivity()).load("http://ww1.sinaimg.cn/mw690/70dfbeb7jw1f6akametpoj215o1hjq95.jpg").placeholder(R.drawable.food1).into(i1);   //加载失败时 显示的图片
//		Picasso.with(getActivity()).load("http://ww1.sinaimg.cn/mw690/70dfbeb7jw1f6akaofx20j215o1hk0y1.jpg").placeholder(R.drawable.food1).into(i2);   //加载失败时 显示的图片
//		Picasso.with(getActivity()).load("http://ww4.sinaimg.cn/mw690/70dfbeb7jw1f6akanf2ebj21jk0zpwka.jpg").placeholder(R.drawable.food1).into(i3);   //加载失败时 显示的图片
		String[] ims ={"http://ww2.sinaimg.cn/mw690/70dfbeb7jw1f6akanxul2j215o1hjajt.jpg","http://ww1.sinaimg.cn/mw690/70dfbeb7jw1f6akametpoj215o1hjq95.jpg",
									"http://ww1.sinaimg.cn/mw690/70dfbeb7jw1f6akaofx20j215o1hk0y1.jpg","http://ww4.sinaimg.cn/mw690/70dfbeb7jw1f6akanf2ebj21jk0zpwka.jpg"};
		View view = inflater.inflate(R.layout.tab02, container, false);
		text = (TextView)view.findViewById(R.id.tab02_textView);
		text.setText("he");
		imageView =(ImageView)view.findViewById(R.id.tab02_image);
		Button bt = (Button) view.findViewById(R.id.tab02_bt_recycleview);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(getActivity() ,RecycleViewActivity.class);
				startActivity(it);
			}
		});
		Button bt1 = (Button) view.findViewById(R.id.tab02_bt_welcome);
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(getActivity() ,WelcomeActivity.class);
				startActivity(it);
			}
		});
		Button bt2 = (Button) view.findViewById(R.id.tab02_bt_swipeRefreshAndLoad);
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(getActivity() ,SwipeRefreshAndLoadActivity.class);
				startActivity(it);
			}
		});



		RollPagerView mRollPV = (RollPagerView) view.findViewById(R.id.tab02_RollPagerView);
		mRollPV.setHintView(new ColorPointHintView(this.getContext(), Color.YELLOW, Color.WHITE ));

		mRollPV.setAdapter(new rollAdapater(mRollPV,imgs,ims) );

//		new Thread(runnable).start();
//		testpic();
//		getHtml();
		return view;
	}
	Runnable runnable =new Runnable() {
		@Override
		public void run() {
			getHttpString();
		}
	};
	public void getHttpString(){
		String url = "http://apis.baidu.com/songshuxiansheng/news/news";
		Map<String, String> map2 = new HashMap<>();
		map2.put("apikey", "69423f293c4e6e6ea93e1ff05da05c9a");
		try {
			re =OkHttpUtils.get().url(url).headers(map2).build().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			text.setText(re.body().string());
			s= re.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getHtml()
	{
		String url = "http://apis.baidu.com/songshuxiansheng/news/news";
		url="http://www.baidu.com";
		Map<String, String> map2 = new HashMap<>();
//		map2.put("apikey", "69423f293c4e6e6ea93e1ff05da05c9a");
		OkHttpUtils
				.get()
				.url(url)
//				.headers(map2)
				.build()
				.execute(new StringCallback()
				{
					@Override
					public void onError(Call call, Exception e, int id)
					{
						e.printStackTrace();
						text.setText("onError:" + "chu错了");
						System.out.println("onError:" + TAG+"出错了");
					}

					@Override
					public void onResponse(String response,int id)
					{
						Log.e(TAG, "onResponse：complete");
						text.setText("onResponse:" + response);
						Toast.makeText(getActivity(), "https", Toast.LENGTH_SHORT).show();

					}
				});
	}
	public void testpic(){
		Picasso.with(getActivity())
				.load("http://img4.imgtn.bdimg.com/it/u=1142469417,341639617&fm=21&gp=0.jpg")
				.fit()
				.tag(getActivity())
				.into(imageView);
	}
	private class rollAdapater extends LoopPagerAdapter {
		private int[] imgs;
		private  String[] ims;

		public rollAdapater(RollPagerView viewPager, int[] imgs,String[] ims) {
			super(viewPager);
			this.imgs = imgs;
			this.ims =ims;
		}

		@Override
		public View getView(ViewGroup container, final int position) {
			ImageView imageView =new ImageView(container.getContext());
//			imageView.setImageResource(imgs[position]);
			Picasso.with(getActivity()).load(ims[position]).placeholder(R.drawable.food1).into(imageView);


			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("点我点我,点我了.."+position);
				}
			});
			return imageView;
		}

		@Override
		public int getRealCount() {
			return imgs.length;
		}
	}

}

