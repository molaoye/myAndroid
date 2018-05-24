package com.android.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.ActivityManage;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

public class InstallAppsPermission extends ListActivity {

	HashMap<String, String[]> map = new HashMap<String, String[]>();
	List<String> packagelist = new ArrayList<String>();
	List<String> pkgNameList = new ArrayList<String>();
	List<String> systemApp = new ArrayList<String>();

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		PackageManager pm = getPackageManager();
		// List<PackageInfo> mPackageinfo=
		// pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		Intent query = new Intent(Intent.ACTION_MAIN);
		query.addCategory("android.intent.category.LAUNCHER");
		List<ResolveInfo> resolves = pm.queryIntentActivities(query, PackageManager.GET_ACTIVITIES);
		for (int i = 0; i < resolves.size(); i++) {
			ResolveInfo info = resolves.get(i);
			// �ж��Ƿ�Ϊϵͳ��Ӧ��
			if ((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				/* ��װ��Ӧ�� */
				String packagename = info.loadLabel(pm).toString();
				String[] permission;
				try {
					permission = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;// ��ȡȨ���б�
					packagelist.add(packagename);
					pkgNameList.add(info.activityInfo.packageName);
					map.put(packagename, permission);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				/* ϵͳӦ�� */
				String packagename = info.loadLabel(pm).toString();
				String[] permission;
				try {
					permission = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;// ��ȡȨ���б�
//					systemApp.add(packagename);
					packagelist.add(packagename);
					pkgNameList.add(info.activityInfo.packageName);
					map.put(packagename, permission);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		/*
		 * �ж��Ƿ�����ϵͳӦ�� for (int i = 0; i < mPackageinfo.size(); i++) {
		 * PackageInfo info=mPackageinfo.get(i);
		 * if((info.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
		 * String packagename=info.applicationInfo.loadLabel(pm).toString();
		 * String[] permission= info.requestedPermissions;
		 * packagelist.add(packagename); map.put(packagename, permission); }
		 * else{ String
		 * packagename=info.applicationInfo.loadLabel(pm).toString(); String[]
		 * permission= info.requestedPermissions; systemApp.add(packagename);
		 * map.put(packagename, permission);
		 * 
		 * } }
		 */
//		packagelist.addAll(systemApp);

		getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, packagelist));
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				//1
//				String[] permission = map.get(packagelist.get(position));
//				StringBuilder sb = new StringBuilder();
//				sb.append("Ȩ����Ŀ��"+permission.length+"\n");
//				for (int i = 0; i < permission.length; i++) {
//					sb.append("Ȩ��" + permission[i] + "\n");
//				}
//				Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
				//2
				String[] permission = map.get(packagelist.get(position));
				Intent i=new Intent(InstallAppsPermission.this, MyAppPermission.class);
				i.putExtra("pkgName", pkgNameList.get(position));
				startActivity(i);
			}
		});
	}

}
