package com.android.listview;

import java.util.ArrayList;

import com.android.ActivityManage;
import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ¼òµ¥listview
 *
 */
public class ListView_test1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.listview_test1);
		
		ListView lv=(ListView) findViewById(R.id.lv);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			arrayList.add((i+1) + "  -------------     " + System.currentTimeMillis());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_test1_item, R.id.textView1, arrayList);

		lv.setAdapter(adapter);
		
	}

}
