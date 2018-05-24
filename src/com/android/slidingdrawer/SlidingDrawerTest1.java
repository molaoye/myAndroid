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

public class SlidingDrawerTest1 extends Activity {
	
	private ImageView ImageView_up; 
	private SlidingDrawer SlidingDrawer1;
	private LinearLayout LinearLayout_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.slidingdrawer_test1);
		
		ImageView_up=(ImageView)findViewById(R.id.ImageView_up);
		ImageView_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SlidingDrawer1.isOpened()){
					SlidingDrawer1.close();
				}else{
					SlidingDrawer1.open();
				}
			}
		});
		
		SlidingDrawer1 = (SlidingDrawer)findViewById(R.id.SlidingDrawer1);
		//�س���
		SlidingDrawer1.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				ImageView_up.setImageResource(R.drawable.bottom_up);
			}
		});
		//������
		SlidingDrawer1.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				ImageView_up.setImageResource(R.drawable.bottom_down);
			}
		});
		
		LinearLayout_menu=(LinearLayout) findViewById(R.id.LinearLayout_menu);
		//�����˵�
		BottomMenu bottomMenu = new BottomMenu(this,R.drawable.setting,R.color.black,"����");
		bottomMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.ToastMsg(SlidingDrawerTest1.this, "����", Gravity.CENTER);
			}
		});
		LinearLayout_menu.addView(bottomMenu);
		//�����˵�
		bottomMenu = new BottomMenu(this, R.drawable.setting, R.color.black,
				"����");
		bottomMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.ToastMsg(SlidingDrawerTest1.this, "����",
						Gravity.CENTER);
			}
		});
		LinearLayout_menu.addView(bottomMenu);
		
	}
	
	/**
	 * �ֻ����ذ�ť���÷���
	 */
	@Override
	public void onBackPressed() {
		if(SlidingDrawer1.isOpened()){
			SlidingDrawer1.close();
		}else{
			super.onBackPressed();
		}
	}
	
	/**
	 * �ֻ��˵���ť�ر�֮ǰ���÷���
	 */
	@Override
	public void onOptionsMenuClosed(Menu menu){
		if(SlidingDrawer1.isOpened()){
			SlidingDrawer1.close();
		}else{
			SlidingDrawer1.open();
		}
	}

	/**
	 * �ֻ��˵���ť��ʾ֮ǰ���÷���
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(SlidingDrawer1.isOpened()){
			SlidingDrawer1.close();
		}else{
			SlidingDrawer1.open();
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
