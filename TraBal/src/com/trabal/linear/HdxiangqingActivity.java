package com.trabal.linear;

import com.trabal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HdxiangqingActivity extends Activity{
	private ImageButton imageButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hdxiangqingye);
		
		imageButton= (ImageButton)findViewById(R.id.hdxq_back);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(HdxiangqingActivity.this,huodongActivity.class);
				HdxiangqingActivity.this.startActivity(intent);			
				}
		});
	}
}
