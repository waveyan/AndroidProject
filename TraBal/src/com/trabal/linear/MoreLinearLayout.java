package com.trabal.linear;

import com.trabal.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MoreLinearLayout extends LinearLayout {

	Context context;
	ImageButton imageButton, imageButton2, imageButton3, imageButton4;

	public MoreLinearLayout(final Context context) {
		super(context);
		this.context = context;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_more,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		imageButton = (ImageButton) this.findViewById(R.id.restaurantID);
		imageButton2 = (ImageButton) this.findViewById(R.id.coffeeID);
		imageButton3 = (ImageButton) this.findViewById(R.id.cakeID);
		imageButton4 = (ImageButton) this.findViewById(R.id.showID);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, RestaurantActivity.class);

				context.startActivity(intent);
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CoffeeActivity.class);

				context.startActivity(intent);
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ForeignfoodActivity.class);

				context.startActivity(intent);
			}
		});
		imageButton4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ExhibitionActivity.class);

				context.startActivity(intent);
			}
		});

	}

}
