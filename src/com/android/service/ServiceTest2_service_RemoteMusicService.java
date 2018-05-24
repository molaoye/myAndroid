/**
 * 
 */
package com.android.service;

import java.io.IOException;

import com.android.R;
import com.android.assist.IMusicControlService;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author allin.dev
 * http://allin.cnblogs.com/
 *
 */
public class ServiceTest2_service_RemoteMusicService extends Service {

	private static final String TAG = "RemoteMusicService";
	private MediaPlayer mediaPlayer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private final IMusicControlService.Stub binder = new IMusicControlService.Stub() {

		public void stop() throws RemoteException {
			Log.d(TAG,"stop....");
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

		public void play() throws RemoteException {
			Log.d(TAG,"play....");
			if (mediaPlayer == null) {
				mediaPlayer = MediaPlayer.create(ServiceTest2_service_RemoteMusicService.this,
						R.raw.tmp);
				mediaPlayer.setLooping(false);
			}
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		}

		public void pause() throws RemoteException {
			Log.d(TAG,"pause....");
			
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}			
		}

	};
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.d(TAG, "onDestroy");
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
}
