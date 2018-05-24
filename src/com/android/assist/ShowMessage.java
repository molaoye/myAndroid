package com.android.assist;

import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMessage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest3);
		setTitle(this.getClass().getName());
		TextView textView=(TextView) findViewById(R.id.textView1);
		String msg=getIntent().getStringExtra("message");
		textView.setText(msg);
	}

}
