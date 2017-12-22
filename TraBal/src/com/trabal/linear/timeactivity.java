package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class timeactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_time;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);

		intent = this.getIntent();
		EditText_time = (EditText) findViewById(R.id.ed9ID);
		button = (ImageButton) findViewById(R.id.leftarrow6ID);
		sure = (TextView) findViewById(R.id.sure5ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(timeactivity.this, addactivity.class);
				startActivity(intent);
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_time = EditText_time.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("time", str_time);
				// 请求代码可以自己设置，这里设置成20
				setResult(25, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
