package com.trabal.linear;

import java.util.ArrayList;

import com.trabal.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SYxqyActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager syxqy_content;
	private TextView syxqy_positionTv, syxqy_commentTv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syxiangqingye);

		// ================================

		linears = new ArrayList<LinearLayout>();
		linears.add(new PositionLinearLayout(SYxqyActivity.this));
		linears.add(new CommentLinearLayout(SYxqyActivity.this));

		// 获取内容组件
		syxqy_content = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.syxqy_content);

		syxqy_positionTv = (TextView) this.findViewById(R.id.syxqy_position);
		syxqy_commentTv = (TextView) this.findViewById(R.id.syxqy_comment);

		syxqy_positionTv.setTextColor(android.graphics.Color.CYAN);
		syxqy_commentTv.setTextColor(android.graphics.Color.BLACK);

		// 设置适配器
		syxqy_content.setAdapter(new CustomPager());
		// 设置当前页面
		syxqy_content.setCurrentItem(0);
		// 设置内容组件事件处理
		syxqy_content.setOnPageChangeListener(new CustomPagerChange());

		syxqy_positionTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				syxqy_content.setCurrentItem(0);
				syxqy_positionTv.setTextColor(android.graphics.Color.CYAN);
				syxqy_commentTv.setTextColor(android.graphics.Color.BLACK);
			}
		});

		syxqy_commentTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				syxqy_content.setCurrentItem(1);
				syxqy_positionTv.setTextColor(android.graphics.Color.BLACK);
				syxqy_commentTv.setTextColor(android.graphics.Color.CYAN);

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
				syxqy_positionTv.setTextColor(android.graphics.Color.CYAN);
				syxqy_commentTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				syxqy_positionTv.setTextColor(android.graphics.Color.BLACK);
				syxqy_commentTv.setTextColor(android.graphics.Color.CYAN);

			}
		}

	}
}



