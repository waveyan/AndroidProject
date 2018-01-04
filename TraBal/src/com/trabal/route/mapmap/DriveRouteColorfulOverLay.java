package com.trabal.route.mapmap;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.trabal.route.tools.AMapUtil;
import com.trabal.R;


/**
 * 导航路线图层类�??
 */
public class DriveRouteColorfulOverLay {

	private AMap mAMap;
	protected LatLng startPoint, endPoint;
	private Marker startMarker, endMarker;
	private DrivePath drivePath;
    private List<LatLonPoint> throughPointList;
    private List<Marker> throughPointMarkerList = new ArrayList<Marker>();
    private boolean throughPointMarkerVisible = true;
    private List<TMC> tmcs;
    private PolylineOptions mPolylineOptions;
    private boolean isColorfulline = true;
    private float mWidth = 17;
    protected boolean nodeIconVisible = true;
    private List<LatLng> mLatLngsOfPath;
    protected List<Marker> stationMarkers = new ArrayList<Marker>();
	protected List<Polyline> allPolyLines = new ArrayList<Polyline>();

	public void setIsColorfulline(boolean iscolorfulline) {
		this.isColorfulline = iscolorfulline;
	}

	/**
	 * 根据给定的参数，构�?�一个导航路线图层类对象�?
	 * @param amap 地图对象�?
	 * @param path 导航路线规划方案�?
	 * @param start 起点
	 * @param end 终点
	 * @param throughPointList 途径�?
	 */
    public DriveRouteColorfulOverLay(AMap amap, DrivePath path,
            LatLonPoint start, LatLonPoint end, List<LatLonPoint> throughPointList) {
        mAMap = amap; 
        drivePath = path;
        startPoint = AMapUtil.convertToLatLng(start);
        endPoint = AMapUtil.convertToLatLng(end);
        this.throughPointList = throughPointList;
    }

    /**
     * 设置路线宽度
     * @param mWidth 路线宽度，取值范围：大于0
     */
    public void setRouteWidth(float mWidth) {
        this.mWidth = mWidth;
    }

    /**
     * 添加驾车路线添加到地图上显示�?
     */
	public void addToMap() {
		initPolylineOptions();
        try {
            if (mAMap == null) {
                return;
            }

            if (mWidth == 0 || drivePath == null) {
                return;
            }
            mLatLngsOfPath = new ArrayList<LatLng>();
            tmcs = new ArrayList<TMC>();
            List<DriveStep> drivePaths = drivePath.getSteps();
            mPolylineOptions.add(startPoint);
            for (DriveStep step : drivePaths) {
                List<LatLonPoint> latlonPoints = step.getPolyline();
                List<TMC> tmclist = step.getTMCs();
                tmcs.addAll(tmclist);
                addDrivingStationMarkers(step, convertToLatLng(latlonPoints.get(0)));
                for (LatLonPoint latlonpoint : latlonPoints) {
                	mPolylineOptions.add(convertToLatLng(latlonpoint));
                	mLatLngsOfPath.add(convertToLatLng(latlonpoint));
				}
            }
            mPolylineOptions.add(endPoint);
            if (startMarker != null) {
                startMarker.remove();
                startMarker = null;
            }
            if (endMarker != null) {
                endMarker.remove();
                endMarker = null;
            }
            addStartAndEndMarker();
            addThroughPointMarker();
            if (isColorfulline && tmcs.size()>0 ) {
            	colorWayUpdate(tmcs);
			}else {
				showPolyline();
			}            
            
        } catch (Throwable e) {
        	e.printStackTrace();
        }
    }

    private void addStartAndEndMarker() {
    	startMarker = mAMap.addMarker((new MarkerOptions())
				.position(startPoint).icon(getStartBitmapDescriptor())
				.title("\u8D77\u70B9"));
    	endMarker = mAMap.addMarker((new MarkerOptions()).position(endPoint)
				.icon(getEndBitmapDescriptor()).title("\u7EC8\u70B9"));
	}

	/**
     * 初始化线段属�?
     */
    private void initPolylineOptions() {

        mPolylineOptions = null;
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(getDriveColor()).width(mWidth);
    }

    private void showPolyline() {
        addPolyLine(mPolylineOptions);
    }

