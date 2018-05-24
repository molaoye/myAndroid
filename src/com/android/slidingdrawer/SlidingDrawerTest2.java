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
		//�س���
		SlidingDrawer2.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				ImageView_up.setVisibility(View.VISIBLE);
			}
		});
		//������
		SlidingDrawer2.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				ImageView_up.setVisibility(View.INVISIBLE);
			}
		});
		
		LinearLayout_menu=(LinearLayout) findViewById(R.id.LinearLayout_menu);
		//�����˵�
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
		//�����˵�
		bottomMenu = new BottomMenu(this, R.drawable.bottom_menu_setting, R.color.black,"����");
		bottomMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.ToastMsg(SlidingDrawerTest2.this, "����", Gravity.CENTER);
			}
		});
		LinearLayout_menu.addView(bottomMenu);
		
	}
	
	/**
	 * �ֻ����ذ�ť���÷���
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
	 * �ֻ��˵���ť�ر�֮ǰ���÷���
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
	 * �ֻ��˵���ť��ʾ֮ǰ���÷���
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
