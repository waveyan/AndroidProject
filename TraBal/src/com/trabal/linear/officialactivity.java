package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class officialactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_officialsite;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_official);

		intent = this.getIntent();

		EditText_officialsite = (EditText) findViewById(R.id.ed8ID);
		button = (ImageButton) findViewById(R.id.leftarrow11ID);
		sure = (TextView) findViewById(R.id.sureID);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(officialactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});

		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_officialsite = EditText_officialsite.getText()
						.toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("officialsite", str_officialsite);
				// 请求代码可以自己设置，这里设置成20
				setResult(20, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
