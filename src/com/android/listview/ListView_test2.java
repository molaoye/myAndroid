package com.android.listview;

import java.util.ArrayList;

import com.android.ActivityManage;
import com.android.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * notifyDataSetChanged() 动态更新ListView 
 * @author kingon
 *
 */
public class ListView_test2 extends Activity {
	/** Called when the activity is first created. */
	ListView lv;
	ArrayAdapter<String> Adapter;
	ArrayList<String> arr = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.listview_test2);
		lv = (ListView) findViewById(R.id.lv);
		arr.add("123");
		arr.add("234");
		arr.add("345");
		Adapter = new ArrayAdapter<String>(this, R.layout.listview_test2_playlist, arr);
		lv.setAdapter(Adapter);
		lv.setOnItemClickListener(lvLis);
		editItem edit = new editItem();
		edit.execute("0", "第1项");// 把第一项内容改为"第一项"
		Handler handler = new Handler();
		handler.postDelayed(add, 3000);// 延迟3秒执行
	}

	Runnable add = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			arr.add("增加一项");// 增加一项
			Adapter.notifyDataSetChanged();
		}
	};

	class editItem extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			arr.set(Integer.parseInt(params[0]), params[1]);
			// params得到的是一个数组，params[0]在这里是"0",params[1]是"第1项"
			// Adapter.notifyDataSetChanged();
			// 执行添加后不能调用 Adapter.notifyDataSetChanged()更新UI，因为与UI不是同线程
			// 下面的onPostExecute方法会在doBackground执行后由UI线程调用
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Adapter.notifyDataSetChanged();
			// 执行完毕，更新UI
		}

	}

	private OnItemClickListener lvLis = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// 点击条目时触发
			// arg2即为点中项的位置
			setTitle(String.valueOf(arr.get(arg2)));

		}

	};

}