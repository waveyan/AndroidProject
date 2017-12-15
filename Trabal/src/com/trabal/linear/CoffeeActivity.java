package com.trabal.linear;

import com.trabal.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class CoffeeActivity extends Activity{
	private ImageView imageView;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.activity_coffee);
	
	imageView= (ImageView)this.findViewById(R.id.Coffee1);
	
	imageView.setOnTouchListener(new OnTouchListener() {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CoffeeActivity.this, LostInLA.class);

			CoffeeActivity.this.startActivity(intent);
			return false;
		}
	});
}
}