    private void colorWayUpdate(List<TMC> tmcSection) {
        if (mAMap == null) {
            return;
        }
        if (mLatLngsOfPath == null || mLatLngsOfPath.size() <= 0) {
            return;
        }
        if (tmcSection == null || tmcSection.size() <= 0) {
            return;
        }
        int j = 0;
        LatLng startLatLng = mLatLngsOfPath.get(0);
        LatLng endLatLng = null;
        double segmentTotalDistance = 0;
        TMC segmentTrafficStatus;
        List<LatLng> tempList = new ArrayList<LatLng>();
        //画出起点到规划路径之间的连线
        addPolyLine(new PolylineOptions().add(startPoint, startLatLng)
				.setDottedLine(true));
        //终点和规划路径之间连�?
        addPolyLine(new PolylineOptions().add(mLatLngsOfPath.get(mLatLngsOfPath.size()-1), 
        		endPoint).setDottedLine(true));
        for (int i = 0; i < mLatLngsOfPath.size() && j < tmcSection.size(); i++) {
            segmentTrafficStatus = tmcSection.get(j);
            endLatLng = mLatLngsOfPath.get(i);
            double distanceBetweenTwoPosition = AMapUtils.calculateLineDistance(startLatLng, endLatLng);
            segmentTotalDistance = segmentTotalDistance + distanceBetweenTwoPosition;
            if (segmentTotalDistance > segmentTrafficStatus.getDistance() + 1) {
                double toSegDis = distanceBetweenTwoPosition - (segmentTotalDistance - segmentTrafficStatus.getDistance());
                LatLng middleLatLng = getPointForDis(startLatLng, endLatLng, toSegDis);
                tempList.add(middleLatLng);
                startLatLng = middleLatLng;
                i--;
            } else {
                tempList.add(endLatLng);
                startLatLng = endLatLng;
            }
            if (segmentTotalDistance >= segmentTrafficStatus.getDistance() || i == mLatLngsOfPath.size() - 1) {
                if (j == tmcSection.size() - 1 && i < mLatLngsOfPath.size() - 1) {
                    for (i++; i < mLatLngsOfPath.size(); i++) {
                        LatLng lastLatLng = mLatLngsOfPath.get(i);
                        tempList.add(lastLatLng);
                    }
                }
                j++;
                if (segmentTrafficStatus.getStatus().equals("畅�??")) {
                	addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.GREEN));
				} else if (segmentTrafficStatus.getStatus().equals("缓行")) {
					addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.YELLOW));
				} else if (segmentTrafficStatus.getStatus().equals("拥堵")) {
					addPolyLine((new PolylineOptions()).addAll(tempList)
                             .width(mWidth).color(Color.RED));
				} else if (segmentTrafficStatus.getStatus().equals("严重拥堵")) {
					addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.parseColor("#990033")));
				} else {
					addPolyLine((new PolylineOptions()).addAll(tempList)
                            .width(mWidth).color(Color.parseColor("#537edc")));
				}
                tempList.clear();
                tempList.add(startLatLng);
                segmentTotalDistance = 0;
            }
           if (i == mLatLngsOfPath.size() - 1) {
        	   addPolyLine(new PolylineOptions().add(endLatLng, endPoint)
        			   .setDottedLine(true));
		} 
        }
    }
    
    private LatLng convertToLatLng(LatLonPoint point) {
        return new LatLng(point.getLatitude(),point.getLongitude());
        }
    
    /**
     * @param driveStep
     * @param latLng
     */
    private void addDrivingStationMarkers(DriveStep driveStep, LatLng latLng) {	
    	MarkerOptions options = new MarkerOptions().position(latLng)
    			.title("\u65B9\u5411:" + driveStep.getAction()
    					+ "\n\u9053\u8DEF:" + driveStep.getRoad())
    					.snippet(driveStep.getInstruction()).visible(nodeIconVisible)
    					.anchor(0.5f, 0.5f).icon(getDriveBitmapDescriptor());
    	if(options == null) {
			return;
		}
		Marker marker = mAMap.addMarker(options);
		if(marker != null) {
			stationMarkers.add(marker);
		}
    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(new LatLng(startPoint.latitude, startPoint.longitude));
        b.include(new LatLng(endPoint.latitude, endPoint.longitude));
        if (this.throughPointList != null && this.throughPointList.size() > 0) {
            for (int i = 0; i < this.throughPointList.size(); i++) {
                b.include(new LatLng(
                        this.throughPointList.get(i).getLatitude(),
                        this.throughPointList.get(i).getLongitude()));
            }
        }
        return b.build();
    }

    
    private void addThroughPointMarker() {
        if (this.throughPointList != null && this.throughPointList.size() > 0) {
            LatLonPoint latLonPoint = null;
            for (int i = 0; i < this.throughPointList.size(); i++) {
                latLonPoint = this.throughPointList.get(i);
                if (latLonPoint != null) {
                    throughPointMarkerList.add(mAMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude()))
                    .visible(throughPointMarkerVisible)
                    .icon(getThroughPointBitDes())
                    .title("\u9014\u7ECF\u70B9")));
                }
            }
        }
    }
    
    private BitmapDescriptor getThroughPointBitDes() {
    	return BitmapDescriptorFactory.fromResource(R.drawable.amap_through);
       
    }
    
    private BitmapDescriptor getDriveBitmapDescriptor() {
		return BitmapDescriptorFactory.fromResource(R.drawable.amap_car);
	}
    
    /**
	 * 给起点Marker设置图标，并返回更换图标的图片�?�如不用默认图片，需要重写此方法�?
	 * @return 更换的Marker图片�?
	 */
    private BitmapDescriptor getStartBitmapDescriptor() {
		return BitmapDescriptorFactory.fromResource(R.drawable.amap_start);
	}
	/**
	 * 给终点Marker设置图标，并返回更换图标的图片�?�如不用默认图片，需要重写此方法�?
	 * @return 更换的Marker图片�?
	 */
    private BitmapDescriptor getEndBitmapDescriptor() {
		return BitmapDescriptorFactory.fromResource(R.drawable.amap_end);
	}
    
	private void addPolyLine(PolylineOptions options) {
		if(options == null) {
			return;
		}
		Polyline polyline = mAMap.addPolyline(options);
		if(polyline != null) {
			allPolyLines.add(polyline);
		}
	}
	
    private int getDriveColor() {
		return Color.parseColor("#537edc");
	}

    private LatLng getPointForDis(LatLng sPt, LatLng ePt, double dis) {
        double lSegLength = AMapUtils.calculateLineDistance(sPt, ePt);
        double preResult = dis / lSegLength;
        return new LatLng((ePt.latitude - sPt.latitude) * preResult + sPt.latitude, (ePt.longitude - sPt.longitude) * preResult + sPt.longitude);
    }

    public void setThroughPointIconVisibility(boolean visible) {
        try {
            throughPointMarkerVisible = visible;
            if (this.throughPointMarkerList != null
                    && this.throughPointMarkerList.size() > 0) {
                for (int i = 0; i < this.throughPointMarkerList.size(); i++) {
                    this.throughPointMarkerList.get(i).setVisible(visible);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 移动镜头到当前的视角�?
	 * @since V2.1.0
	 */
	public void zoomToSpan() {
		if (startPoint != null) {
			if (mAMap == null)
				return;
			try {
				LatLngBounds bounds = getLatLngBounds();
				mAMap.animateCamera(CameraUpdateFactory
						.newLatLngBounds(bounds, 50));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 路段节点图标控制显示接口�?
	 * @param visible true为显示节点图标，false为不显示�?
	 * @since V2.3.1
	 */
	public void setNodeIconVisibility(boolean visible) {
		try {
			nodeIconVisible = visible;
			if (this.stationMarkers != null && this.stationMarkers.size() > 0) {
				for (int i = 0; i < this.stationMarkers.size(); i++) {
					this.stationMarkers.get(i).setVisible(visible);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 去掉DriveLineOverlay上的线段和标记�??
     */
    public void removeFromMap() {
        try {
        	if (startMarker != null) {
    			startMarker.remove();

    		}
    		if (endMarker != null) {
    			endMarker.remove();
    		}
    		for (Marker marker : stationMarkers) {
    			marker.remove();
    		}
    		for (Polyline line : allPolyLines) {
    			line.remove();
    		}
            if (this.throughPointMarkerList != null
                    && this.throughPointMarkerList.size() > 0) {
                for (int i = 0; i < this.throughPointMarkerList.size(); i++) {
                    this.throughPointMarkerList.get(i).remove();
                }
                this.throughPointMarkerList.clear();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
}