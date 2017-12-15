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
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//��ȡ����ֵ
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				//����֤
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
				//���紫��
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
		
		//ע����ת
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

