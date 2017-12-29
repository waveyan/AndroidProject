package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

import com.trabal.R;
import com.trabal.listviewAdapter;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

public class RestaurantActivity extends Activity {
	private  ArrayList<HotSpotBean> hs_list;
	private ImageView imageView;
	private UserBean user;
	private Intent last_intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_restaurant);
		// 维持登录状态
		last_intent = this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		hs_list=initListview1();

		ListView listview1 = (ListView) findViewById(R.id.listview_restaurant);

		listviewAdapter fruitAdapter = new listviewAdapter(
				RestaurantActivity.this, R.layout.listview_item, hs_list);
		listview1.setAdapter(fruitAdapter);

	}

	private ArrayList<HotSpotBean> initListview1() {
		// 网络传输
		ArrayList params = new ArrayList();

		params.add(new BasicNameValuePair("what", last_intent.getStringExtra("what")));     //上传数据对接属性 

		String url = "hotspot/base";
		NetTransfer nt = new NetTransfer();
		try {

			String data = NetTransfer.transfer(url, "get", params, true, user.getAccess_token(),null);
			ArrayList<HotSpotBean> hs_list=nt.handle_hs_list(data);
			return hs_list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
