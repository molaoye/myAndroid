package com.android.gps;

import com.android.ActivityManage;
import com.android.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class BaiduGpsTest1 extends Activity implements BDLocationListener,OnGetGeoCoderResultListener{

	private TextView textView_gpsLocationResult;
	
	private LocationClient mLocClient;
	private GeoCoder geoCoder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.baidugpstest1);
		
		textView_gpsLocationResult=(TextView) findViewById(R.id.textView_gpsLocationResult);
		
		SDKInitializer.initialize(getApplicationContext());
		
		mLocClient = new LocationClient(this);//this getApplicationContext()
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		geoCoder=GeoCoder.newInstance();//首先要进行SDKInitializer.initialize();
		geoCoder.setOnGetGeoCodeResultListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocClient.stop();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null){
			return;
		}
//		String s_addr=location.getAddrStr();
//		String s=location.toJsonString();
//		String s_city=location.getCity();
//		String s_province=location.getProvince();
//		String s_street=location.getStreet();
//		textView_gpsLocationResult.setText(location.getTime()+":"+
//				s_province+s_city+s_street+s_addr+"("+String.valueOf(location.getLongitude())+","+String.valueOf(location.getLatitude())+")"+
//				s);
		LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
	}

	@Override
	public void onReceivePoi(BDLocation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
		}else{
			textView_gpsLocationResult.setText(result.getAddress());
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
