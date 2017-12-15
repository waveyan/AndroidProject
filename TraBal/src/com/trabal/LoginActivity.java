package com.trabal;

import com.trabal.RegisterActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity{
	
	
	private TextView registerTV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		
		initView();
		registerTV=(TextView)this.findViewById(R.id.register1ID);
		
		registerTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				LoginActivity.this.startActivity(intent);
				
			}
		});
		
	
	}
	
	public void initView(){
		
		
		
	}
	


}

