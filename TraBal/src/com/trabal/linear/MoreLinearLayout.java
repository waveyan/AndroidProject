package com.trabal.linear;

import com.trabal.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class MoreLinearLayout extends LinearLayout {

	Context context;
	ImageButton imageButton, imageButton2, imageButton3, imageButton4,
			imageButton5, imageButton6, imageButton7;
	Intent intent;

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
		imageButton5 = (ImageButton) this.findViewById(R.id.furnitureID);
		imageButton6 = (ImageButton) this.findViewById(R.id.shoppingID);
		imageButton7 = (ImageButton) this.findViewById(R.id.teaID);
		// 维持登录状态
		intent = new Intent(context, RestaurantActivity.class);
		Intent last_intent = ((Activity) context).getIntent();
		intent.putExtra("user", last_intent.getSerializableExtra("user"));

		// 绑定点击事件
		imageButton.setOnClickListener(new ButtonOnClick());
		imageButton2.setOnClickListener(new ButtonOnClick());
		imageButton3.setOnClickListener(new ButtonOnClick());
		imageButton4.setOnClickListener(new ButtonOnClick());
		imageButton5.setOnClickListener(new ButtonOnClick());
		imageButton6.setOnClickListener(new ButtonOnClick());
		imageButton7.setOnClickListener(new ButtonOnClick());

	}
	class ButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();

			switch (id) {
			case R.id.coffeeID:
				intent.putExtra("what", "coffee");
				context.startActivity(intent);
				break;
			case R.id.restaurantID:
				intent.putExtra("what", "restaurant");
				context.startActivity(intent);
				break;
			case R.id.cakeID:
				intent.putExtra("what", "cake");
				context.startActivity(intent);
				break;
			case R.id.showID:
				intent.putExtra("what", "show");
				context.startActivity(intent);
				break;
			case R.id.furnitureID:
				intent.putExtra("what", "furniture");
				context.startActivity(intent);
				break;
			case R.id.shoppingID:
				intent.putExtra("what", "shopping");
				context.startActivity(intent);
				break;
			case R.id.teaID:
				intent.putExtra("what", "tea");
				context.startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

}
