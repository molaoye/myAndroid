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
//				//取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
//				Uri mImageCaptureUri = data.getData();
//				//返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
//				if (mImageCaptureUri != null) {
//					try {
//						//这个方法是根据Uri获取Bitmap图片的静态方法
//						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				} else {
//					//1
////					Bundle extras = data.getExtras();
////					if (extras != null) {
////						//这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
////						bitmap = extras.getParcelable("data");
////						//保存相片
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
			//使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
			startActivityForResult(new Intent(this, SelectPicPopupWindow.class), reqCode);
		}
		}
	}

}
