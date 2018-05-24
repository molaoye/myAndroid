package com.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;


/**
 * 创建对话框
 * <br><br>
 * 普通对话框 CommonDialog1(Context context)
 * <br>
	 * 示例：
	 * <br>
	 * 1 信息提示
	 * <br>
	 * 1-1
	 * <br>
	 * new AlertDialogBuilder().CommonDialog1(GoodsSignfor.this)
									.setMessage(response)
									.show();
	   <br>
	   1-2
	   <br>
	   new AlertDialogBuilder().CommonDialog1(GlwtAddRecord.this)
    		.setTitle(AlertDialogBuilder.title_notice)
			.setIcon(R.drawable.warning)
    		.setMessage(msg).show();
	   <br><br>
	   2 2个按钮
	   <br>
	   new AlertDialogBuilder().CommonDialog1(this)
				.setMessage("是否显示该运单详细信息？")
				.setIcon(R.drawable.info)
				.setTitle(AlertDialogBuilder.title_notice)
				.setPositiveButton(AlertDialogBuilder.button_text1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						StaticVar.guid_kf_ConsignmentNote=guid;
						setTagName(TabHostTag.tab_GLlogistics_truckingOrderDetail_info, TabHostTag.tab_GLlogistics_truckingOrderList);
						TruckingOrderDetail_info truckingOrderDetail_info=MyApplication().truckingOrderDetail_info;
						truckingOrderDetail_info.toTab(TabHostTag.tab_GLlogistics_truckingOrderDetail_info_baseInfo);
					}
				})
				.setCancelable(false)
				.setNegativeButton(AlertDialogBuilder.button_text2, null)
				.show();
		<br><br>
		3 自定义view
		<br>
		3-1
		<br>
		View v_pic=LayoutInflater.from(AbnormalitiesDetail.this).inflate(R.layout.gllogistics_abnormalitiesdetailpic_view, null);
		ImageView iv = (ImageView) v_pic.findViewById(R.id.ImageView_pic);
		iv.setImageBitmap(bmp);
		CommonDialog1 cd=new AlertDialogBuilder().CommonDialog1(AbnormalitiesDetail.this)
			.setView(v_pic);
		<br>
		3-2
		<br>
		ImageView img.setImageBitmap(bmp);
		new AlertDialogBuilder().CommonDialog1(context)
			.setView(img)
 * <br><br>
 * 过程对话框 ProgressDialog1
 * <br>
	 * 示例：
	 * <br>
	 * 1 有按钮
	 * <br>
	 * private ProgressDialog1 progressDialog1;
	 * ...
	 * progressDialog1=new AlertDialogBuilder().ProgressDialog1
			.setContext(context)
			.setMessage(AlertDialogBuilder.progessMsg_loading_data)
			.setCancelable(false)
			.setProgressStyle(ProgressDialog.STYLE_SPINNER)
			.setButton(AlertDialogBuilder.button_text2, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					progressDialog1.dismiss();
				}
			})
			.show();
			...
			<br>
			关闭可以用Thread或Handler等其他线程关闭，如:
			<br>
			new Thread() {
				public void run() {
					...
					progressDialog1.dismiss();
				};
			}.start();
		<br><br>
		2 无按钮
		 <br>
	 	private ProgressDialog1 progressDialog1;
	 	...
	 	progressDialog1=new AlertDialogBuilder().ProgressDialog1
			.setContext(context)
			.setMessage(AlertDialogBuilder.progessMsg_loading_data)
			.setCancelable(false)
			.setProgressStyle(ProgressDialog.STYLE_SPINNER)
			.show();
			...
			new Thread() {
				public void run() {
					...
					progressDialog1.dismiss();
				};
			}.start();
 * <br><br>
 * 自定义对话框 CustomAlertDialog1
 * <br>
 * 示例:
 * <br>
 * 1
 * <br>
 * CustomAlertDialog1 customAlertDialog=new AlertDialogBuilder().
					new CustomAlertDialog1(this,R.style.dialog_test2,R.layout.carnumime_popup);
			customAlertDialog.setCanceledOnTouchOutside(true);
			customAlertDialog.show();
			...
			customAlertDialog.dismiss();
	<br>
	2
	<br>
	View view=LayoutInflater.from(this).inflate(R.layout.carnumime_popup, null);
			CustomAlertDialog1 customAlertDialog=new AlertDialogBuilder().new CustomAlertDialog1(this,R.style.dialog_test2,view);
			customAlertDialog.setCanceledOnTouchOutside(true);
			customAlertDialog.show();
			...
			customAlertDialog.dismiss();
 * @author kingon
 *
 */
