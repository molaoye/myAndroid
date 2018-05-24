package com.android.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.android.ActivityManage;
import com.android.R;
import com.android.graphics.ImageCompressFactory;
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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 调用系统相机拍照保存图片并浏览图片，使用ByteArrayOutputStream保存图片
 *
 */
public class CameraTest9 extends Activity implements OnClickListener{
	
	private String PHOTO_FOLDER=new File(Environment.getExternalStorageDirectory(), "").getPath()+"/myAndroid/";
	private String PHOTO_NAME="";
	
	private static final int requestCode_camera=1;
	
	private ImageView imageView_pic;
	private Button button_save;
	
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.cameratest9);
		
		File file=new File(PHOTO_FOLDER);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		Button button_camera=(Button)findViewById(R.id.button_camera);
		button_camera.setOnClickListener(this);
		button_save=(Button) findViewById(R.id.button_save);
		button_save.setOnClickListener(this);
		button_save.setEnabled(false);
		imageView_pic=(ImageView)findViewById(R.id.imageView_pic);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case requestCode_camera:{
			//1
//			if(data!=null){
//				// 选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用
//				Bundle extras = data.getExtras();
//				if (extras != null){
//					//这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
//					bitmap = extras.getParcelable("data");
//					if(bitmap!=null){
//						imageView_pic.setImageBitmap(bitmap);
////						PHOTO_NAME="MyPic"+CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS")+".jpg";
//						button_save.setEnabled(true);
//					}
//				}
//			}
			//2
//			bitmap=CommonUtil.getLocalBitmap(PHOTO_FOLDER+PHOTO_NAME);
//			imageView_pic.setImageBitmap(bitmap);
//			button_save.setEnabled(true);
			//3
//			ImageCompressFactory imageCompressFactory=new ImageCompressFactory();
//			String[] sarr=PHOTO_NAME.split("\\.");
//			String PHOTO_NAME2=sarr[0]+"_2."+sarr[1];
//			try {
//				imageCompressFactory.compressAndGenImage(PHOTO_FOLDER+PHOTO_NAME, PHOTO_FOLDER+PHOTO_NAME2, 500, false);//maxSize设小报错
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			bitmap=imageCompressFactory.getBitmap(PHOTO_FOLDER+PHOTO_NAME2);
//			imageView_pic.setImageBitmap(bitmap);
			//4
//			ImageCompressFactory imageCompressFactory=new ImageCompressFactory();
//			bitmap=imageCompressFactory.ratio(PHOTO_FOLDER+PHOTO_NAME, 480f, 800f);
//			imageView_pic.setImageBitmap(bitmap);
//			button_save.setEnabled(true);
			//5
			String[] sarr=PHOTO_NAME.split("\\.");
			String PHOTO_NAME2=sarr[0]+"_2."+sarr[1];
			decodeFile(new File(PHOTO_FOLDER+PHOTO_NAME), new File(PHOTO_FOLDER+PHOTO_NAME2), 600, 75);
			bitmap=CommonUtil.getLocalBitmap(PHOTO_FOLDER+PHOTO_NAME2);
			imageView_pic.setImageBitmap(bitmap);
			
			break;
		}
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_camera:{
			//1
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//2
			PHOTO_NAME="MyPic"+CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS")+".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PHOTO_FOLDER+PHOTO_NAME)));
			
			startActivityForResult(intent, requestCode_camera);
			break;
		}
		case R.id.button_save:{
			//压缩
			ImageCompressFactory imageCompressFactory=new ImageCompressFactory();
			
			//onActivityResult-2
			//1
			bitmap=imageCompressFactory.scale(bitmap, 200);
			//2
//			bitmap=imageCompressFactory.ratio(bitmap, 480, 800);
			//3
//			bitmap=imageCompressFactory.compress(bitmap);
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			try {
				FileOutputStream fos = new FileOutputStream(PHOTO_FOLDER+PHOTO_NAME);
				baos.writeTo(fos);
				CommonUtil.ToastMsg(this, "图片保存在"+PHOTO_FOLDER+PHOTO_NAME, Gravity.CENTER);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//onActivityResult-2
//			try {
//				imageCompressFactory.compressAndGenImage(bitmap, PHOTO_FOLDER+PHOTO_NAME, 200);
//				CommonUtil.ToastMsg(this, "图片保存在"+PHOTO_FOLDER+PHOTO_NAME, Gravity.CENTER);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			//onActivityResult-4
//			try {
//				imageCompressFactory.storeImage(bitmap, PHOTO_FOLDER+PHOTO_NAME);
//				CommonUtil.ToastMsg(this, "图片保存在"+PHOTO_FOLDER+PHOTO_NAME, Gravity.CENTER);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			break;
		}
		}
	}
	
	private boolean decodeFile(File f_src,File f_output,int needsize,int quality){
    	boolean resultflag=false;
    	try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f_src);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/needsize;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f_src);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            FileOutputStream fout = new FileOutputStream(f_output);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fout);//质量压缩
            fout.flush();  
            fout.close(); 
            bitmap.recycle();
            System.gc();
            resultflag = true;
        } catch (FileNotFoundException e) {
        	 resultflag = false;
        }catch (IOException e) {
            e.printStackTrace();
            resultflag = false;
        }
    	return resultflag;
    }


}
