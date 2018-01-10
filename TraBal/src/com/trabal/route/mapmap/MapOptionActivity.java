package com.trabal.route.mapmap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.trabal.hotspot.Bean.DistrictBean;
import com.trabal.route.tools.Constants;

/**
 * 通过Java代码添加一个SupportMapFragment对象
 */
public class MapOptionActivity extends FragmentActivity {

	private static final String MAP_FRAGMENT_TAG = "map";
	static CameraPosition position;
	private AMap aMap;
	private SupportMapFragment aMapFragment;
	private DistrictBean db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db=(DistrictBean) this.getIntent().getSerializableExtra("db");
		position= new CameraPosition(new LatLng(Float.parseFloat(db.getLatitude()),Float.parseFloat(db.getLongitude())),(float)15.0,(float)2.0,(float)2.0);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initMap();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		AMapOptions aOptions = new AMapOptions();
		aOptions.zoomGesturesEnabled(false);// 禁止通过手势缩放地图
		aOptions.scrollGesturesEnabled(false);// 禁止通过手势移动地图
		aOptions.camera(position);
		if (aMapFragment == null) {
			aMapFragment = SupportMapFragment.newInstance(aOptions);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(android.R.id.content, aMapFragment,
					MAP_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}

	}

	/**
	 * 初始化AMap对象
	 */
	private void initMap() {
		if (aMap == null) {
			aMap = aMapFragment.getMap();// amap对象初始化成功
		}
	}
}
