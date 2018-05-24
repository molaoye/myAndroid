package com.android.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.android.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通过消息类（Message）和处理类（Handler）实现扫描码的自动对焦效果和拍照预览
 */
public class CameraTest5 extends Activity implements SurfaceHolder.Callback {

	private Camera camera;
	private TextView textView;
	private ImageView imageView;
	
	private AutoFocusCallback autoFocusCallback;
	private PreviewCallback previewCallback;
	
	private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
	private static final int AUTOFOCUS_MSG=1;
	private static final int PREVIEW_MSG=2;
	private boolean useOneShotPreviewCallback;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameratest5);
		setTitle(this.getClass().getName());
		textView=(TextView) findViewById(R.id.textview);
		imageView = (ImageView) findViewById(R.id.imageView);
		SurfaceView surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
		SurfaceHolder surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		autoFocusCallback=new AutoFocusCallback();
		previewCallback=new PreviewCallback();
		useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > 3;
		System.out.println("SDK 版本："+Build.VERSION.SDK);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTOFOCUS_MSG:
				requestAF();
				textView.setText("UUID:"+UUID.randomUUID().toString());
				break;
			case PREVIEW_MSG:
				requestPreview();
				textView.setText("AF again");
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
		requestPreview();
	}
	
	private void requestAF(){
		autoFocusCallback.setHandler(handler, AUTOFOCUS_MSG);
	    camera.autoFocus(autoFocusCallback);
	}
	
	@SuppressLint("NewApi")
	private void requestPreview() {
		previewCallback.setHandler(handler, PREVIEW_MSG);
		if (useOneShotPreviewCallback) {
			camera.setOneShotPreviewCallback(previewCallback);
		} else {
			camera.setPreviewCallback(previewCallback);
		}
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
		private int i=0;
		private final String TAG = AutoFocusCallback.class.getSimpleName();
		private Handler autoFocusHandler;
		private int autoFocusMessage;

		void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
			this.autoFocusHandler = autoFocusHandler;
			this.autoFocusMessage = autoFocusMessage;
		}

		
		public void onAutoFocus(boolean success, Camera camera) {
			if (autoFocusHandler != null) {
				Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
				while(i++>5){
					message.what=PREVIEW_MSG;
					i=0;
				}
				autoFocusHandler.sendMessage(message);
//				autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
				autoFocusHandler = null;
			}else{
				Log.d(TAG, "Got auto-focus callback, but no handler for it");
			}

		}
	}
	
	class PreviewCallback implements Camera.PreviewCallback {
		private final String TAG = PreviewCallback.class.getSimpleName();

		private Handler previewHandler;
		private int previewMessage;

		void setHandler(Handler previewHandler, int previewMessage) {
			this.previewHandler = previewHandler;
			this.previewMessage = previewMessage;
		}

		public void onPreviewFrame(byte[] data, Camera camera) {
			if (previewHandler != null) {
				img(data, camera);
				Message message = previewHandler.obtainMessage(previewMessage, data);
				message.what=AUTOFOCUS_MSG;
				message.sendToTarget();
				previewHandler = null;
			} else {
				Log.d(TAG, "Got preview callback, but no handler for it");
			}
		}
		@SuppressLint({ "NewApi" })
		private void img(byte[] data,Camera camera){
			if (data != null) {
				Camera.Parameters parameters = camera.getParameters();
				int imageFormat = parameters.getPreviewFormat();
				Log.i("map", "Image Format: " + imageFormat);
				Log.i("CameraPreviewCallback", "data length:" + data.length);
				if (imageFormat == ImageFormat.NV21) {
					// get full picture
					Bitmap image = null;
					int w = parameters.getPreviewSize().width;
					int h = parameters.getPreviewSize().height;
					Rect rect = new Rect(0, 0, w, h);
					YuvImage img = new YuvImage(data, ImageFormat.NV21, w, h, null);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					if (img.compressToJpeg(rect, 100, baos)) {
						image = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
						//屏幕预览
						imageView.setImageBitmap(image);
					}
				}
			}
		}

	}

}
