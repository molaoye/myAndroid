package com.android.camera;

import java.io.File;
import java.io.InputStreamReader;

import com.android.ActivityManage;
import com.android.R;
import com.utils.CommonUtil;
import com.utils.ExceptionDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 调用系统相机拍照保存图片并浏览图片
 *
 */
public class CameraTest8 extends Activity implements OnClickListener{
	
	private String PHOTO_FOLDER=new File(Environment.getExternalStorageDirectory(), "").getPath()+"/myAndroid/";
	private String PHOTO_NAME="";
	
	private ImageView imageView_pic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.cameratest8);
		
		File file=new File(PHOTO_FOLDER);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		Button button_camera=(Button)findViewById(R.id.button_camera);
		button_camera.setOnClickListener(this);
		imageView_pic=(ImageView)findViewById(R.id.imageView_pic);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case Activity.DEFAULT_KEYS_DIALER:{
			try{ 
		         Bitmap take = resizeBitmap(getBitmapForFile(PHOTO_FOLDER+PHOTO_NAME), 640); 
		         imageView_pic.setImageBitmap(take);
		     }catch(Exception e){
		    	 System.out.println(ExceptionDetail.getErrorMessage(e));
		     } 

			break;
		}
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_camera:{
			PHOTO_NAME="MyPic"+CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS")+".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PHOTO_FOLDER+PHOTO_NAME))); 
			startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER); 
			break;
		}
		}
	}


}
