package com.trabal.linear;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
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

public class FriendLinearLayout extends LinearLayout{
	private ListView listView;
	private ArrayList<UserBean> follow;
	private UserBean user;

	Intent last_intent;

	Context context;
	


	public FriendLinearLayout(Context context,UserBean user) {
		super(context);
		this.context=context;
		this.user=user;
		follow=((haoyouActivity)this.context).follow;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_friend,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
        listView = (ListView)this.findViewById(R.id.friendpage);
		
		//创建数据源
		
		listView.setAdapter(new CustomAdapter());
		
		
	}
	 class CustomAdapter extends BaseAdapter{


		public int getCount() {
			return  follow.size();
		}

		@Override
		public Object getItem(int position) {
			return follow.get(position);
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
			
			Picasso.with(context).load(follow.get(position).getPic()).into(head);
			name.setText(follow.get(position).getName());
			view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// 维持登录状态
					Intent intent = new Intent(FriendLinearLayout.this.context,pingjiaActivity.class);
					intent.putExtra("user", user);
					intent.putExtra("flag", "following");
					intent.putExtra("person", follow.get(position));
					context.startActivity(intent);
				}});
			
			return view;
		}
		
		
	}
	
	

}
