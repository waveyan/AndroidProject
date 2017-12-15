package com.trabal;

import com.trabal.RegisterActivity;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.util.Code;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private ImageView code_view;
	private Code cerificate_code;
	private TextView telephone, password, code;
	private Button submit;
	private TextView registerTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		initView();		
	
	}
	
	public void initView(){
		// 显示验证码
		cerificate_code = new Code();
		code_view = (ImageView) this.findViewById(R.id.code1);
		code_view.setImageBitmap(cerificate_code.createBitmap());
		code_view.setOnClickListener(new CodeOnClick());

		// 登录表单提交
		telephone = (TextView) this.findViewById(R.id.yonghu1ID);
		password = (TextView) this.findViewById(R.id.password1ID);
		code = (TextView) this.findViewById(R.id.validate1ID);
		submit = (Button) this.findViewById(R.id.loginID);
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//获取输入值
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				//表单验证
				if (tel_text.equals("")) {
					Toast.makeText(LoginActivity.this, "用户名不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (password_text.equals("")) {
					Toast.makeText(LoginActivity.this, "密码不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (code_text.equals("")) {
					Toast.makeText(LoginActivity.this, "验证码不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (!code_text.equals(cerificate_code.getCode().toLowerCase())) {
					Toast.makeText(LoginActivity.this, "验证码错误！",
							Toast.LENGTH_LONG).show();
					LoginActivity.this.cerificate_code = new Code();
					code_view.setImageBitmap(cerificate_code.createBitmap());
					return;
				}
				//网络传输
				ArrayList params = new ArrayList();
				params.add(new BasicNameValuePair("telephone", tel_text));
				params.add(new BasicNameValuePair("password", password_text));
				String url="login";
				try {
					String msg=NetTransfer.transfer(url, "post", params, null);
					Toast.makeText(LoginActivity.this, msg,
								Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
		//注册跳转
		registerTV=(TextView)this.findViewById(R.id.register1ID);
		registerTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				LoginActivity.this.startActivity(intent);
				
			}
		});

	}
	


	/**
	 * 验证码点击事件
	 * 
	 * @author Administrator
	 * 
	 */
	private class CodeOnClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			LoginActivity.this.cerificate_code = new Code();
			code_view.setImageBitmap(cerificate_code.createBitmap());
			Log.e("code", cerificate_code.getCode());

		}
	}

}

