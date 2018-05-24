package com.android.gesture;

import com.android.ActivityManage;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Gesture3 extends Activity {
	private GestureDetector gestureDetector;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		LinearLayout main=new LinearLayout(this);
		setContentView(main);

		gestureDetector = initGestureDetector();

		main.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});

		main.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			}
		});
	}

	private GestureDetector initGestureDetector() {
		return new GestureDetector(new SimpleOnGestureListener() {

			private SwipeDetector detector = new SwipeDetector();

			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				try {
					if (detector.isSwipeDown(e1, e2, velocityY)) {
//						return false;
						showToast("Down Swipe");
					} else if (detector.isSwipeUp(e1, e2, velocityY)) {
						showToast("Up Swipe");
					} else if (detector.isSwipeLeft(e1, e2, velocityX)) {
						showToast("Left Swipe");
					} else if (detector.isSwipeRight(e1, e2, velocityX)) {
						showToast("Right Swipe");
					}
				} catch (Exception e) {
				} // for now, ignore
				return false;
			}

			private void showToast(String phrase) {
				Toast.makeText(getApplicationContext(), phrase, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
