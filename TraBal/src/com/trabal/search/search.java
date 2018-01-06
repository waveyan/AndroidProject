package com.trabal.search;

import java.io.IOException;
import java.util.ArrayList;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.search.UserLinearLayout.CustomAdapter;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class search extends Activity {
	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content3;
	private TextView positionTv, userTv;
	private UserBean user;
	ArrayList<UserBean> follow;
	private TextView textView;
	private ListView listView;
	private ArrayList<UserBean> us_list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Intent intent = search.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");

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
		
		listView = (ListView) this.findViewById(R.id.search_listview);

		// 创建数据源
		us_list = new ArrayList<UserBean>();

		UserBean ab = new UserBean();
		ab.setName("玖点钟");
		ab.setPic(String.valueOf(R.drawable.person));
		us_list.add(ab);
		
		listView.setAdapter(new CustomAdapter());

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {

			return  us_list.size();

		}

		@Override
		public Object getItem(int position) {

			return us_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override

		public View getView(final int position, View convertView, ViewGroup parent) {
			
			//获取布局文件
			View view = LayoutInflater.from(search.this).inflate(R.layout.friend_item, null);
			
			TextView name =(TextView) view.findViewById(R.id.item_friend);
			ImageView head =(ImageView) view.findViewById(R.id.item_imageview1);
			
			head.setBackgroundResource(Integer.parseInt(us_list.get(
					position).getPic()));
			name.setText(us_list.get(position).getName());
			
			return view;
		}
	}
}