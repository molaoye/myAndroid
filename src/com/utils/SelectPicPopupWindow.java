package com.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.android.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private LinearLayout layout;
	private Intent intent;
	
	private String PHOTO_FOLDER=new File(Environment.getExternalStorageDirectory(), "").getPath()+"/myAndroid/";
	private String PHOTO_NAME="";
	
	private static final int requestCode_camera=1;
	private static final int requestCode_getImg=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectpicpopupwindow);
		intent = getIntent();
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);

		layout = (LinearLayout) findViewById(R.id.pop_layout);
		layout.setOnClickListener(this);
		
		// ��Ӱ�ť����
		btn_cancel.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
		
		File file=new File(PHOTO_FOLDER);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}

	/**
	 * ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		
		//1
//		if(requestCode==requestCode_camera){
//			Bitmap bmp = resizeBitmap(getBitmapForFile(PHOTO_FOLDER+PHOTO_NAME), 640);
//			ByteArrayOutputStream baos=new ByteArrayOutputStream();  
//			bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
//			byte [] bitmapByte = baos.toByteArray();
//			intent.putExtra("intent_SelectPicPopupWindow", bitmapByte);
//		}
		//2
		if(requestCode==requestCode_camera){
			//1 ѹ������Сѹ����һ��
//			Bitmap bmp = CommonUtil.resizeBitmap(CommonUtil.getBmpFromImgPath(PHOTO_FOLDER+PHOTO_NAME), 640);
//			ByteArrayOutputStream baos=new ByteArrayOutputStream();
//			bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
//			byte[] bitmapByte = baos.toByteArray();
//			try {
//				baos.flush();
//				baos.close();
//			} catch (IOException e) {
//				System.out.println(ExceptionDetail.getErrorMessage(e));
//			}
			//2 ûѹ����ԭͼ����intent���ݳ���
//			Bitmap bmp = CommonUtil.getBmpFromImgPath(PHOTO_FOLDER+PHOTO_NAME);
//			ByteArrayOutputStream baos=new ByteArrayOutputStream();
//			bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
//			byte[] bitmapByte = baos.toByteArray();
//			try {
//				baos.flush();
//				baos.close();
//			} catch (IOException e) {
//				System.out.println(ExceptionDetail.getErrorMessage(e));
//			}
			//3 ѹ����������Сѹ�����Ϻ�
			byte[] bitmapByte=CommonUtil.resizeBitmap(new File(PHOTO_FOLDER+PHOTO_NAME), 600, 75);
			System.out.println("camera bmp byte len:"+bitmapByte.length);
			
			//1 ��byte[]
			intent.putExtra("intent_SelectPicPopupWindow", bitmapByte);
			//2 ��string
//			intent.putExtra("intent_SelectPicPopupWindow", PHOTO_FOLDER+PHOTO_NAME);
		}
		
		//1
//		if(data!=null){
//			// ѡ������������պ�������ﴦ��Ȼ�����Ǽ���ʹ��setResult����Intent�Ա���Դ������ݺ͵���
//			if (data.getExtras() != null){
//				intent.putExtras(data.getExtras());
//			}
//			if (data.getData() != null){
//				intent.setData(data.getData());
//			}
//		}
		//2
		if(requestCode==requestCode_getImg){
			if(data!=null){
				//1 ûѹ����ԭͼ��,����uri
				// ѡ������������պ�������ﴦ��Ȼ�����Ǽ���ʹ��setResult����Intent�Ա���Դ������ݺ͵���
//				if (data.getExtras() != null){
//					intent.putExtras(data.getExtras());
//				}
//				if (data.getData() != null){
//					intent.setData(data.getData());
//				}
				//2 ѹ��������byte[]
				Uri uri = data.getData();
				if (uri != null){
					ContentResolver cr = getContentResolver();
					InputStream is = null;
					try {
						is = cr.openInputStream(uri);
					} catch (FileNotFoundException e) {
						System.out.println(ExceptionDetail.getErrorMessage(e));
					}
					
					byte[] bitmapByte=CommonUtil.resizeBitmap(is, 600, 75, new Object());
					System.out.println("getImg bmp byte len:"+bitmapByte.length);
					
					intent.putExtra("intent_SelectPicPopupWindow", bitmapByte);
					
					//��ȡͼƬ·��
					String[] proj = {MediaStore.Images.Media.DATA}; 
					Cursor cursor = managedQuery(uri, proj, null, null, null); 
					//���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ 
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
					cursor.moveToFirst(); 
					//����������ֵ��ȡͼƬ·�� 
					String path = cursor.getString(column_index);
					CommonUtil.ToastMsg(this, path, Gravity.CENTER);
				}
			}
		}
		
		setResult(RESULT_OK, intent);
		finish();

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			try {
				//1
				// ����������ActionΪMediaStore.ACTION_IMAGE_CAPTURE��
				// ��Щ��ʹ��������Action���ҷ�������Щ�����л�����⣬��������ѡ�����
//				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				//2
//				PHOTO_NAME="MyPic"+CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS")+".jpg";
//				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				//����ͼƬ
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PHOTO_FOLDER+PHOTO_NAME)));
				//3
				PHOTO_NAME="MyPic"+CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS")+".jpg";
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PHOTO_FOLDER+PHOTO_NAME)));
				
				startActivityForResult(intent, requestCode_camera);
			} catch (Exception e) {
				System.out.println(ExceptionDetail.getErrorMessage(e));
			}
			break;
		case R.id.btn_pick_photo:
			try {
				// ѡ����Ƭ��ʱ��Ҳһ����������ActionΪIntent.ACTION_GET_CONTENT��
				// ��Щ��ʹ��������Action���ҷ�������Щ�����л�����⣬��������ѡ�����
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				
				startActivityForResult(intent, requestCode_getImg);
			} catch (ActivityNotFoundException e) {
				System.out.println(ExceptionDetail.getErrorMessage(e));
			}
			break;
		case R.id.btn_cancel:
			finish();
			break;
		// ���ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
		case R.id.pop_layout:
			Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", Toast.LENGTH_SHORT).show();
			break;
		}

	}
	
	private static Bitmap resizeBitmap(Bitmap bitmap, int newWidth) { 
	     int width = bitmap.getWidth(); 
	     int height = bitmap.getHeight(); 
	     float temp = ((float) height) / ((float) width); 
	     int newHeight = (int) ((newWidth) * temp); 
	     float scaleWidth = ((float) newWidth) / width; 
	     float scaleHeight = ((float) newHeight) / height; 
	     Matrix matrix = new Matrix(); 
	     // resize the bit map 
	     matrix.postScale(scaleWidth, scaleHeight); 
	     // matrix.postRotate(45); 
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true); 
	     bitmap.recycle(); 
	     return resizedBitmap; 
	}

	private static Bitmap getBitmapForFile(String filePath){
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;
	}

}
