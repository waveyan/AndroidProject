package com.trabal.search;

import java.io.IOException;
import java.util.ArrayList;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class search extends Activity {
	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content3;
	private TextView positionTv, userTv;
	private UserBean user;
	ArrayList<UserBean> follow;
	private TextView textView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Intent intent = search.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");

		linears = new ArrayList<LinearLayout>();
		linears.add(new PositonLinearLayout(search.this, user));
		linears.add(new UserLinearLayout(search.this, user));

		// 获取内容组件
		content3 = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content3);
		positionTv = (TextView) this.findViewById(R.id.ptID);
		userTv = (TextView) this.findViewById(R.id.userID);

		positionTv.setTextColor(android.graphics.Color.CYAN);
		userTv.setTextColor(android.graphics.Color.BLACK);

		// 设置适配器
		content3.setAdapter(new CustomPager());
		// 设置当前页面
		content3.setCurrentItem(0);
		// 设置内容组件事件处理
		content3.setOnPageChangeListener(new CustomPagerChange());

		positionTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content3.setCurrentItem(0);
				positionTv.setTextColor(android.graphics.Color.CYAN);
				userTv.setTextColor(android.graphics.Color.BLACK);

			}
		});

		userTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content3.setCurrentItem(1);
				positionTv.setTextColor(android.graphics.Color.BLACK);
				userTv.setTextColor(android.graphics.Color.CYAN);

			}
		});
		// 返回上层界面
		textView = (TextView) findViewById(R.id.cancel);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(search.this,
						MainActivity.class);
				intent.putExtra("user", user);
				search.this.startActivity(intent);
				finish();
			}
		});
	}

	/**
	 * 内容组件适配器
	 */
	private class CustomPager extends PagerAdapter {

		@Override
		public int getCount() {
			return linears.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(linears.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			container.addView(linears.get(position));
			return linears.get(position);

		}

	}

	/**
	 * 
	 * 内容事件处理
	 * 
	 */
	private class CustomPagerChange implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == 0) {
				positionTv.setTextColor(android.graphics.Color.CYAN);
				userTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				positionTv.setTextColor(android.graphics.Color.BLACK);
				userTv.setTextColor(android.graphics.Color.CYAN);

			}
		}

	}
}
