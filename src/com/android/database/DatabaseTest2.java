package com.android.database;

import com.android.ActivityManage;
import com.android.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseTest2 extends Activity implements OnClickListener {
	
	private TextView result;
	private EditText expression;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.database_test2);
		
		result=(TextView) findViewById(R.id.result);
		expression=(EditText) findViewById(R.id.expression);
		findViewById(R.id.calculate).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SQLiteDatabase db=new BooksDB(this).getWritableDatabase();
		Cursor cr = db.rawQuery("select "+expression.getText().toString().trim(), null);
		if (cr.moveToFirst()) {
			result.setText(cr.getString(0));
		}
	}
}