package com.trabal.routeplan;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.listviewAdapter;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.SYxqyActivity;
import com.trabal.linear.TakeMeToYourHome;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.route.mapmap.DriveRouteActivity;
import com.trabal.routeplan.RouteplanActivity1.CustomAdapter;
import com.trabal.search.search;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.SharePreferencesTool;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RouteplanActivity2 extends Activity {
	private Intent last_intent;
	private UserBean user;
	private ListView listView;
	private ArrayList<HotSpotBean> hsb_list, hsb_plan;
	private Button button;
	private TextView textview;
	private EditText search;
	private String city_name;
	private CustomAdapter ca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan2);

		last_intent = RouteplanActivity2.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		listView = (ListView) this.findViewById(R.id.routeplan2_listview);
		city_name = last_intent.getStringExtra("city_name");

		hsb_list = initListview1();
		hsb_plan = new ArrayList<HotSpotBean>();

		// listview
		ca = new CustomAdapter();
		listView.setAdapter(ca);

		// 返回上层
		button = (Button) findViewById(R.id.backButton);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(RouteplanActivity2.this,
//						RouteplanActivity1.class);
//				intent.putExtra("user", user);
//				RouteplanActivity2.this.startActivity(intent);
				finish();
			}
		});

		// 下一步跳转
		textview = (TextView) findViewById(R.id.next);
		textview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (hsb_plan.size() < 2) {
					Toast.makeText(RouteplanActivity2.this, "地点至少选择2个或以上", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(RouteplanActivity2.this,
						DriveRouteActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("hsb_plan", hsb_plan);
				RouteplanActivity2.this.startActivity(intent);
			}
		});

		// 搜索
		search = (EditText) this.findViewById(R.id.route_search);
		search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				if ((arg1 == EditorInfo.IME_ACTION_SEARCH)) {
					// 先隐藏键盘
					((InputMethodManager) search.getContext().getSystemService(
							Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(RouteplanActivity2.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);

					String key = search.getText().toString().trim();
					//当输入为空时
					if ("".equals(key)) {
						hsb_list = initListview1();
						ca.notifyDataSetChanged();
					} else {
						String url = "hotspot/search";
						ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
						params.add(new BasicNameValuePair("key", key));
						params.add(new BasicNameValuePair("cityname", city_name));
						try {
							String data = NetTransfer.transfer(url, "get",
									params, true, user.getAccess_token(), null);
							hsb_list = new NetTransfer().handle_hs_list(data);
							ca.notifyDataSetChanged();
						} catch (Exception e) {

						}
					}
					return true;
				}
				return false;
			}
		});
	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return hsb_list.size();
		}

		@Override
		public Object getItem(int position) {
			return hsb_list.get(position);
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

			TextView mTextView1 = (TextView) view.findViewById(R.id.text2);

			TextView mTextView2 = (TextView) view.findViewById(R.id.text1);

			mTextView1.setText(hsb_list.get(position).getEnglishName());
			mTextView2.setText(hsb_list.get(position).getName());

			final ImageView imageView = (ImageView) view
					.findViewById(R.id.image1);

			Picasso.with(RouteplanActivity2.this)
					.load(hsb_list.get(position).getPic1()).centerCrop().fit()
					.into(imageView);
			//地点图像点击到takemetoyourhome
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent last_intent = ((Activity)(RouteplanActivity2.this)).getIntent();
					Intent intent = new Intent(RouteplanActivity2.this, TakeMeToYourHome.class);
					UserBean user = (UserBean) last_intent.getSerializableExtra("user");
					intent.putExtra("user", user);
					intent.putExtra("hsb",hsb_list.get(position));
					RouteplanActivity2.this.startActivity(intent);
				}
			});

			final Button btn = (Button) view.findViewById(R.id.addButton);
			// 防止滚动刷新
			boolean flag = false;
			for (HotSpotBean h : hsb_plan) {
				if (h.getId().equals(hsb_list.get(position).getId())) {
					flag = true;
					break;
				}
			}
			// 变图
			if (flag) {
				btn.setBackgroundResource(R.drawable.ojbk);
			} else {

				btn.setBackgroundResource(R.drawable.add_circle);
			}

			// 选择地点
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					boolean flag = false;
					HotSpotBean tmp = null;
					for (HotSpotBean h : hsb_plan) {
						if (h.getId().equals(hsb_list.get(position).getId())) {
							flag = true;
							tmp = h;
							break;
						}
					}
					// 变图
					if (flag) {
						hsb_plan.remove(tmp);
						btn.setBackgroundResource(R.drawable.add_circle);
					} else {
						hsb_plan.add(hsb_list.get(position));
						btn.setBackgroundResource(R.drawable.ojbk);
					}
				}
			});
			return view;
		}
	}

	private ArrayList<HotSpotBean> initListview1() {
		// 网络传输
		String url = "hotspot/base";
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("cityname", city_name));
		NetTransfer nt = new NetTransfer();
		try {

			String data = NetTransfer.transfer(url, "get", params, true,
					user.getAccess_token(), null);
			ArrayList<HotSpotBean> hs_list = nt.handle_hs_list(data);
			return hs_list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
