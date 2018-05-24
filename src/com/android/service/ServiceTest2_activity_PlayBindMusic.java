package com.android.service;

import com.android.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * @author allin.dev
 * http://allin.cnblogs.com/
 * 
 * 服务绑定成功后会调用ServiceConnection中的回调函数：public void onServiceConnected(ComponentName name, IBinder service)
回调函数里面使用musicService = ((BindMusicService.MyBinder)(service)).getService()来获取BindMusicService服务对象，
有了BindMusicService实例对象，就可以调用服务提供的接口。
 *
 */
public class ServiceTest2_activity_PlayBindMusic extends Activity implements OnClickListener {

	private static final String TAG = "PlayBindMusic";
	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;
	
	private ServiceTest2_service_BindMusicService musicService;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest2_bindmusicservice);
		setTitle(this.getClass().getName());
		findView();
		bindButton();
		connection();
	}


	private void findView() {
		playBtn = (Button) findViewById(R.id.play);
		stopBtn = (Button) findViewById(R.id.stop);
		pauseBtn = (Button) findViewById(R.id.pause);
		exitBtn = (Button) findViewById(R.id.exit);
	}

	private void bindButton() {
		playBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

	private void connection(){
		Log.d(TAG, "connecting.....");
		Intent intent = new Intent("com.android.service.ServiceTest2_service_BindMusicService");
		bindService(intent, sc, Context.BIND_AUTO_CREATE);
		
	}
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.play:
			Log.d(TAG, "onClick: binding srvice");
			musicService.play();
			break;
		case R.id.stop:
			Log.d(TAG, "onClick: stoping srvice");
			if(musicService != null){
				musicService.stop();
			}
			break;
		case R.id.pause:
			Log.d(TAG, "onClick: pausing srvice");
			if(musicService != null){
				musicService.pause();
			}
			break;
		case R.id.exit:
			Log.d(TAG, "onClick: exit");
			this.finish();
			break;
		}
	}
	
	
	
	private ServiceConnection sc = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
			Log.d(TAG, "in onServiceDisconnected");
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ((ServiceTest2_service_BindMusicService.MyBinder)(service)).getService();
			if(musicService != null){
				musicService.play();
			}
			
			Log.d(TAG, "in onServiceConnected");
		}
	};
}
