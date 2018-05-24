package com.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常详情
 */
public class ExceptionDetail {
	
	/**
	 * 获取异常出错信息
	 * @param e
	 */
	public static String getErrorMessage(Exception e){
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return String.format("异常信息:%s",s.toString());
	}

	/**
	 * 获取异常出错信息
	 * @param e
	 * @param notic 出错来源注明
	 */
	public static String getErrorMessage(Exception e,String notic){
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return String.format("异常信息:%s，出错内容为：%s",s.toString(),notic);
	}

}