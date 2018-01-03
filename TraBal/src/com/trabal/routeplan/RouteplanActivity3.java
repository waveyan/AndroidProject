package com.trabal.routeplan;

import com.trabal.R;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RouteplanActivity3 extends Activity{
 private Button button;
private TextView textview;
private Intent last_intent;
private UserBean user;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_routeplan3);
	
	last_intent = RouteplanActivity3.this.getIntent();
	user = (UserBean) last_intent.getSerializableExtra("user");
	
	//返回上层
	button =(Button)findViewById(R.id.backButton);
	
	button.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(RouteplanActivity3.this,RouteplanActivity2.class);
			intent.putExtra("user", user);
			RouteplanActivity3.this.startActivity(intent);
			finish();
		}
	});
	
	//下一步跳转
	textview =(TextView)findViewById(R.id.next);
	
	textview.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(RouteplanActivity3.this,RouteplanActivity4.class);
			intent.putExtra("user", user);
			RouteplanActivity3.this.startActivity(intent);
			finish();
		}
	});
}
}
