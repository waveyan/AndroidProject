package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

public class haoyouActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content2;
	private TextView fansTv, friendTv;
	private UserBean user;
	private ImageView imageView;
	ArrayList<UserBean> fans;
	ArrayList<UserBean> follow;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_haoyou);
		
		Intent intent = haoyouActivity.this.getIntent();
		user = (UserBean)intent.getSerializableExtra("user");

		// ================================
		try {
			// 获取粉丝和关注
			String url = "user/get_my_follow";
			String method = "get";
			String data = NetTransfer.transfer(url, method, null, true,
					user.getAccess_token(), null);
			NetTransfer nt = new NetTransfer();
			fans = nt.handle_fans_list(data);
			if(fans==null)
				fans=new ArrayList<UserBean>();
			follow = nt.handle_follow_user_list(data);
			if(follow==null)
				follow=new ArrayList<UserBean>();
		} catch (IOException e) {
			e.printStackTrace();
		}

		linears = new ArrayList<LinearLayout>();
		linears.add(new FansLinearLayout(haoyouActivity.this,user));
		linears.add(new FriendLinearLayout(haoyouActivity.this,user));

		// 获取内容组件
		content2 = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content2);
		fansTv = (TextView) this.findViewById(R.id.fansID);
		friendTv = (TextView) this.findViewById(R.id.friendID);

		fansTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
		friendTv.setTextColor(android.graphics.Color.BLACK);

		// 设置适配器
		content2.setAdapter(new CustomPager());
		// 设置当前页面
		content2.setCurrentItem(0);
		// 设置内容组件事件处理
		content2.setOnPageChangeListener(new CustomPagerChange());

		fansTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content2.setCurrentItem(0);
				fansTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
				friendTv.setTextColor(android.graphics.Color.BLACK);

			}
		});

		friendTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content2.setCurrentItem(1);
				fansTv.setTextColor(android.graphics.Color.BLACK);
				friendTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));

			}
		});
		// 返回上层界面
		imageView = (ImageView)findViewById(R.id.back_haoyou);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(haoyouActivity.this,MainActivity.class);
				intent.putExtra("user", user);
				haoyouActivity.this.startActivity(intent);
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
				fansTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));
				friendTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				fansTv.setTextColor(android.graphics.Color.BLACK);
				friendTv.setTextColor(android.graphics.Color.argb(250, 53, 138, 115));

			}
		}

	}
}