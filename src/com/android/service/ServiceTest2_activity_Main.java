package com.android.service;

import com.android.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * 简单的音乐播放的应用，使用Service
 *
 */
public class ServiceTest2_activity_Main extends Activity implements OnClickListener {

	private Button musicServiceBtn;
	private Button bindMusicServiceBtn;
	private Button remoteMusicServiceBtn;
	private Button MusicReceiver;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest2_main);
		setTitle(this.getClass().getName());
		findView();
		bindButton();
	}

	private void findView() {
		musicServiceBtn = (Button) findViewById(R.id.musicService);
		bindMusicServiceBtn = (Button) findViewById(R.id.bindMusicService);
		remoteMusicServiceBtn = (Button) findViewById(R.id.remoteMusicService);
		MusicReceiver = (Button) findViewById(R.id.MusicReceiver);
	}

	private void bindButton() {
		musicServiceBtn.setOnClickListener(this);
		bindMusicServiceBtn.setOnClickListener(this);
		remoteMusicServiceBtn.setOnClickListener(this);
		MusicReceiver.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.musicService:
			startActivity(new Intent(this, ServiceTest2_activity_PlayMusic.class));
			break;
		case R.id.bindMusicService:
			startActivity(new Intent(this, ServiceTest2_activity_PlayBindMusic.class));
			break;
		case R.id.remoteMusicService:
			startActivity(new Intent(this, ServiceTest2_activity_PlayRemoteMusic.class));
			break;
		case R.id.MusicReceiver:
			startActivity(new Intent(this, ServiceTest2_activity_PlayBroadcaseMusic.class));
			break;
		}
	}
	
	
	
	
	
	
	
	
	
}
