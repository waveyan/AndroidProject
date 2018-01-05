package com.trabal.search;

import android.widget.LinearLayout;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.user.Bean.UserBean;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PositonLinearLayout extends LinearLayout {
	Context context;
	private ListView listView;
	private ArrayList<UserBean> pt_list;
	private UserBean user;
	Intent last_intent;

	public PositonLinearLayout(Context context, UserBean user) {
		super(context);
		this.context = context;
		this.user = user;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(
				R.layout.linear_position, null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		listView = (ListView) this.findViewById(R.id.search_positon_listview);

		// 创建数据源
		pt_list = new ArrayList<UserBean>();

		UserBean ab = new UserBean();
		ab.setName("玖点钟");
		pt_list.add(ab);
		listView.setAdapter(new CustomAdapter());

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {

			return pt_list.size();

		}

		@Override
		public Object getItem(int position) {

			return pt_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// 获取布局文件
			View view = LayoutInflater.from(context).inflate(
					R.layout.chose_listview_item, null);

			TextView name = (TextView) view.findViewById(R.id.chose_itemtext1);

			name.setText(pt_list.get(position).getName());

			return view;
		}
	}
}
