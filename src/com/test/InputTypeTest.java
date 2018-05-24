package com.test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.android.ActivityManage;
import com.android.R;
import com.utils.CommonUtil;
import com.utils.DaoException;
import com.utils.ExceptionDetail;
import com.utils.JdbcConn;
import com.utils.JdbcDao;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InputTypeTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.inputtypetest);
		
		EditText et1=(EditText) findViewById(R.id.et1);
		LinearLayout main=(LinearLayout) findViewById(R.id.ll1);
		
		LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		TextView tv1=new TextView(this);
		tv1.setText("TYPE_CLASS_DATETIME");
		tv1.setLayoutParams(lp);
		main.addView(tv1);
		
		EditText e1=new EditText(this);
		e1.setInputType(InputType.TYPE_CLASS_DATETIME);
		e1.setLayoutParams(lp);
		main.addView(e1);
		
		TextView tv2=new TextView(this);
		tv2.setText("TYPE_CLASS_PHONE");
		tv2.setLayoutParams(lp);
		main.addView(tv2);
		
		EditText e2=new EditText(this);
		e2.setInputType(InputType.TYPE_CLASS_PHONE);
		e2.setLayoutParams(lp);
		main.addView(e2);
		
		TextView tv3=new TextView(this);
		tv3.setText("TYPE_CLASS_TEXT");
		tv3.setLayoutParams(lp);
		main.addView(tv3);
		
		EditText e3=new EditText(this);
		e3.setInputType(InputType.TYPE_CLASS_TEXT);
		e3.setLayoutParams(lp);
		main.addView(e3);
		
		TextView tv4=new TextView(this);
		tv4.setText("TYPE_CLASS_NUMBER");
		tv4.setLayoutParams(lp);
		main.addView(tv4);
		
		EditText e4=new EditText(this);
		e4.setInputType(InputType.TYPE_CLASS_NUMBER);
		e4.setLayoutParams(lp);
		main.addView(e4);
		
		TextView tv5=new TextView(this);
		tv5.setText("TYPE_NUMBER_FLAG_DECIMAL");
		tv5.setLayoutParams(lp);
		main.addView(tv5);
		
		EditText e5=new EditText(this);
		e5.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		e5.setLayoutParams(lp);
		main.addView(e5);
		
		CommonUtil.ToastMsg(this, "numberDecimal:"+String.valueOf(et1.getInputType())+
				",TYPE_NUMBER_FLAG_DECIMAL:"+String.valueOf(e5.getInputType()), Gravity.CENTER);
		
	}

}
