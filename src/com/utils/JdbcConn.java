package com.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcConn {

	/**
	 * 获取连接实例
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Connection getConn() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		return getInstance();
	}
	
	private static Connection conn;
	
	private static Connection getInstance() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		if(conn==null){
			String driverClassName="net.sourceforge.jtds.jdbc.Driver";
			String url="jdbc:jtds:sqlserver://172.10.0.102:1433/GlwlfbDb";
			String username="sa";
			String password="sa";
			Class.forName(driverClassName).newInstance();
			conn = DriverManager.getConnection(url,username,password);
		}
		return conn;
	}
	
	/**
	 * 测试连接
	 * @return
	 */
	public static boolean testConnection(){
		Connection conn = null;
		try {
			conn = getConn();
		} catch (Exception e) {
			System.out.println(ExceptionDetail.getErrorMessage(e));
		}
		if(conn==null){
			return false;
		}else{
			return true;
		}
	}
	
}
