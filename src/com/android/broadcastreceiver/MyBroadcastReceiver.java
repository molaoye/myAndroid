package com.android.broadcastreceiver;

import com.android.service.Gps_baidu_service;
import com.utils.CommonUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.Gravity;

public class MyBroadcastReceiver extends BroadcastReceiver {

	private static final boolean startService=false;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("", "MyBroadcastReceiver");
		
		if(startService){
		CommonUtil.ToastMsg(context, intent.getAction(), Gravity.TOP);
		
		Intent i=new Intent(context, Gps_baidu_service.class);
		context.startService(i);
		}
	}

}
