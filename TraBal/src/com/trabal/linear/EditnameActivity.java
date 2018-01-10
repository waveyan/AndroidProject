package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditnameActivity extends Activity {
	private UserBean user;
	private TextView edit_submit, edit_back;
	private EditText edit_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editname);

		// debug
//		user = new UserBean();
//		user.setAccess_token("40b7ffbfc793d48154ef6f916b45ce7b");
//		user.setTelephone("android");
//		user.setName("Eason");
//		user.setPic("http://127.0.0.1:8000/media/pic/headpic_gYNeoBB.png");
		user=(UserBean) this.getIntent().getSerializableExtra("user");
		
		init_view();

	}

	private void init_view() {
		edit_submit = (TextView) this.findViewById(R.id.edit_submit);
		edit_back = (TextView) this.findViewById(R.id.backButton);
		edit_name = (EditText) this.findViewById(R.id.edit_name);
		edit_name.setText(user.getName());

		// 取消
		edit_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditnameActivity.this.finish();

			}
		});

		// 提交按钮
		edit_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = "user/base";
				ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("action", "alter"));
				params.add(new BasicNameValuePair("name", edit_name.getText()
						.toString().trim()));
				try {
					String data = NetTransfer.transfer(url, "post", params,
							true, user.getAccess_token(), null);
					NetTransfer nt = new NetTransfer();
					nt.return_data(data);
					Toast.makeText(EditnameActivity.this, nt.getMsg(),
							Toast.LENGTH_SHORT).show();
					if("success".equals(nt.getStatus()))
							EditnameActivity.this.finish();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

	}

}
