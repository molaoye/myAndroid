package com.android.message;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Message类和Handler类，消息传递和消息处理
 */
public class MessageTest1 extends Activity {

	private TextView tv;
	
	private Timer timer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagetest1);
		setTitle(this.getClass().getName());
		tv=(TextView)findViewById(R.id.textview);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			}
			
		}, 1, 1000);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				tv.setText("UUID:"+UUID.randomUUID().toString());
				break;
			}
		}

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			switch (msg.what) {
			case 1:
				tv.setText("UUID:"+UUID.randomUUID().toString());
				break;
			}
		};
		
	};
}
