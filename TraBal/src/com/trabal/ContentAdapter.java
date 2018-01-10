package com.trabal;

import java.io.IOException;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.linear.EditnameActivity;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter {

	private Context context;
	private List<ContentModel> list;
	private UserBean user;
	private MyDialog myDialog;

	public ContentAdapter(Context context, List<ContentModel> list) {
		super();
		this.context = context;
		this.list = list;
		user = ((MainActivity) this.context).user;
		myDialog = new MyDialog(this.context);
		myDialog.setOnButtonClickListener((MainActivity) this.context);
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list != null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (position == 0) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.header_item, null);
			ImageView headpic = (ImageView) convertView
					.findViewById(R.id.p_pic);
			TextView rightname = (TextView) convertView
					.findViewById(R.id.right_name);

			// 网络传输获取用户头像和昵称
			String url = "user/base";
			NetTransfer nt = new NetTransfer();
			String data;
			try {
				data = NetTransfer.transfer(url, "get", null, true,
						user.getAccess_token(), null);
				String pic = nt.handle_user_data(data, user).getPic();
				// 设置远程头像
				Picasso.with(ContentAdapter.this.context).load(pic)
						.into(headpic);
				// 设置昵称
				rightname.setText(user.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// //点击头像时
			headpic.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					myDialog.show();
					//关闭右侧拉菜单
					((MainActivity) ContentAdapter.this.context).getDrawerLayout().closeDrawer(Gravity.RIGHT);
				}
			});

			// 点击昵称时
			rightname.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(ContentAdapter.this.context,
							EditnameActivity.class);
					intent.putExtra("user", user);
					//关闭右侧拉菜单
					((MainActivity) ContentAdapter.this.context).getDrawerLayout().closeDrawer(Gravity.RIGHT);
					ContentAdapter.this.context.startActivity(intent);
				}
			});

		} else {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.content_item, null);

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.item_imageview);
			TextView textView = (TextView) convertView
					.findViewById(R.id.item_textview);

			imageView.setImageResource(list.get(position).getImageView());
			textView.setText(list.get(position).getText());

		}
		return convertView;
	}

	public MyDialog getMyDialog() {
		return myDialog;
	}

	public void setMyDialog(MyDialog myDialog) {
		this.myDialog = myDialog;
	}

}
