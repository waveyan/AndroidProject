package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;
import com.trabal.user.Bean.UserBean;

public class placeactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_place;

	private Intent intent;
	private Intent last_intent;
	private UserBean user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		intent = this.getIntent();

		// 用戶傳輸
		last_intent = placeactivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		EditText_place = (EditText) findViewById(R.id.ed5ID);
		button = (ImageButton) findViewById(R.id.leftarrow7ID);
		sure = (TextView) findViewById(R.id.sure4ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(placeactivity.this,
//						addactivity.class);
//				intent.putExtra("user", user);
//				startActivity(intent);
				finish();
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_place = EditText_place.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("place", str_place);
				// 请求代码可以自己设置，这里设置成20
				setResult(24, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
