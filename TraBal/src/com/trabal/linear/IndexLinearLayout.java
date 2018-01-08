package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.listviewAdapter;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.DistrictBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.SharePreferencesTool;
import com.trabal.util.net.NetTransfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class IndexLinearLayout extends LinearLayout {

	private Context context;
	private ListView mlistView;
	private ArrayList<DistrictBean> db_list;
	private UserBean user;
	

	public IndexLinearLayout(final Context context) {
		super(context);
		this.context = context;
		user=((MainActivity)this.context).user;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_index,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		mlistView = (ListView) findViewById(R.id.index_listview);
		
		
		//获取城市
		String city_name=((MainActivity)this.context).getCity_name();
		//网络请求，获取首页数据
		String url="hotspot/create_index";
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("cityname", city_name));
		NetTransfer nt=new NetTransfer();
		String data;
		try {
			data = NetTransfer.transfer(url, "get", params, true, user.getAccess_token(), null);
			db_list=nt.handle_db_list(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 创建一个Adapter的实例
		mlistView.setAdapter(new MyBaseAdapter());

	}
	
	class MyBaseAdapter extends BaseAdapter {
		
		ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
		

		public int getCount() {
			return db_list.size();
		}

		public Object getItem(int position) {
			return db_list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi") public View getView(final int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.index_listview_item, null);
			TextView mTextView1 = (TextView) view
					.findViewById(R.id.item_text1);
			mTextView1.setText(db_list.get(position).getName());
			imageView1 = (ImageView) view
					.findViewById(R.id.item1);
			imageView2 = (ImageView) view
					.findViewById(R.id.item3);
			imageView3 = (ImageView) view
					.findViewById(R.id.item4);
			imageView4 = (ImageView) view
					.findViewById(R.id.item5);
			imageView5 = (ImageView) view
					.findViewById(R.id.item6);
			imageView6 = (ImageView) view
					.findViewById(R.id.item7);
			imageView7 = (ImageView) view
					.findViewById(R.id.item8);
			
			Picasso.with(context).load(db_list.get(position).getPic()).centerCrop().fit().into(imageView1);
			Picasso.with(context).load(db_list.get(position).getHsb().get(0).getPic1()).centerCrop().fit().into(imageView2);
			Picasso.with(context).load(db_list.get(position).getHsb().get(1).getPic1()).centerCrop().fit().into(imageView3);
			Picasso.with(context).load(db_list.get(position).getHsb().get(2).getPic1()).centerCrop().fit().into(imageView4);
			Picasso.with(context).load(db_list.get(position).getHsb().get(3).getPic1()).centerCrop().fit().into(imageView5);
			Picasso.with(context).load(db_list.get(position).getHsb().get(4).getPic1()).centerCrop().fit().into(imageView6);
			Picasso.with(context).load(db_list.get(position).getHsb().get(5).getPic1()).centerCrop().fit().into(imageView7);
			
			//透明度
			imageView1.setAlpha(0.8f);
			
			class IVOnclick implements View.OnClickListener{

				@Override
				public void onClick(View arg0) {
					if(arg0.getId()==imageView1.getId()){
						// 维持登录状态
						Intent intent = new Intent(IndexLinearLayout.this.context, SYxqyActivity.class);
						intent.putExtra("user", user);
						intent.putExtra("db", db_list.get(position));
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView2.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(0));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView3.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(1));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView4.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(2));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView5.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(3));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView6.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(4));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}else if(arg0.getId()==imageView7.getId()){
						Intent intent = new Intent(IndexLinearLayout.this.context, TakeMeToYourHome.class);
						intent.putExtra("user", user);
						intent.putExtra("hsb", db_list.get(position).getHsb().get(5));
						intent.putExtra("from", "index");
						IndexLinearLayout.this.context.startActivity(intent);
					}
				}
				
			}
			
			imageView1.setOnClickListener(new IVOnclick());
			imageView2.setOnClickListener(new IVOnclick());
			imageView3.setOnClickListener(new IVOnclick());
			imageView4.setOnClickListener(new IVOnclick());
			imageView5.setOnClickListener(new IVOnclick());
			imageView6.setOnClickListener(new IVOnclick());
			imageView7.setOnClickListener(new IVOnclick());
			return view;

		}
	}
	

}
