package com.trabal.routeplan;

import java.io.IOException;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.CityBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.SharePreferencesTool;
import com.trabal.util.net.NetTransfer;

import android.annotation.SuppressLint;
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
	private ArrayList<CityBean> ab_list;
	private String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan1);

		last_intent = RouteplanActivity1.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		from = last_intent.getStringExtra("from");

		listView = (ListView) this.findViewById(R.id.routeplan1_listview);

		// 获取数据
		String url = "hotspot/getcities";
		try {
			String data = NetTransfer.transfer(url, "get", null, true,
					user.getAccess_token(), null);
			NetTransfer nt = new NetTransfer();
			ab_list = nt.handle_city_list(data);
			if (ab_list == null)
				ab_list = new ArrayList<CityBean>();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// 获取布局文件
			View view = View.inflate(RouteplanActivity1.this,
					R.layout.routeplan1_item, null);

			TextView mTextView1 = (TextView) view.findViewById(R.id.text2);

			TextView mTextView2 = (TextView) view.findViewById(R.id.text1);

			mTextView1.setText(ab_list.get(position).getName());
			mTextView2.setText(ab_list.get(position).getEnglishname());

			ImageView imageView = (ImageView) view.findViewById(R.id.image1);

			Picasso.with(RouteplanActivity1.this)
					.load(ab_list.get(position).getPic()).centerCrop().fit()
					.into(imageView);

			imageView.setAlpha(0.8f);

			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					if ("main".equals(from)) {
						//写入本地文件
						SharePreferencesTool spt=new SharePreferencesTool(RouteplanActivity1.this,MODE_PRIVATE);
						spt.putInto("city_id", ab_list.get(position).getId());
						spt.putInto("city_name", ab_list.get(position).getName());
						Intent intent = new Intent(RouteplanActivity1.this,
								MainActivity.class);
						intent.putExtra("user", user);
						RouteplanActivity1.this.startActivity(intent);
						finish();

					} else {
						Intent intent = new Intent(RouteplanActivity1.this,
								RouteplanActivity2.class);
						intent.putExtra("user", user);
						intent.putExtra("city_name", ab_list.get(position).getName());
						RouteplanActivity1.this.startActivity(intent);
						finish();
					}
				}
			});

			return view;
		}
	}
}