public class AlertDialogBuilder{
	
	/**
	 * 确认提交？
	 */
	public final static String title_sumbitConfirm="确认提交？";
	
	/**
	 * 错误
	 */
	public final static String title_error="错误";
	
	/**
	 * 系统提示
	 */
	public final static String title_notice="系统提示";
	
	/**
	 * 确定
	 */
	public final static String button_text1="确定";
	
	/**
	 * 取消
	 */
	public final static String button_text2="取消";
	
	/**
	 * 后台加载
	 */
	public final static String button_text3="后台加载";
	
	/**
	 * 操作成功
	 */
	public final static String msg_success="操作成功";
	
	/**
	 * 操作失败
	 */
	public final static String msg_failure="操作失败";
	
	/**
	 * 正在加载数据...
	 */
	public final static String progessMsg_loading_data="正在加载数据...";
	
	/**
	 * 不继承父类，是为了只有本类的公共方法可见
	 * @author kingon
	 *
	 */
	public class CommonDialog1{
		
//		private AlertDialog alertDialog;
		
		private AlertDialog.Builder builder;
		
		private Context context;
		
		private CommonDialog1(Context context){
			this.context=context;
			builder=new AlertDialog.Builder(context);
//			alertDialog=builder.create();
		}
		
		/**
		 * 对话框图标
		 * @param iconId
		 * @return
		 */
		public CommonDialog1 setIcon(Object icon) {
			if(icon instanceof Integer){
				builder.setIcon(Integer.valueOf(String.valueOf(icon)));
			}else if(icon instanceof Drawable){
				builder.setIcon((Drawable)icon);
			}
			return this;
		}

		/**
		 * 点击其他地方是否取消对话框，默认true
		 * @param cancelable
		 * @return
		 */
		public CommonDialog1 setCancelable(boolean cancelable) {
			builder.setCancelable(cancelable);
			return this;
		}

		/**
		 * 设置右键
		 * @param text
		 * @param listener
		 * @return
		 */
		public CommonDialog1 setNegativeButton(Object text, OnClickListener listener) {
			if(text instanceof Integer){
				builder.setNegativeButton(Integer.valueOf(String.valueOf(text)), listener);
			}else if (text instanceof CharSequence){
				builder.setNegativeButton(String.valueOf(text), listener);
			}
			return this;
		}

		/**
		 * 设置左键
		 * @param text
		 * @param listener
		 * @return
		 */
		public CommonDialog1 setPositiveButton(Object text, OnClickListener listener) {
			if(text instanceof Integer){
				builder.setPositiveButton(Integer.valueOf(String.valueOf(text)), listener);
			}else if (text instanceof CharSequence){
				builder.setPositiveButton(String.valueOf(text), listener);
			}
			return this;
		}

		/**
		 * 对话框标题
		 * @param title
		 * @return
		 */
		public CommonDialog1 setTitle(Object title) {
			if(title instanceof Integer){
				builder.setTitle(Integer.valueOf(String.valueOf(title)));
			}else if(title instanceof CharSequence){
				builder.setTitle(String.valueOf(title));
			}
			return this;
		}

		/**
		 * 显示对话框
		 * @return
		 */
		public CommonDialog1 show() {
			builder.show();
			return this;
		}
		
		/**
		 * 显示对话框
		 * @return
		 */
		public AlertDialog show(Object o) {
			return builder.show();
		}
		
		/**
		 * 对话框信息
		 * @param message
		 * @return
		 */
		public CommonDialog1 setMessage(Object message){
			if(message instanceof CharSequence){
				builder.setMessage(String.valueOf(message));
			}else if(message instanceof Integer){
				builder.setMessage(Integer.valueOf(String.valueOf(message)));
			}
			
			return this;
		}
		
