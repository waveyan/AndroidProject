package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.DynamicLinearLayout.MyBaseAdapter;
import com.trabal.linear.FansLinearLayout.CustomAdapter;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

public class chosepositionactivity extends Activity {

	private ImageButton button;
	private Intent last_intent;
	private ListView listView;
	private UserBean user;
	private  ArrayList<HotSpotBean> hs_list;
	private BaseAdapter ba;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choseposition);

		
		last_intent = chosepositionactivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		String url = "hotspot/base";
		NetTransfer nt = new NetTransfer();
		try {
			String data = NetTransfer.transfer(url, "get", null, true, user.getAccess_token(),null);
			hs_list=nt.handle_hs_list(data);	
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		button = (ImageButton) findViewById(R.id.leftarrow2ID);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(chosepositionactivity.this,
						assessactivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		listView = (ListView) this.findViewById(R.id.chose_listview);

		ba = new CustomAdapter();
		listView.setAdapter(ba);

	}

	class CustomAdapter extends BaseAdapter {
		
		
		public int getCount() {
			return hs_list.size();
		}

		@Override
		public Object getItem(int position) {
			return hs_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// 获取布局文件
			View view = LayoutInflater.from(chosepositionactivity.this)
					.inflate(R.layout.chose_listview_item, null);
			TextView name = (TextView) view.findViewById(R.id.chose_itemtext1);
			name.setText(hs_list.get(position).getName());
			
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent last_intent = chosepositionactivity.this.getIntent();
					user = (UserBean) last_intent.getSerializableExtra("user");
					Intent intent = new Intent();
					String backData = hs_list.get(position).getName();
					intent.putExtra("position", backData);
					intent.putExtra("user", user);
					intent.putExtra("positionID", hs_list.get(position).getId());
					setResult(2000, intent);
					// 结束当前页面(关闭当前界面)
					finish();
				}
			});

			return view;
		}

	}
}
