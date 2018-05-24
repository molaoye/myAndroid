package com.android.service;

import com.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * @author allin.dev
 * http://allin.cnblogs.com/
 * 
 */
public class ServiceTest2_activity_PlayBroadcaseMusic extends Activity implements OnClickListener {

	private static final String TAG = "BroadcaseMusic";
	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;
	private Button closeBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest2_playmusic);
		setTitle(this.getClass().getName());
		findView();
		bindButton();
	}


	private void findView() {
		playBtn = (Button) findViewById(R.id.play);
		stopBtn = (Button) findViewById(R.id.stop);
		pauseBtn = (Button) findViewById(R.id.pause);
		exitBtn = (Button) findViewById(R.id.exit);
		closeBtn = (Button) findViewById(R.id.close);

	}

	private void bindButton() {
		playBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		closeBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		int op = -1;
		Intent intent = new Intent("com.android.receiver.MusicReceiver");
		
		switch (v.getId()) {
		case R.id.play:
			Log.d(TAG, "onClick: playing muic");
			op = 1;
			break;
		case R.id.stop:
			Log.d(TAG, "onClick: stoping music");
			op = 2;
			break;
		case R.id.pause:
			Log.d(TAG, "onClick: pausing music");
			op = 3;
			break;
		case R.id.close:
			Log.d(TAG, "onClick: close");
			this.finish();
			break;
		case R.id.exit:
			Log.d(TAG, "onClick: exit");
			op = 4;
			stopService(intent);
			this.finish();
			break;
		}
		
		Bundle bundle  = new Bundle();
		bundle.putInt("op", op);
		intent.putExtras(bundle);
		sendBroadcast(intent);
	}
}
