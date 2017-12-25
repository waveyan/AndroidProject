package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.user.Bean.UserBean;

public class collectActivity extends Activity implements OnItemSelectedListener {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content1;
	private TextView siteTv, exerciseTv, lineTv;
	private TextView cityID;
	private Spinner spDown;
	private List<String> list;
	private ArrayAdapter<String> adapter;
	private UserBean user;
	private ImageButton imageButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect);
		// 获取上一个页面传过来的用户
		Intent intent = collectActivity.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");



		// ================================

		linears = new ArrayList<LinearLayout>();
		linears.add(new SiteLinearLayout(collectActivity.this));
		linears.add(new ExerciseLinearLayout(collectActivity.this));
		linears.add(new LineLinearLayout(collectActivity.this));

		// 获取内容组件
		content1 = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content1);
		siteTv = (TextView) this.findViewById(R.id.siteID);
		exerciseTv = (TextView) this.findViewById(R.id.exerciseID);
		lineTv = (TextView) this.findViewById(R.id.lineID);

		siteTv.setTextColor(android.graphics.Color.CYAN);
		exerciseTv.setTextColor(android.graphics.Color.BLACK);
		lineTv.setTextColor(android.graphics.Color.BLACK);

		// 设置适配器
		content1.setAdapter(new CustomPager());
		// 设置当前页面
		content1.setCurrentItem(0);
		// 设置内容组件事件处理
		content1.setOnPageChangeListener(new CustomPagerChange());

		siteTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content1.setCurrentItem(0);
				siteTv.setTextColor(android.graphics.Color.CYAN);
				exerciseTv.setTextColor(android.graphics.Color.BLACK);
				lineTv.setTextColor(android.graphics.Color.BLACK);
			}
		});

		exerciseTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content1.setCurrentItem(1);
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.CYAN);
				lineTv.setTextColor(android.graphics.Color.BLACK);

			}
		});

		lineTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content1.setCurrentItem(2);
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.BLACK);
				lineTv.setTextColor(android.graphics.Color.CYAN);

			}
		});

		// ======================================

		// 跳转回原界面
		imageButton = (ImageButton) this.findViewById(R.id.backButton);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(collectActivity.this,
						MainActivity.class);
				intent.putExtra("user", user);
				collectActivity.this.startActivity(intent);
			}
		});

		spDown = (Spinner) findViewById(R.id.spDwon);

		/* 设置数据源 */
		list = new ArrayList<String>();
		list.add("全部城市");
		list.add("北京市");
		list.add("上海市");
		list.add("广州市");
		list.add("深圳市");

		/* 新建适配器 */
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		/* adapter设置一个下拉列表样式，参数为系统子布局 */
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		/* spDown加载适配器 */
		spDown.setAdapter(adapter);

		/* soDown的监听器 */
		spDown.setOnItemSelectedListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String cityName = adapter.getItem(position); // 获取选中的那一项

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
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
				siteTv.setTextColor(android.graphics.Color.CYAN);
				exerciseTv.setTextColor(android.graphics.Color.BLACK);
				lineTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.CYAN);
				lineTv.setTextColor(android.graphics.Color.BLACK);
			} else if (arg0 == 2) {
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.BLACK);
				lineTv.setTextColor(android.graphics.Color.CYAN);
			}
		}
	}
}