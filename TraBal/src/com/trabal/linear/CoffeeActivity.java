package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;

import com.trabal.R;
import com.trabal.listview1;
import com.trabal.listviewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CoffeeActivity extends Activity {
	private List<listview1> Listview = new ArrayList<listview1>();
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_coffee);

		initListview1();
		ListView listview1 = (ListView) findViewById(R.id.listview_coffee);

		listviewAdapter fruitAdapter = new listviewAdapter(
				CoffeeActivity.this, R.layout.listview_item, Listview);
		listview1.setAdapter(fruitAdapter);
		
	}

	private void initListview1() {
//		listview1 Coffee = new listview1("Lost in L.A.", R.drawable.l7,
//				R.drawable.l1, R.drawable.l2, R.drawable.l3);
//		Listview.add(Coffee);
	}

}
