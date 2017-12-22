package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class introduceactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_introduce;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduce);

		intent = this.getIntent();
		EditText_introduce = (EditText) findViewById(R.id.ed6ID);
		button = (ImageButton) findViewById(R.id.leftarrow8ID);
		sure = (TextView) findViewById(R.id.sure2ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(introduceactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_introduce = EditText_introduce.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("introduce", str_introduce);
				// 请求代码可以自己设置，这里设置成20
				setResult(22, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
