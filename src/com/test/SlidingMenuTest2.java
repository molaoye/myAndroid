package com.test;

import com.android.ActivityManage;
import com.android.R;
import com.android.layout.SlideHolder;
import com.android.layout.SlidingLayout;
import com.utils.CarNumIME;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * »¬¶¯²Ëµ¥
 *
 */
public class SlidingMenuTest2 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.slidingmenutest2);
		
		final SlideHolder mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		
		Button menuButton = (Button) findViewById(R.id.menuButton);
		menuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideHolder.toggle();
			}
		});
	}

}
