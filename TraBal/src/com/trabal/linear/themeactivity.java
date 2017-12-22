package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class themeactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_theme;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme);

		intent = this.getIntent();
		EditText_theme = (EditText) findViewById(R.id.ed4ID);
		button = (ImageButton) findViewById(R.id.leftarrow5ID);
		sure = (TextView) findViewById(R.id.sure3ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(themeactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_theme = EditText_theme.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("theme", str_theme);
				// 请求代码可以自己设置，这里设置成20
				setResult(23, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
