package com.test;

import com.android.ActivityManage;
import com.android.R;
import com.utils.CarNumIME;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;

/**
 * 
 * 运行直接打开输入法
 *
 */
public class EditTextTest1 extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.edittext_test1);
		
	}

}
