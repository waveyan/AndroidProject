package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import com.squareup.picasso.Picasso;
import com.trabal.LoginActivity;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.huodongActivity.MyBaseAdapter;
import com.trabal.search.search;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.SharePreferencesTool;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class TakeMeToYourHome extends Activity {

	private ImageView pic1, pic2, pic3, pic4, report;

	private ImageButton favourButton, backButton, shareButton;

	private TextView englishname, chinaname, introduction, openTimes, cost,
			address, hs_telephone, content, pic1_text, pic2_text, pic3_text;

	private HotSpotBean hsb;

	private UserBean user;

	private Intent last_intent;


	private ArrayList<ActivityBean> ab_list;

	private ListView mListView, mlistView1;

	private CustomAdapter ba;
	private ArrayList<EvaluationBean> eb_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takemetoyourhome);

		// 获取上一个页面传过来的用户
		last_intent = TakeMeToYourHome.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		TextView city=(TextView) this.findViewById(R.id.city);
		city.setText(new SharePreferencesTool(TakeMeToYourHome.this, MODE_PRIVATE).popOut("city_name"));

		backButton = (ImageButton) this.findViewById(R.id.backButton);
		report = (ImageView) this.findViewById(R.id.report);

		hsb = (HotSpotBean) last_intent.getSerializableExtra("hsb");
		//刷新哎
		String url1="hotspot/base";
		ArrayList<BasicNameValuePair> params1=new ArrayList<BasicNameValuePair>();
		params1.add(new BasicNameValuePair("hotspot_id", hsb.getId()));
		String data1;
		try {
			data1 = NetTransfer.transfer(url1, "get", params1, true, user.getAccess_token(), null);
			hsb=new NetTransfer().handle_hs_data(data1);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		initView();
		
		//发表评论
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent;
				intent = new Intent(TakeMeToYourHome.this, assessactivity.class);
				intent.putExtra("user", user);
				intent.putExtra("didian", hsb.getName());
				intent.putExtra("hotspot_id", hsb.getId());
				intent.putExtra("go", "take");
				intent.putExtra("hsb", hsb);
				TakeMeToYourHome.this.startActivity(intent);
				TakeMeToYourHome.this.finish();
			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TakeMeToYourHome.this.finish();
			}
		});
		// 活动listview
		ab_list = hsb.getAbs();
		if (ab_list == null || ab_list.size() == 0) {
			// 网络传输
			ArrayList params = new ArrayList();
			params.add(new BasicNameValuePair("action", "hotspot")); // 上传数据对接属性
			params.add(new BasicNameValuePair("hotspot_id", hsb.getId())); // 上传数据对接属性
			String url = "activity/base";
			NetTransfer nt = new NetTransfer();
			try {
				String data = NetTransfer.transfer(url, "get", params, true,
						user.getAccess_token(), null);
				ab_list = nt.handle_ac_list(data);
				if (ab_list == null)
					ab_list = new ArrayList<ActivityBean>();
			} catch (IOException e) {
				ab_list = new ArrayList<ActivityBean>();
				e.printStackTrace();
			}
		}
		//若没有活动则隐藏活动栏
		if (ab_list.size()==0) {
			View r2 = this.findViewById(R.id.r2);
			r2.setVisibility(View.GONE);
		} else {

			// 初始化ListView控件,活动列表
			mListView = (ListView) findViewById(R.id.listview1);
			// 创建一个Adapter的实例
			MyBaseAdapter mAdapter = new MyBaseAdapter();
			// 设置Adapter
			mListView.setAdapter(mAdapter);
		}

		// 获取评价数据
		eb_list = hsb.getEbs();
		if (eb_list == null || eb_list.size() == 0) {

			// 网络传输
			ArrayList params = new ArrayList();
			params.add(new BasicNameValuePair("action", "hotspot")); // 上传数据对接属性
			params.add(new BasicNameValuePair("hotspot_id", hsb.getId())); // 上传数据对接属性
			String url = "evaluation/base";
			NetTransfer nt = new NetTransfer();
			try {

				String data = NetTransfer.transfer(url, "get", params,
						true, user.getAccess_token(), null);
				eb_list = nt.handle_eb_list(data);
				if (eb_list == null)
					eb_list = new ArrayList<EvaluationBean>();
			} catch (IOException e) {
				eb_list = new ArrayList<EvaluationBean>();
				e.printStackTrace();
			}

		}
		//若没有评价时候则隐藏活动栏
		if (eb_list.size()==0) {
			View r3 = this.findViewById(R.id.r3);
			r3.setVisibility(View.GONE);
		} else {
			// 评价listviwe
			mlistView1 = (ListView) findViewById(R.id.listview2);
			ba = new CustomAdapter();
			mlistView1.setAdapter(ba);
		}
		

	}

	private void initView() {
		// 初始化数据
		pic1 = (ImageView) this.findViewById(R.id.pic1);
		pic2 = (ImageView) this.findViewById(R.id.pic2);
		pic3 = (ImageView) this.findViewById(R.id.pic3);
		pic4 = (ImageView) this.findViewById(R.id.pic4);
		shareButton = (ImageButton) this.findViewById(R.id.shareButton);
		englishname = (TextView) this.findViewById(R.id.EnglishName);
		chinaname = (TextView) this.findViewById(R.id.ChinaName);
		introduction = (TextView) this.findViewById(R.id.Introduction);
		openTimes = (TextView) this.findViewById(R.id.OpenTimes);
		cost = (TextView) this.findViewById(R.id.Cost);
		address = (TextView) this.findViewById(R.id.Address);
		hs_telephone = (TextView) this.findViewById(R.id.hs_telephone);
		content = (TextView) this.findViewById(R.id.content);
		pic1_text = (TextView) this.findViewById(R.id.pic1_text);
		pic2_text = (TextView) this.findViewById(R.id.pic2_text);
		pic3_text = (TextView) this.findViewById(R.id.pic3_text);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic1()).centerCrop()
				.fit().into(pic1);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic2()).centerCrop()
				.fit().into(pic2);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic3()).centerCrop()
				.fit().into(pic3);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic2()).centerCrop()
				.fit().into(pic4);
		englishname.setText(hsb.getEnglishName());
		chinaname.setText(hsb.getName());
		introduction.setText(hsb.getWord());
		openTimes.setText(hsb.getWorktime());
		cost.setText(String.valueOf(hsb.getPrice()));
		address.setText(hsb.getAddress());
		hs_telephone.setText(hsb.getTelephone());
		content.setText(hsb.getContent());
		pic1_text.setText(hsb.getPic1_text());
		pic2_text.setText(hsb.getPic2_text());
		pic3_text.setText(hsb.getPic3_text());
		favourButton = (ImageButton) this.findViewById(R.id.favourButton);
		if (hsb.getIsfavour() == 1) {
			favourButton.setBackgroundResource(R.drawable.favour_clicked);
		}
		// 绑定收藏事件
		favourButton.setOnClickListener(new FavourClick());

		// 分享
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_TEXT,hsb.getName());
				shareIntent.setType("text/plain");
				// 设置分享列表的标题，并且每次都显示分享列表
				TakeMeToYourHome.this.startActivity(Intent.createChooser(
						shareIntent, "分享到"));

			}

		});
	}

	// 收藏事件
	private class FavourClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// 网络传输
			ArrayList params = new ArrayList();
			params.add(new BasicNameValuePair("hotspot_id", hsb.getId()));
			params.add(new BasicNameValuePair("action", "favour_hs"));
			String url = "user/base";
			try {
				String data = NetTransfer.transfer(url, "put", params, true,
						user.getAccess_token(), null);
				NetTransfer nt = new NetTransfer();
				nt.return_data(data);
				if ("success_favour".equals(nt.getStatus())) {
					hsb.setIsfavour(1);
					TakeMeToYourHome.this.favourButton
							.setBackgroundResource(R.drawable.favour_clicked);

				} else if ("success_unfavour".equals(nt.getStatus())) {
					hsb.setIsfavour(0);
					TakeMeToYourHome.this.favourButton
							.setBackgroundResource(R.drawable.favour);
				}
				Toast.makeText(TakeMeToYourHome.this, nt.getMsg(),
						Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// 活动详情页
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = View.inflate(TakeMeToYourHome.this,
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
			Picasso.with(TakeMeToYourHome.this)
					.load(ab_list.get(position).getPic1()).into(imageView);
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(TakeMeToYourHome.this,
							HdxiangqingActivity.class);
					intent.putExtra("ab", ab_list.get(position));
					intent.putExtra("user", TakeMeToYourHome.this.user);
					intent.putExtra("hsb", hsb);
					TakeMeToYourHome.this.startActivity(intent);
				}
			});
			return view;
		}
	}

	// 评价
	class CustomAdapter extends BaseAdapter {

		
		private ImageView love;

		public CustomAdapter() {
			
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
			View view = View.inflate(TakeMeToYourHome.this,
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
			rate.setRating(Float.parseFloat(eb_list.get(position).getRate()));
			mTextView1.setText(eb_list.get(position).getUser().getName());
			mTextView2.setText(eb_list.get(position).getTime());
			mTextView3.setText(eb_list.get(position).getMood());
			mTextView4.setText(hsb.getName());
			mTextView5
					.setText(String.valueOf(eb_list.get(position).getPrice()));
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.pjxqy_headpic3);

			// 用户头像
			Picasso.with(TakeMeToYourHome.this)
					.load(eb_list.get(position).getUser().getPic())
					.centerCrop().fit().into(imageView1);
			// 用户头像
			imageView1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(TakeMeToYourHome.this,
							pingjiaActivity.class);
					// intent.putExtra("from", "district");
					if (eb_list.get(position).getUser().getTelephone()
							.equals(user.getTelephone())) {
						intent.putExtra("flag", "mine");
						String evaluation_url = "evaluation/base";
						ArrayList<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
						params1.add(new BasicNameValuePair("action", "person"));
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
					} else {
						intent.putExtra("person", eb_list.get(position)
								.getUser());
					}
					intent.putExtra("user", user);
					TakeMeToYourHome.this.startActivity(intent);

				}
			});

			// 动态图片
			GridView gv = (GridView) view.findViewById(R.id.gridview);
			// silly！！！！！
			final ArrayList<String> list = new ArrayList<String>();
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
					View view = View.inflate(TakeMeToYourHome.this,
							R.layout.grid_item, null);
					ImageView iv2 = (ImageView) view.findViewById(R.id.iv);
					Picasso.with(TakeMeToYourHome.this).load(list.get(arg0))
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
					View view = View.inflate(TakeMeToYourHome.this,
							R.layout.usrlike_item, null);
					ImageView iv1 = (ImageView) view
							.findViewById(R.id.usrlike_pic);
					Picasso.with(TakeMeToYourHome.this)
							.load(eb_list.get(position).getUsr_like().get(arg0)
									.getPic()).resize(100, 100).centerCrop()
							.into(iv1);
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
						Toast.makeText(TakeMeToYourHome.this, nt.getMsg(),
								Toast.LENGTH_LONG).show();
						// 刷新数据
						String update_url = "evaluation/base";
						ArrayList update_params = new ArrayList();
						update_params.add(new BasicNameValuePair("action",
								"hotspot"));
						update_params.add(new BasicNameValuePair("hotspot_id",
								hsb.getId()));
						NetTransfer update_nt = new NetTransfer();
						String update_data = NetTransfer.transfer(update_url,
								"get", update_params, true,
								user.getAccess_token(), null);
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
