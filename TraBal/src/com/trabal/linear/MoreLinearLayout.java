package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.picasso.Picasso;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.listview1;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreLinearLayout extends LinearLayout {

	Context context;
	ImageButton imageButton, imageButton2, imageButton3, imageButton4,
			imageButton5, imageButton6, imageButton7;

	ImageView act_image1, act_image2, act_image3, act_image4;

	TextView tv1, tv2, tv3, tv4;

	Intent intent;

	public MoreLinearLayout(final Context context) {
		super(context);
		this.context = context;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_more,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		imageButton = (ImageButton) this.findViewById(R.id.restaurantID);
		imageButton2 = (ImageButton) this.findViewById(R.id.coffeeID);
		imageButton3 = (ImageButton) this.findViewById(R.id.cakeID);
		imageButton4 = (ImageButton) this.findViewById(R.id.showID);
		imageButton5 = (ImageButton) this.findViewById(R.id.furnitureID);
		imageButton6 = (ImageButton) this.findViewById(R.id.shoppingID);
		imageButton7 = (ImageButton) this.findViewById(R.id.teaID);
		act_image1 = (ImageView) this.findViewById(R.id.caremaID);
		act_image2 = (ImageView) this.findViewById(R.id.christmasID);
		act_image3 = (ImageView) this.findViewById(R.id.paintID);
		act_image4 = (ImageView) this.findViewById(R.id.houseID);
		tv1 = (TextView) this.findViewById(R.id.tv1);
		tv2 = (TextView) this.findViewById(R.id.tv2);
		tv3 = (TextView) this.findViewById(R.id.tv3);
		tv4 = (TextView) this.findViewById(R.id.tv4);
		//加载活动
		init_act();
		// 维持登录状态
		intent = new Intent(context, RestaurantActivity.class);
		Intent last_intent = ((Activity) context).getIntent();
		intent.putExtra("user", last_intent.getSerializableExtra("user"));

		// 绑定点击事件
		imageButton.setOnClickListener(new ButtonOnClick());
		imageButton2.setOnClickListener(new ButtonOnClick());
		imageButton3.setOnClickListener(new ButtonOnClick());
		imageButton4.setOnClickListener(new ButtonOnClick());
		imageButton5.setOnClickListener(new ButtonOnClick());
		imageButton6.setOnClickListener(new ButtonOnClick());
		imageButton7.setOnClickListener(new ButtonOnClick());

	}

	class ButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();

			switch (id) {
			case R.id.coffeeID:
				intent.putExtra("what", "coffee");
				context.startActivity(intent);
				break;
			case R.id.restaurantID:
				intent.putExtra("what", "restaurant");
				context.startActivity(intent);
				break;
			case R.id.cakeID:
				intent.putExtra("what", "cake");
				context.startActivity(intent);
				break;
			case R.id.showID:
				intent.putExtra("what", "show");
				context.startActivity(intent);
				break;
			case R.id.furnitureID:
				intent.putExtra("what", "furniture");
				context.startActivity(intent);
				break;
			case R.id.shoppingID:
				intent.putExtra("what", "shopping");
				context.startActivity(intent);
				break;
			case R.id.teaID:
				intent.putExtra("what", "tea");
				context.startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

	private void init_act() {
		// 网络传输
		ArrayList params = new ArrayList();
		params.add(new BasicNameValuePair("num","4"));
		String url = "activity/base";
		NetTransfer nt = new NetTransfer();
		String msg;
		try {
			UserBean user =(UserBean)(((Activity)context) .getIntent().getSerializableExtra("user"));
			String data = NetTransfer.transfer(url, "get", params, true,
					user.getAccess_token(),null);
			ArrayList<ActivityBean> ac_list = nt.handle_ac_list(data);
			Picasso.with(context).load(ac_list.get(0).getPic1()).into(act_image1);
			Picasso.with(context).load(ac_list.get(1).getPic1()).into(act_image2);
			Picasso.with(context).load(ac_list.get(2).getPic1()).into(act_image3);
			Picasso.with(context).load(ac_list.get(3).getPic1()).into(act_image4);
			tv1.setText(ac_list.get(0).getIntroduction().substring(0, 20)+"...");
			tv2.setText(ac_list.get(1).getIntroduction().substring(0, 20)+"...");
			tv3.setText(ac_list.get(2).getIntroduction().substring(0, 20)+"...");
			tv4.setText(ac_list.get(3).getIntroduction().substring(0, 20)+"...");
			} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
