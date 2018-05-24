package com.android.gesture;

import com.android.ActivityManage;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Gesture2 extends Activity {
	/** Called when the activity is first created. */

	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		LinearLayout main=new LinearLayout(this);
		setContentView(main);
		
		gestureDetector = new BuildGestureExt(this,
				new BuildGestureExt.OnGestureResult() {
					@Override
					public void onGestureResult(int direction) {
						String msg="";
						switch(direction){
						case 0:msg="ÏÂÍùÉÏ";break;
						case 1:msg="ÉÏÍùÏÂ";break;
						case 2:msg="ÓÒÍù×ó";break;
						case 3:msg="×óÍùÓÒ";break;
						}
						show(msg);
					}
				}).Build();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private void show(String value) {
		Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
	}

}
