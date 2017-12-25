package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trabal.R;

public class placeactivity extends Activity {
	private ImageButton button;
	private TextView sure;
	private EditText EditText_place;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		intent = this.getIntent();

		EditText_place = (EditText) findViewById(R.id.ed5ID);
		button = (ImageButton) findViewById(R.id.leftarrow7ID);
		sure = (TextView) findViewById(R.id.sure4ID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(placeactivity.this,
						addactivity.class);
				startActivity(intent);
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
				// �����������Լ����ã��������ó�20
				setResult(24, intent);
				// �رյ����Activity
				finish();
			}
		});
	}
}