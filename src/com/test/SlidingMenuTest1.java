package com.test;

import com.android.ActivityManage;
import com.android.R;
import com.android.layout.SlidingLayout;
import com.utils.CarNumIME;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 
 * 滑动菜单
 *
 */
public class SlidingMenuTest1 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.slidingmenutest1);
		
		//侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
		final SlidingLayout slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		Button menuButton = (Button) findViewById(R.id.menuButton);
		LinearLayout v=(LinearLayout) findViewById(R.id.includeView);
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
	}

}
