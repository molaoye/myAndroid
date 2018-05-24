package com.android.service;

import com.android.MyAndroidList;
import com.android.R;
import com.android.broadcastreceiver.MyBroadcastReceiver;
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
import com.utils.CommonUtil;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Gps_baidu_service extends Service implements BDLocationListener,OnGetGeoCoderResultListener{

	private LocationClient mLocClient;
	private GeoCoder geoCoder;
	
	private boolean b_run=true;
	private static final boolean b_notification=false;
	private boolean b_start=false;
	
	private Context context;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		super.onDestroy();
		
		Log.i("", "service destroy");
		
//		mLocClient.stop();
//		b_run=false;
		
		if(b_notification){
			stopForeground(true);
		}
		
		Intent intent=new Intent(this, MyBroadcastReceiver.class);
		sendBroadcast(intent);

	}

//	@Override
//	public void onStart(Intent intent, int startId) {
//		init();
//		
//		super.onStart(intent, startId);
//	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		init();
		
		if(b_notification){
//			Notification notification = new Notification(R.drawable.ic_launcher_my_android, getString(R.string.app_name), 
//					System.currentTimeMillis());
//			PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(this, MyAndroidList.class), 0);
//			notification.setLatestEventInfo(this, "uploadservice", "请保持程序在后台运行", pendingintent);
//			startForeground(0x111, notification);
			
			Notification notification = new Notification();  
			notification.flags = Notification.FLAG_ONGOING_EVENT;  
			notification.flags |= Notification.FLAG_NO_CLEAR;  
			notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;  
			startForeground(1, notification);
		}
		
		CommonUtil.ToastMsg(this, "Gps_baidu_service has started!!", Gravity.CENTER);
		
		flags=START_STICKY;
		int ret=super.onStartCommand(intent, flags, startId);
		
		return ret;
	}
	
	private void init(){
		SDKInitializer.initialize(getApplicationContext());
		
		context=this;
		
		if(!b_start){
			b_start=true;
			
			//1
//			new Location().start();
			//2
//			mLocClient = new LocationClient(this);//this getApplicationContext() Gps_baidu_service.this
//			mLocClient.registerLocationListener(this);
//			LocationClientOption option = new LocationClientOption();
//			option.setOpenGps(true);// 打开gps
//			option.setCoorType("bd09ll"); // 设置坐标类型
//			option.setScanSpan(60000);//设置发起定位请求的间隔时间，单位为ms
//			mLocClient.setLocOption(option);
//			mLocClient.start();
//			geoCoder=GeoCoder.newInstance();//首先要进行SDKInitializer.initialize();
//			geoCoder.setOnGetGeoCodeResultListener(this);
			//3
			new Location2().start();
			
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, result.getAddress(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null){
			return;
		}
		LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
	}

	@Override
	public void onReceivePoi(BDLocation arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class BDLocationListener_inner implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null){
				return;
			}
			LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
			geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
		}
		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	class OnGetGeoCoderResultListener_inner implements OnGetGeoCoderResultListener{
		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(Gps_baidu_service.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(Gps_baidu_service.this, result.getAddress(), Toast.LENGTH_LONG).show();
			}
			mLocClient.stop();
		}
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			mLocClient = new LocationClient(Gps_baidu_service.this);//this getApplicationContext() Gps_baidu_service.this
			mLocClient.registerLocationListener(new BDLocationListener_inner());
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(5000);//设置发起定位请求的间隔时间，单位为ms
			mLocClient.setLocOption(option);
			geoCoder=GeoCoder.newInstance();//首先要进行SDKInitializer.initialize();
			geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener_inner());
			mLocClient.start();
		}
		
	};
	
	class Location2 extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			while(b_run){
				Message msg=new Message();
				handler.sendMessage(msg);
				
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
//	class Location extends Thread implements BDLocationListener,OnGetGeoCoderResultListener{
//		
//		private LocationClient mLocClient;
//		private GeoCoder geoCoder;
//		
//		private boolean b_run=true;
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//			
//			Looper.prepare();
//			
//			
//			
//			while(b_run){
//				mLocClient = new LocationClient(Gps_baidu_service.this);//this getApplicationContext() Gps_baidu_service.this
//				mLocClient.registerLocationListener(this);
//				LocationClientOption option = new LocationClientOption();
//				option.setOpenGps(true);// 打开gps
//				option.setCoorType("bd09ll"); // 设置坐标类型
//				option.setScanSpan(1000);//设置发起定位请求的间隔时间，单位为ms
//				mLocClient.setLocOption(option);
//				geoCoder=GeoCoder.newInstance();//首先要进行SDKInitializer.initialize();
//				geoCoder.setOnGetGeoCodeResultListener(this);
//				mLocClient.start();
//				
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			}
//			
//			Looper.loop();
//
//		}
//
//		@Override
//		public void onGetGeoCodeResult(GeoCodeResult arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//				Toast.makeText(Gps_baidu_service.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
//			}else{
//				Toast.makeText(Gps_baidu_service.this, result.getAddress(), Toast.LENGTH_LONG).show();
//			}
//			mLocClient.stop();
//		}
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null){
//				return;
//			}
//			LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
//			geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
//		}
//
//		@Override
//		public void onReceivePoi(BDLocation arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}

}
