package com.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * �쳣����
 */
public class ExceptionDetail {
	
	/**
	 * ��ȡ�쳣������Ϣ
	 * @param e
	 */
	public static String getErrorMessage(Exception e){
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return String.format("�쳣��Ϣ:%s",s.toString());
	}

	/**
	 * ��ȡ�쳣������Ϣ
	 * @param e
	 * @param notic ������Դע��
	 */
	public static String getErrorMessage(Exception e,String notic){
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return String.format("�쳣��Ϣ:%s����������Ϊ��%s",s.toString(),notic);
	}

}