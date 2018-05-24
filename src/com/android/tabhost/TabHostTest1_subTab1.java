package com.android.tabhost;

import com.android.R;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 
 * 继承TabActivity，TabHost、TabWidget 、FrameLayout 三者的ID必须是android.R.id.tabhost、android.R.id.tabs、android.R.id.tabcontent
 * 
 */
public class TabHostTest1_subTab1 extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhosttest1_subtab1);

		TabHost mTabHost = getTabHost();
		TabWidget tabWidget = mTabHost.getTabWidget();

		mTabHost.addTab(mTabHost.newTabSpec("苏州").setIndicator("苏州")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("上海").setIndicator("上海")
				.setContent(R.id.widget60));
		mTabHost.addTab(mTabHost.newTabSpec("天津").setIndicator("天津")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("北京").setIndicator("北京")
				.setContent(R.id.widget60));
		mTabHost.setCurrentTab(2);

		int height = 30;
		// int width =45;

		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			/** 设置高度、宽度，由于宽度设置为fill_parent，在此对它没效果 */
			tabWidget.getChildAt(i).getLayoutParams().height = height;
			// tabWidget.getChildAt(i).getLayoutParams().width = width;
			/** 设置tab中标题文字的颜色，不然默认为黑色 */
			final TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
		}
	}
}