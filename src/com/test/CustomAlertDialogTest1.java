package com.test;

import com.android.ActivityManage;
import com.android.R;
import com.utils.AlertDialogBuilder;
import com.utils.CarNumIME;
import com.utils.AlertDialogBuilder.CustomAlertDialog1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;

/**
 * 背景透明对话框
 * @author kingon
 *
 */
public class CustomAlertDialogTest1 extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.customalertdialog_test1);
		
		findViewById(R.id.Button1).setOnClickListener(this);
		findViewById(R.id.Button2).setOnClickListener(this);
		findViewById(R.id.Button3).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.Button1:{
			Dialog dialog = new Dialog(this, R.style.dialog_test1);
//			dialog.setTitle("背景透明对话框");
			View view=LayoutInflater.from(this).inflate(R.layout.carnumime_popup, null);
			dialog.setContentView(view);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			dialog.getWindow().setGravity(Gravity.CENTER);
			break;
		}
		case R.id.Button2:{
			SelectDialog selectDialog = new SelectDialog(this,R.style.dialog_test2);//创建Dialog并设置样式主题
			Window win = selectDialog.getWindow();
			LayoutParams params = new LayoutParams();
			params.x = -80;//设置x坐标
			params.y = -60;//设置y坐标
			win.setAttributes(params);
			selectDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
			selectDialog.show();
			break;
		}
		case R.id.Button3:{
			//1
			CustomAlertDialog1 customAlertDialog=new AlertDialogBuilder().
					new CustomAlertDialog1(this,R.style.dialog_test2,R.layout.carnumime_popup);
			customAlertDialog.setCanceledOnTouchOutside(true);
			customAlertDialog.show();
			//2
//			View view=LayoutInflater.from(this).inflate(R.layout.carnumime_popup, null);
//			CustomAlertDialog1 customAlertDialog=new AlertDialogBuilder().new CustomAlertDialog1(this,R.style.dialog_test2,view);
//			customAlertDialog.setCanceledOnTouchOutside(true);
//			customAlertDialog.show();
			break;
		}
		}
		
	}
	
	class SelectDialog extends AlertDialog{

		public SelectDialog(Context context, int theme) {
		    super(context, theme);
		}

		public SelectDialog(Context context) {
		    super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.slt_cnt_type);
		}
	}

}
