package com.android.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.android.ActivityManage;
import com.android.R;

import android.R.bool;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * 点击屏幕自动对焦
 * 
 * AndroidManifest.xml:
 * <activity android:name="com.android.camera.CameraTest1" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
 * 
 */
public class CameraTest1 extends Activity implements SurfaceHolder.Callback {
	SurfaceHolder surfaceHolder;
	private Camera camera;
	SurfaceView surfaceView;
	List<Size> sizeList;
	int bestWidth = 0;
	int bestHeight = 0;
	boolean isTaked = false;
	Uri EXTRA_OUTPUT;
	Bitmap bm;
	int requestCode = 0;
	int CameraNumb = 0;
	int cameraCount = 0;
	int currentapiVersion = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.cameratest1);
		setTitle(this.getClass().getName());
//		Intent intent = getIntent();
//		EXTRA_OUTPUT = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
		cameraCount = Camera.getNumberOfCameras(); // get cameras number
		currentapiVersion = android.os.Build.VERSION.SDK_INT;

		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView_camera);
		surfaceView.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if (!isTaked)
					camera.autoFocus(new AutoFoucus()); // 自动对焦
			}
		});
		bm = null;
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		isTaked = false;
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	}

	private final class AutoFoucus implements AutoFocusCallback {
		
		public void onAutoFocus(boolean success, Camera camera) {
			Toast.makeText(CameraTest1.this, "点击屏幕自动对焦可使画面清晰！", Toast.LENGTH_SHORT).show();
		}

	}

	

	@SuppressLint({ "NewApi", "NewApi" })
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters param = camera.getParameters();
		/**
		 * 设置拍照图片格式
		 */
		param.setPictureFormat(PixelFormat.JPEG);
		sizeList = param.getSupportedPreviewSizes();
		bestWidth = width;
		bestHeight = height;

		if (sizeList.size() > 1) {
			// button_turn.setVisibility(View.VISIBLE);
			Iterator<Camera.Size> itor = sizeList.iterator();
			while (itor.hasNext()) {
				Camera.Size cur = itor.next();

				if (cur.width > bestWidth || cur.height > bestHeight) { // &&
					// cur.width
					// <MAX_WIDTH
					// &&
					// cur.height
					// <
					// MAX_HEIGHT

					bestWidth = cur.width;
					bestHeight = cur.height;
				}
			}
			if (bestWidth != 0 && bestHeight != 0) { // 得到大分辨率的那个
				param.setPreviewSize(bestWidth, bestHeight);
				// 这里改变了SIze后，我们还要告诉SurfaceView，否则，Surface将不会改变大小，进入Camera的图像将质量很差
				// surfaceView.setLayoutParams(new
				// LinearLayout.LayoutParams(bestWidth, bestHeight));
			}
		}

		// 此时supportedSizes会有多个预览尺寸。通常前置摄像头的分辨率会比较小，选择比较小的尺寸设置即可。
		// 取大的分辨率
		// for(int mun1==1;num1<supportedSizes
		// param.setPreviewSize(Size.width, Size.height);

		/**
		 * 设置预览尺寸【这里需要注意：预览尺寸有些数字正确，有些会报错，不清楚为啥】
		 */
		// param.setPreviewSize(240, 320);
		/**
		 * 设置图片大小
		 */
		param.setPictureSize(bestWidth, bestHeight);
		// 对于Android 2.2及以上版本

		camera.setDisplayOrientation(90);

		// 对于Android 2.2及以上版本取消注释

		param.setRotation(90);
		param.setFocusMode("auto");
		// param.set("orientation", "portrait");

		camera.setParameters(param);
		/**
		 * 开始预览
		 */
		camera.startPreview();

		camera.autoFocus(new AutoFoucus()); // 自动对焦
	}

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		try {
			if (currentapiVersion > 8) {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
					Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
					if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {// 优先打开后置摄像头
						// 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
						camera = Camera.open(camIdx);
						CameraNumb = Camera.CameraInfo.CAMERA_FACING_BACK;
					}
				}
			} else {
				camera = Camera.open(); // 打开摄像头
			}
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			camera.release();
			camera = null;
		}
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		if (camera != null)
			camera.release();
		camera = null;
	}

}
