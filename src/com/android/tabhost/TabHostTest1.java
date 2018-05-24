package com.android.tabhost;

import com.android.ActivityManage;
import com.android.R;
import com.android.camera.CameraTest1;
import com.android.camera.CameraTest7;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * ����̳���TabActivity
 * 
 * @author Administrator
 * 
 */
public class TabHostTest1 extends TabActivity {

	/*
	 * ע�⣺ ����TabHost�������ļ��б������ TabHost��TabWidget ��FrameLayout
	 * ����̳�TabActivity������ͨ��getTabHost()��������ȡTabHost
	 * ��ô���ߵ�ID������android.R.id.tabhost��android.R.id.tabs��android.R.id.tabcontent
	 * 
	 * ����̳�Activity������ͨ��findViewById����ȡ�������������ʱID���Զ���
	 */

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.tabhosttest1);

		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("Twitter").setIndicator("Twitter",getResources().getDrawable(android.R.drawable.arrow_down_float))
				.setContent(new Intent(this, TabHostTest1_subTab3.class)));
		mTabHost.addTab(mTabHost.newTabSpec("CameraTest7").setIndicator("CameraTest7",getResources().getDrawable(android.R.drawable.arrow_down_float))
				.setContent(new Intent(this, CameraTest7.class)));
		mTabHost.setCurrentTab(0);
	}
}