package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;
import com.trabal.util.net.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HdxiangqingActivity extends Activity {
	private ImageButton back;

	private Intent last_intent;

	private UserBean user;

	private ActivityBean ab;

	private TextView type, title, host_user, hotspot, time, telephone,
			introduction, hs_name;

	private ImageView pic1, pic2, pic3, pic4, host_usr_pic, w1, w2, w3, w4,
			all_w, hs1, hs2, hs3, hs4;

	private ImageButton favour;

	private RelativeLayout hd_hotspot;
	String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hdxiangqingye);

		last_intent = HdxiangqingActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		ab = (ActivityBean) last_intent.getSerializableExtra("ab");
		from = last_intent.getStringExtra("from");
		// 填充数据
		init_view();

		// 返回按钮
		back = (ImageButton) findViewById(R.id.hdxq_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//
//				if ("collect".equals(from)) {
//					Intent intent = new Intent(HdxiangqingActivity.this,
//							collectActivity.class);
//					intent.putExtra("from", "collect_exercise");
//					intent.putExtra("user", user);
//					HdxiangqingActivity.this.startActivity(intent);
//					HdxiangqingActivity.this.finish();
//				}
//
//				else if ("more".equals(from)) {
//					Intent intent = new Intent(HdxiangqingActivity.this,
//							MainActivity.class);
//					intent.putExtra("from", "more");
//					intent.putExtra("user", user);
//					HdxiangqingActivity.this.startActivity(intent);
//					HdxiangqingActivity.this.finish();
//				} else if ("myactivity".equals(from)) {
//					Intent intent = new Intent(HdxiangqingActivity.this,
//							huodongActivity.class);
//					intent.putExtra("user", user);
//					HdxiangqingActivity.this.startActivity(intent);
//					HdxiangqingActivity.this.finish();
//				} else if ("take".equals(from)) {
//					Intent intent = new Intent(HdxiangqingActivity.this,
//							TakeMeToYourHome.class);
//					intent.putExtra("user", user);
//					intent.putExtra("hsb",
//							last_intent.getSerializableExtra("hsb"));
//					intent.putExtra("from",
//							last_intent.getStringExtra("last_from"));
//					HdxiangqingActivity.this.startActivity(intent);
//					HdxiangqingActivity.this.finish();
//				}

				// Intent intent=new
				// Intent(HdxiangqingActivity.this,huodongActivity.class);
				// HdxiangqingActivity.this.startActivity(intent);
				HdxiangqingActivity.this.finish();
			}
		});
	}

	private void init_view() {
		type = (TextView) this.findViewById(R.id.hdxq_textview2);
		title = (TextView) this.findViewById(R.id.hdxq_textview3);
		host_user = (TextView) this.findViewById(R.id.hdxq_textview4);
		hotspot = (TextView) this.findViewById(R.id.hdxq_textview5);
		time = (TextView) this.findViewById(R.id.hdxq_textview6);
		telephone = (TextView) this.findViewById(R.id.hdxq_textview7);
		introduction = (TextView) this.findViewById(R.id.hdxq_textview8);
		hs_name = (TextView) this.findViewById(R.id.hdxq_textview9);
		pic1 = (ImageView) this.findViewById(R.id.hdxq_image1);
		pic2 = (ImageView) this.findViewById(R.id.hdxq_image2);
		pic3 = (ImageView) this.findViewById(R.id.hdxq_image3);
		pic4 = (ImageView) this.findViewById(R.id.hdxq_image4);
		host_usr_pic = (ImageView) this.findViewById(R.id.hdxq_headpic);
		w1 = (ImageView) this.findViewById(R.id.hdxq_headpic1);
		w2 = (ImageView) this.findViewById(R.id.hdxq_headpic2);
		w3 = (ImageView) this.findViewById(R.id.hdxq_headpic3);
		w4 = (ImageView) this.findViewById(R.id.hdxq_headpic4);
		all_w = (ImageView) this.findViewById(R.id.hdxq_headpic5);
		hs1 = (ImageView) this.findViewById(R.id.hdxq_image5);
		hs2 = (ImageView) this.findViewById(R.id.hdxq_image6);
		hs3 = (ImageView) this.findViewById(R.id.hdxq_image7);
		hs4 = (ImageView) this.findViewById(R.id.hdxq_image8);
		favour = (ImageButton) this.findViewById(R.id.hdxq_favour);

		type.setText(ab.getType());
		title.setText(ab.getTitle());
		time.setText(ab.getTime());
		telephone.setText(ab.getTelephone());
		introduction.setText(ab.getIntroduction());

		Picasso.with(HdxiangqingActivity.this).load(ab.getPic1()).centerCrop()
				.fit().into(pic1);
		Picasso.with(HdxiangqingActivity.this).load(ab.getPic2()).centerCrop()
				.fit().into(pic2);
		Picasso.with(HdxiangqingActivity.this).load(ab.getPic3()).centerCrop()
				.fit().into(pic3);
		Picasso.with(HdxiangqingActivity.this).load(ab.getPic4()).centerCrop()
				.fit().into(pic4);

		if ("take".equals(from)) {
			View hs = this.findViewById(R.id.hd_hotspot);
			hs.setVisibility(View.GONE);
		}
		else {

			try {
				if (ab.getHsb() != null) {
					Picasso.with(HdxiangqingActivity.this)
							.load(ab.getHsb().getPic1()).centerCrop().fit()
							.into(hs1);
					Picasso.with(HdxiangqingActivity.this)
							.load(ab.getHsb().getPic2()).centerCrop().fit()
							.into(hs2);
					Picasso.with(HdxiangqingActivity.this)
							.load(ab.getHsb().getPic3()).centerCrop().fit()
							.into(hs3);
					Picasso.with(HdxiangqingActivity.this)
							.load(ab.getHsb().getPic1()).centerCrop().fit()
							.into(hs4);
					hotspot.setText(ab.getHsb().getName());
					hs_name.setText(ab.getHsb().getName());
				} else {
					HotSpotBean hsb = new HotSpotBean();
					hsb = (HotSpotBean) last_intent.getSerializableExtra("hsb");
					Picasso.with(HdxiangqingActivity.this).load(hsb.getPic1())
							.centerCrop().fit().into(hs1);
					Picasso.with(HdxiangqingActivity.this).load(hsb.getPic2())
							.centerCrop().fit().into(hs2);
					Picasso.with(HdxiangqingActivity.this).load(hsb.getPic3())
							.centerCrop().fit().into(hs3);
					Picasso.with(HdxiangqingActivity.this).load(hsb.getPic1())
							.centerCrop().fit().into(hs4);
					hotspot.setText(hsb.getName());
					hs_name.setText(hsb.getName());
				}
				host_user.setText(ab.getHost_user().getName());
				Picasso.with(HdxiangqingActivity.this)
						.load(ab.getHost_user().getPic())
						.transform(new Tools.CircleTransform()).centerCrop()
						.fit().into(host_usr_pic);

			} catch (Exception e) {
				// Log.e("hdxiangqing",e.getMessage());
			}
		}

		// 想去的人
		load_whowanttogo(ab.getWho_want_to_go());

		if (ab.getIsfavour() == 1) {
			favour.setBackgroundResource(R.drawable.favour_clicked);
		}
		// 绑定收藏事件
		favour.setOnClickListener(new FavourClick());
		// 地点的点击事件
		hd_hotspot = (RelativeLayout) this.findViewById(R.id.hd_hotspot);
		hd_hotspot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(HdxiangqingActivity.this,
						TakeMeToYourHome.class);
				intent.putExtra("user", user);
				intent.putExtra("from", "hdxiangqing");
				intent.putExtra("last_from", from);
				intent.putExtra("hsb", ab.getHsb());
				intent.putExtra("ab", ab);
				HdxiangqingActivity.this.startActivity(intent);
			}
		});
	}

	// 收藏事件
	private class FavourClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// 网络传输
			ArrayList params = new ArrayList();
			params.add(new BasicNameValuePair("activity_id", ab.getId()));
			params.add(new BasicNameValuePair("action", "favour_act"));
			String url = "user/base";
			try {
				String data = NetTransfer.transfer(url, "put", params, true,
						user.getAccess_token(), null);
				NetTransfer nt = new NetTransfer();
				nt.return_data(data);
				if ("success_favour".equals(nt.getStatus())) {
					HdxiangqingActivity.this.favour
							.setBackgroundResource(R.drawable.favour_clicked);
					// ajax加载，不知道這是怎麼回事！！！！
					ArrayList whowanttogo = new ArrayList();
					whowanttogo
							.add(new BasicNameValuePair("act_id", ab.getId()));
					whowanttogo.add(new BasicNameValuePair("action", "detail"));
					String ajax_url = "activity/base";
					String ajax_data = NetTransfer.transfer(ajax_url, "get",
							whowanttogo, true, user.getAccess_token(), null);
					NetTransfer ajax_nt = new NetTransfer();
					ActivityBean ajax_ab = nt.handle_ac_data(ajax_data);
					load_whowanttogo(ajax_ab.getWho_want_to_go());

				} else if ("success_unfavour".equals(nt.getStatus())) {
					HdxiangqingActivity.this.favour
							.setBackgroundResource(R.drawable.favour);
					// ajax加载
					ArrayList whowanttogo = new ArrayList();
					whowanttogo
							.add(new BasicNameValuePair("act_id", ab.getId()));
					whowanttogo.add(new BasicNameValuePair("action", "detail"));
					String ajax_url = "activity/base";
					String ajax_data = NetTransfer.transfer(ajax_url, "get",
							whowanttogo, true, user.getAccess_token(), null);
					NetTransfer ajax_nt = new NetTransfer();
					ActivityBean ajax_ab = nt.handle_ac_data(ajax_data);
					load_whowanttogo(ajax_ab.getWho_want_to_go());
				}
				Toast.makeText(HdxiangqingActivity.this, nt.getMsg(),
						Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// 防ajax加载想去的人
	private void load_whowanttogo(ArrayList<UserBean> user_list) {
		switch (user_list.size()) {
		case 0:
			w1.setVisibility(View.INVISIBLE);
			w2.setVisibility(View.INVISIBLE);
			w3.setVisibility(View.INVISIBLE);
			w4.setVisibility(View.INVISIBLE);
			all_w.setVisibility(View.INVISIBLE);
			break;
		case 1:
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(0).getPic())
					.transform(new Tools.CircleTransform()).into(w1);
			w1.setVisibility(View.VISIBLE);
			w2.setVisibility(View.INVISIBLE);
			w3.setVisibility(View.INVISIBLE);
			w4.setVisibility(View.INVISIBLE);
			all_w.setVisibility(View.INVISIBLE);
			break;
		case 2:
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(0).getPic())
					.transform(new Tools.CircleTransform()).into(w1);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(1).getPic())
					.transform(new Tools.CircleTransform()).into(w2);
			w1.setVisibility(View.VISIBLE);
			w2.setVisibility(View.VISIBLE);
			w3.setVisibility(View.INVISIBLE);
			w4.setVisibility(View.INVISIBLE);
			all_w.setVisibility(View.INVISIBLE);
			break;
		case 3:
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(0).getPic())
					.transform(new Tools.CircleTransform()).into(w1);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(1).getPic())
					.transform(new Tools.CircleTransform()).into(w2);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(2).getPic())
					.transform(new Tools.CircleTransform()).into(w3);
			w1.setVisibility(View.VISIBLE);
			w2.setVisibility(View.VISIBLE);
			w3.setVisibility(View.VISIBLE);
			w4.setVisibility(View.INVISIBLE);
			all_w.setVisibility(View.INVISIBLE);
		case 4:
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(0).getPic())
					.transform(new Tools.CircleTransform()).into(w1);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(1).getPic())
					.transform(new Tools.CircleTransform()).into(w2);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(2).getPic())
					.transform(new Tools.CircleTransform()).into(w3);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(3).getPic())
					.transform(new Tools.CircleTransform()).into(w4);
			w1.setVisibility(View.VISIBLE);
			w2.setVisibility(View.VISIBLE);
			w3.setVisibility(View.VISIBLE);
			w4.setVisibility(View.VISIBLE);
			all_w.setVisibility(View.INVISIBLE);
		default:
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(0).getPic())
					.transform(new Tools.CircleTransform()).into(w1);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(1).getPic())
					.transform(new Tools.CircleTransform()).into(w2);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(2).getPic())
					.transform(new Tools.CircleTransform()).into(w3);
			Picasso.with(HdxiangqingActivity.this)
					.load(user_list.get(3).getPic())
					.transform(new Tools.CircleTransform()).into(w4);
			w1.setVisibility(View.VISIBLE);
			w2.setVisibility(View.VISIBLE);
			w3.setVisibility(View.VISIBLE);
			w4.setVisibility(View.VISIBLE);
		}
	}

}
