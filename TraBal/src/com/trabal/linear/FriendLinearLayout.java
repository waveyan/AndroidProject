package com.trabal.linear;

import java.util.ArrayList;

import com.trabal.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FriendLinearLayout extends LinearLayout{
	private ListView listView;
	private ArrayList<String> list;
	Context context;
	


	public FriendLinearLayout(Context context) {
		super(context);
		this.context=context;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_friend,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
        listView = (ListView)this.findViewById(R.id.friendpage);
		
		//创建数据源
		list = new ArrayList<String>();
		
		list.add("Zhou");
		list.add("Ine‘");
		list.add("Only.Ly");
		listView.setAdapter(new CustomAdapter());
		
		
	}
	private class CustomAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return  list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			//获取布局文件
			View view = LayoutInflater.from(context).inflate(R.layout.friend_item, null);
			
			TextView tv = (TextView)view.findViewById(R.id.item_friend);
			
			
			tv.setText(list.get(arg0));
			
			
			return view;
		}
		
		
	}
	
	

}
