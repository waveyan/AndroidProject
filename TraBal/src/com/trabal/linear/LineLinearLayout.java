package com.trabal.linear;

import com.trabal.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class LineLinearLayout extends LinearLayout {

	Context context;

	public LineLinearLayout(Context context) {
		super(context);
		this.context = context;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.linear_line,
				null);
		this.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

	}
}