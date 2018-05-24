package com.android.camera;

import java.io.IOException;

import com.android.R;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 简单打开相机预览
 */
public class CameraTest2 extends Activity implements SurfaceHolder.Callback {

	private Camera camera;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameratest2);
		setTitle(this.getClass().getName());
		SurfaceView surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
		SurfaceHolder surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		openCamera(holder);
	}
	
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
	}

}
