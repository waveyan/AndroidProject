package com.trabal.linear;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.linear.SiteLinearLayout.MyBaseAdapter;
import com.trabal.user.Bean.UserBean;

import android.content.Context;
import android.content.Intent;
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


public class ExerciseLinearLayout extends LinearLayout {
	Context context;

	private ListView mlistView;
	private ArrayList<ActivityBean> ab_list;
	private UserBean user;
	
	public ExerciseLinearLayout(final Context context,UserBean user) {
		super(context);
		this.context = context;
		//保存登录状态
		this.user=user;
		// 创建数据源,收藏地点和活动在collectActivity中一次性获取
		ab_list = ((collectActivity)this.context).ab_list;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_exercise,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		mlistView = (ListView) findViewById(R.id.exercise_listview);

		// 创建一个Adapter的实例
		mlistView.setAdapter(new MyBaseAdapter());
		
//		mlistView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				Intent intent=new Intent(context,HdxiangqingActivity.class);
//				context.startActivity(intent);
//			}
//			
//		});

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
			View view = LayoutInflater.from(context).inflate(
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
			Picasso.with(context).load(ab_list.get(position).getPic1()).centerCrop().fit().into(imageView);
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent =new Intent(ExerciseLinearLayout.this.context,HdxiangqingActivity.class);
					intent.putExtra("ab", ab_list.get(position));
					intent.putExtra("user", ExerciseLinearLayout.this.user);
					intent.putExtra("from", "collect");
					ExerciseLinearLayout.this.context.startActivity(intent);
				}
			});
			return view;
		}
	}

}
