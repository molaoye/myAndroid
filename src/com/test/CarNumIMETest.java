package com.test;

import com.android.ActivityManage;
import com.android.R;
import com.utils.CarNumIME;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class CarNumIMETest extends Activity implements OnClickListener{

	private EditText EditText_carNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.carnumime_test);
		
		EditText_carNum=(EditText) findViewById(R.id.EditText_carNum);
		EditText_carNum.setOnClickListener(this);
		EditText_carNum.setKeyListener(null);//²»¿É±à¼­
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this, CarNumIME.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK){
			EditText_carNum.setText(data.getStringExtra("carNum"));
		}
	}

}
