package com.android.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	private static ImageUtil instance;
	private boolean resultflag;
	private int REQUIRED_SIZE=600;
	private int QUALITY=80;
	public static int DEFAULT=-1;
	public static ImageUtil getInstance() {
		if(instance==null)instance = new ImageUtil();
		return instance;
	}

	/**
	 * 
	 * @param f
	 * @param needsize 最小宽或者高（默认为请填-1）
	 * @param quality  质量压缩比（默认为请填-1）
	 * @return
	 */
    @SuppressWarnings("finally")
	public boolean decodeFile(File f,int needsize,int quality){
    	resultflag=false;
    	if(needsize!=DEFAULT){
    		this.REQUIRED_SIZE=needsize;
    	}
    	if(quality!=DEFAULT){
    		this.QUALITY = quality;
    	}
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/REQUIRED_SIZE;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            FileOutputStream fout = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, fout);//质量压缩
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
        }finally{
        	 return resultflag;
        }
    }
    public Bitmap getbitmap(File f,int needsize){
    	if(needsize==DEFAULT){
    		this.REQUIRED_SIZE=70;
    	}else{
    		this.REQUIRED_SIZE=needsize;
    	}
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/REQUIRED_SIZE;
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Bitmap getbitmap(String path,int needsize){
    	if(needsize==DEFAULT){
    		this.REQUIRED_SIZE=70;
    	}else{
    		this.REQUIRED_SIZE=needsize;
    	}
        try {
        	File f=new File(path);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/REQUIRED_SIZE;
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void clear(){
    	instance = null;
    }
}
