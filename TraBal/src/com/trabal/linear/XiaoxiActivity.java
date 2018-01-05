package com.trabal.linear;

import java.util.ArrayList;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.hotspot.Bean.DistrictBean;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class XiaoxiActivity extends Activity{
	
	ImageButton imageButton;
	private UserBean user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiaoxi_item);
		
		// 获取上一个页面传过来的用户
		Intent intent = XiaoxiActivity.this.getIntent();
		user = (UserBean) intent.getSerializableExtra("user");
		
		imageButton=(ImageButton)findViewById(R.id.leftarrow12ID);
		
		imageButton.setOnClickListener(new OnClickListener() {
			
			private int position;

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(XiaoxiActivity.this,MainActivity.class);
				intent.putExtra("user", user);
				XiaoxiActivity.this.startActivity(intent);
				finish();
			}
		});
	}

}
