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
 * �����Ի���
 * <br><br>
 * ��ͨ�Ի��� CommonDialog1(Context context)
 * <br>
	 * ʾ����
	 * <br>
	 * 1 ��Ϣ��ʾ
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
	   2 2����ť
	   <br>
	   new AlertDialogBuilder().CommonDialog1(this)
				.setMessage("�Ƿ���ʾ���˵���ϸ��Ϣ��")
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
		3 �Զ���view
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
 * ���̶Ի��� ProgressDialog1
 * <br>
	 * ʾ����
	 * <br>
	 * 1 �а�ť
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
			�رտ�����Thread��Handler�������̹߳رգ���:
			<br>
			new Thread() {
				public void run() {
					...
					progressDialog1.dismiss();
				};
			}.start();
		<br><br>
		2 �ް�ť
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
 * �Զ���Ի��� CustomAlertDialog1
 * <br>
 * ʾ��:
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
	 * ȷ���ύ��
	 */
	public final static String title_sumbitConfirm="ȷ���ύ��";
	
	/**
	 * ����
	 */
	public final static String title_error="����";
	
	/**
	 * ϵͳ��ʾ
	 */
	public final static String title_notice="ϵͳ��ʾ";
	
	/**
	 * ȷ��
	 */
	public final static String button_text1="ȷ��";
	
	/**
	 * ȡ��
	 */
	public final static String button_text2="ȡ��";
	
	/**
	 * ��̨����
	 */
	public final static String button_text3="��̨����";
	
	/**
	 * �����ɹ�
	 */
	public final static String msg_success="�����ɹ�";
	
	/**
	 * ����ʧ��
	 */
	public final static String msg_failure="����ʧ��";
	
	/**
	 * ���ڼ�������...
	 */
	public final static String progessMsg_loading_data="���ڼ�������...";
	
	/**
	 * ���̳и��࣬��Ϊ��ֻ�б���Ĺ��������ɼ�
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
		 * �Ի���ͼ��
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
		 * ��������ط��Ƿ�ȡ���Ի���Ĭ��true
		 * @param cancelable
		 * @return
		 */
		public CommonDialog1 setCancelable(boolean cancelable) {
			builder.setCancelable(cancelable);
			return this;
		}

		/**
		 * �����Ҽ�
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
		 * �������
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
		 * �Ի������
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
		 * ��ʾ�Ի���
		 * @return
		 */
		public CommonDialog1 show() {
			builder.show();
			return this;
		}
		
		/**
		 * ��ʾ�Ի���
		 * @return
		 */
		public AlertDialog show(Object o) {
			return builder.show();
		}
		
		/**
		 * �Ի�����Ϣ
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
		 * �����м�
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
		 * �Զ�����ͼ
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
	 * ���̶Ի���
	 * <br>
	 * ʾ����
	 * <br>
	 * 1 �а�ť
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
		2 �ް�ť
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
		 * ��������ط��Ƿ�ȡ���Ի���Ĭ��true
		 * @param flag
		 * @return
		 */
		public ProgressDialog1 setCancelable(boolean flag){
			progressDialog.setCancelable(flag);
			return this;
		}
		
		/**
		 * ���ñ���
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
		 * �Ի���ͼ��
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
		 * �Ի�����Ϣ
		 * @param message
		 * @return
		 */
		public ProgressDialog1 setMessage(CharSequence message){
			progressDialog.setMessage(message);
			return this;
		}
		
		/**
		 * ��ʾ�Ի���
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
		 * 		��:ProgressDialog.BUTTON_POSITIVE 
		 * 		��:ProgressDialog.BUTTON_NEGATIVE 
		 * 		��:ProgressDialog.BUTTON_NEUTRAL
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
	 * ��ͨ�Ի������-�Ҽ�-�м�-����-��Ϣ-ͼ��-ȡ��
	 * <br>
	 * ʾ����
	 * <br>
	 * 1 ��Ϣ��ʾ
	 * <br>
	 * new AlertDialogBuilder().CommonDialog1(GoodsSignfor.this)
									.setMessage(response)
									.show();
	   <br><br>
	   2 2����ť
	   <br>
	   new AlertDialogBuilder().CommonDialog1(this)
				.setMessage("�Ƿ���ʾ���˵���ϸ��Ϣ��")
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
		3 �Զ���view
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
