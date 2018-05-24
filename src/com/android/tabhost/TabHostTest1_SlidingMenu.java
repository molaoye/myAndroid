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
 * 在原有上增加滑动菜单
 * 
 */
public class TabHostTest1_SlidingMenu extends TabActivity {

	/*
	 * 注意： 对于TabHost、布局文件中必须包含 TabHost、TabWidget 、FrameLayout
	 * 如果继承TabActivity，并且通过getTabHost()方法来获取TabHost
	 * 那么三者的ID必须是android.R.id.tabhost、android.R.id.tabs、android.R.id.tabcontent
	 * 
	 * 如果继承Activity，可以通过findViewById来获取这三个组件，此时ID可自定义
	 */

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.tabhosttest1_slidingmenu);

		//侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
		final SlidingLayout slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		Button menuButton = (Button) findViewById(R.id.menuButton);
		View v = findViewById(android.R.id.tabhost);
//		View v = findViewById(R.id.includeView);
		// 将监听滑动事件绑定在contentListView上
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