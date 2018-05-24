package com.android.media;

import com.android.ActivityManage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 调用系统自带的播放器
 *
 */
public class VideoPlay1 extends Activity{
	
	public static String path=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/VID_20150702_160548.mp4";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		Uri uri = Uri.parse(path);    
		//调用系统自带的播放器
		Intent intent = new Intent(Intent.ACTION_VIEW);   
		Log.i("URI:::::::::", uri.toString());   
		intent.setDataAndType(uri, "video/mp4");   
		startActivity(intent);
	}

}