		/**
		 * 设置中键
		 * @param text
		 * @param listener
		 * @return
		 */
		public CommonDialog1 setNeutralButton(Object text, OnClickListener listener){
			if(text instanceof CharSequence){
				builder.setNeutralButton(String.valueOf(text), listener);
			}else if(text instanceof Integer){
				builder.setNeutralButton(Integer.valueOf(String.valueOf(text)), listener);
			}
			
			return this;
		}
		
//		private View view;
//		
//		public View getView() {
//			return view;
//		}

		/**
		 * 自定义视图
		 * @param view
		 * @return
		 */
		public CommonDialog1 setView(Object view){
			if(view instanceof Integer){
				LayoutInflater factory = LayoutInflater.from(this.context);
				View view2 = factory.inflate(Integer.valueOf(String.valueOf(view)), null);
				builder.setView(view2);
//				this.view=view2;
			}else if(view instanceof View){
				builder.setView((View) view);
			}
			return this;
		}
		
//		public void dismiss(){
//			alertDialog.dismiss();
//		}
		
	}
	
	/**
	 * 过程对话框：
	 * <br>
	 * 示例：
	 * <br>
	 * 1 有按钮
	 * <br>
	 * private ProgressDialog1 progressDialog1;
	 * ...
	 * progressDialog1=new AlertDialogBuilder().ProgressDialog1
			.setContext(context)
			.setMessage(AlertDialogBuilder.progessMsg_loading_data)
			.setCancelable(false)
			.setProgressStyle(ProgressDialog.STYLE_SPINNER)
			.setButton(AlertDialogBuilder.button_text2, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					progressDialog1.dismiss();
				}
			})
			.show();
			...
			new Thread() {
				public void run() {
					...
					progressDialog1.dismiss();
				};
			}.start();
		<br><br>
		2 无按钮
		 <br>
	 	private ProgressDialog1 progressDialog1;
	 	...
	 	progressDialog1=new AlertDialogBuilder().ProgressDialog1
			.setContext(context)
			.setMessage(AlertDialogBuilder.progessMsg_loading_data)
			.setCancelable(false)
			.setProgressStyle(ProgressDialog.STYLE_SPINNER)
			.show();
			...
			new Thread() {
				public void run() {
					...
					progressDialog1.dismiss();
				};
			}.start();
	 */
	public ProgressDialog1 ProgressDialog1=new ProgressDialog1();
	
	public class ProgressDialog1{
		
		private ProgressDialog progressDialog;
		
//		private Context context;

		public ProgressDialog1 setContext(Context context) {
//			this.context = context;
			progressDialog=new ProgressDialog(context);
			return this;
		}
		
//		private ProgressDialog1(Context context){
//			progressDialog=new ProgressDialog(context);
//		}
		
		/**
		 * 点击其他地方是否取消对话框，默认true
		 * @param flag
		 * @return
		 */
		public ProgressDialog1 setCancelable(boolean flag){
			progressDialog.setCancelable(flag);
			return this;
		}
		
		/**
		 * 设置标题
		 * @param title
		 * @return
		 */
		public ProgressDialog1 setTitle(Object title){
			if(title instanceof Integer){
				progressDialog.setTitle(Integer.valueOf(String.valueOf(title)));
			}else if(title instanceof CharSequence){
				progressDialog.setTitle(String.valueOf(title));
			}
			
			return this;
		}
		
		/**
		 * 对话框图标
		 * @param iconId
		 * @return
		 */
		public ProgressDialog1 setIcon(Object icon) {
			if(icon instanceof Integer){
				progressDialog.setIcon(Integer.valueOf(String.valueOf(icon)));
			}else if(icon instanceof Drawable){
				progressDialog.setIcon((Drawable)icon);
			}
			return this;
		}
		
		/**
		 * 对话框信息
		 * @param message
		 * @return
		 */
		public ProgressDialog1 setMessage(CharSequence message){
			progressDialog.setMessage(message);
			return this;
		}
		
		/**
		 * 显示对话框
		 * @return
		 */
		public ProgressDialog1 show() {
			progressDialog.show();
			return this;
		}
		
		public ProgressDialog1 setButton(CharSequence text, OnClickListener listener){
			progressDialog.setButton(text, listener);
			return this;
		}
		
