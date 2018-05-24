package com.android.graphics;

import com.android.ActivityManage;
import com.android.R;
import com.android.graphics.ImageControl.ICustomMethod;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.imagetest1);
		findView();
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		init();
	}

	ImageControl imgControl;
	LinearLayout llTitle;
	TextView tvTitle;

	private void findView() {
		imgControl = (ImageControl) findViewById(R.id.common_imageview_imageControl1);
		llTitle = (LinearLayout) findViewById(R.id.common_imageview_llTitle);
		tvTitle = (TextView) findViewById(R.id.common_imageview_title);
	}

	private void init() {
		tvTitle.setText("ͼƬ����");
		// �������Ϊimgcontrol��ͼƬ·����̬��ֵ
		// ............

		Bitmap bmp;
		if (imgControl.getDrawingCache() != null) {
			bmp = Bitmap.createBitmap(imgControl.getDrawingCache());
		} else {
			bmp = ((BitmapDrawable) imgControl.getDrawable()).getBitmap();
		}
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
		int screenH = this.getWindowManager().getDefaultDisplay().getHeight() - statusBarHeight;
		if (bmp != null) {
			imgControl.imageInit(bmp, screenW, screenH, statusBarHeight,
					new ICustomMethod() {

						@Override
						public void customMethod(Boolean currentStatus) {
							// ��ͼƬ���ڷŴ����С״̬ʱ�����Ʊ����Ƿ���ʾ
							if (currentStatus) {
								llTitle.setVisibility(View.GONE);
							} else {
								llTitle.setVisibility(View.VISIBLE);
							}
						}
					});
		} else {
			Toast.makeText(ImageTest1.this, "ͼƬ����ʧ�ܣ����Ժ����ԣ�", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			imgControl.mouseDown(event);
			break;

		/**
		 * �ǵ�һ���㰴��
		 */
		case MotionEvent.ACTION_POINTER_DOWN:

			imgControl.mousePointDown(event);

			break;
		case MotionEvent.ACTION_MOVE:
			imgControl.mouseMove(event);

			break;

		case MotionEvent.ACTION_UP:
			imgControl.mouseUp();
			break;

		}

		return super.onTouchEvent(event);
	}
}
