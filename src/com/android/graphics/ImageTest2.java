package com.android.graphics;

import com.android.ActivityManage;
import com.android.R;
import com.android.graphics.ImageControl.ICustomMethod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageTest2 extends Activity implements OnClickListener{

	private ImageView imageview1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.imagetest2);
		imageview1=(ImageView) findViewById(R.id.imageview1);
		imageview1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.imageview1){
			ImageSee.bmp=((BitmapDrawable)imageview1.getDrawable()).getBitmap();
			Intent i=new Intent(this, ImageSee.class);
			startActivity(i);
		}
	}
}
