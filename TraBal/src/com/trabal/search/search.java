package com.trabal.search;

import java.io.IOException;
import java.util.ArrayList;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class search extends Activity {
	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content3;
	private TextView positionTv, userTv;
	private UserBean user;
	ArrayList<UserBean> follow;
	private TextView textView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Intent intent = search.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");

		// 返回上层界面
		textView = (TextView) findViewById(R.id.cancel);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(search.this,
						MainActivity.class);
				intent.putExtra("user", user);
				search.this.startActivity(intent);
				finish();
			}
		});
	}
}