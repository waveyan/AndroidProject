package com.trabal.routeplan;

import java.util.ArrayList;

import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.routeplan.RouteplanActivity1.CustomAdapter;
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

public class RouteplanActivity2 extends Activity {
	private Intent last_intent;
	private UserBean user;
	private ListView listView;
	private ArrayList<ActivityBean> ab_list;
	private Button button;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan2);
		
		last_intent = RouteplanActivity2.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		listView = (ListView) this.findViewById(R.id.routeplan2_listview);

		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setEnglish("Shenzhen");
		ab.setId("深圳");
		ab.setPic1(String.valueOf(R.drawable.wanghong2));

		ab_list.add(ab);

		listView.setAdapter(new CustomAdapter());
		
		//返回上层
		button =(Button)findViewById(R.id.backButton);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RouteplanActivity2.this,RouteplanActivity1.class);
				intent.putExtra("user", user);
				RouteplanActivity2.this.startActivity(intent);
				finish();
			}
		});
		
		//下一步跳转
		textview =(TextView)findViewById(R.id.next);
		
		textview.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RouteplanActivity2.this,RouteplanActivity3.class);
				intent.putExtra("user", user);
				RouteplanActivity2.this.startActivity(intent);
				finish();
			}
		});
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
			View view = View.inflate(RouteplanActivity2.this,
					R.layout.routeplan2_item, null);

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

			return view;
		}
	}
}
