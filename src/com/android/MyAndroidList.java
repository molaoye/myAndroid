package com.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.broadcastreceiver.MyBroadcastReceiver;
import com.android.camera.CameraTest1;
import com.android.camera.CameraTest7;
import com.android.database.DatabaseTest2;
import com.android.graphics.ImageSee;
import com.android.graphics.ImageTest2;
import com.android.permission.InstallAppsPermission;
import com.android.service.Gps_baidu_service;
import com.android.tabhost.TabHostTest1;
import com.android.tabhost.TabHostTest1_SlidingMenu;
import com.android.web.WebViewTest1;
import com.test.InputTypeTest;
import com.test.SelectPicTest;
import com.test.SlidingMenuTest1;
import com.utils.UncatchExceptionHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MyAndroidList extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(Boolean.parseBoolean(getString(R.string.runServiceByActivity))){
        	Intent i=new Intent(this, Gps_baidu_service.class);
    		startService(i);
        }
        
        if(Boolean.parseBoolean(getString(R.string.runReceiverByActivity))){
        	Intent i=new Intent(this, MyBroadcastReceiver.class);
        	sendBroadcast(i);
        }
        
        ActivityManage.getInstance().addActivity(this);
        
        UncatchExceptionHandler.getInstance().init(getApplicationContext());
        
        setListAdapter(new SimpleAdapter(this, 
        		loadData(),
                android.R.layout.simple_list_item_1, 
                new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }
    
    private List<Map<String,Object>> loadData() {
    	List<Map<String,Object>> l_intent = new ArrayList<Map<String,Object>>();
    	
    	WebViewTest1(l_intent);
    	
    	InputTypeTest(l_intent);
    	
    	DatabaseTest2(l_intent);
    	
    	LayoutTest(l_intent);
    	
    	SlidingMenuTest2(l_intent);
    	
    	ViewPager2(l_intent);
    	
    	ViewPager1(l_intent);
    	
    	TabHostTest1_SlidingMenu(l_intent);
    	
    	SlidingMenuTest1(l_intent);
    	
    	EditTextTest1(l_intent);
    	
    	VideoPlay3(l_intent);
    	
    	VideoPlay2(l_intent);
    	
    	VideoPlay1(l_intent);
    	
    	CameraTest7(l_intent);
    	
    	CameraTest1(l_intent);
    	
    	TabHostTest1(l_intent);
    	
    	SelectPicTest(l_intent);
    	
    	BaiduGpsTest1(l_intent);
    	
    	GpsTest1(l_intent);
    	
    	Level3LinkageTest(l_intent);
    	
    	JdbcTest(l_intent);
    	
    	CameraTest8(l_intent);
    	
    	ListView_test1(l_intent);
    	
    	SlidingDrawerTest1(l_intent);
    	
    	SlidingDrawerTest2(l_intent);
    	
    	CarNumIMETest(l_intent);
    	
    	CustomAlertDialogTest1(l_intent);
    	
    	ListView_test2(l_intent);
    	
    	CameraTest9(l_intent);
    	
    	Gesture1(l_intent);
    	
    	Gesture2(l_intent);
    	
    	Gesture3(l_intent);
    	
    	StorageTest1(l_intent);
    	
    	StorageTest2(l_intent);
    	
    	StorageTest3(l_intent);
    	
    	DatabaseTest1(l_intent);
    	
    	ImageTest1(l_intent);
    	
    	InstallAppsPermission(l_intent);
    	
    	MyAppPermission(l_intent);
    	
    	ImageTest2(l_intent);
    	
    	return l_intent;
    }
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(Boolean.parseBoolean(getString(R.string.stopServiceByActivity))){
			Intent i=new Intent(this, Gps_baidu_service.class);
			stopService(i);
		}
		
		ActivityManage.getInstance().AppExit(this);
	}

	private void CameraTest7(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CameraTest7");
    	Intent intent=new Intent(this, CameraTest7.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void CameraTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CameraTest1");
    	Intent intent=new Intent(this, CameraTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void TabHostTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "TabHostTest1");
    	Intent intent=new Intent(this, TabHostTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void SelectPicTest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "SelectPicTest");
    	Intent intent=new Intent(this, SelectPicTest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void BaiduGpsTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "BaiduGpsTest1");
    	Intent intent=new Intent(this, com.android.gps.BaiduGpsTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void GpsTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "GpsTest1");
    	Intent intent=new Intent(this, com.android.gps.GpsTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void Level3LinkageTest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "Level3LinkageTest");
    	Intent intent=new Intent(this, com.test.Level3LinkageTest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void JdbcTest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "JdbcTest");
    	Intent intent=new Intent(this, com.test.JdbcTest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void CameraTest8(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CameraTest8");
    	Intent intent=new Intent(this, com.android.camera.CameraTest8.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ListView_test1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ListView_test1");
    	Intent intent=new Intent(this, com.android.listview.ListView_test1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void SlidingDrawerTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "SlidingDrawerTest1");
    	Intent intent=new Intent(this, com.android.slidingdrawer.SlidingDrawerTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void SlidingDrawerTest2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "SlidingDrawerTest2");
    	Intent intent=new Intent(this, com.android.slidingdrawer.SlidingDrawerTest2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void CarNumIMETest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CarNumIMETest");
    	Intent intent=new Intent(this, com.test.CarNumIMETest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void CustomAlertDialogTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CustomAlertDialogTest1");
    	Intent intent=new Intent(this, com.test.CustomAlertDialogTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ListView_test2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ListView_test2");
    	Intent intent=new Intent(this, com.android.listview.ListView_test2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void CameraTest9(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "CameraTest9");
    	Intent intent=new Intent(this, com.android.camera.CameraTest9.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void Gesture1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "Gesture1");
    	Intent intent=new Intent(this, com.android.gesture.Gesture1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void Gesture2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "Gesture2");
    	Intent intent=new Intent(this, com.android.gesture.Gesture2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void Gesture3(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "Gesture3");
    	Intent intent=new Intent(this, com.android.gesture.Gesture3.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void StorageTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "StorageTest1");
    	Intent intent=new Intent(this, com.android.storage.StorageTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void StorageTest2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "StorageTest2");
    	Intent intent=new Intent(this, com.android.storage.StorageTest2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void StorageTest3(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "StorageTest3");
    	Intent intent=new Intent(this, com.android.storage.StorageTest3.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void DatabaseTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "DatabaseTest1");
    	Intent intent=new Intent(this, com.android.database.DatabaseTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ImageTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ImageTest1");
    	Intent intent=new Intent(this, com.android.graphics.ImageTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void InstallAppsPermission(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "InstallAppsPermission");
    	Intent intent=new Intent(this, InstallAppsPermission.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void MyAppPermission(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "MyAppPermission");
    	Intent intent=new Intent(this, com.android.permission.MyAppPermission.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ImageTest2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ImageTest2");
    	Intent intent=new Intent(this, ImageTest2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void VideoPlay1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "VideoPlay1");
    	Intent intent=new Intent(this, com.android.media.VideoPlay1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void VideoPlay2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "VideoPlay2");
    	Intent intent=new Intent(this, com.android.media.VideoPlay2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void VideoPlay3(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "VideoPlay3");
    	Intent intent=new Intent(this, com.android.media.VideoPlay3.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void EditTextTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "EditTextTest1");
    	Intent intent=new Intent(this, com.test.EditTextTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void SlidingMenuTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "SlidingMenuTest1");
    	Intent intent=new Intent(this, SlidingMenuTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void SlidingMenuTest2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "SlidingMenuTest2");
    	Intent intent=new Intent(this, com.test.SlidingMenuTest2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void TabHostTest1_SlidingMenu(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "TabHostTest1_SlidingMenu");
    	Intent intent=new Intent(this, TabHostTest1_SlidingMenu.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ViewPager1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ViewPager1");
    	Intent intent=new Intent(this, com.android.viewpager.ViewPager1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void ViewPager2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "ViewPager2");
    	Intent intent=new Intent(this, com.android.viewpager.ViewPager2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void LayoutTest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "LayoutTest");
    	Intent intent=new Intent(this, com.test.LayoutTest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void DatabaseTest2(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "DatabaseTest2");
    	Intent intent=new Intent(this, DatabaseTest2.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void InputTypeTest(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "InputTypeTest");
    	Intent intent=new Intent(this, InputTypeTest.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    private void WebViewTest1(List<Map<String,Object>> l_intent){
    	Map<String,Object> m_intent=new HashMap<String,Object>();
    	m_intent.put("title", "WebViewTest1");
    	Intent intent=new Intent(this, WebViewTest1.class);
    	m_intent.put("intent", intent);
    	l_intent.add(m_intent);
    }
    
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String,Object> map = (Map) l.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
