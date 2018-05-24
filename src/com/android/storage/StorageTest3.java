package com.android.storage;

import java.io.File;

import com.android.ActivityManage;
import com.utils.CommonUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StorageTest3 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);

		LinearLayout main=new LinearLayout(this);
		setContentView(main);
		
		String text="";
		String rootPath=StorageUtil.getExternalStorageDirectory();
		text+=rootPath+"\n";
		File file=new File(rootPath);
		if(file.isDirectory()){
			File[] files=file.listFiles();
			if(files!=null){
				for(File f_child:file.listFiles()){
					text+=f_child.getName()+"\n";
				}
			}
			
		}
		TextView textView=new TextView(this);
		textView.setText(text);
		main.addView(textView);
		
	}

}
