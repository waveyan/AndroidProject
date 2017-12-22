package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class priceactivity extends Activity {
	private ImageButton button;
	private Button button1;
	private TextView sure;
	private EditText EditText_price;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price);

		intent = this.getIntent();
		EditText_price = (EditText) findViewById(R.id.ed11ID);
		button = (ImageButton) findViewById(R.id.leftarrow10ID);
		button1 = (Button) findViewById(R.id.c001);
		sure = (TextView) findViewById(R.id.sure7ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(priceactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_free = "免费活动";
				intent.putExtra("free", str_free);
				// 请求代码可以自己设置，这里设置成20
				setResult(37, intent);
				finish();
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_price = EditText_price.getText().toString();
				intent.putExtra("price", str_price);
				setResult(26, intent);
				finish();
			}
		});
	}
}
