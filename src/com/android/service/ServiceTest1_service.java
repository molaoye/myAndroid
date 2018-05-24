package com.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * ��Ҫ��AndroidManifest.xml��application��ǩ����룺
 *         <service android:name="com.android.service.ServiceTest1_service" android:enabled="true">
            <intent-filter>
                <action android:name="com.android.service.ServiceTest1_service" />
            </intent-filter>
        </service>
	��������ִ��˳��onCreate(ִֻ��һ��)-onStartCommand-onStart
	�󶨷���ִ��˳��onCreate(ִֻ��һ��)-onBind
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
