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

public class ExhibitionActivity extends Activity {
	private List<listview1> Listview = new ArrayList<listview1>();
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_exhibition);

		initListview1();
		ListView listview1 = (ListView) findViewById(R.id.listview_exhibiton);

		listviewAdapter fruitAdapter = new listviewAdapter(
				ExhibitionActivity.this, R.layout.listview_item, Listview);
		listview1.setAdapter(fruitAdapter);
		
	}

	private void initListview1() {
		// TODO Auto-generated method stub
//		listview1 Exhibition = new listview1("����ʱ��������", R.drawable.t6,
//				R.drawable.t2, R.drawable.t3, R.drawable.t7);
//		Listview.add(Exhibition);
	}

}
