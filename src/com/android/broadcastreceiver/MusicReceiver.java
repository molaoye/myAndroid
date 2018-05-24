/**
 * 
 */
package com.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @author allin.dev
 * http://allin.cnblogs.com/
 * 
 * 需要在AndroidManifest.xml中进行注册：
 * 		<receiver android:name="com.android.assist.MusicReceiver">
			<intent-filter>
				<action android:name="com.android.assist.MusicReceiver" />
			</intent-filter>
		</receiver>
 *
 */
public class MusicReceiver extends BroadcastReceiver {

	private static final String TAG = "MusicReceiver";
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		Intent it = new Intent("com.android.service.ServiceTest2_service_MusicService");
		Bundle bundle = intent.getExtras();
		it.putExtras(bundle);
		
		if(bundle != null){
			int op = bundle.getInt("op");
			if(op == 4){
				context.stopService(it);
			}else{
				context.startService(it);
			}
		}
		
		
	}

}
