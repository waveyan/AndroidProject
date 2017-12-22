package com.trabal;

import com.trabal.RegisterActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.user.Bean.UserBean;
import com.trabal.user.Dao.UserDao;
import com.trabal.util.Code;
import com.trabal.util.net.NetTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private ImageView code_view, imageView;
	private Code cerificate_code;
	private TextView telephone, password, code;
	private Button submit;
	private TextView registerTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		initView(this.getIntent());

		// test upload file
		// String url="activity/base";
		// ArrayList params = new ArrayList();
		// params.add(new BasicNameValuePair("title", "123"));
		// params.add(new BasicNameValuePair("subject", "123"));
		// params.add(new BasicNameValuePair("time", "2017-10-10 19:30:30"));
		// params.add(new BasicNameValuePair("person", "1"));
		// params.add(new BasicNameValuePair("price", "30"));
		// ArrayList<HashMap<String,String>> files=new
		// ArrayList<HashMap<String,String>>();
		// HashMap<String,String> file=new HashMap<String, String>();
		// file.put("pic1","1.jpeg");
		// files.add(file);
		// try {
		// String msg=NetTransfer.transfer(url, "post", params, true,
		// "8e7c2373b74341b399da25c537bc8513", files);
		// } catch (IOException e) {
		// Log.e("ssssssssss",e.getMessage());
		// e.printStackTrace();
		// }

		imageView = (ImageView) this.findViewById(R.id.back1ID);
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);

				LoginActivity.this.startActivity(intent);
				return false;
			}
		});

	}

	public void initView(Intent intent) {

		// ��ʾ��֤��
		cerificate_code = new Code();
		code_view = (ImageView) this.findViewById(R.id.code1);
		code_view.setImageBitmap(cerificate_code.createBitmap());
		code_view.setOnClickListener(new CodeOnClick());

		// ��¼���ύ
		telephone = (TextView) this.findViewById(R.id.yonghu1ID);
		password = (TextView) this.findViewById(R.id.password1ID);
		code = (TextView) this.findViewById(R.id.validate1ID);
		submit = (Button) this.findViewById(R.id.loginID);

		// ע�����ת
		UserBean newuser = (UserBean) intent.getSerializableExtra("user");
		if (newuser != null) {
			telephone.setText(newuser.getTelephone());
			password.setText(newuser.getPassword());
		}

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ����ֵ
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				// ����֤
				if (tel_text.equals("")) {
					Toast.makeText(LoginActivity.this, "�û�������Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (password_text.equals("")) {
					Toast.makeText(LoginActivity.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (code_text.equals("")) {
					Toast.makeText(LoginActivity.this, "��֤�벻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (!code_text.equals(cerificate_code.getCode().toLowerCase())) {
					Toast.makeText(LoginActivity.this, "��֤�����",
							Toast.LENGTH_LONG).show();
					LoginActivity.this.cerificate_code = new Code();
					code_view.setImageBitmap(cerificate_code.createBitmap());
					return;
				}
				// ���紫��
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

						// ��¼�ɹ�д�뱾�����ݿ�
						UserBean user = new UserBean();
						user.setPassword(password_text);
						user.setTelephone(tel_text);
						user.setAccess_token(nt.getAccess_token());
						/**
						 * ��ǰactivity �������ݿ�local ���ݿ�汾Ϊ1
						 */
						UserDao dao = new UserDao(LoginActivity.this, "local",
								1);
						UserBean old_user = dao.querryOne(nt.getAccess_token());
						if (old_user == null)
							dao.insert(user);
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						// ���ָ��û���¼״̬
						intent.putExtra("user", user);
						LoginActivity.this.startActivity(intent);
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

		// ע����ת
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
	 * ��֤�����¼�
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
