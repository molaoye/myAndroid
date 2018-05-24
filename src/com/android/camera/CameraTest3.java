package com.android.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 通过定时实现扫描码的自动对焦效果和拍照预览
 */
public class CameraTest3 extends Activity implements
		SurfaceHolder.Callback {
	private static String TAG = CameraTest3.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private ImageView imageView;
	private Timer mTimer;
	private TimerTask mTimerTask;

	private Camera.AutoFocusCallback mAutoFocusCallBack;
	private Camera.PreviewCallback previewCallback;

	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameratest3);
		setTitle(this.getClass().getName());
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		imageView = (ImageView) findViewById(R.id.imageView);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mAutoFocusCallBack = new Camera.AutoFocusCallback() {
			
			public void onAutoFocus(boolean success, Camera camera) {
				if (success) {
					// isAutoFocus = true;
					//拍照
					camera.setOneShotPreviewCallback(previewCallback);
					Log.d(TAG, "onAutoFocus success");
				}
			}
		};

		previewCallback = new Camera.PreviewCallback() {
			
			public void onPreviewFrame(byte[] data, Camera arg1) {
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
						YuvImage img = new YuvImage(data, ImageFormat.NV21, w,
								h, null);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						if (img.compressToJpeg(rect, 100, baos)) {
							image = BitmapFactory.decodeByteArray(
									baos.toByteArray(), 0, baos.size());
							//屏幕预览
							imageView.setImageBitmap(image);
							
						}

					}
				}
			}
		};

		mTimer = new Timer();
		mTimerTask = new CameraTimerTask();
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		initCamera();
	}

	
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		previewCallback = null;
		mAutoFocusCallBack = null;
		mTimer.cancel();
		mTimerTask.cancel();
	}

	public void initCamera() {
		camera = Camera.open();
		Camera.Parameters parameters = camera.getParameters();
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE); // 获取当前屏幕管理器对象
		Display display = wm.getDefaultDisplay(); // 获取屏幕信息的描述类
		parameters.setPreviewSize(display.getWidth(), display.getHeight());
		camera.setParameters(parameters);
		try {
			camera.setPreviewDisplay(surfaceHolder);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		camera.setDisplayOrientation(90);
		camera.startPreview();
	}

	class CameraTimerTask extends TimerTask {
		
		public void run() {
			if (camera != null) {
				//启动AF
				camera.autoFocus(mAutoFocusCallBack);
			}
		}
	}
}