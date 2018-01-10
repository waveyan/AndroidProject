package com.trabal.search;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.TakeMeToYourHome;
import com.trabal.linear.pingjiaActivity;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;
import com.trabal.util.net.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class search extends Activity {
	private UserBean user;
	private TextView textView;
	private ListView listView;
	private ArrayList<Object> us_list=new ArrayList<Object>();
	private EditText edit;
	private String data;
	private String which;
	private Spinner drop;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Intent intent = search.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");
		//debug
//		user=new UserBean();
//		user.setAccess_token("af17a6d2be6676b4cf53b3ae81796fa6");
//		user.setTelephone("android");
//		user.setName("Eason");
//		user.setPic("http://127.0.0.1:8000/media/pic/headpic_gYNeoBB.png");
		//listview
		listView = (ListView) this.findViewById(R.id.search_listview);
		final CustomAdapter ca = new CustomAdapter();
		listView.setAdapter(ca);
		
		//选择下拉框时刷新
		drop=(Spinner) search.this.findViewById(R.id.spinner1);
		drop.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				us_list=new ArrayList<Object>();
				ca.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}});
		
		// 输入框
		edit = (EditText) this.findViewById(R.id.edit);
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				which = drop.getSelectedItem().toString().trim();
				if ((arg1 == EditorInfo.IME_ACTION_SEARCH)) {
					// 先隐藏键盘
					((InputMethodManager) edit.getContext().getSystemService(
							Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(search.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);

					String key = edit.getText().toString().trim();
					String url;
					ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					if ("用户".equals(which)) {
						url = "user/base";
						params.add(new BasicNameValuePair("action", "search"));
						params.add(new BasicNameValuePair("key", key));
						try {
							data = NetTransfer.transfer(url, "get", params, true,
									user.getAccess_token(), null);
							us_list=new NetTransfer().handle_usr_list(data);
							ca.notifyDataSetChanged();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					else {
						url = "hotspot/search";
						params.add(new BasicNameValuePair("key", key));
						try {
							data = NetTransfer.transfer(url, "get", params, true,
									user.getAccess_token(), null);
							us_list=new NetTransfer().handle_hs_list(data);
							ca.notifyDataSetChanged();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					return true;
				}
				return false;
			}
		});

		// 返回上层界面
		textView = (TextView) findViewById(R.id.cancel);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(search.this, MainActivity.class);
//				intent.putExtra("user", user);
//				search.this.startActivity(intent);
				finish();
			}
		});

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return us_list.size();

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			
			
			if(us_list.get(position) instanceof HotSpotBean){
				View view = LayoutInflater.from(search.this).inflate(
						R.layout.chose_listview_item, null);

				TextView name = (TextView) view
						.findViewById(R.id.chose_itemtext1);

				name.setText(((HotSpotBean)us_list.get(position)).getName());
				//绑定点击事件
				view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(search.this, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", (HotSpotBean)(us_list.get(position)));
//						intent.putExtra("from", "search");
						search.this.startActivity(intent);
					}
				});

				return view;
				
			}
			else{
				// 获取布局文件
				View view = LayoutInflater.from(search.this).inflate(
						R.layout.friend_item, null);

				TextView name = (TextView) view.findViewById(R.id.item_friend);
				ImageView head = (ImageView) view
						.findViewById(R.id.item_imageview1);
				Picasso.with(search.this)
				.load(((UserBean)us_list.get(position)).getPic()).centerCrop().transform(new Tools.CircleTransform()).fit().into(head);
				name.setText(((UserBean)us_list.get(position)).getName());
				//点击事件
				view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intent=new Intent(search.this,pingjiaActivity.class);
						String telephone=((UserBean)us_list.get(position)).getTelephone();
						if(telephone.equals(user.getTelephone())){
							intent.putExtra("flag", "mine");
							String evaluation_url = "evaluation/base";
							ArrayList<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
							params1.add(new BasicNameValuePair("action",
									"person"));
							try {
								String evaluation = NetTransfer.transfer(
										evaluation_url, "get", params1, true,
										user.getAccess_token(), null);
								ArrayList<EvaluationBean> myAssess = new NetTransfer()
										.handle_eb_list(evaluation);
								intent.putExtra("myAssess", myAssess);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						else{
							intent.putExtra("person",(UserBean)(us_list.get(position)));
						}
//						intent.putExtra("from", "search");
						intent.putExtra("user", user);
						search.this.startActivity(intent);
					}
				});

				return view;
			}
		}
	}

}
