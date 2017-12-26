package com.trabal.linear;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.listviewAdapter;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class SiteLinearLayout extends LinearLayout {
	private ListView mlistView;
	private Context context;
	private UserBean user;
	private Intent last_intent;
	ArrayList<HotSpotBean> hs_list;


	public SiteLinearLayout(Context context,UserBean user) {
		super(context);
		this.context = context;
		//登录状态
		this.user=user;
		//
		hs_list=((collectActivity)this.context).hs_list;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_site,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		
		mlistView = (ListView) findViewById(R.id.site_listview);
		
		// 创建一个Adapter的实例
		mlistView.setAdapter(new MyBaseAdapter());

	}
	
	//地点收藏的适配器
	class MyBaseAdapter extends BaseAdapter {

		public int getCount() {
			return hs_list.size();
		}

		public Object getItem(int position) {
			return hs_list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {

			View view = LayoutInflater.from(context).inflate(
					R.layout.listview_item, null);
			TextView mTextView1 = (TextView) view
					.findViewById(R.id.item_text1);
			mTextView1.setText(hs_list.get(position).getName());
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.item1);
			ImageView imageView2 = (ImageView) view
					.findViewById(R.id.item3);
			ImageView imageView3 = (ImageView) view
					.findViewById(R.id.item4);
			ImageView imageView4 = (ImageView) view
					.findViewById(R.id.item5);
			//地点收藏图片展示
			Picasso.with(context).load(hs_list.get(position).getPic1()).into(imageView1);
			Picasso.with(context).load(hs_list.get(position).getPic2()).into(imageView2);
			Picasso.with(context).load(hs_list.get(position).getPic3()).into(imageView3);
			Picasso.with(context).load(hs_list.get(position).getPic2()).into(imageView4);
			view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// 维持登录状态
					Intent intent = new Intent(SiteLinearLayout.this.context, TakeMeToYourHome.class);
					intent.putExtra("user", user);
					intent.putExtra("from","collect");
					intent.putExtra("hsb", hs_list.get(position));
					context.startActivity(intent);
				}});
			return view;
		}
	}

}
