package com.android.gps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.android.ActivityManage;
import com.android.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.utils.CommonUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class GpsTest1 extends Activity implements LocationListener{

	private TextView textView_gpsLocationResult;
	
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.baidugpstest1);
		
		textView_gpsLocationResult=(TextView) findViewById(R.id.textView_gpsLocationResult);
		
//		openGPSSettings();
		getLocation();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		locationManager.removeUpdates(this);
		super.onDestroy();
	}
	
	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			getLocation();
			return;
		}
		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
	}
	
	private void getLocation() {
		// 获取位置管理服务
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		// 低功耗
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		// 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider);
		updateLocation(location);
		//第 2个参数为毫秒， 表示调用 listener的周期，第 3个参数为米,表示位置移动指定距离后就调用 listener
		locationManager.requestLocationUpdates(provider, 5 * 1000, 100, this);
//		locationManager.requestLocationUpdates("gps", 10 * 1000, 100, this);
	}
	
	private void updateLocation(Location location) {
		String text="";
		if (location != null) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			Map<String,String> m=CommonUtil.coordinateConvert(String.valueOf(longitude)+","+String.valueOf(latitude),"1");
			m=CommonUtil.coordinateReverse(m.get("lng")+","+m.get("lat"));
			for(Entry<String, String> e:m.entrySet()){
				text+=e.getKey()+":"+e.getValue()+"\n";
			}
		} else {
			text="无法获取GPS坐标信息";
		}
		textView_gpsLocationResult.setText(text);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 0:{
			getLocation();
			break;
		}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
}
