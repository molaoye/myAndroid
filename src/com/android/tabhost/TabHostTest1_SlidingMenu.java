package com.android.tabhost;

import com.android.ActivityManage;
import com.android.R;
import com.android.camera.CameraTest1;
import com.android.camera.CameraTest7;
import com.android.layout.SlidingLayout;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

/**
 * ��ԭ�������ӻ����˵�
 * 
 */
public class TabHostTest1_SlidingMenu extends TabActivity {

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
		
		setContentView(R.layout.tabhosttest1_slidingmenu);

		//�໬���ֶ�������ͨ����ָ���������Ĳ˵����ֽ�����ʾ�����ء�
		final SlidingLayout slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		Button menuButton = (Button) findViewById(R.id.menuButton);
		View v = findViewById(android.R.id.tabhost);
//		View v = findViewById(R.id.includeView);
		// �����������¼�����contentListView��
		slidingLayout.setScrollEvent(v);
		menuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (slidingLayout.isLeftLayoutVisible()) {
					slidingLayout.scrollToRightLayout();
				} else {
					slidingLayout.scrollToLeftLayout();
				}
			}
		});
				
		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("Twitter").setIndicator("Twitter",getResources().getDrawable(android.R.drawable.arrow_down_float))
				.setContent(new Intent(this, TabHostTest1_subTab3.class)));
		mTabHost.addTab(mTabHost.newTabSpec("CameraTest7").setIndicator("CameraTest7",getResources().getDrawable(android.R.drawable.arrow_down_float))
				.setContent(new Intent(this, CameraTest7.class)));
		mTabHost.setCurrentTab(0);
	}
}