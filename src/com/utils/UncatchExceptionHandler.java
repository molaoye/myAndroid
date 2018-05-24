package com.utils;

import android.content.Context;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.PrintWriter;  
import java.io.StringWriter;  
import java.io.Writer;  
import java.lang.Thread.UncaughtExceptionHandler;  
import java.lang.reflect.Field;  
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.List;
import java.util.Map;

import com.android.ActivityManage;
import com.android.Config;
  
import android.content.pm.PackageInfo;  
import android.content.pm.PackageManager;  
import android.content.pm.PackageManager.NameNotFoundException;  
import android.os.Build;  
import android.os.Environment;  
import android.os.Looper;  
import android.os.Message;
import android.util.Log;  
import android.widget.Toast;  

public class UncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
	public static final String TAG = "UncatchExceptionHandler";  
    
    //系统默认的UncaughtException处理类   
	
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
   
    //MyappsExceptionHandler实例  
    private static UncatchExceptionHandler INSTANCE = new UncatchExceptionHandler();  
    
    private Context mContext; 
    
    //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();  
  
    //用于格式化日期,作为日志文件名的一部分  
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static final String FILENAME = "%s(%s).log";//"errorReportShow.log";
    
//    private static final String ERRORDIR = Config.AppFolder;  
    private static String ERRORDIR = "";
  
    /** 保证只有一个CrashHandler实例 */  
    private UncatchExceptionHandler() {
    	
    }  
  
    /** 获取CrashHandler实例 ,单例模式 */  
    public static UncatchExceptionHandler getInstance() {
    	if(INSTANCE==null){
    		INSTANCE = new UncatchExceptionHandler();
    	}
        return INSTANCE;  
    }  
    
    private void initAppFolder(){
		try{
//			String path_root=Config.dir_externalStorage;
			String path_AppRoot=Config.AppFolder;//path_root+Config.AppRoot;
//			String path_AppRoot_PicFolder=path_AppRoot+Config.PicFolder;
//			String path_AppRoot_ErrorFolder=path_AppRoot+Config.ErrorFolder;
//			String path_AppRoot_ApkFolder=path_AppRoot+Config.ApkFolder;
			File file=new File(path_AppRoot);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
//			file=new File(path_AppRoot_PicFolder);
//			if (!file.exists() && !file.isDirectory()) {
//				file.mkdir();
//			}
//			file=new File(path_AppRoot_ErrorFolder);
//			if (!file.exists() && !file.isDirectory()) {
//				file.mkdir();
//			}
//			file=new File(path_AppRoot_ApkFolder);
//			if (!file.exists() && !file.isDirectory()) {
//				file.mkdir();
//			}
			Log.i("", "初始化app文件夹完成");
		}catch(Exception e){
			Log.e("", "初始化app文件夹出错", e);
		}
	}
  
    /** 
     * 初始化 
     *  
     * @param context 
     */  
    public void init(Context context) {  
    	mContext = context;  
        //获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this); 
        
        initAppFolder();
    	
        ERRORDIR = Config.AppFolder;
    }  
  
    /** 
     * 当UncaughtException发生时会转入该函数来处理 
     */  
      
    public void uncaughtException(Thread thread, Throwable ex) {  
        if (!handleException(ex) && mDefaultHandler != null) {  
            //如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  
            try {
            	Log.i("", "before exit DT:"+CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss.SSS"));
                Thread.sleep(Config.uncaughtExceptionNoticeDuration);
            } catch (InterruptedException e) {  
                Log.e(TAG, "error : ", e);  
            }
            Log.i("", "exit DT:"+CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss.SSS"));
            //退出程序
            ActivityManage.getInstance().killAllActivity();
            System.exit(1);
            int myPid=android.os.Process.myPid();
            Log.i("", "myPid:"+myPid);
            android.os.Process.killProcess(myPid);
            
        }  
    }  
  
    /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息;否则返回false. 
     */
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        //使用Toast来显示异常信息  
        new Thread() {
            public void run() {  
                Looper.prepare();  
                Toast.makeText(mContext, "程序出错，正在退出...", Toast.LENGTH_LONG).show();
                Looper.loop();  
            }  
        }.start();
        //收集设备参数信息   
        collectDeviceInfo(mContext);  
        //保存日志文件   
        saveExecptionInfo2File(ex);
        
//        Log.e("", "", ex);
        return true;
    }  
      
    /** 
     * 收集设备参数信息 
     * @param ctx 
     */  
    public void collectDeviceInfo(Context ctx) {  
        try {  
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                String versionName = pi.versionName == null ?"0.0" : pi.versionName;  
                infos.put("versionName", versionName);  
            }  
        } catch (NameNotFoundException e) {  
            Log.e(TAG, "an error occured when collect package info", e);  
        }  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {
                field.setAccessible(true);
                if(field.getName() != null && (field.getName().equals("CPU_ABI") || field.getName().equals("FINGERPRINT"))){
	                infos.put(field.getName(), field.get(null).toString());  
//	                Log.i(TAG, field.getName() + " : " + field.get(null));
                }
            } catch (Exception e) {  
                Log.e(TAG, "an error occured when collect crash info", e);  
            }  
        }  
    }  
  
    /** 
     * 保存错误信息到文件中 
     *  
     * @param ex 
     * @return  返回文件名称,便于将文件传送到服务器 
     */  
    private String saveExecptionInfo2File(Throwable ex) {  
    	String status=Environment.getExternalStorageState();
		if(status.equals(Environment.MEDIA_MOUNTED)){//判断是否有SD卡
	        StringBuffer sb = new StringBuffer();
	        String time = formatter.format(new Date());
	        sb.append("show error Date=" + time +"\n");
	        for (Map.Entry<String, String> entry : infos.entrySet()) {  
	            String key = entry.getKey();  
	            String value = entry.getValue();  
	            sb.append(key + "=" + value + "\n");  
	        }  
	        sb.append("\n");
	        Writer writer = new StringWriter();  
	        PrintWriter printWriter = new PrintWriter(writer);  
	        ex.printStackTrace(printWriter);  
	        Throwable cause = ex.getCause();  
	        while (cause != null) {  
	            cause.printStackTrace(printWriter);  
	            cause = cause.getCause();  
	        }  
	        printWriter.close();  
	        String result = writer.toString();  
	        sb.append(result);
	        
	        //增加输出文档的间隔符号，区分不同报错
	        sb.append("\n\n");
	        sb.append("****************************************");
	        sb.append("\n\n");
	        
	        Log.e("", sb.toString());
	        
	        String fileName=ex.getMessage();
            String fileName_replace="";
	        try {  
	            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
	                if(fileName==null){
	                	if(ex.getCause()==null){
	                		fileName=ex.toString();
	                	}else{
	                		fileName=ex.getCause().getMessage();
	                	}
	                }
	                if(fileName==null){
	                	fileName="unknown error";
	                }else{
		            	if(fileName.length()>"quality must be 0..100".length()){
		            		fileName=fileName.substring(0, "quality must be 0..100".length());
		            	}
	            	}
		            writeFile(ERRORDIR+String.format(FILENAME, fileName, time), sb.toString());
		            
		            //保存到数据库
		            //参照ExceptionReport.java里DetailsModelAdd，或ClsGetDataModelList.java
//		            new AddExceptionData(StaticVar.domain_url + "addFromInfo");
				}else{
					Toast.makeText(mContext, "sd卡不存在,无法生成错误报告", Toast.LENGTH_LONG).show();
				}
	            return FILENAME;
	        } catch (Exception e) {
	            Log.e("", "", e);
	            try{
	            	fileName_replace=fileNameReplace(fileName);
		            writeFile(ERRORDIR+String.format(FILENAME, fileName_replace, time.replaceAll(":", "_")), sb.toString());
	            }catch(Exception e2){
	            	Log.e("", "", e2);
	            	try{
	            		sb.append("\n\n");
	            		sb.append("append-----------------");
	            		sb.append("\n\n");
	            		sb.append("generated fileName:"+fileName_replace);
			            writeFile(ERRORDIR+String.format(FILENAME, "fileName generate error", time.replaceAll(":", "_")), 
			            		sb.toString());
	            	}catch(Exception e3){
	            		Log.e("", "", e3);
	            	}
	            }
	        }
		}
		return null;
    }
    
    /**
     * @return 替换成"_"
     */
    private String fileNameReplace(String str){
    	String[] ss={":", "\"",/* "\\",*/ "/",/* "*", "?",*/ "<", ">", "|"};
    	for(String s:ss){
    		try{
    			if(s.equals("|")){
    				str=str.replaceAll(s, "");
    			}else{
    				str=str.replaceAll(s, "_");
    			}
    		}catch(Exception e){
    			Log.e("", "", e);
    			try{
		            writeFile(ERRORDIR+String.format(FILENAME, "fileName replace error", formatter.format(new Date()).replaceAll(":", "_")), 
		            		"fileName replace error:"+s+"");
    			}catch(Exception e2){
    				Log.e("", "", e2);
    			}
    		}
    	}
    	return str;
    }
    
    private void writeFile(String fileName, String content) throws IOException{
    	FileOutputStream fos = new FileOutputStream(fileName, true);
        fos.write(content.getBytes());
        fos.close();
    }
    
    
}
