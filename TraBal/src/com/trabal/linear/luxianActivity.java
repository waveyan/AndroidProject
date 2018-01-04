package com.trabal.linear;

import java.util.ArrayList;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.routeplan.RouteplanActivity1;
import com.trabal.routeplan.RouteplanActivity2;
import com.trabal.routeplan.RouteplanActivity4;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class luxianActivity extends Activity{
	private Intent last_intent;
	private UserBean user;
	private Button button;
	private ListView listView;
	private ArrayList<ActivityBean> ab_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luxian);
		
		last_intent = luxianActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		//返回上层
		button =(Button)findViewById(R.id.backButton);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(luxianActivity.this,MainActivity.class);
				intent.putExtra("user", user);
				luxianActivity.this.startActivity(intent);
				finish();
			}
		});
		
		listView = (ListView) this.findViewById(R.id.luxian_listview);

		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setTitle("广州一日游");
		ab.setTime("2018-1-1");
		ab.setIntroduction("这里有你想要的诗与远方");
		

		ab_list.add(ab);

		listView.setAdapter(new CustomAdapter());
}
	
	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return ab_list.size();
		}

		@Override
		public Object getItem(int position) {
			return ab_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// 获取布局文件
			View view = View.inflate(luxianActivity.this,
					R.layout.luxian_item, null);

			TextView mTextView1 = (TextView) view
					.findViewById(R.id.text1);

			TextView mTextView2 = (TextView) view
					.findViewById(R.id.text2);
			
			TextView mTextView3 = (TextView) view
					.findViewById(R.id.text3);

			mTextView1.setText(ab_list.get(position).getTitle());
			mTextView2.setText(ab_list.get(position).getTime());
			mTextView3.setText(ab_list.get(position).getIntroduction());

			return view;
		}
	}
}

