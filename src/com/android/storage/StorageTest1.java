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

		CommonUtil.ToastMsg(this, "����Ƿ�ɴ�:"+(StorageUtil.externalMemoryAvailable()?"��":"��"), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "��ȡ���ʣ��洢�ռ�:"+String.valueOf(StorageUtil.getAvailableExternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "��ȡ�ֻ��ڲ�ʣ��洢�ռ�:"+String.valueOf(StorageUtil.getAvailableInternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "��ȡ����ܵĴ洢�ռ�:"+String.valueOf(StorageUtil.getTotalExternalMemorySize()), Gravity.CENTER);
		CommonUtil.ToastMsg(this, "��ȡ�ֻ��ڲ��ܵĴ洢�ռ�:"+String.valueOf(StorageUtil.getTotalInternalMemorySize()), Gravity.CENTER);
		
	}

}
