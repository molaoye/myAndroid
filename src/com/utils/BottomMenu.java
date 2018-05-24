package com.utils;

import com.android.R;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 底栏菜单
 *
 */
public class BottomMenu extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
	private int menuId;
	private LayoutParams params;
	
	public BottomMenu(Context context,int icon,int color,String label) {
		this(context,icon,color,label,0);
	}
	
	public BottomMenu(Context context,int icon,int color,String label,int menuId) {
		super(context);
		this.menuId = menuId;
		setBackgroundResource(R.drawable.bottommenu_item_select_icon);
		setPadding(0, 5, 0, 5);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		//每屏放5个按钮
		DisplayMetrics screenSize = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(screenSize); 
		params = new LayoutParams(screenSize.widthPixels/3,LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		setLayoutParams(params);
		
		imageView = new ImageView(context);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		imageView.setLayoutParams(params);
		imageView.setImageResource(icon);
		addView(imageView);
		
		textView = new TextView(context);
		if(color!=-1)
		textView.setTextColor(color);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		textView.setLayoutParams(params);
		textView.setText(label);
		addView(textView);
	}
	
	/**
	 * 获取菜单编号
	 * @return
	 */
	public int getMenuId(){
		return this.menuId;
	}
	
}
