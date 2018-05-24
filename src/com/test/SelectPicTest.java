package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.android.ActivityManage;
import com.android.R;
import com.utils.SelectPicPopupWindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SelectPicTest extends Activity implements OnClickListener{
	
	private ImageView photo;
	
	private static final int reqCode=1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.selectpictest);
		photo = (ImageView) this.findViewById(R.id.ImageView_photo);
		photo.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode!=RESULT_OK){
			return;
		}
		switch (requestCode) {
		case reqCode:
			if (data != null) {
				//1
//				Bitmap bitmap = null;
//				//ȡ�÷��ص�Uri,������ѡ����Ƭ��ʱ�򷵻ص�����Uri��ʽ���������������еû�����Uri�ǿյģ�����Ҫ�ر�ע��
//				Uri mImageCaptureUri = data.getData();
//				//���ص�Uri��Ϊ��ʱ����ôͼƬ��Ϣ���ݶ�����Uri�л�á����Ϊ�գ���ô���Ǿͽ�������ķ�ʽ��ȡ
//				if (mImageCaptureUri != null) {
//					try {
//						//��������Ǹ���Uri��ȡBitmapͼƬ�ľ�̬����
//						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				} else {
//					//1
////					Bundle extras = data.getExtras();
////					if (extras != null) {
////						//��������Щ���պ��ͼƬ��ֱ�Ӵ�ŵ�Bundle�е��������ǿ��Դ��������ȡBitmapͼƬ
////						bitmap = extras.getParcelable("data");
////						//������Ƭ
//////						try {
//////							OutputStream outStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "koAppTemp.jpg"));
//////							bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//////							outStream.close();
//////						} catch (Exception e) {
//////							String s=e.getMessage();
//////							System.out.println(s);
//////						}
////					}
//					//2
//					byte[] barr=data.getByteArrayExtra("intent_SelectPicPopupWindow");
//					bitmap=BitmapFactory.decodeByteArray(barr, 0, barr.length);
//				}
//				if (bitmap != null) {
//					photo.setImageBitmap(bitmap);
//				}
				//2
				byte[] barr=data.getByteArrayExtra("intent_SelectPicPopupWindow");
				Bitmap bitmap=BitmapFactory.decodeByteArray(barr, 0, barr.length);
				if (bitmap != null) {
					photo.setImageBitmap(bitmap);
				}
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ImageView_photo:{
			//ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
			startActivityForResult(new Intent(this, SelectPicPopupWindow.class), reqCode);
		}
		}
	}

}
