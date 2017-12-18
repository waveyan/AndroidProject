package com.trabal;


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

public class RegisterActivity extends Activity {

	private ImageView back2TV,code_view;
	private TextView telephone,password,comfirm,code;
	private Code cerificate_code;
	private Button submit;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_register);
	    
	    initView();
	    back2TV=(ImageView)this.findViewById(R.id.back2ID);
	    back2TV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
				RegisterActivity.this.startActivity(intent);
			}
		});
	    
	}
	
    public void initView(){
    	// 验证码显示
    	cerificate_code = new Code();
		code_view = (ImageView) this.findViewById(R.id.code2);
		code_view.setImageBitmap(cerificate_code.createBitmap());
		code_view.setOnClickListener(new CodeOnClick());
		
		//注册表单提交
		telephone = (TextView) this.findViewById(R.id.yonghu2ID);
		password = (TextView) this.findViewById(R.id.password2ID);
		code = (TextView) this.findViewById(R.id.validate2ID);
		submit = (Button) this.findViewById(R.id.register2ID);
		comfirm = (TextView) this.findViewById(R.id.comfirmID);
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//获取输入值
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String comfirm_text =comfirm.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				//表单验证
				if (tel_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "用户名不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (password_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "密码不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (comfirm_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "请输入确认密码！",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (code_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "验证码不能为空！",
							Toast.LENGTH_LONG).show();
					return;
				}
				if(!password_text.equals(comfirm_text)){
					Toast.makeText(RegisterActivity.this, "新密码与确认密码不一致，请重新输入！",
							Toast.LENGTH_LONG).show();
				}

				if (!code_text.equals(cerificate_code.getCode().toLowerCase())) {
					Toast.makeText(RegisterActivity.this, "验证码错误！",
							Toast.LENGTH_LONG).show();
					RegisterActivity.this.cerificate_code = new Code();
					code_view.setImageBitmap(cerificate_code.createBitmap());
					return;
				}
				//网络传输
				ArrayList params = new ArrayList();
				params.add(new BasicNameValuePair("telephone", tel_text));
				params.add(new BasicNameValuePair("password", password_text));
				String url="register";
				try {
					String msg=NetTransfer.transfer(url, "post", params, null);
					Toast.makeText(RegisterActivity.this, msg,
								Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		

		
		
	}
	
    
    private class CodeOnClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			RegisterActivity.this.cerificate_code = new Code();
			code_view.setImageBitmap(cerificate_code.createBitmap());
			Log.e("code", cerificate_code.getCode());

		}
	}
}
