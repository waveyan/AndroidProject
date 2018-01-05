package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.route.Bean.RouteBean;
import com.trabal.route.mapmap.DriveRouteActivity;
import com.trabal.routeplan.RouteplanActivity1;
import com.trabal.routeplan.RouteplanActivity2;
import com.trabal.routeplan.RouteplanActivity4;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

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
import android.widget.Toast;

public class luxianActivity extends Activity{
	private Intent last_intent;
	private UserBean user;
	private Button button;
	private ListView listView;
	private ArrayList<RouteBean> rb_list;

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

		rb_list = initRb_List();

		listView.setAdapter(new CustomAdapter());
}
	
	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return rb_list.size();
		}

		@Override
		public Object getItem(int position) {
			return rb_list.get(position);
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

			mTextView1.setText(rb_list.get(position).getTitle());
			mTextView2.setText(rb_list.get(position).getTime());
			mTextView3.setText(rb_list.get(position).getIntroduce());
			
			//点击事件
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(luxianActivity.this,DriveRouteActivity.class);
					intent.putExtra("user", user);
					intent.putExtra("hsb_plan", rb_list.get(position).getHsb_list());
					intent.putExtra("from", "mine");
					luxianActivity.this.startActivity(intent);
				}
			});

			return view;
		}
	}
	
	private ArrayList<RouteBean> initRb_List(){

		//上传数据
		String url="hotspot/route";
		ArrayList<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("action","person"));
		ArrayList<RouteBean> rb_list=new ArrayList<RouteBean>();
		try {
			String data=NetTransfer.transfer(url, "get", parameters, true, user.getAccess_token(), null);
			NetTransfer nt=new NetTransfer();
			rb_list=nt.handle_rb_list(data);
			return rb_list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	
	}
}

