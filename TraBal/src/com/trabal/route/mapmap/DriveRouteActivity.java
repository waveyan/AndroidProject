package com.trabal.route.mapmap;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.route.tools.ToastUtil;
import com.trabal.route.tools.AMapUtil;
import com.trabal.routeplan.RouteplanActivity2;
import com.trabal.routeplan.RouteplanActivity4;
import com.trabal.R;

public class DriveRouteActivity extends Activity implements OnMapClickListener,
		OnMarkerClickListener, OnInfoWindowClickListener, InfoWindowAdapter,
		OnRouteSearchListener {
	private AMap aMap;
	private MapView mapView;
	private Context mContext;
	private RouteSearch mRouteSearch;
	private DriveRouteResult mDriveRouteResult;
	private LatLonPoint mStartPoint;// 起点�??116.335891,39.942295
	private LatLonPoint mEndPoint;// 终点�??116.481288,39.995576
	private ArrayList<LatLonPoint> point_list = new ArrayList<LatLonPoint>();
	private final int ROUTE_TYPE_DRIVE = 2;
	private Intent last_intent;
	private HotSpotBean start, end;
	private ArrayList<HotSpotBean> hsb_plan, old_plan;
	private String hs_ids = "";

	//
	private Button backButton;
	private TextView next;

	private RelativeLayout mBottomLayout, mHeadLayout;
	private TextView mRotueTimeDes, mRouteDetailDes;
	private ProgressDialog progDialog = null;// 搜索时进度条

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.route_activity);

		last_intent = this.getIntent();
		hsb_plan = (ArrayList<HotSpotBean>) last_intent
				.getSerializableExtra("hsb_plan");

		mContext = this.getApplicationContext();
		mapView = (MapView) findViewById(R.id.route_map);
		mapView.onCreate(bundle);// 此方法必须重�??

		point_list = InitPointList();

		init();
		searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		mRouteSearch = new RouteSearch(this);
		mRouteSearch.setRouteSearchListener(this);
		mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
		mHeadLayout = (RelativeLayout) findViewById(R.id.routemap_header);
		mRotueTimeDes = (TextView) findViewById(R.id.firstline);
		mRouteDetailDes = (TextView) findViewById(R.id.secondline);
		backButton = (Button) this.findViewById(R.id.backButton);
		next = (TextView) this.findViewById(R.id.next);
		if ("mine".equals(last_intent.getStringExtra("from")))
			mHeadLayout.setVisibility(View.GONE);
		else
			mHeadLayout.setVisibility(View.VISIBLE);

		// 返回
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DriveRouteActivity.this.finish();
			}
		});

		// 下一步
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DriveRouteActivity.this,
						RouteplanActivity4.class);
				intent.putExtra("user",
						last_intent.getSerializableExtra("user"));
				intent.putExtra("hs_ids", hs_ids);
				DriveRouteActivity.this.startActivity(intent);
				DriveRouteActivity.this.finish();
			}
		});

	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	@Override
	public void onMapClick(LatLng arg0) {

	}

	/**
	 * �??始搜索路径规划方�??
	 */
	public void searchRouteResult(int routeType, int mode) {
		if (mStartPoint == null) {
			ToastUtil.show(mContext, "定位中，稍后再试...");
			return;
		}
		if (mEndPoint == null) {
			ToastUtil.show(mContext, "终点未设置！");
		}
		showProgressDialog();
		RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				mStartPoint, mEndPoint);
		if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
			DriveRouteQuery query = new DriveRouteQuery(fromAndTo, mode,
					point_list, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示�?�经点，第四个参数表示避让区域，第五个参数表示避让道�??
			mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
		}
	}

	@Override
	public void onBusRouteSearched(BusRouteResult result, int errorCode) {

	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
		dissmissProgressDialog();
		aMap.clear();// 清理地图上的�??有覆盖物
		if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mDriveRouteResult = result;
					final DrivePath drivePath = mDriveRouteResult.getPaths()
							.get(0);
					DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
							mContext, aMap, drivePath,
							mDriveRouteResult.getStartPos(),
							mDriveRouteResult.getTargetPos(), null,start,end);
					drivingRouteOverlay.setNodeIconVisibility(false);// 设置节点marker是否显示
					drivingRouteOverlay.setIsColorfulline(true);// 是否用颜色展示交通拥堵情况，默认true
					drivingRouteOverlay.removeFromMap();
					drivingRouteOverlay.addToMap();
					drivingRouteOverlay.zoomToSpan();
					mBottomLayout.setVisibility(View.VISIBLE);
					// 途径点信息
					addMarkersToMap();
					
					int dis = (int) drivePath.getDistance();
					int dur = (int) drivePath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur) + "("
							+ AMapUtil.getFriendlyLength(dis) + ")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.VISIBLE);
					int taxiCost = (int) mDriveRouteResult.getTaxiCost();
					mRouteDetailDes.setText("打车" + taxiCost + "元");
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext,
									DriveRouteDetailActivity.class);
							intent.putExtra("drive_path", drivePath);
							intent.putExtra("drive_result", mDriveRouteResult);
							startActivity(intent);
						}
					});
				} else if (result != null && result.getPaths() == null) {
					ToastUtil.show(mContext, R.string.no_result);
				}

			} else {
				ToastUtil.show(mContext, R.string.no_result);
			}
		} else {
			ToastUtil.showerror(this.getApplicationContext(), errorCode);
		}

	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {

	}

	/**
	 * 显示进度�??
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在搜索");
		progDialog.show();
	}

	/**
	 * 隐藏进度�??
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onRideRouteSearched(RideRouteResult arg0, int arg1) {

	}

	private ArrayList<LatLonPoint> InitPointList() {
		ArrayList<LatLonPoint> llp_list = new ArrayList<LatLonPoint>();
		// 起点
		old_plan = hsb_plan;
		start = hsb_plan.get(0);
		// 下步提交
		hs_ids += start.getId() + ";";
		hsb_plan.remove(0);
		mStartPoint = new LatLonPoint(Double.parseDouble(start.getLatitude()),
				Double.parseDouble(start.getLongitude()));
		// 终点
		end = hsb_plan.get(hsb_plan.size() - 1);
		hsb_plan.remove(hsb_plan.size() - 1);
		mEndPoint = new LatLonPoint(Double.parseDouble(end.getLatitude()),
				Double.parseDouble(end.getLongitude()));
		for (HotSpotBean hsb : hsb_plan) {
			LatLonPoint tmp = new LatLonPoint(Double.parseDouble(hsb
					.getLatitude()), Double.parseDouble(hsb.getLongitude()));
			hs_ids += hsb.getId() + ";";
			llp_list.add(tmp);
		}
		hs_ids += end.getId();
		return llp_list;
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {

		for (int i = 0; i < point_list.size(); i++) {

			MarkerOptions markerOption = new MarkerOptions()
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.position(AMapUtil.convertToLatLng(point_list.get(i)))
					.title(hsb_plan.get(i).getEnglishName())
					.snippet(hsb_plan.get(i).getName());
			aMap.addMarker(markerOption);
		}
	}

}
