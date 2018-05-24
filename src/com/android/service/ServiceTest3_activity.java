package com.android.service;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
* 消息推送，利用service实现
* 
*/
public class ServiceTest3_activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.servicetest3);
		setTitle(this.getClass().getName());
		Intent i=new Intent("com.android.service.ServiceTest3_service");
		startService(i);
	}

}
