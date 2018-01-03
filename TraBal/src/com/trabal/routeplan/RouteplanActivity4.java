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

public class RouteplanActivity4 extends Activity {
	private Intent last_intent;
	private UserBean user;
	private Button button;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan4);
		
		last_intent = RouteplanActivity4.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		//∑µªÿ…œ≤„
		button =(Button)findViewById(R.id.backButton);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RouteplanActivity4.this,RouteplanActivity3.class);
				intent.putExtra("user", user);
				RouteplanActivity4.this.startActivity(intent);
				finish();
			}
		});
	}
}
