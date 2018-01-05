package com.trabal.routeplan;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.LoginActivity;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.RegisterActivity;
import com.trabal.route.mapmap.DriveRouteActivity;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RouteplanActivity4 extends Activity {
	private Intent last_intent;
	private UserBean user;
	private Button button;
	private TextView textview,title,time,introduce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan4);
		
		init();
		
		//返回上层
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RouteplanActivity4.this,RouteplanActivity2.class);
				intent.putExtra("user", user);
				RouteplanActivity4.this.startActivity(intent);
				finish();
			}
		});
		
		//确定
		textview.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//上传数据
				String hs_ids=last_intent.getStringExtra("hs_ids");
				String url="hotspot/route";
				ArrayList<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("title",title.getText().toString().trim()));
				parameters.add(new BasicNameValuePair("introduce",introduce.getText().toString().trim()));
				parameters.add(new BasicNameValuePair("time",time.getText().toString().trim()));
				parameters.add(new BasicNameValuePair("hotspot_ids",hs_ids));
				try {
					String data=NetTransfer.transfer(url, "post", parameters, true, user.getAccess_token(), null);
					NetTransfer nt=new NetTransfer();
					nt.return_data(data);
					Toast.makeText(RouteplanActivity4.this, nt.getMsg(),
							Toast.LENGTH_SHORT).show();
					if("success".equals(nt.getStatus())){
						
					Intent intent = new Intent(RouteplanActivity4.this,
							MainActivity.class);
					// 保持该用户登录状态
					intent.putExtra("user", user);
					RouteplanActivity4.this.startActivity(intent);
					RouteplanActivity4.this.finish();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void init(){
		last_intent = RouteplanActivity4.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		textview=(TextView) this.findViewById(R.id.next);
		button =(Button)findViewById(R.id.backButton);
		title=(TextView) findViewById(R.id.title);
		time=(TextView) findViewById(R.id.time);
		introduce=(TextView) findViewById(R.id.introduce);
		
	}
}
