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
 * notifyDataSetChanged() ��̬����ListView 
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
		edit.execute("0", "��1��");// �ѵ�һ�����ݸ�Ϊ"��һ��"
		Handler handler = new Handler();
		handler.postDelayed(add, 3000);// �ӳ�3��ִ��
	}

	Runnable add = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			arr.add("����һ��");// ����һ��
			Adapter.notifyDataSetChanged();
		}
	};

	class editItem extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			arr.set(Integer.parseInt(params[0]), params[1]);
			// params�õ�����һ�����飬params[0]��������"0",params[1]��"��1��"
			// Adapter.notifyDataSetChanged();
			// ִ����Ӻ��ܵ��� Adapter.notifyDataSetChanged()����UI����Ϊ��UI����ͬ�߳�
			// �����onPostExecute��������doBackgroundִ�к���UI�̵߳���
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Adapter.notifyDataSetChanged();
			// ִ����ϣ�����UI
		}

	}

	private OnItemClickListener lvLis = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// �����Ŀʱ����
			// arg2��Ϊ�������λ��
			setTitle(String.valueOf(arr.get(arg2)));

		}

	};

}