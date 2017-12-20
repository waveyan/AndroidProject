package com.trabal.linear;

import java.util.ArrayList;
import java.util.List;

import com.trabal.R;
import com.trabal.listview1;
import com.trabal.listviewAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ExhibitionActivity extends Activity {
	private List<listview1> Listview = new ArrayList<listview1>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_exhibition);

		initListview1();
        ListView listview1 = (ListView) findViewById(R.id.listview_exhibiton);
        
        listviewAdapter fruitAdapter = new listviewAdapter(ExhibitionActivity.this, R.layout.listview_item, Listview);
        listview1.setAdapter(fruitAdapter);
 
	}

	private void initListview1() {
		// TODO Auto-generated method stub
		listview1 Exhibition = new listview1("µ»Œ“m…œÈT", R.drawable.dw6,R.drawable.dw1,R.drawable.dw3,R.drawable.dw2 );
		Listview.add(Exhibition);
	}
	}   

	