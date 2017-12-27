package com.trabal.linear;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class huodongActivity extends Activity {
	private ListView mListView;
	private ArrayList<ActivityBean> ab_list;
	private ImageButton imageButton;
	private int[] icons = { R.drawable.l2};
	private UserBean user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huodong);
		// 获取上一个页面传过来的用户
		Intent last_intent = huodongActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		ab_list=new ArrayList<ActivityBean>();
		//网络传输获得数据，一次性获取收藏的活动和地点
		String url = "user/get_my_activity";
		try {
			String data = NetTransfer.transfer(url, "get", null, true, user.getAccess_token(),null);
			NetTransfer nt = new NetTransfer();
			//防止收藏为空时的空指针
			ArrayList<ActivityBean> ab_data=nt.handle_ac_list(data);
			ab_list=new ArrayList<ActivityBean> ();
			if(ab_data!=null)
				ab_list=ab_data;
		}
		catch (Exception e) {
			Log.e("地点收藏",e.getMessage());
		}


		// 初始化ListView控件
		mListView = (ListView) findViewById(R.id.huodong_listview);
		// 创建一个Adapter的实例
		MyBaseAdapter mAdapter = new MyBaseAdapter();
		// 设置Adapter
		mListView.setAdapter(mAdapter);
		
		imageButton =(ImageButton)this.findViewById(R.id.back_huodong);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(huodongActivity.this,
						MainActivity.class);
				intent.putExtra("user", user);
				huodongActivity.this.startActivity(intent);
			}
		});
		
	}

	class MyBaseAdapter extends BaseAdapter {

		public int getCount() {
			return ab_list.size();
		}

		public Object getItem(int position) {
			return ab_list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = View.inflate(huodongActivity.this,
					R.layout.huodong_listview_item, null);
			TextView mTextView1 = (TextView) view
					.findViewById(R.id.huodong_textview1);
			TextView mTextView2 = (TextView) view
					.findViewById(R.id.huodong_textview2);
			TextView mTextView3 = (TextView) view
					.findViewById(R.id.huodong_textview3);
			TextView mTextView4 = (TextView) view
					.findViewById(R.id.huodong_textview4);
			mTextView1.setText(ab_list.get(position).getType());
			mTextView2.setText(ab_list.get(position).getTitle());
			mTextView3.setText(ab_list.get(position).getEnglish());
			mTextView4.setText(ab_list.get(position).getTime());
			ImageView imageView = (ImageView) view
					.findViewById(R.id.huodong_image);
			Picasso.with(huodongActivity.this).load(ab_list.get(position).getPic1()).into(imageView);
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent =new Intent(huodongActivity.this,HdxiangqingActivity.class);
					intent.putExtra("ab", ab_list.get(position));
					intent.putExtra("user", huodongActivity.this.user);
					intent.putExtra("from", "myactivity");
					huodongActivity.this.startActivity(intent);
				}
			});
			return view;
		}
	}
}
