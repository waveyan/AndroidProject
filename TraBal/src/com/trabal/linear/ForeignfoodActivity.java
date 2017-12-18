package com.trabal.linear;

import com.trabal.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ForeignfoodActivity extends Activity{
	private ImageView imageView;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.activity_foreginfood);
	
	imageView= (ImageView)this.findViewById(R.id.Foreignfood1);
	
	imageView.setOnTouchListener(new OnTouchListener() {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ForeignfoodActivity.this, DIMUCUBE.class);

			ForeignfoodActivity.this.startActivity(intent);
			return false;
		}
	});
}
}