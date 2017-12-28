package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;

import com.trabal.R;
import com.trabal.listviewAdapter;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.user.Bean.UserBean;

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
	private ArrayList<ActivityBean> ab_list;
	private UserBean user;
	

	public IndexLinearLayout(final Context context) {
		super(context);
		this.context = context;
		

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_index,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		mlistView = (ListView) findViewById(R.id.index_listview);

		// 创建数据源
		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setTitle("生活总有说不完的美好");
		ab.setPic1(String.valueOf(R.drawable.l2));
		ab.setPic2(String.valueOf(R.drawable.l2));
		ab.setPic3(String.valueOf(R.drawable.l2));
		ab.setPic4(String.valueOf(R.drawable.l2));
		ab.setPic5(String.valueOf(R.drawable.l2));
		ab.setPic6(String.valueOf(R.drawable.l2));
		ab.setPic7(String.valueOf(R.drawable.l2));

		ActivityBean ab1 = new ActivityBean();
		ab1.setTitle("艺术展览");
		ab1.setPic1(String.valueOf(R.drawable.l3));
		ab1.setPic2(String.valueOf(R.drawable.l1));
		ab1.setPic3(String.valueOf(R.drawable.l2));
		ab1.setPic4(String.valueOf(R.drawable.l4));
		ab1.setPic5(String.valueOf(R.drawable.l2));
		ab1.setPic6(String.valueOf(R.drawable.l2));
		ab1.setPic7(String.valueOf(R.drawable.l2));
		
		ab_list.add(ab);
		ab_list.add(ab1);
		
		// 创建一个Adapter的实例
		mlistView.setAdapter(new MyBaseAdapter());

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

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.index_listview_item, null);
			TextView mTextView1 = (TextView) view
					.findViewById(R.id.item_text1);
			mTextView1.setText(ab_list.get(position).getTitle());
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.item1);
			ImageView imageView2 = (ImageView) view
					.findViewById(R.id.item3);
			ImageView imageView3 = (ImageView) view
					.findViewById(R.id.item4);
			ImageView imageView4 = (ImageView) view
					.findViewById(R.id.item5);
			ImageView imageView5 = (ImageView) view
					.findViewById(R.id.item6);
			ImageView imageView6 = (ImageView) view
					.findViewById(R.id.item7);
			ImageView imageView7 = (ImageView) view
					.findViewById(R.id.item8);
			
			
			imageView1.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic1()));
			imageView2.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic2()));
			imageView3.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic3()));
			imageView4.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic4()));
			imageView5.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic5()));
			imageView6.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic6()));
			imageView7.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic7()));
			return view;
			

		}
	}

}
