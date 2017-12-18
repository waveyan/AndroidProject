package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;

import com.trabal.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class ExhibitionActivity extends Activity implements
		OnItemSelectedListener {
	private ImageView imageView;
	private Spinner spDown;
	private List<String> list;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_exhibition);
		spDown = (Spinner) findViewById(R.id.spDwon);
		list = new ArrayList<String>();
		list.add("北京");
		list.add("上海");
		list.add("广州");
		list.add("深圳");
		imageView = (ImageView) this.findViewById(R.id.Exhibition1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spDown.setAdapter(adapter);
		spDown.setOnItemSelectedListener(this);
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ExhibitionActivity.this,
						Timesmuseum.class);

				ExhibitionActivity.this.startActivity(intent);
				return false;
			}
		});
	}
	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        getMenuInflater().inflate(R.menu.main, menu);  
	        return true;  
	    } 
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}