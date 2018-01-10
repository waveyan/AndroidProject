package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.linear.PositionLinearLayout.MyBaseAdapter;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;

import com.trabal.util.net.NetTransfer;
import com.trabal.util.net.Tools;

public class DynamicLinearLayout extends LinearLayout {

	private Context context;
	private Intent last_intent;
	private UserBean user;
	private ListView mlistView;
	private View headerView;
	private ArrayList<EvaluationBean> eb_list;
	private ArrayList<String> list;
	private BaseAdapter ba;
	private ImageView love;

	public DynamicLinearLayout(Context context) {
		super(context);
		this.context = context;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(
				R.layout.linear_dynamic, null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		// 获取上一个页面传过来的用户
		last_intent = ((Activity) context).getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		// 获取数据
		String url = "evaluation/get_evaluation_from_my_follow";
		NetTransfer nt = new NetTransfer();
		try {
			String data = NetTransfer.transfer(url, "get", null, true,
					user.getAccess_token(), null);
			eb_list = nt.handle_eb_list(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		mlistView = (ListView) view.findViewById(R.id.dynamic_listview);


		// 创建一个Adapter的实例
		ba = new MyBaseAdapter();
		mlistView.setAdapter(ba);

	}

	class MyBaseAdapter extends BaseAdapter {

		public MyBaseAdapter() {

		}

		public int getCount() {
			return eb_list.size();
		}

		public Object getItem(int position) {
			return eb_list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = LayoutInflater.from(context).inflate(
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
			RatingBar rate =(RatingBar)view.findViewById(R.id.pjxqy_stars3);
			rate.setRating(Float.parseFloat(eb_list.get(position).getRate()));
			mTextView1.setText(eb_list.get(position).getUser().getName());
			mTextView2.setText(eb_list.get(position).getTime());
			mTextView3.setText(eb_list.get(position).getMood());
			mTextView4.setText(eb_list.get(position).getHs().getName());
			mTextView5
					.setText(String.valueOf(eb_list.get(position).getPrice()));
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.pjxqy_headpic3);
			ImageView imageView5 = (ImageView) view
					.findViewById(R.id.pjxqy_head6);
			ImageView imageView6 = (ImageView) view
					.findViewById(R.id.pjxqy_head7);
			ImageView imageView7 = (ImageView) view
					.findViewById(R.id.pjxqy_head8);
			ImageView imageView8 = (ImageView) view
					.findViewById(R.id.pjxqy_head9);
			ImageView imageView9 = (ImageView) view
					.findViewById(R.id.pjxqy_head10);

			Picasso.with(DynamicLinearLayout.this.context)
					.load(eb_list.get(position).getUser().getPic())
					.centerCrop().transform(new Tools.CircleTransform()).fit().into(imageView1);
			
			//用户头像
			imageView1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(DynamicLinearLayout.this.context,pingjiaActivity.class);
					if(eb_list.get(position).getUser().getTelephone().equals(user.getTelephone())){
						intent.putExtra("flag", "mine");
						String evaluation_url = "evaluation/base";
						ArrayList<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
						params1.add(new BasicNameValuePair("action",
								"person"));
						try {
							String evaluation = NetTransfer.transfer(
									evaluation_url, "get", params1, true,
									user.getAccess_token(), null);
							ArrayList<EvaluationBean> myAssess = new NetTransfer()
									.handle_eb_list(evaluation);
							intent.putExtra("myAssess", myAssess);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						intent.putExtra("person",eb_list.get(position).getUser());
					}
					intent.putExtra("user", user);
					DynamicLinearLayout.this.context.startActivity(intent);
					
				}
			});
			

			// 动态图片
			GridView gv = (GridView) view.findViewById(R.id.gridview);
			// silly！！！！！
			list = new ArrayList<String>();
			if (eb_list.get(position).getPic1() != null
					&& !("".equals(eb_list.get(position).getPic1())))
				list.add(eb_list.get(position).getPic1());
			if (eb_list.get(position).getPic2() != null
					&& !("".equals(eb_list.get(position).getPic2())))
				list.add(eb_list.get(position).getPic2());
			if (eb_list.get(position).getPic3() != null
					&& !("".equals(eb_list.get(position).getPic3())))
				list.add(eb_list.get(position).getPic3());
			if (list.size() == 0)
				gv.setVisibility(View.GONE);
			else if (list.size() == 1)
				gv.setNumColumns(1);
			else if (list.size() == 2)
				gv.setNumColumns(2);
			else
				gv.setNumColumns(3);
			
			gv.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					View view = View.inflate(DynamicLinearLayout.this.context,
							R.layout.grid_item, null);
					ImageView iv2 = (ImageView) view.findViewById(R.id.iv);
					Picasso.with(DynamicLinearLayout.this.context)
							.load(list.get(arg0)).into(iv2);
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
					View view = View.inflate(DynamicLinearLayout.this.context,
							R.layout.usrlike_item, null);
					ImageView iv1 = (ImageView) view
							.findViewById(R.id.usrlike_pic);
					Picasso.with(DynamicLinearLayout.this.context)
							.load(eb_list.get(position).getUsr_like().get(arg0)
									.getPic()).resize(100, 100).centerCrop().into(iv1);
					return view;
				}

				@Override
				public long getItemId(int arg0) {
					return arg0;
				}

				@Override
				public Object getItem(int arg0) {
					return eb_list.get(position).getUsr_like().get(arg0);
				}

				@Override
				public int getCount() {
					return eb_list.get(position).getUsr_like().size();
				}
			});

			// 点赞
			love = (ImageView) view.findViewById(R.id.pjxqy_love3);
			// 已点赞时爱心的颜色
			for (UserBean usr : eb_list.get(position).getUsr_like()) {
				if (usr != null)
					if (usr.getTelephone().equals(user.getTelephone()))
						love.setBackgroundResource(R.drawable.favour_clicked);
			}

			love.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 网络传输
					ArrayList params = new ArrayList();
					params.add(new BasicNameValuePair("evaluation_id", eb_list
							.get(position).getId()));
					String url = "evaluation/base";
					String data;
					try {
						data = NetTransfer.transfer(url, "put", params, true,
								user.getAccess_token(), null);
						NetTransfer nt = new NetTransfer();
						nt.return_data(data);
						Toast.makeText(DynamicLinearLayout.this.context,
								nt.getMsg(), Toast.LENGTH_LONG).show();
						//刷新数据
						String update_url = "evaluation/get_evaluation_from_my_follow";
						NetTransfer update_nt = new NetTransfer();
							String update_data = NetTransfer.transfer(update_url,
									"get", null, true, user.getAccess_token(),
									null);
							eb_list = update_nt.handle_eb_list(update_data);
						ba.notifyDataSetChanged();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			return view;
		}
	}

}
