package com.trabal.linear;

import java.util.ArrayList;

import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.linear.SiteLinearLayout.MyBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class ExerciseLinearLayout extends LinearLayout {
	Context context;

	private ListView mlistView;
	private ArrayList<ActivityBean> ab_list;
	
	public ExerciseLinearLayout(Context context) {
		super(context);
		this.context = context;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_exercise,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		mlistView = (ListView) findViewById(R.id.exercise_listview);

		// 创建数据源
		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setType("艺术展览");
		ab.setTitle("生活总有说不完的美好");
		ab.setEnglish("Poem");
		ab.setTime("三天后结束");
		ab.setPic1(String.valueOf(R.drawable.l2));

		ActivityBean ab1 = new ActivityBean();
		ab1.setType("艺术展览");
		ab1.setTitle("历史总是惊人的相似");
		ab1.setEnglish("Poem");
		ab1.setTime("三天后结束");
		ab1.setPic1(String.valueOf(R.drawable.l3));
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
			imageView.setBackgroundResource(Integer.parseInt(ab_list.get(position).getPic1()));
			return view;
		}
	}

}
