package com.trabal.linear;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.DistrictBean;
import com.trabal.linear.SiteLinearLayout.MyBaseAdapter;
import com.trabal.route.mapmap.MapOptionActivity;
import com.trabal.user.Bean.UserBean;

public class PositionLinearLayout extends LinearLayout {

	private Context context;
	private Intent last_intent;
	private UserBean user;
	private ListView mlistView;
	private ImageView imageView;
	private DistrictBean db;
	private View headerView;

	public PositionLinearLayout(Context context) {
		super(context);
		this.context = context;
		db=((SYxqyActivity)this.context).getDb();
		// 获取上一个页面传过来的用户
		last_intent = ((Activity) context).getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(
				R.layout.syxqy_linear_position, null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		// header_listview
		
		mlistView = (ListView) view.findViewById(R.id.syxqy_position_listview);
		
		headerView = init_headerView();		
		
		// 创建一个Adapter的实例
		mlistView.addHeaderView(headerView);
		mlistView.setAdapter(new MyBaseAdapter());
		

	}
	
	private View init_headerView (){
		View headerView = LayoutInflater.from(context).inflate(R.layout.header_listview, null);	
		ImageView pic=(ImageView) headerView.findViewById(R.id.syxqy_pic1);
		TextView syxqy_text=(TextView) headerView.findViewById(R.id.syxqy_text1);
		Picasso.with(context).load(db.getPic()).centerCrop().fit().into(pic);
		syxqy_text.setText(db.getIntroduction());
		//商圈地图
		TextView map=(TextView) headerView.findViewById(R.id.syxqy_maptext);
		map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(PositionLinearLayout.this.context,MapOptionActivity.class);
				intent.putExtra("db", db);
				PositionLinearLayout.this.context.startActivity(intent);
			}
		});
		return headerView;
		
	}

	class MyBaseAdapter extends BaseAdapter {

		public int getCount() {
			return db.getHsb().size();
		}

		public Object getItem(int position) {
			return db.getHsb().get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.listview_item, null);
			TextView mTextView1 = (TextView) view.findViewById(R.id.item_text1);
			TextView mTextView2 = (TextView) view.findViewById(R.id.item_text2);
			mTextView2.setText(db.getHsb().get(position).getWord());
			mTextView1.setText(db.getHsb().get(position).getName());
			ImageView imageView1 = (ImageView) view.findViewById(R.id.item1);
			ImageView imageView2 = (ImageView) view.findViewById(R.id.item3);
			ImageView imageView3 = (ImageView) view.findViewById(R.id.item4);
			ImageView imageView4 = (ImageView) view.findViewById(R.id.item5);
			
			Picasso.with(context).load(db.getHsb().get(position).getPic1()).centerCrop().fit().into(imageView1);
			Picasso.with(context).load(db.getHsb().get(position).getPic2()).centerCrop().fit().into(imageView2);
			Picasso.with(context).load(db.getHsb().get(position).getPic3()).centerCrop().fit().into(imageView3);
			Picasso.with(context).load(db.getHsb().get(position).getPic1()).centerCrop().fit().into(imageView4);
			
			view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(PositionLinearLayout.this.context, TakeMeToYourHome.class);
					intent.putExtra("user", user);
					intent.putExtra("hsb", db.getHsb().get(position));
					intent.putExtra("db", db);
					intent.putExtra("from", "syxqy");
					PositionLinearLayout.this.context.startActivity(intent);
					
				}
				
			});

			return view;
		}
	}
}
