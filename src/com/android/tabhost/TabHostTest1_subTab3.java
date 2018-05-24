package com.android.tabhost;

import com.android.R;
import com.android.camera.CameraTest1;
import com.android.camera.CameraTest7;
import com.android.gps.BaiduGpsTest1;
import com.android.gps.GpsTest1;
import com.android.message.MessageTest1;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 
 * �̳�TabActivity��TabHost��TabWidget ��FrameLayout ���ߵ�ID������android.R.id.tabhost��android.R.id.tabs��android.R.id.tabcontent
 * 
 */
public class TabHostTest1_subTab3 extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhosttest1_subtab1);

		TabHost mTabHost = getTabHost();
		TabWidget tabWidget = mTabHost.getTabWidget();

		mTabHost.addTab(mTabHost.newTabSpec("����").setIndicator("����")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("�Ϻ�").setIndicator("�Ϻ�")
				.setContent(R.id.widget60));
		mTabHost.addTab(mTabHost.newTabSpec("���").setIndicator("���")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("CameraTest1").setIndicator("CameraTest1")
				.setContent(new Intent(this, CameraTest1.class)));
		mTabHost.addTab(mTabHost.newTabSpec("MessageTest1").setIndicator("MessageTest1")
				.setContent(new Intent(this, MessageTest1.class)));
		mTabHost.addTab(mTabHost.newTabSpec("BaiduGpsTest1").setIndicator("BaiduGpsTest1")
				.setContent(new Intent(this, BaiduGpsTest1.class)));
		mTabHost.addTab(mTabHost.newTabSpec("GpsTest1").setIndicator("GpsTest1")
				.setContent(new Intent(this, GpsTest1.class)));
		
		mTabHost.setCurrentTab(2);
		
		int height = 30;
		// int width =45;

		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			/** ���ø߶ȡ���ȣ����ڿ������Ϊfill_parent���ڴ˶���ûЧ�� */
			tabWidget.getChildAt(i).getLayoutParams().height = height;
			// tabWidget.getChildAt(i).getLayoutParams().width = width;
			/** ����tab�б������ֵ���ɫ����ȻĬ��Ϊ��ɫ */
			final TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
		}
	}
}