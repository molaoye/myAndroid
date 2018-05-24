package com.android.tabhost;

import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 
 * �̳�Activity������ͨ��findViewById����ȡ�������������ʱID���Զ���
 * 
 */
public class TabHostTest1_subTab2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhosttest1_subtab2);

		// ����������룬ע��˳��
		TabHost mTabHost = (TabHost) findViewById(R.id.mytabhost);
		mTabHost.setup();
		TabWidget tabWidget = mTabHost.getTabWidget();

		mTabHost.addTab(mTabHost.newTabSpec("����").setIndicator("����")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("�Ϻ�").setIndicator("�Ϻ�")
				.setContent(R.id.widget60));
		mTabHost.addTab(mTabHost.newTabSpec("���").setIndicator("���")
				.setContent(R.id.widget59));
		mTabHost.addTab(mTabHost.newTabSpec("����").setIndicator("����")
				.setContent(R.id.widget60));
		mTabHost.setCurrentTab(2);

		int height = 30;
		// int width =45;

		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			/** ���ø߶ȡ���ȣ����ڿ������Ϊfill_parent���ڴ˶���ûЧ�� */
			tabWidget.getChildAt(i).getLayoutParams().height = height;
			// tabWidget.getChildAt(i).getLayoutParams().width = width;
			/** ����tab�б������ֵ���ɫ����ȻĬ��Ϊ��ɫ */
			final TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
		}
	}
}