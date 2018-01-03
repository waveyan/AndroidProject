package com.trabal.linear;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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
		

		EditText_time = (EditText) findViewById(R.id.ed9ID);

		EditText_time.setOnClickListener(new View.OnClickListener() {
			Calendar c = Calendar.getInstance();

			@Override
			public void onClick(View v) {
				// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
				new DoubleDatePickerDialog(timeactivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						String textString = String.format("%d-%d-%d—%d-%d-%d", startYear,
								startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
						EditText_time.setText(textString);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
			}
		});

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
