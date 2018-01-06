package com.trabal.linear;

import com.trabal.MainActivity;
import com.trabal.R;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.CommentLinearLayout.MyBaseAdapter;
import com.trabal.linear.FriendLinearLayout.CustomAdapter;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class pingjiaActivity extends Activity {
	private ListView listView;
	private UserBean user, person = new UserBean();// 查看他人信息
	private Intent last_intent;
	ArrayList<EvaluationBean> hxb;
	private View headerView;
	private ImageButton imageButton;
	ArrayList<EvaluationBean> myAssess;
	private ImageView love;
	private BaseAdapter ba;
	private TextView status;
	private String flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_pingjia);

		last_intent = pingjiaActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		flag = last_intent.getStringExtra("flag");

		listView = (ListView) this.findViewById(R.id.hyxiangqingyeID);

		// header_listview
		headerView = LayoutInflater.from(pingjiaActivity.this).inflate(
				R.layout.pingjia_item, null);
		// 姓名
		TextView mTextView6 = (TextView) headerView
				.findViewById(R.id.hyxqy_name6);
		// 关注状态
		status = (TextView) headerView.findViewById(R.id.status);
		status.setOnClickListener(new FollowOnclick());
		// 头像
		ImageView imageView2 = (ImageView) headerView
				.findViewById(R.id.hyxqy_headpic6);

		person = (UserBean) last_intent.getSerializableExtra("person");
		mTextView6.setText(person.getName());
		Picasso.with(pingjiaActivity.this).load(person.getPic()).centerCrop()
				.fit().into(imageView2);
		// 判断关注状态和数据来源
		if ("mine".equals(flag)) {
			status.setVisibility(View.INVISIBLE);
			mTextView6.setText(user.getName());
			Picasso.with(pingjiaActivity.this).load(user.getPic()).centerCrop()
					.fit().into(imageView2);
			myAssess = (ArrayList<EvaluationBean>) last_intent
					.getSerializableExtra("myAssess");
		} else {
			if ("following".equals(flag)) {
				status.setText("取消关注");
			} else {
				String url = "user/get_my_follow";
				String data = null;
				try {
					data = NetTransfer.transfer(url, "get", null, true,
							user.getAccess_token(), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ArrayList<UserBean> follow = new NetTransfer()
						.handle_follow_user_list(data);
				boolean flag = false;
				if (follow != null)
					for (UserBean usr : follow) {
						if (usr.getTelephone().equals(person.getTelephone())) {
							flag = true;
							break;
						}
					}
				if (flag)
					status.setText("取消关注");
			}
			// 得到某个人的所有评论
			String evaluation_url = "evaluation/base";
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("action", "person"));
			params.add(new BasicNameValuePair("user_tel", person.getTelephone()));
			try {
				String evaluation = NetTransfer.transfer(evaluation_url, "get",
						params, true, user.getAccess_token(), null);
				myAssess = new NetTransfer().handle_eb_list(evaluation);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// 创建一个Adapter的实例
		listView.addHeaderView(headerView);
		listView.setAdapter(new CustomAdapter());

		ba = new CustomAdapter();
		listView.setAdapter(ba);

		// 返回上一个界面
		imageButton = (ImageButton) findViewById(R.id.back_pingjia);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(pingjiaActivity.this,
						MainActivity.class);
				intent.putExtra("user", user);
				pingjiaActivity.this.startActivity(intent);

			}
		});

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return myAssess.size();
		}

		public Object getItem(int position) {
			return myAssess.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = LayoutInflater.from(pingjiaActivity.this).inflate(
					R.layout.pjxiangqingye3_item, null);
			TextView mTextView1 = (TextView) view
					.findViewById(R.id.pjxqy_name3);
			TextView mTextView2 = (TextView) view
					.findViewById(R.id.pjxqy_date3);
			TextView mTextView3 = (TextView) view.findViewById(R.id.pjxqy_pj3);
			TextView mTextView4 = (TextView) view
					.findViewById(R.id.pjxqy_site3);
			TextView mTextView5 = (TextView) view
					.findViewById(R.id.pjxqy_price3);

			RatingBar rate = (RatingBar) view.findViewById(R.id.pjxqy_stars3);
			rate.setRating(Float.parseFloat(myAssess.get(position).getRate()));
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.pjxqy_headpic3);

			// 评价的头像和名称
			if ("mine".equals(flag)) {
				mTextView1.setText(user.getName());
				Picasso.with(pingjiaActivity.this).load(user.getPic())
						.centerCrop().fit().into(imageView1);
			} else {
				mTextView1.setText(person.getName());
				Picasso.with(pingjiaActivity.this).load(person.getPic())
						.centerCrop().fit().into(imageView1);
			}
			mTextView2.setText(myAssess.get(position).getTime());
			mTextView3.setText(myAssess.get(position).getMood());
			mTextView4.setText(myAssess.get(position).getHs().getName());
			mTextView5.setText(String
					.valueOf(myAssess.get(position).getPrice()));

			// 动态图片
			GridView gv = (GridView) view.findViewById(R.id.gridview);
			// silly！！！！！
			final ArrayList<String> list = new ArrayList<String>();
			if (myAssess.get(position).getPic1() != null
					&& !("".equals(myAssess.get(position).getPic1())))
				list.add(myAssess.get(position).getPic1());
			if (myAssess.get(position).getPic2() != null
					&& !("".equals(myAssess.get(position).getPic2())))
				list.add(myAssess.get(position).getPic2());
			if (myAssess.get(position).getPic3() != null
					&& !("".equals(myAssess.get(position).getPic3())))
				list.add(myAssess.get(position).getPic3());
			if (list.size() == 0)
				gv.setVisibility(View.INVISIBLE);
			else if (list.size() == 1)
				gv.setNumColumns(1);
			else if (list.size() == 2)
				gv.setNumColumns(2);
			else
				gv.setNumColumns(3);

			gv.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					View view = View.inflate(pingjiaActivity.this,
							R.layout.grid_item, null);
					ImageView iv2 = (ImageView) view.findViewById(R.id.iv);
					Picasso.with(pingjiaActivity.this).load(list.get(arg0))
							.into(iv2);
					return view;
				}

				@Override
				public long getItemId(int arg0) {
					return arg0;
				}

				@Override
				public Object getItem(int arg0) {
					return list.get(arg0);
				}

				@Override
				public int getCount() {
					return list.size();
				}
			});

			// 点赞的人
			GridView gv1 = (GridView) view.findViewById(R.id.gridview1);
			gv1.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					View view = View.inflate(pingjiaActivity.this,
							R.layout.usrlike_item, null);
					ImageView iv1 = (ImageView) view
							.findViewById(R.id.usrlike_pic);
					Picasso.with(pingjiaActivity.this)
							.load(myAssess.get(position).getUsr_like()
									.get(arg0).getPic()).resize(100, 100)
							.centerCrop().into(iv1);
					return view;
				}

				@Override
				public long getItemId(int arg0) {
					return arg0;
				}

				@Override
				public Object getItem(int arg0) {
					return myAssess.get(position).getUsr_like().get(arg0);
				}

				@Override
				public int getCount() {
					return myAssess.get(position).getUsr_like().size();
				}
			});

			// 点赞
			love = (ImageView) view.findViewById(R.id.pjxqy_love3);
			// 已点赞时爱心的颜色
			for (UserBean usr : myAssess.get(position).getUsr_like()) {
				if (usr != null)
					if (usr.getTelephone().equals(user.getTelephone()))
						love.setBackgroundResource(R.drawable.favour_clicked);
			}

			love.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 网络传输
					ArrayList params = new ArrayList();
					params.add(new BasicNameValuePair("evaluation_id", myAssess
							.get(position).getId()));
					String url = "evaluation/base";
					String data;
					try {
						data = NetTransfer.transfer(url, "put", params, true,
								user.getAccess_token(), null);
						NetTransfer nt = new NetTransfer();
						nt.return_data(data);
						Toast.makeText(pingjiaActivity.this, nt.getMsg(),
								Toast.LENGTH_LONG).show();
						// 刷新数据
						if ("mine".equals(flag)) {
							ArrayList update_params = new ArrayList();
							update_params.add(new BasicNameValuePair("action",
									"person"));
							String update_url = "evaluation/base";
							NetTransfer update_nt = new NetTransfer();
							String update_data = NetTransfer.transfer(
									update_url, "get", update_params, true,
									user.getAccess_token(), null);
							myAssess = update_nt.handle_eb_list(update_data);
						} else {
							String evaluation_url = "evaluation/base";
							ArrayList<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
							params1.add(new BasicNameValuePair("action",
									"person"));
							params1.add(new BasicNameValuePair("user_tel",
									person.getTelephone()));
							try {
								String evaluation = NetTransfer.transfer(
										evaluation_url, "get", params1, true,
										user.getAccess_token(), null);
								myAssess = new NetTransfer()
										.handle_eb_list(evaluation);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						ba.notifyDataSetChanged();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});

			return view;
		}
	}

	// 关注事件
	private class FollowOnclick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("action", "follow"));
			params.add(new BasicNameValuePair("follow_tel", person
					.getTelephone()));
			String url = "user/base";
			try {
				String data = NetTransfer.transfer(url, "put", params, true,
						user.getAccess_token(), null);
				NetTransfer nt = new NetTransfer();
				nt.return_data(data);
				if ("success_follow".equals(nt.getStatus()))
					status.setText("取消关注");
				else
					status.setText("关注");
				Toast.makeText(pingjiaActivity.this, nt.getMsg(),
						Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
