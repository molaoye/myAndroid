package com.android.slidingdrawer;

import com.android.ActivityManage;
import com.android.R;
import com.utils.BottomMenu;
import com.utils.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class SlidingDrawerTest2 extends Activity {
	
	private ImageView ImageView_up; 
	private SlidingDrawer SlidingDrawer2;
	private LinearLayout LinearLayout_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.slidingdrawer_test2);
		
		ImageView_up=(ImageView)findViewById(R.id.ImageView_up);
		ImageView_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SlidingDrawer2.isOpened()){
					SlidingDrawer2.close();
				}else{
					SlidingDrawer2.open();
				}
			}
		});
		
		SlidingDrawer2 = (SlidingDrawer)findViewById(R.id.SlidingDrawer2);
		//关抽屉
		SlidingDrawer2.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				ImageView_up.setVisibility(View.VISIBLE);
			}
		});
		//开抽屉
		SlidingDrawer2.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				ImageView_up.setVisibility(View.INVISIBLE);
			}
		});
		
		LinearLayout_menu=(LinearLayout) findViewById(R.id.LinearLayout_menu);
		//底栏菜单
		BottomMenu bottomMenu = new BottomMenu(this,R.drawable.slidingdrawer_down,R.color.black,"");
		bottomMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SlidingDrawer2.isOpened()){
					SlidingDrawer2.close();
				}
			}
		});
		LinearLayout_menu.addView(bottomMenu);
		//底栏菜单
		bottomMenu = new BottomMenu(this, R.drawable.bottom_menu_setting, R.color.black,"设置");
		bottomMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.ToastMsg(SlidingDrawerTest2.this, "设置", Gravity.CENTER);
			}
		});
		LinearLayout_menu.addView(bottomMenu);
		
	}
	
	/**
	 * 手机返回按钮调用方法
	 */
	@Override
	public void onBackPressed() {
		if(SlidingDrawer2.isOpened()){
			SlidingDrawer2.close();
		}else{
			super.onBackPressed();
		}
	}
	
	/**
	 * 手机菜单按钮关闭之前调用方法
	 */
	@Override
	public void onOptionsMenuClosed(Menu menu){
		if(SlidingDrawer2.isOpened()){
			SlidingDrawer2.close();
		}else{
			SlidingDrawer2.open();
		}
	}

	/**
	 * 手机菜单按钮显示之前调用方法
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(SlidingDrawer2.isOpened()){
			SlidingDrawer2.close();
		}else{
			SlidingDrawer2.open();
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
