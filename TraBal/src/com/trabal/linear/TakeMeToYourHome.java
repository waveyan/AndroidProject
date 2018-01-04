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
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;
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
import android.widget.TextView;
import android.widget.Toast;

public class TakeMeToYourHome extends Activity {

	private ImageView pic1, pic2, pic3, pic4;

	private ImageButton favourButton, backButton, shareButton;

	private TextView englishname, chinaname, introduction, openTimes, cost,
			address, hs_telephone, content, pic1_text, pic2_text, pic3_text;

	private HotSpotBean hsb;

	private UserBean user;

	private Intent last_intent;

	private String from;

	private ArrayList<ActivityBean> ab_list;

	private ListView mListView, mlistView1;

	private CustomAdapter ba;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takemetoyourhome);

		// 获取上一个页面传过来的用户
		last_intent = TakeMeToYourHome.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		from = last_intent.getStringExtra("from");

		backButton = (ImageButton) this.findViewById(R.id.backButton);

		hsb = (HotSpotBean) last_intent.getSerializableExtra("hsb");
		initView();

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent;
				if ("collect".equals(from)) {
					intent = new Intent(TakeMeToYourHome.this,
							collectActivity.class);
				} else if ("hdxiangqing".equals(from)) {
					intent = new Intent(TakeMeToYourHome.this,
							HdxiangqingActivity.class);
					intent.putExtra("ab",
							last_intent.getSerializableExtra("ab"));
					intent.putExtra("from",
							last_intent.getSerializableExtra("last_from"));
				} else if ("index".equals(from)) {
					intent = new Intent(TakeMeToYourHome.this,
							MainActivity.class);
					intent.putExtra("from", "index");
				} else if ("syxqy".equals(from)) {
					intent = new Intent(TakeMeToYourHome.this,
							SYxqyActivity.class);
					intent.putExtra("from", "syxqy");
					intent.putExtra("db",
							last_intent.getSerializableExtra("db"));
					intent.putExtra("user", user);
				} else {
					intent = new Intent(TakeMeToYourHome.this,
							RestaurantActivity.class);
					String what = last_intent.getStringExtra("what");
					intent.putExtra("what", last_intent.getStringExtra("what"));
				}
				intent.putExtra("user", user);
				TakeMeToYourHome.this.startActivity(intent);
				TakeMeToYourHome.this.finish();
			}
		});

		// 活动listview
		ab_list = new ArrayList<ActivityBean>();
		// 网络传输获得数据，一次性获取收藏的活动和地点
		String url = "user/get_my_activity";
		try {
			String data = NetTransfer.transfer(url, "get", null, true,
					user.getAccess_token(), null);
			NetTransfer nt = new NetTransfer();
			// 防止收藏为空时的空指针
			ArrayList<ActivityBean> ab_data = nt.handle_ac_list(data);
			ab_list = new ArrayList<ActivityBean>();
			if (ab_data != null)
				ab_list = ab_data;
		} catch (Exception e) {
			Log.e("地点收藏", e.getMessage());
		}

		// 初始化ListView控件
		mListView = (ListView) findViewById(R.id.listview1);
		// 创建一个Adapter的实例
		MyBaseAdapter mAdapter = new MyBaseAdapter();
		// 设置Adapter
		mListView.setAdapter(mAdapter);

		// 评价listviwe
		mlistView1 = (ListView) findViewById(R.id.listview2);

		// 创建一个Adapter的实例
		ba = new CustomAdapter();
		mlistView1.setAdapter(ba);
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
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic2()).into(pic2);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic3()).into(pic3);
		Picasso.with(TakeMeToYourHome.this).load(hsb.getPic2()).into(pic4);
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
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						"This is my Share text.");
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
					TakeMeToYourHome.this.favourButton
							.setBackgroundResource(R.drawable.favour_clicked);

				} else if ("success_unfavour".equals(nt.getStatus())) {
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
					intent.putExtra("from", "take");
					TakeMeToYourHome.this.startActivity(intent);
				}
			});
			return view;
		}
	}

	class CustomAdapter extends BaseAdapter {

		private ArrayList<EvaluationBean> eb_list;
		private ArrayList<String> list;
		private ImageView love;

		public CustomAdapter() {
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

			Picasso.with(TakeMeToYourHome.this)
					.load(eb_list.get(position).getUser().getPic())
					.centerCrop().fit().into(imageView1);

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
					Picasso.with(TakeMeToYourHome.this)
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
						Toast.makeText(TakeMeToYourHome.this,
								nt.getMsg(), Toast.LENGTH_LONG).show();
						// 刷新数据
						String update_url = "evaluation/get_evaluation_from_my_follow";
						NetTransfer update_nt = new NetTransfer();
						String update_data = NetTransfer.transfer(update_url,
								"get", null, true, user.getAccess_token(), null);
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
