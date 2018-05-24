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
import android.content.pm.FeatureInfo;
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

public class MyAppPermission extends Activity {

	HashMap<String, String[]> map = new HashMap<String, String[]>();
	List<String> packagelist = new ArrayList<String>();
	List<String> systemApp = new ArrayList<String>();
	
	private String pkgName="";

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		pkgName=getIntent().getStringExtra("pkgName");
		
		LinearLayout main=new LinearLayout(this);
		setContentView(main);
		ScrollView scrollView=new ScrollView(this);
		TextView tv=new TextView(this);
		scrollView.addView(tv);
		main.addView(scrollView);
		tv.setText(getPermisson(this));
	}
	
	private String getPermisson(Context context) {
		StringBuffer str=new StringBuffer();
		try {
			if(pkgName==null){
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				// 得到自己的包名
				pkgName = pi.packageName;
			}
			str.append(getPermisson(pkgName, context));
			
		} catch (NameNotFoundException e) {
			Log.e("##ddd", "Could not retrieve permissions for package");
		}

		return str.toString();
	}
	
	private String getPermisson2(Context context) {
		StringBuffer str=new StringBuffer();
		String permName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			// 得到自己的包名
			String pkgName = pi.packageName;

			PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);//通过包名，返回包信息
			String sharedPkgList[] = pkgInfo.requestedPermissions;//得到权限列表

			for (int i = 0; i < sharedPkgList.length; i++) {
				try {
				permName = sharedPkgList[i];

				PermissionInfo tmpPermInfo = pm.getPermissionInfo(permName, 0);//通过permName得到该权限的详细信息
				PermissionGroupInfo pgi = pm.getPermissionGroupInfo(tmpPermInfo.group, 0);//权限分为不同的群组，通过权限名，我们得到该权限属于什么类型的权限。
				
				str.append(i + "-" + permName + "\n");
				str.append(i + "-" + pgi.loadLabel(pm).toString() + "\n");
				str.append(i + "-" + tmpPermInfo.loadLabel(pm).toString()+ "\n");
				str.append(i + "-" + tmpPermInfo.loadDescription(pm).toString()+ "\n");
//				str.append(mDivider + "\n");
				
				} catch (NameNotFoundException e) {
					Log.e("", "NameNotFoundException permissions:"+permName);
				}
			}
		} catch (NameNotFoundException e) {
			Log.e("##ddd", "Could not retrieve permissions for package");
		}

		return str.toString();
	}
	
	private String getPermisson(String pkgName, Context context) {
		StringBuffer str=new StringBuffer();
		String permName = "";
		try {
			PackageManager pm = context.getPackageManager();
			
			PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);//通过包名，返回包信息
			String sharedPkgList[] = pkgInfo.requestedPermissions;//得到权限列表

			for (int i = 0; i < sharedPkgList.length; i++) {
				try {
				permName = sharedPkgList[i];

				PermissionInfo tmpPermInfo = pm.getPermissionInfo(permName, 0);//通过permName得到该权限的详细信息
				PermissionGroupInfo pgi = pm.getPermissionGroupInfo(tmpPermInfo.group, 0);//权限分为不同的群组，通过权限名，我们得到该权限属于什么类型的权限。
				
				int result = pm.checkPermission(permName, pkgName);
				String s_forbidden="";
                if (result == PackageManager.PERMISSION_GRANTED) {
                	s_forbidden="有权限";
                }else if(result==PackageManager.PERMISSION_DENIED){
                	s_forbidden="没权限";
                }
				
				str.append(i + "-" + permName + "\n");
				str.append(i + "-" + pgi.loadLabel(pm).toString() + "\n");
				str.append(i + "-" + tmpPermInfo.loadLabel(pm).toString()+ "\n");
				str.append(i + "-" + tmpPermInfo.loadDescription(pm).toString()+ "\n");
				str.append(i + "-" + s_forbidden+ "\n");
//				str.append(mDivider + "\n");
				
				} catch (NameNotFoundException e) {
					Log.e("", "NameNotFoundException permissions:"+permName);
				}
			}
		} catch (NameNotFoundException e) {
			Log.e("##ddd", "Could not retrieve permissions for package");
		}

		return str.toString();
	}

}
