package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class telephoneactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_telephone;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephone);

		intent = this.getIntent();
		EditText_telephone = (EditText) findViewById(R.id.ed7ID);
		button = (ImageButton) findViewById(R.id.leftarrow9ID);
		sure = (TextView) findViewById(R.id.sure1ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(telephoneactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});

		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_telephone = EditText_telephone.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("telephone", str_telephone);
				// 请求代码可以自己设置，这里设置成20
				setResult(21, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
	}
}
