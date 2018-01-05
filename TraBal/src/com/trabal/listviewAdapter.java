package com.trabal;

import java.util.ArrayList;
import java.util.List;


import com.squareup.picasso.Picasso;

import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.RestaurantActivity;
import com.trabal.linear.TakeMeToYourHome;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listviewAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<HotSpotBean>  objects;

	public listviewAdapter(Context context, int textViewResourceId,
			ArrayList<HotSpotBean> objects) {
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.listview_item, null);

		ImageView itemImage1 = (ImageView) view.findViewById(R.id.item1);
		ImageView itemImage2 = (ImageView) view.findViewById(R.id.item3);
		ImageView itemImage3 = (ImageView) view.findViewById(R.id.item4);
		ImageView itemImage4 = (ImageView) view.findViewById(R.id.item5);
		TextView item_textview1 = (TextView) view.findViewById(R.id.item_text1);
		TextView item_textview2 = (TextView) view.findViewById(R.id.item_text2);
		
		
		itemImage1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ά�ֵ�¼״̬
				Intent last_intent = ((Activity)(listviewAdapter.this.context)).getIntent();
				Intent intent = new Intent(listviewAdapter.this.context, TakeMeToYourHome.class);
				UserBean user = (UserBean) last_intent.getSerializableExtra("user");
				intent.putExtra("user", user);
				String what= last_intent.getStringExtra("what");
				intent.putExtra("what", last_intent.getStringExtra("what"));
				intent.putExtra("hsb",objects.get(position));
				context.startActivity(intent);
				((Activity)context).finish();
			}
		});
		Picasso.with(context).load(objects.get(position).getPic1()).centerCrop().fit().into(itemImage1);
		Picasso.with(context).load(objects.get(position).getPic2()).centerCrop().fit().into(itemImage2);
		Picasso.with(context).load(objects.get(position).getPic3()).centerCrop().fit().into(itemImage3);
		Picasso.with(context).load(objects.get(position).getPic2()).centerCrop().fit().into(itemImage4);
		item_textview1.setText(objects.get(position).getName());
		item_textview2.setText(objects.get(position).getWord());
		return view;

	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int arg0) {
		return objects.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
}
