package com.trabal;

import java.util.ArrayList;

import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.MoreLinearLayout;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		linears = new ArrayList<LinearLayout>();
		linears.add(new IndexLinearLayout(MainActivity.this));
		linears.add(new MoreLinearLayout(MainActivity.this));
		linears.add(new DynamicLinearLayout(MainActivity.this));
		
		//��ȡ�������
		content = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content);
		//����������
		content.setAdapter(new CustomPager());
		//���õ�ǰҳ��
		content.setCurrentItem(0);
		//������������¼�����
		content.setOnPageChangeListener(new CustomPagerChange());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * �������������
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
	 * �����¼�����
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

		}
	}

}
