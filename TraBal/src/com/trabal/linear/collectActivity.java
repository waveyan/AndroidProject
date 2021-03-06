package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
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
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

public class collectActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content1;
	private TextView siteTv, exerciseTv;
	private TextView cityID;
	private List<String> list;
	private ArrayAdapter<String> adapter;
	private UserBean user;
	private ImageButton imageButton;
	ArrayList<HotSpotBean> hs_list;
	ArrayList<ActivityBean> ab_list;
	private Intent last_intent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect);
		// 获取上一个页面传过来的用户
		last_intent = collectActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		//网络传输获得数据，一次性获取收藏的活动和地点
		String url = "user/get_my_favour";
		try {
			String data = NetTransfer.transfer(url, "get", null, true, user.getAccess_token(),null);
			NetTransfer nt = new NetTransfer();
			//防止收藏为空时的空指针
			hs_list=new ArrayList<HotSpotBean> ();
			ArrayList<HotSpotBean> hs_data=nt.handle_hs_list(data);
			if(hs_data!=null)
				hs_list=hs_data;
			ArrayList<ActivityBean> ab_data=nt.handle_ac_list(data);
			ab_list=new ArrayList<ActivityBean> ();
			if(ab_data!=null)
				ab_list=ab_data;
		}
		catch (Exception e) {
			Log.e("地点收藏",e.getMessage());
		}



		// ================================

		linears = new ArrayList<LinearLayout>();
		linears.add(new SiteLinearLayout(collectActivity.this,user));
		linears.add(new ExerciseLinearLayout(collectActivity.this,user));
		linears.add(new LineLinearLayout(collectActivity.this,user));

		// 获取内容组件
		content1 = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content1);
		siteTv = (TextView) this.findViewById(R.id.siteID);
		exerciseTv = (TextView) this.findViewById(R.id.exerciseID);

		// 设置适配器
		content1.setAdapter(new CustomPager());
		// 设置当前页面-----活动详情返回
//		if("collect_exercise".equals(last_intent.getStringExtra("from"))){
//			siteTv.setTextColor(android.graphics.Color.BLACK);
//			exerciseTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
//			content1.setCurrentItem(1);
//		}
//		else{
//			siteTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
//			exerciseTv.setTextColor(android.graphics.Color.BLACK);
//			content1.setCurrentItem(0);
//		}
		// 设置内容组件事件处理
		content1.setOnPageChangeListener(new CustomPagerChange());

		siteTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				content1.setCurrentItem(0);
				siteTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
				exerciseTv.setTextColor(android.graphics.Color.BLACK);
			}
		});

		exerciseTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				content1.setCurrentItem(1);
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));

			}
		});


		// ======================================

		// 跳转回原界面
		imageButton = (ImageButton) this.findViewById(R.id.backButton);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(collectActivity.this,
//						MainActivity.class);
//				intent.putExtra("user", user);
//				collectActivity.this.startActivity(intent);
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
				siteTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
				exerciseTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				siteTv.setTextColor(android.graphics.Color.BLACK);
				exerciseTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
			}
		}
	}
}