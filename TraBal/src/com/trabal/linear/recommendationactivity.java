package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;

public class recommendationactivity extends Activity {
	private ImageButton backTv;
	private UserBean user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommendation);
		backTv = (ImageButton)findViewById(R.id.leftarrow3ID);
		backTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Intent last_intent = recommendationactivity.this.getIntent();
//				user = (UserBean)last_intent.getSerializableExtra("user");
//				Intent intent = new Intent(recommendationactivity.this,MainActivity.class);
//				intent.putExtra("user", user);
//				recommendationactivity.this.startActivity(intent);
				finish();
				
			}
		});
		
	}
}
