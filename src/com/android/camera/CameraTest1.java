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
 * �����Ļ�Զ��Խ�
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
					camera.autoFocus(new AutoFoucus()); // �Զ��Խ�
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
			Toast.makeText(CameraTest1.this, "�����Ļ�Զ��Խ���ʹ����������", Toast.LENGTH_SHORT).show();
		}

	}

	

	@SuppressLint({ "NewApi", "NewApi" })
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters param = camera.getParameters();
		/**
		 * ��������ͼƬ��ʽ
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
			if (bestWidth != 0 && bestHeight != 0) { // �õ���ֱ��ʵ��Ǹ�
				param.setPreviewSize(bestWidth, bestHeight);
				// ����ı���SIze�����ǻ�Ҫ����SurfaceView������Surface������ı��С������Camera��ͼ�������ܲ�
				// surfaceView.setLayoutParams(new
				// LinearLayout.LayoutParams(bestWidth, bestHeight));
			}
		}

		// ��ʱsupportedSizes���ж��Ԥ���ߴ硣ͨ��ǰ������ͷ�ķֱ��ʻ�Ƚ�С��ѡ��Ƚ�С�ĳߴ����ü��ɡ�
		// ȡ��ķֱ���
		// for(int mun1==1;num1<supportedSizes
		// param.setPreviewSize(Size.width, Size.height);

		/**
		 * ����Ԥ���ߴ硾������Ҫע�⣺Ԥ���ߴ���Щ������ȷ����Щ�ᱨ�������Ϊɶ��
		 */
		// param.setPreviewSize(240, 320);
		/**
		 * ����ͼƬ��С
		 */
		param.setPictureSize(bestWidth, bestHeight);
		// ����Android 2.2�����ϰ汾

		camera.setDisplayOrientation(90);

		// ����Android 2.2�����ϰ汾ȡ��ע��

		param.setRotation(90);
		param.setFocusMode("auto");
		// param.set("orientation", "portrait");

		camera.setParameters(param);
		/**
		 * ��ʼԤ��
		 */
		camera.startPreview();

		camera.autoFocus(new AutoFoucus()); // �Զ��Խ�
	}

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		try {
			if (currentapiVersion > 8) {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
					Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
					if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {// ���ȴ򿪺�������ͷ
						// ��������ͷ�ķ�λ��Ŀǰ�ж���ֵ�����ֱ�ΪCAMERA_FACING_FRONTǰ�ú�CAMERA_FACING_BACK����
						camera = Camera.open(camIdx);
						CameraNumb = Camera.CameraInfo.CAMERA_FACING_BACK;
					}
				}
			} else {
				camera = Camera.open(); // ������ͷ
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
