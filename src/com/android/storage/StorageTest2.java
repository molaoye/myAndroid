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
		text+="�ڲ����ô洢�ռ��ǣ�"+Long.toString(StorageUtil.getAvailableInternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="�ڲ��ܹ��洢�ռ��ǣ�"+Long.toString(StorageUtil.getTotalInternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="�ⲿ���ô洢�ռ��ǣ�"+Long.toString(StorageUtil.getAvailableExternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="�ⲿ�ܹ��洢�ռ��ǣ�"+Long.toString(StorageUtil.getTotalExternalMemorySize()/(1024*1024))+"M"+"\n";
		text+="����Ƿ�ɴ棺"+(StorageUtil.externalMemoryAvailable()?"��":"��")+"\n";
		text+="��ȡAndroid ����Ŀ¼/�ֻ��ڲ��洢Ŀ¼��"+StorageUtil.getDataDirectory()+"\n";
		text+="��ȡAndroid ����/��������Ŀ¼��"+StorageUtil.getDownloadCacheDirectory()+"\n";
		text+="��ȡ�ⲿ�洢Ŀ¼��"+StorageUtil.getExternalStorageDirectory()+"\n";
		text+="��ȡAndroid �ĸ�Ŀ¼��"+StorageUtil.getRootDirectory()+"\n";
		text+="����Ƿ��Ƴ���"+(StorageUtil.isExternalStorageRemovable()?"��":"��")+"\n";
		TextView textView=new TextView(this);
		textView.setText(text);
		main.addView(textView);
		
	}

}
