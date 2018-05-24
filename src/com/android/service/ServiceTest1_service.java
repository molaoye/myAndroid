package com.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * 需要在AndroidManifest.xml里application标签里加入：
 *         <service android:name="com.android.service.ServiceTest1_service" android:enabled="true">
            <intent-filter>
                <action android:name="com.android.service.ServiceTest1_service" />
            </intent-filter>
        </service>
	启动服务，执行顺序：onCreate(只执行一次)-onStartCommand-onStart
	绑定服务，执行顺序：onCreate(只执行一次)-onBind
 */
public class ServiceTest1_service extends Service {
	private static final String TAG = "ServiceTest1_service" ;
	public static final String ACTION = "com.android.service.ServiceTest1_service";
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "ServiceTest1_service onBind");
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.v(TAG, "ServiceTest1_service onCreate");
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "ServiceTest1_service onStart");
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "ServiceTest1_service onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
}
