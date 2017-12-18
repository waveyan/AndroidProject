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
    	// ��֤����ʾ
    	cerificate_code = new Code();
		code_view = (ImageView) this.findViewById(R.id.code2);
		code_view.setImageBitmap(cerificate_code.createBitmap());
		code_view.setOnClickListener(new CodeOnClick());
		
		//ע����ύ
		telephone = (TextView) this.findViewById(R.id.yonghu2ID);
		password = (TextView) this.findViewById(R.id.password2ID);
		code = (TextView) this.findViewById(R.id.validate2ID);
		submit = (Button) this.findViewById(R.id.register2ID);
		comfirm = (TextView) this.findViewById(R.id.comfirmID);
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//��ȡ����ֵ
				String tel_text = telephone.getText().toString().trim();
				String password_text = password.getText().toString().trim();
				String comfirm_text =comfirm.getText().toString().trim();
				String code_text = code.getText().toString().trim()
						.toLowerCase();
				//����֤
				if (tel_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "�û�������Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (password_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (comfirm_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "������ȷ�����룡",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (code_text.equals("")) {
					Toast.makeText(RegisterActivity.this, "��֤�벻��Ϊ�գ�",
							Toast.LENGTH_LONG).show();
					return;
				}
				if(!password_text.equals(comfirm_text)){
					Toast.makeText(RegisterActivity.this, "��������ȷ�����벻һ�£����������룡",
							Toast.LENGTH_LONG).show();
				}

				if (!code_text.equals(cerificate_code.getCode().toLowerCase())) {
					Toast.makeText(RegisterActivity.this, "��֤�����",
							Toast.LENGTH_LONG).show();
					RegisterActivity.this.cerificate_code = new Code();
					code_view.setImageBitmap(cerificate_code.createBitmap());
					return;
				}
				//���紫��
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
