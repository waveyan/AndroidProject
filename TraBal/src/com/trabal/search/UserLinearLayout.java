package com.trabal.search;

import android.widget.LinearLayout;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler.Value;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class UserLinearLayout extends LinearLayout {
	Context context;
	private ListView listView;

	private ArrayList<UserBean> us_list;
	private UserBean user;
	Intent last_intent;

	public UserLinearLayout(Context context, UserBean user) {
		super(context);
		this.context=context;
		this.user=user;
		
		

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_user,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		listView = (ListView) this.findViewById(R.id.search_user_listview);

		// 创建数据源
		us_list = new ArrayList<UserBean>();

		UserBean ab = new UserBean();
		ab.setName("玖点钟");
		ab.setPic(String.valueOf(R.drawable.person));
		us_list.add(ab);
		
		listView.setAdapter(new CustomAdapter());

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {

			return  us_list.size();

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

		public View getView(final int position, View convertView, ViewGroup parent) {
			
			//获取布局文件
			View view = LayoutInflater.from(context).inflate(R.layout.friend_item, null);
			
			TextView name =(TextView) view.findViewById(R.id.item_friend);
			ImageView head =(ImageView) view.findViewById(R.id.item_imageview1);
			
			head.setBackgroundResource(Integer.parseInt(us_list.get(
					position).getPic()));
			name.setText(us_list.get(position).getName());
			
			return view;
		}
	}
}

