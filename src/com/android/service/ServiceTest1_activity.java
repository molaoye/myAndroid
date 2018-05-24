package com.android.service;

import com.android.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * service简单示例，了解service运行和绑定的执行顺序
 *
 */
public class ServiceTest1_activity extends Activity {
	private static final String TAG = "Servicetest1_Activity";
	
	Button bindBtn;
	Button startBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicetest1);
        setTitle(this.getClass().getName());
        
        bindBtn = (Button)findViewById(R.id.bindBtn);
        startBtn = (Button)findViewById(R.id.startBtn);
        
        bindBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				bindService(new Intent(ServiceTest1_service.ACTION), conn, BIND_AUTO_CREATE);
			}
		});
        
        startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startService(new Intent(ServiceTest1_service.ACTION));
			}
		});
    }
    
    ServiceConnection conn = new ServiceConnection() {
    	public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(TAG, "onServiceConnected");
		}
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected");
		}
	};
	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy unbindService");
		unbindService(conn);
		super.onDestroy();
	};
}