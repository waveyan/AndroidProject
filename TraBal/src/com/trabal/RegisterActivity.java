package com.trabal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterActivity extends Activity {

	private ImageView back2TV;
	
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
		
		
		
	}
	
}
