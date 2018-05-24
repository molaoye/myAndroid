package com.android.storage;

import com.android.ActivityManage;
import com.utils.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;

public class StorageTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);

		CommonUtil.ToastMsg(this, "外存是否可存:"+(StorageUtil.externalMemoryAvailable()?"是":"否"), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "获取外存剩余存储空间:"+String.valueOf(StorageUtil.getAvailableExternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "获取手机内部剩余存储空间:"+String.valueOf(StorageUtil.getAvailableInternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "获取外存总的存储空间:"+String.valueOf(StorageUtil.getTotalExternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "获取手机内部总的存储空间:"+String.valueOf(StorageUtil.getTotalInternalMemorySize()), Gravity.CENTER);
		
	}

}
