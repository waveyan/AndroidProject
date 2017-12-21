package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.R;
import com.trabal.listview1;
import com.trabal.listviewAdapter;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantActivity extends Activity {
	private List<listview1> Listview;
	private ImageView imageView;
	private UserBean user;
	private Intent intent,last_intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_restaurant);
		Listview = new ArrayList<listview1>();
		// Î¬³ÖµÇÂ¼×´Ì¬
		last_intent = this.getIntent();
		intent = new Intent(RestaurantActivity.this, RestaurantActivity.class);
		user = (UserBean) last_intent.getSerializableExtra("user");
		intent.putExtra("user", user);
		
		initListview1();
		ListView listview1 = (ListView) findViewById(R.id.listview_restaurant);

		listviewAdapter fruitAdapter = new listviewAdapter(
				RestaurantActivity.this, R.layout.listview_item, Listview);
		listview1.setAdapter(fruitAdapter);



	}

	private void initListview1() {
		// ÍøÂç´«Êä
		ArrayList params = new ArrayList();
		params.add(new BasicNameValuePair("what", last_intent.getStringExtra("what")));
		String url = "hotspot/base";
		NetTransfer nt = new NetTransfer();
		String msg;
		try {
			String data = NetTransfer.transfer(url, "get", params, true, user.getAccess_token());
			ArrayList<HotSpotBean> hs_list=nt.handle_hs_list(data);
			if(hs_list!=null){
				for(HotSpotBean hsb :hs_list){
					listview1 hs = new listview1(hsb.getName(),hsb.getId(), hsb.getPic2(),hsb.getPic1(),hsb.getPic2(),hsb.getPic3());
					Listview.add(hs);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}
