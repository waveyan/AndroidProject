package com.trabal.linear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.trabal.R;
import com.trabal.user.Bean.UserBean;

public class classifyactivity extends Activity {
	private ImageButton button;
	private Button button1, button2, button3, button4, button5, button6,
			button7, button8, button9, button10;

	private Intent intent;
	private Intent last_intent;
	private UserBean user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);

		// 用戶傳輸
		last_intent = classifyactivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");

		intent = this.getIntent();

		button = (ImageButton) findViewById(R.id.leftarrow4ID);
		button1 = (Button) findViewById(R.id.a001);
		button2 = (Button) findViewById(R.id.a002);
		button3 = (Button) findViewById(R.id.a003);
		button4 = (Button) findViewById(R.id.a004);
		button5 = (Button) findViewById(R.id.a005);
		button6 = (Button) findViewById(R.id.a006);
		button7 = (Button) findViewById(R.id.a007);
		button8 = (Button) findViewById(R.id.a008);
		button9 = (Button) findViewById(R.id.a009);
		button10 = (Button) findViewById(R.id.a010);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(classifyactivity.this,
						addactivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
				finish();
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_xmas = "圣诞季";
				intent.putExtra("xmas", str_xmas);
				// 请求代码可以自己设置，这里设置成20
				setResult(27, intent);
				finish();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_exhibit = "主题展览";
				intent.putExtra("exhibit", str_exhibit);
				setResult(28, intent);
				finish();
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_attention = "城中必关注";
				intent.putExtra("attention", str_attention);
				setResult(29, intent);
				finish();
			}
		});

		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_music = "音乐演出";
				intent.putExtra("music", str_music);
				setResult(30, intent);
				finish();
			}
		});
		button5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_opera = "歌剧话剧";
				intent.putExtra("opera", str_opera);
				setResult(31, intent);
				finish();
			}
		});
		button6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_buy = "周边买买";
				intent.putExtra("buy", str_buy);
				setResult(32, intent);
				finish();
			}
		});
		button7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_casual = "休闲沙龙";
				intent.putExtra("casual", str_casual);
				setResult(33, intent);
				finish();
			}
		});
		button8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_discount = "好康折扣";
				intent.putExtra("discount", str_discount);
				setResult(34, intent);
				finish();
			}
		});
		button9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_cartoon = "动漫卡通";
				intent.putExtra("cartoon", str_cartoon);
				setResult(35, intent);
				finish();
			}
		});
		button10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str_sport = "体育现场";
				intent.putExtra("sport", str_sport);
				setResult(36, intent);
				finish();
			}
		});
	}
}