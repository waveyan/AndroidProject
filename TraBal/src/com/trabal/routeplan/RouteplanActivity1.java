package com.trabal.routeplan;

import java.util.ArrayList;

import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RouteplanActivity1 extends Activity {
	private Intent last_intent;
	private UserBean user;
	private ListView listView;
	private ArrayList<ActivityBean> ab_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan1);

		last_intent = RouteplanActivity1.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		listView = (ListView) this.findViewById(R.id.routeplan1_listview);

		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setEnglish("Shenzhen");
		ab.setId("深圳");
		ab.setPic1(String.valueOf(R.drawable.wanghong2));

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
			View view = View.inflate(RouteplanActivity1.this,
					R.layout.routeplan1_item, null);

			TextView mTextView1 = (TextView) view
					.findViewById(R.id.text2);

			TextView mTextView2 = (TextView) view
					.findViewById(R.id.text1);

			mTextView1.setText(ab_list.get(position).getId());
			mTextView2.setText(ab_list.get(position).getEnglish());

			ImageView imageView = (ImageView) view
					.findViewById(R.id.image1);

			imageView.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic1()));
			
			view.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(RouteplanActivity1.this,RouteplanActivity2.class);
					intent.putExtra("user", user);
					RouteplanActivity1.this.startActivity(intent);
					finish();
				}
			});

			return view;
		}
	}
}
