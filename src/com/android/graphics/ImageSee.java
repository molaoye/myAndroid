package com.android.graphics;

import com.android.R;
import com.android.graphics.ImageControl.ICustomMethod;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageSee extends Activity {

	private ImageControl ImageControl1;
	
	public static Bitmap bmp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.imagesee);
		ImageControl1=(ImageControl) findViewById(R.id.ImageControl1);
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		init();
	}

	private void init() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
		int screenH = this.getWindowManager().getDefaultDisplay().getHeight() - statusBarHeight;
		if (bmp != null) {
			ImageControl1.imageInit(bmp, screenW, screenH, statusBarHeight,
					new ICustomMethod() {
						@Override
						public void customMethod(Boolean currentStatus) {
							// 当图片处于放大或缩小状态时，控制标题是否显示
							Log.i("", currentStatus.toString());
						}
					});
		} else {
			Toast.makeText(ImageSee.this, "图片加载失败，请稍候再试！", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			ImageControl1.mouseDown(event);
			break;
		/**
		 * 非第一个点按下
		 */
		case MotionEvent.ACTION_POINTER_DOWN:
			ImageControl1.mousePointDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			ImageControl1.mouseMove(event);
			break;
		case MotionEvent.ACTION_UP:
			ImageControl1.mouseUp();
			break;
		}
		return super.onTouchEvent(event);
	}
}
