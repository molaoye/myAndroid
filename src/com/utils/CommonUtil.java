package com.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CommonUtil {
	
	/**
	 * 验证字段值是否为空
	 * @param fieldsName 字段名(与字段值对应)
	 * @param fieldsValue 字段值(与字段名对应)
	 * @return 都不为空返回""
	 */
	public static String validateFieldsValueBlank(String[] fieldsName,String...fieldsValue){
		String msg="";
		for(int i=0;i<fieldsValue.length;i++){
			if(fieldsValue[i].trim().equals("")){
				if(i==fieldsValue.length-1){
					msg+=fieldsName[i]+"不能为空";
				}else{
					msg+=fieldsName[i]+"不能为空\n";
				}
			}
		}
		return msg;
	}
	
	/**
	 * 重设EditText为“”
	 * @param editTexts
	 */
	public static void editTextReset(EditText...editTexts){
		for(int i=0;i<editTexts.length;i++){
			editTexts[i].setText("");
		}
	}
	
	/**
	 * 重设TextView(包含EditText)为“”
	 */
	public static void textViewReset(TextView...textViews){
		for(int i=0;i<textViews.length;i++){
			textViews[i].setText("");
		}
	}
	
	private static String getContent(InputStream is,String charset) throws IOException{
		InputStreamReader isr=new InputStreamReader(is, charset);//gbk//UTF-8
		BufferedReader reader = new BufferedReader(isr);
		String line="";
		String content="";
		while ((line = reader.readLine()) != null) {
            //System.out.println(line);
			content+=line;
        }
//		log.info("content:"+content);
		return content;
	}
	
	/**
	 * 经纬度坐标反解析得到地址
	 * @param coordinate 格式：“lng,lat”
	 * @return 异常map.size()=0,map:lat,lng,street_number,street,district,city,province,business,formatted_address
	 */
	public static Map<String,String> coordinateReverse(String coordinate){
		Map<String,String> m=new HashMap<String,String>();
		String url="http://api.map.baidu.com/geocoder/v2/?ak=YHPfMR1hMGxvCHajVfpyIljf&callback=renderReverse&location=%s&output=json";
		String[] s=coordinate.split(",");
		url=String.format(url, s[1]+","+s[0]);
		try {
			HttpURLConnection conn=getConn(url, "GET");
			conn.connect();
			InputStream is=conn.getInputStream();
			String json=getContent(is,"utf-8");
			json=json.replace("renderReverse&&renderReverse(", "");
			json=json.replace(")", "");
//			json="{\"status\":0,\"result\":{\"location\":{\"lng\":108.35947795311,\"lat\":22.819371830282},\"formatted_address\":\"广西壮族自治区南宁市青秀区民族大道95-3号\",\"business\":\"南湖,民族大道,麻村\",\"addressComponent\":{\"city\":\"南宁市\",\"direction\":\"附近\",\"distance\":\"28\",\"district\":\"青秀区\",\"province\":\"广西壮族自治区\",\"street\":\"民族大道\",\"street_number\":\"95-3号\"},\"poiRegions\":[],\"cityCode\":261}}";
			JSONTokener jSONTokener = new JSONTokener(json);
			JSONObject jSONObject = (JSONObject) jSONTokener.nextValue();
			JSONObject jSONObject_result=jSONObject.getJSONObject("result");
			String formatted_address=jSONObject_result.getString("formatted_address");
			m.put("formatted_address", formatted_address);
			String business=jSONObject_result.getString("business");
			m.put("business", business);
			JSONObject addressComponent=jSONObject_result.getJSONObject("addressComponent");
			String province=addressComponent.getString("province");
			m.put("province", province);
			String city=addressComponent.getString("city");
			m.put("city", city);
			String district=addressComponent.getString("district");
			m.put("district", district);
			String street=addressComponent.getString("street");
			m.put("street", street);
			String street_number=addressComponent.getString("street_number");
			m.put("street_number", street_number);
			JSONObject loc=jSONObject_result.getJSONObject("location");
			String lng=loc.getString("lng");
			m.put("lng", lng);
			String lat=loc.getString("lat");
			m.put("lat", lat);
		} catch (Exception e) {
//			log.info("地址反解析", e);
		}
		return m;
	}
	
	/**
	 * 百度地图坐标转换
	 * @param coordinate 格式：“lng,lat”
	 * @param from 3=谷歌转百度,1=gps转百度
	 * @return 异常map.size()=0,map:lat,lng
	 */
	public static Map<String,String> coordinateConvert(String coordinate,String from){
		Map<String,String> map=new HashMap<String,String>();
		String url="http://api.map.baidu.com/geoconv/v1/?coords=%s&from=%s&output=json&ak=YHPfMR1hMGxvCHajVfpyIljf";
		url=String.format(url, coordinate,from);
		try {
			HttpURLConnection conn=getConn(url, "GET");
			conn.connect();
			InputStream is=conn.getInputStream();
			String json=getContent(is,"utf-8");
			JSONTokener jSONTokener = new JSONTokener(json);
			JSONObject jSONObject = (JSONObject) jSONTokener.nextValue();
			JSONArray jSONArray=jSONObject.getJSONArray("result");
			String x="";
			String y="";
			for(int i=0;i<jSONArray.length();i++){
				JSONObject jSONObject_coordinate=(JSONObject) jSONArray.opt(i);
				x=jSONObject_coordinate.getString("x");
				y=jSONObject_coordinate.getString("y");
				map.put("lng", x);
				map.put("lat", y);
			}
		} catch (Exception e) {
//			log.info("百度地图坐标转换", e);
		}
		return map;
	}
	
	/**
	 * 取得HttpURLConnection对象，已setDoInput(true)
	 * @param url
	 * @param methodType POST,GET
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConn(String url,String methodType) throws IOException{
		URL url_ = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) url_.openConnection();
		conn.setDoInput(true);
		conn.setRequestMethod(methodType);
		if(methodType.equals("POST")){
//			log.info("methodType:"+methodType);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
		}
//		log.info("url:"+url);
		return conn;
	}
	
	/**
	 * object转long
	 * @param o
	 * @return
	 */
	public static Long objectToLong(Object o){
		long l = Long.valueOf(String.valueOf(o)).longValue();
		return l;
	}
	
	/**
	 * 获取当前时间
	 * @param format 日期格式
	 * <br>
	 * G Era 标志符 Text AD 
	 * <br>
	* 	y 年 Year 1996; 96  
	 * <br>
	* 	M 年中的月份 Month July; Jul; 07  
	 * <br>
	* 	w 年中的周数 Number 27 
	 * <br>
	* 	W 月份中的周数 Number 2 
	 * <br>
	* 	D 年中的天数 Number 189 
	 * <br>
	* 	d 月份中的天数 Number 10 
	 * <br>
	* 	F 月份中的星期 Number 2 
	 * <br>
	* 	E 星期中的天数 Text Tuesday; Tue  
	 * <br>
	* 	a Am/pm 标记 Text PM 
	 * <br>
	* 	H 一天中的小时数（0-23） Number 0 
	 * <br>
	* 	k 一天中的小时数（1-24） Number 24 
	 * <br>
	* 	K am/pm 中的小时数（0-11） Number 0 
	 * <br>
	* 	h am/pm 中的小时数（1-12） Number 12 
	 * <br>
	* 	m 小时中的分钟数 Number 30 
	 * <br>
	* 	s 分钟中的秒数 Number 55 
	 * <br>
	* 	S 毫秒数 Number 978 
	 * <br>
	 * z 时区 General time zone Pacific Standard 
	 * <br>
	 * Z 时区 RFC 822 time zone -0800 Time; PST; GMT-08:00  
	 * @return 返回传入格式的当前日期
	 */
	public static String getCurrentTime(String format){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 运行内存
	 */
	public static void RuntimeMemory(){
		//应用程序最大可用内存  
		float maxMemory = (Runtime.getRuntime().maxMemory())/1024/1024;  
	    //应用程序已获得内存  
		float totalMemory = (Runtime.getRuntime().totalMemory())/1024/1024;  
	    //应用程序已获得内存中未使用内存  
		float freeMemory = (Runtime.getRuntime().freeMemory())/1024/1024;  
	    System.out.println("---> maxMemory="+maxMemory+"M,totalMemory="+totalMemory+"M,freeMemory="+freeMemory+"M");
		
	}
	
	/**
	* 从服务器取图片
	* @param url
	* @return 异常返回null
	*/
	public static Bitmap getHttpBitmap(String url) {
		//1
//	     URL myFileUrl = null;
//	     Bitmap bitmap = null;
//	     try {
////	          Log.d(TAG, url);
//	          myFileUrl = new URL(url);
//	     } catch (MalformedURLException e) {
//	          System.out.println(ExceptionDetail.getErrorMessage(e));
//	     }
//	     try {
//	          HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
////	          conn.setConnectTimeout(0);
//	          conn.setConnectTimeout(6000);
//	          conn.setDoInput(true);
////	          conn.setUseCaches(false);
//	          conn.connect();
//	          InputStream is = conn.getInputStream();
//	          bitmap = BitmapFactory.decodeStream(is);
//	          is.close();
//	     } catch (IOException e) {
//	    	 System.out.println(ExceptionDetail.getErrorMessage(e));
//	     }
//	     return bitmap;
	     //2
	     return getHttpBitmap(url, null);
	}
	
	/**
	* 从服务器取图片
	* @param url
	* @return 异常返回null
	*/
	public static Bitmap getHttpBitmap(String url,File file) {
	     URL myFileUrl = null;
	     Bitmap bitmap = null;
	     try {
//	          Log.d(TAG, url);
	          myFileUrl = new URL(url);
	     } catch (MalformedURLException e) {
	          System.out.println(ExceptionDetail.getErrorMessage(e));
	     }
	     try {
	          HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//	          conn.setConnectTimeout(0);
	          conn.setConnectTimeout(6000);
	          conn.setDoInput(true);
//	          conn.setUseCaches(false);
	          conn.connect();
	          InputStream is = conn.getInputStream();
	          
	          //测试InputStream长度，和服务器一样
//	          byte[] buf = new byte[1024];
//	          int byte_total=buf.length;
//	          int i=is.read(buf);
//	          while (i!=-1) {
//					byte_total+=i;
//					i=is.read(buf);
//	          }
//	          System.out.println("getHttpBitmap InputStream byte len:"+byte_total);
	          
	          bitmap=resizeBitmap(is, 600, 75, file);//大小质量压缩
	          
	          is.close();
	     } catch (IOException e) {
	    	 System.out.println(ExceptionDetail.getErrorMessage(e));
	     }
	     return bitmap;
	}
	
	/**
	* 加载本地图片
	* @param filePath
	* @return 异常返回null
	*/
	public static Bitmap getLocalBitmap(String filePath) {
		//1
//		try {
//			FileInputStream fis = new FileInputStream(filePath);
//			return BitmapFactory.decodeStream(fis);
//		} catch (FileNotFoundException e) {
//			System.out.println(ExceptionDetail.getErrorMessage(e));
//			return null;
//		}
		//2
		return getBmpFromImgPath(filePath);
	}
	
	/**
	 * 保存bmp文件到本地
	 * @param bmp
	 * @param filePath
	 * @param quality
	 * @param format jpg,png
	 * @throws IOException
	 */
	public static void saveBmpToLocal(Bitmap bmp,String filePath,int quality,String format) throws IOException{
		OutputStream outStream = new FileOutputStream(filePath);
		if(format.equals("jpg")){
			bmp.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
		}else if(format.equals("png")){
			bmp.compress(Bitmap.CompressFormat.PNG, quality, outStream);
		}
		outStream.close();
	}
	
	/**
	 * Toast信息
	 * @param context
	 * @param msg
	 * @param gravity Gravity类静态的方位属性,如Gravity.BOTTOM，Gravity.CENTER。若==0，则为默认位置
	 */
	public static void ToastMsg(Context context,String msg,int gravity){
//		int a=Gravity.BOTTOM;
		Toast toast = Toast.makeText(context ,msg, Toast.LENGTH_LONG);
		if(gravity!=0){
			toast.setGravity(gravity, 0, 0);
		}
		toast.show();
	}
	
	/**
	 * bmp调整尺寸，可用于大小质量压缩，可保存图片
	 * @param file
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static byte[] resizeBitmap(File file,int needsize,int quality){
    	byte[] bitmapByte = null;
    	int DEFAULT=-1;
    	int QUALITY=80;
    	int SIZE=600;
    	if(needsize!=DEFAULT){
    		SIZE=needsize;
    	}
    	if(quality!=DEFAULT){
    		QUALITY = quality;
    	}
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis=new FileInputStream(file);
            BitmapFactory.decodeStream(fis,null,o);
            fis.close();
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/SIZE;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream fis2=new FileInputStream(file);
            Bitmap bitmap=BitmapFactory.decodeStream(fis2, null, o2);
            fis2.close();
            
            //1
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, fos);//质量压缩
//            fos.flush();  
//            fos.close(); 
//            
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);  
//            bitmapByte=baos.toByteArray();
            //2
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);//质量压缩
            bitmapByte=baos.toByteArray();
            System.out.println("bmp byte len:"+bitmapByte.length);
			FileOutputStream fos = new FileOutputStream(file);
			baos.writeTo(fos);
			
            baos.flush();
            baos.close();
            
            bitmap.recycle();
//            System.gc();
        } catch (Exception e) {
        	System.out.println(ExceptionDetail.getErrorMessage(e));
        }
        return bitmapByte;
    }
    
    /**
	 * bmp调整尺寸，可用于大小质量压缩
	 * @param is
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static Bitmap resizeBitmap(InputStream is,int needsize,int quality){
    	//1
//    	Bitmap bmp = null;
//    	int DEFAULT=-1;
//    	int QUALITY=80;
//    	int SIZE=600;
//    	if(needsize!=DEFAULT){
//    		SIZE=needsize;
//    	}
//    	if(quality!=DEFAULT){
//    		QUALITY = quality;
//    	}
//        try {
//        	bmp=BitmapFactory.decodeStream(is);
//            int width_tmp=bmp.getWidth(), height_tmp=bmp.getHeight();
//            
//            int scale=1;
//            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
//            scale = cal/SIZE;
//            //decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize=scale;
//            is=bitmap2InputStream(bmp);
//            bmp=BitmapFactory.decodeStream(is, null, o2);
//            
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
//            System.out.println("bmp byte len:"+baos.toByteArray().length);
//            
//            baos.flush();
//            baos.close();
//            is.close();
//        } catch (Exception e) {
//        	System.out.println(ExceptionDetail.getErrorMessage(e));
//        }
//        return bmp;
    	//2
    	return resizeBitmap(is, needsize, quality, null);
    }
    
    /**
	 * bmp调整尺寸，可用于大小质量压缩
	 * @param is
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static byte[] resizeBitmap(InputStream is,int needsize,int quality,Object o){
    	byte[] bitmapByte = null;
    	int DEFAULT=-1;
    	int QUALITY=80;
    	int SIZE=600;
    	if(needsize!=DEFAULT){
    		SIZE=needsize;
    	}
    	if(quality!=DEFAULT){
    		QUALITY = quality;
    	}
        try {
        	Bitmap bmp=BitmapFactory.decodeStream(is);
            int width_tmp=bmp.getWidth(), height_tmp=bmp.getHeight();
            
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/SIZE;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            is=bitmap2InputStream(bmp);
            bmp=BitmapFactory.decodeStream(is, null, o2);
            
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
            bitmapByte=baos.toByteArray();
            System.out.println("bmp byte len:"+bitmapByte.length);
            
            baos.flush();
            baos.close();
            is.close();
            bmp.recycle();
        } catch (Exception e) {
        	System.out.println(ExceptionDetail.getErrorMessage(e));
        }
        return bitmapByte;
    }
    
    /**
	 * bmp调整尺寸，可用于大小质量压缩
	 * @param is
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static Bitmap resizeBitmap(InputStream is,int needsize,int quality,File file){
    	Bitmap bmp = null;
    	int DEFAULT=-1;
    	int QUALITY=80;
    	int SIZE=600;
    	if(needsize!=DEFAULT){
    		SIZE=needsize;
    	}
    	if(quality!=DEFAULT){
    		QUALITY = quality;
    	}
        try {
        	bmp=BitmapFactory.decodeStream(is);
            int width_tmp=bmp.getWidth(), height_tmp=bmp.getHeight();
            
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/SIZE;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            is=bitmap2InputStream(bmp);
            bmp=BitmapFactory.decodeStream(is, null, o2);
            
            //1
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, fos);//质量压缩
//            fos.flush();
//            fos.close();
//            
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
//            System.out.println("bmp byte len:"+baos.toByteArray().length);
            //2
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);//质量压缩
            System.out.println("bmp byte len:"+baos.toByteArray().length);
            if(file!=null){
				FileOutputStream fos = new FileOutputStream(file);
				baos.writeTo(fos);
            }
			
            baos.flush();
            baos.close();
            is.close();
        } catch (Exception e) {
        	System.out.println(ExceptionDetail.getErrorMessage(e));
        }
        return bmp;
    }
    
    /**
	 * bmp调整尺寸，可用于大小质量压缩
	 * @param bmp
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static Bitmap resizeBitmap(Bitmap bmp,int needsize,int quality,File file){
    	Bitmap bmp_new=null;
    	int DEFAULT=-1;
    	int QUALITY=80;
    	int SIZE=600;
    	if(needsize!=DEFAULT){
    		SIZE=needsize;
    	}
    	if(quality!=DEFAULT){
    		QUALITY = quality;
    	}
        try {
        	int width_tmp=bmp.getWidth(), height_tmp=bmp.getHeight();
            
            int scale=1;
            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
            scale = cal/SIZE;
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            bmp_new=BitmapFactory.decodeStream(bitmap2InputStream(bmp), null, o2);
            
            //1
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp_new.compress(Bitmap.CompressFormat.JPEG, QUALITY, fos);//质量压缩
//            fos.flush();  
//            fos.close(); 
//            
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bmp_new.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
//            System.out.println("bmp byte len:"+baos.toByteArray().length);
            //2
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);//质量压缩
            System.out.println("bmp byte len:"+baos.toByteArray().length);
            if(file!=null){
            	FileOutputStream fos = new FileOutputStream(file);
            	baos.writeTo(fos);
            }
			
            baos.flush();
            baos.close();
        } catch (Exception e) {
        	System.out.println(ExceptionDetail.getErrorMessage(e));
        } finally{
        	bmp.recycle();
        }
        return bmp_new;
    }
    
    /**
	 * bmp调整尺寸，可用于大小质量压缩
	 * @param bmp
	 * @param needsize 最小宽或者高，一般为600（默认为请填-1）
	 * @param quality  质量压缩比，一般为75（默认为请填-1）
	 * @return 异常返回null
	 */
    public static Bitmap resizeBitmap(Bitmap bmp,int needsize,int quality){
    	//1
//    	Bitmap bmp_new=null;
//    	int DEFAULT=-1;
//    	int QUALITY=80;
//    	int SIZE=600;
//    	if(needsize!=DEFAULT){
//    		SIZE=needsize;
//    	}
//    	if(quality!=DEFAULT){
//    		QUALITY = quality;
//    	}
//        try {
//        	int width_tmp=bmp.getWidth(), height_tmp=bmp.getHeight();
//            
//            int scale=1;
//            final int cal = width_tmp>height_tmp?height_tmp:width_tmp;
//            scale = cal/SIZE;
//            //decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize=scale;
//            bmp_new=BitmapFactory.decodeStream(bitmap2InputStream(bmp), null, o2); 
//            
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bmp_new.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
//            System.out.println("bmp byte len:"+baos.toByteArray().length);
//            
//            baos.flush();
//            baos.close();
//        } catch (Exception e) {
//        	System.out.println(ExceptionDetail.getErrorMessage(e));
//        } finally{
//        	bmp.recycle();
//        }
//        return bmp_new;
    	//2
    	return resizeBitmap(bmp, needsize, quality, null);
    }
    
    /**
     * 获取Bmp来自图片路径
     * @param filePath
     * @return
     */
    private static Bitmap getBmpFromImgPath(String filePath){
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;
	}
    
    /**
     * bmp调整尺寸，可用于大小压缩
     * @param bitmap
     * @param newWidth 一般为640
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth) { 
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
    
    /**
	 * 将Bitmap转换成InputStream
	 * @param bm
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		
		System.out.println("bitmap2InputStream bmp byte len:"+baos.toByteArray().length);
		return is;
	}
	
	/**
	 * 根据值, 设置spinner默认选中:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter spinnerAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
	    int k=spinnerAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(spinnerAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// 默认选中项
				break;
			}
		}
	}
	

}
