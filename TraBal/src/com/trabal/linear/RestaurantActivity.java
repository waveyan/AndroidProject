package com.trabal.linear;

import com.trabal.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class RestaurantActivity extends Activity{
	private ImageView imageView;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.activity_restaurant);
	
	imageView= (ImageView)this.findViewById(R.id.Res1);
	
	imageView.setOnTouchListener(new OnTouchListener() {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(RestaurantActivity.this, TakeMeToYourHome.class);

			RestaurantActivity.this.startActivity(intent);
			return false;
		}
	});
}
}