		/**
		 * 
		 * @param whichButton 
		 * 		左:ProgressDialog.BUTTON_POSITIVE 
		 * 		右:ProgressDialog.BUTTON_NEGATIVE 
		 * 		中:ProgressDialog.BUTTON_NEUTRAL
		 * @param text
		 * @param listener
		 * @return
		 */
//		public ProgressDialog1 setButton(int whichButton, CharSequence text, OnClickListener listener){
//			progressDialog.setButton(whichButton, text, listener);
//			return this;
//		}
		
//		public ProgressDialog1 setButton2(CharSequence text, OnClickListener listener){
//			progressDialog.setButton2(text, listener);
//			return this;
//		}
		
//		public ProgressDialog1 setButton3(CharSequence text, OnClickListener listener){
//			progressDialog.setButton3(text, listener);
//			return this;
//		}
		
		/**
		 * 
		 * @param style ProgressDialog.STYLE_HORIZONTAL ProgressDialog.STYLE_SPINNER
		 * @return
		 */
		public ProgressDialog1 setProgressStyle(int style){
			progressDialog.setProgressStyle(style);
			return this;
		}
		
//		public void dismiss(){
//			if(progressDialog!=null){
//				progressDialog.dismiss();
//			}
//		}
		
		public void dismiss(){
			Message msg=new Message();
			msg.what=dismiss;
			handler.sendMessage(msg);
		}
		
		private final int dismiss=1;
		
		private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case dismiss:{
						if(progressDialog!=null){
							progressDialog.dismiss();
						}
						break;
					}
				}
				super.handleMessage(msg);
			}
			
		};
		
	}
	
	/**
	 * 普通对话框：左键-右键-中键-标题-信息-图标-取消
	 * <br>
	 * 示例：
	 * <br>
	 * 1 信息提示
	 * <br>
	 * new AlertDialogBuilder().CommonDialog1(GoodsSignfor.this)
									.setMessage(response)
									.show();
	   <br><br>
	   2 2个按钮
	   <br>
	   new AlertDialogBuilder().CommonDialog1(this)
				.setMessage("是否显示该运单详细信息？")
				.setIcon(R.drawable.info)
				.setTitle(AlertDialogBuilder.title_notice)
				.setPositiveButton(AlertDialogBuilder.button_text1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						StaticVar.guid_kf_ConsignmentNote=guid;
						setTagName(TabHostTag.tab_GLlogistics_truckingOrderDetail_info, TabHostTag.tab_GLlogistics_truckingOrderList);
						TruckingOrderDetail_info truckingOrderDetail_info=MyApplication().truckingOrderDetail_info;
						truckingOrderDetail_info.toTab(TabHostTag.tab_GLlogistics_truckingOrderDetail_info_baseInfo);
					}
				})
				.setCancelable(false)
				.setNegativeButton(AlertDialogBuilder.button_text2, null)
				.show();
		<br><br>
		3 自定义view
		<br>
		View v_pic=...;
		CommonDialog1 cd=new AlertDialogBuilder().CommonDialog1(AbnormalitiesDetail.this)
			.setView(v_pic);
		final AlertDialog ad=cd.show(null);
		ImageView imageView_closeAlertDialog=(ImageView) v_pic.findViewById(R.id.imageView_closeAlertDialog);
		imageView_closeAlertDialog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ad.dismiss();
			}
		});
		ImageView imageView_savePic=(ImageView) v_pic.findViewById(R.id.imageView_savePic);
		imageView_savePic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ad.dismiss();
			}
		});
	 * @param context
	 * @return
	 */
	public CommonDialog1 CommonDialog1(Context context){
		return new CommonDialog1(context);
	}
	
//	public ProgressDialog1 ProgressDialog1(Context context){
//		return new ProgressDialog1(context);
//	}
	
	public class CustomAlertDialog1 extends AlertDialog{

		private Object layout;
		
		public CustomAlertDialog1(Context context, int theme, Object layout) {
		    super(context, theme);
		    this.layout=layout;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    if(layout instanceof Integer){
		    	setContentView((Integer) layout);
		    }else if(layout instanceof View){
		    	setContentView((View) layout);
		    }
		}
	}

}
