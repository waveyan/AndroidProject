package com.trabal;

import com.trabal.RegisterActivity;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

import com.trabal.user.Bean.UserBean;
import com.trabal.user.Dao.UserDao;
import com.trabal.util.Code;
import com.trabal.util.SharePreferencesTool;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private ImageView code_view, imageView;
	private Code cerificate_code;
	private TextView telephone, password, code;
	private Button submit;
	private TextView registerTV;
	private CheckBox box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		
		initView(this.getIntent());

		box = (CheckBox) this.findViewById(R.id.box);
		box.setOnClickListener(new OnclickListenerImp());
		
	}

	public void initView(Intent intent) {

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

		// 注册后跳转
		UserBean newuser = (UserBean) intent.getSerializableExtra("user");
		if (newuser != null) {
			telephone.setText(newuser.getTelephone());
			password.setText(newuser.getPassword());
		}

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取输入值
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				// 表单验证
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
				// 网络传输
				ArrayList params = new ArrayList();
				params.add(new BasicNameValuePair("telephone", tel_text));
				params.add(new BasicNameValuePair("password", password_text));
				params.add(new BasicNameValuePair("action", "login"));
				String url = "user/base";
				NetTransfer nt = new NetTransfer();
				try {
					String msg = NetTransfer.transfer(url, "post", params,
							false, null, null);
					nt.return_data(msg);
					if ("success".equals(nt.getStatus())) {

						// 登录成功写入本地数据库
						UserBean user = new UserBean();
						user=nt.handle_user_data(msg, user);
						user.setAccess_token(nt.getAccess_token());
						/**
						 * 当前activity 本地数据库local 数据库版本为1
						 */
						UserDao dao = new UserDao(LoginActivity.this, "local",
								1);
						UserBean old_user = dao.querryOne(user.getAccess_token());
						if (old_user == null)
							dao.insert(user);
						//保存登录状态
						SharePreferencesTool spt=new SharePreferencesTool(LoginActivity.this,MODE_PRIVATE);
						spt.putInto("online", user.getAccess_token());
						//
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						// 保持该用户登录状态
						intent.putExtra("user", user);
						LoginActivity.this.startActivity(intent);
						Toast.makeText(LoginActivity.this, nt.getMsg(),
								Toast.LENGTH_SHORT).show();
						LoginActivity.this.finish();
						
					} else {
						Toast.makeText(LoginActivity.this, nt.getMsg(),
								Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});

		// 注册跳转
		registerTV = (TextView) this.findViewById(R.id.register1ID);
		registerTV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
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
	
	 private class OnclickListenerImp implements OnClickListener{
	    	
	    @Override
	    public void onClick(View arg0) {
	    	if(LoginActivity.this.box.isChecked()){
	    		LoginActivity.this.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());    	
	    }	
	    	else{
	    	LoginActivity.this.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    	
	    }

	 }	

	 }
}
   