package com.trabal;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;


import com.trabal.user.Bean.UserBean;
import com.trabal.util.Code;
import com.trabal.util.net.NetTransfer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private ImageView back2TV,code_view;
	private TextView telephone,password,comfirm,code;
	private Code cerificate_code;
	private Button submit;
	private CheckBox box1,box2;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_register);
	    
	    
	    box1 = (CheckBox) this.findViewById(R.id.box1);
		box1.setOnClickListener(new OnclickListenerImp());
		box2 = (CheckBox) this.findViewById(R.id.box2);
		box2.setOnClickListener(new OnclickListenerOmp());
	    initView();
	    back2TV=(ImageView)this.findViewById(R.id.back2ID);
	    back2TV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
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
					return;
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
				params.add(new BasicNameValuePair("action", "register"));
				String url="user/base";
				NetTransfer nt = new NetTransfer();
				try {
					String msg=NetTransfer.transfer(url, "post", params, false, null,null);
					nt.return_data(msg);
					if ("success".equals(nt.getStatus())) {
						Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
						UserBean user=new UserBean();
						user.setPassword(password_text);
						user.setTelephone(tel_text);
						intent.putExtra("user", user);
						RegisterActivity.this.startActivity(intent);
						RegisterActivity.this.finish();
					} else {
						Toast.makeText(RegisterActivity.this, nt.getMsg(),
								Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		

		
		
	}
    private class OnclickListenerImp implements OnClickListener{
    	
	    @Override
	    public void onClick(View arg0) {
	    	if(RegisterActivity.this.box1.isChecked()){
	    		RegisterActivity.this.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());    	
	    }	
	    	else{
	    	RegisterActivity.this.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    	
	    }

	 }	

	 }
    
 private class OnclickListenerOmp implements OnClickListener{
    	
	    @Override
	    public void onClick(View arg0) {
	    	if(RegisterActivity.this.box2.isChecked()){
	    		RegisterActivity.this.comfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());    	
	    }	
	    	else{
	    	RegisterActivity.this.comfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
    	
	    }

	 }	

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
