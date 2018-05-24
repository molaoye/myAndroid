package com.android.storage;

import com.android.ActivityManage;
import com.utils.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StorageTest2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);

		LinearLayout main=new LinearLayout(this);
		setContentView(main);
		
		String text="";
		text+="内部可用存储空间是："+Long.toString(StorageUtil.getAvailableInternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="内部总共存储空间是："+Long.toString(StorageUtil.getTotalInternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="外部可用存储空间是："+Long.toString(StorageUtil.getAvailableExternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="外部总共存储空间是："+Long.toString(StorageUtil.getTotalExternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="外存是否可存："+(StorageUtil.externalMemoryAvailable()?"是":"否")+"\n";
		text+="获取Android 数据目录/手机内部存储目录："+StorageUtil.getDataDirectory()+"\n";
		text+="获取Android 下载/缓存内容目录："+StorageUtil.getDownloadCacheDirectory()+"\n";
		text+="获取外部存储目录："+StorageUtil.getExternalStorageDirectory()+"\n";
		text+="获取Android 的根目录："+StorageUtil.getRootDirectory()+"\n";
		text+="外存是否移除："+(StorageUtil.isExternalStorageRemovable()?"是":"否")+"\n";
		TextView textView=new TextView(this);
		textView.setText(text);
		main.addView(textView);
		
	}

}
