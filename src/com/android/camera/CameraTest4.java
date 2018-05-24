package com.android.camera;

import java.io.IOException;
import java.util.UUID;

import com.android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

/**
 * 通过消息类（Message）和处理类（Handler）实现扫描码的自动对焦效果
 */
public class CameraTest4 extends Activity implements SurfaceHolder.Callback {

	private Camera camera;
	
	private TextView textView;
	
	private AutoFocusCallback autoFocusCallback;
	
	private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameratest4);
		setTitle(this.getClass().getName());
		textView=(TextView) findViewById(R.id.textview);
		SurfaceView surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
		SurfaceHolder surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		autoFocusCallback=new AutoFocusCallback();
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				requestAF();
				textView.setText("UUID:"+UUID.randomUUID().toString());
				break;
			}
		}
		
	};

	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		openCamera(holder);
		requestAF();
	}
	
	private void requestAF(){
		autoFocusCallback.setHandler(handler, 1);
	    camera.autoFocus(autoFocusCallback);
	}
	
	@SuppressLint("NewApi")
	private void openCamera(SurfaceHolder holder){
		if(camera==null){
			camera = Camera.open();
		}
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		camera.setDisplayOrientation(90);
		camera.startPreview();
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		if (camera != null){
			camera.release();
		}
		autoFocusCallback.setHandler(null, 0);
		autoFocusCallback=null;
		handler=null;
	}
	
	class AutoFocusCallback implements Camera.AutoFocusCallback {
		private Handler autoFocusHandler;
		private int autoFocusMessage;

		void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
			this.autoFocusHandler = autoFocusHandler;
			this.autoFocusMessage = autoFocusMessage;
		}

		
		public void onAutoFocus(boolean success, Camera camera) {
			Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
//			autoFocusHandler.sendMessage(message);
			autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
			autoFocusHandler = null;
		}
	}

}
