/**
 * 
 */
package com.android.service;

import java.io.IOException;

import com.android.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author allin.dev
 * http://allin.cnblogs.com/
 */
public class ServiceTest2_service_BindMusicService extends Service {

	private static final String TAG = "MyService";
	private MediaPlayer mediaPlayer;

	private final IBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		ServiceTest2_service_BindMusicService getService() {
			return ServiceTest2_service_BindMusicService.this;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 * 
	 * 这个IBinder对象和之前看到的onServiceConnected方法中传入的那个IBinder是同一个东西,应用和Service间就依靠这个IBinder对象进行通信
	 * 
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		play();
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d(TAG, "onCreate");
		Toast.makeText(this, "show media player", Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.d(TAG, "onDestroy");
		Toast.makeText(this, "stop media player", Toast.LENGTH_SHORT);
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	
	public void play() {
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
			mediaPlayer.setLooping(false);
		}
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			try {
				// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
				mediaPlayer.prepare();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
