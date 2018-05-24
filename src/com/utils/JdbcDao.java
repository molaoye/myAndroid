package com.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JdbcDao {

	private Connection conn;

	public JdbcDao() throws DaoException {
		try {
			this.conn = JdbcConn.getConn();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JdbcDao(Connection conn) {
		this.conn = conn;
	}

	public void begin() throws DaoException {
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new DaoException("can not begin transaction", e);
			}
		} else {
			throw new DaoException("connection not opened!");
		}
	}

	public void commit() throws DaoException {
		try {
			if (conn != null && !conn.getAutoCommit()) {
				conn.commit();
				conn.setAutoCommit(true);
			} else {
				if (conn == null) {
					throw new DaoException("connection not opened!");
				} else {
					throw new DaoException("first begin then commit please!");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("can not commit transaction!", e);
		}
	}

	public void rollback() throws DaoException {
		try {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
				conn.setAutoCommit(true);
			} else {
				if (conn == null) {
					throw new DaoException("connection not opened!");
				} else {
					throw new DaoException("first begin then rollback please!");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("can not rollback transaction!", e);
		}
	}

	private List<Map> convert(ResultSet rs) throws DaoException {
		// record list
		List<Map> retList = new ArrayList<Map>();
		try {
			ResultSetMetaData meta = rs.getMetaData();
			// column count
			int colCount = meta.getColumnCount();
			// each record
			while (rs.next()) {
				Map<String,Object> recordMap = new HashMap<String,Object>();
				// each column
				for (int i = 1; i <= colCount; i++) {
					// column name
					String name = meta.getColumnName(i);
					//字段名重复，后面重复的字段名加序号
					int a=2;
					while(true){
						if(recordMap.containsKey(name)){
							name=name+String.valueOf(a);
							a++;
						}else{
							break;
						}
					}
					// column value
					Object value = rs.getObject(i);
					// add column to record
					recordMap.put(name, value);
				}
				// ad record to list
				retList.add(recordMap);
			}
		} catch (SQLException ex) {
			throw new DaoException("can not convert result set to list of map", ex);
		}
		return retList;
	}

	private void apply(PreparedStatement pstmt, List params)
			throws DaoException {
		try {
			// if params exist
			if (params != null && params.size() > 0) {
				// parameters iterator
				Iterator it = params.iterator();

				// parameter index
				int index = 1;
				while (it.hasNext()) {

					Object obj = it.next();
					// if null set ""
					if (obj == null) {
						pstmt.setObject(index, "");
					} else {
						// else set object
						pstmt.setObject(index, obj);
					}

					// next index
					index++;
				}
			}
		} catch (SQLException ex) {
			throw new DaoException("can not apply parameter", ex);
		}
	}

	/**
	 * 查询方法
	 * @param sql
	 * @param params
	 * @return 查询不到list.size()=0
	 * @throws DaoException
	 */
	public List<Map> query(String sql, List params) throws DaoException {
		System.out.println("查询sql语句："+sql);
		List<Map> result = new ArrayList<Map>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			this.apply(pstmt, params);
			rs = pstmt.executeQuery();
			result = this.convert(rs);
		} catch (SQLException ex) {
			throw new DaoException("can not execute query", ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// nothing
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// nothing
				}
			}
		}
		return result;
	}

	/**
	 * 查询方法（返回单值）
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public Object queryOne(String sql, List params) throws DaoException {
		List<Map> list = this.query(sql, params);
		if (list == null || list.size() == 0) {
			throw new DaoException("data not exist");
		} else {
			Map record = list.get(0);
			if (record == null || record.size() == 0) {
				throw new DaoException("data not exist");
			} else {
				return record.values().toArray()[0];
			}
		}
	}

	/**
	 * 更新，删除，插入
	 * @param sql
	 * @param params
	 * @return 返回执行的条数，执行失败返回0
	 * @throws DaoException
	 */
	public int execute(String sql, List params) throws DaoException {
		System.out.println("执行sql语句："+sql);
		int ret = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			this.apply(pstmt, params);
			ret = pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DaoException("", ex);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// nothing.
				}
			}
		}

		return ret;
	}

	/**
	 * 批处理方法（查询）
	 * @param sqlArray
	 * @param paramArray
	 * @return
	 * @throws DaoException
	 */
	public List[] queryBatch(String[] sqlArray, List[] paramArray)
			throws DaoException {
		List rets = new ArrayList();
		if (sqlArray.length != paramArray.length) {
			throw new DaoException("sql size not equal parameter size");
		} else {
			for (int i = 0; i < sqlArray.length; i++) {
				String sql = sqlArray[i];
				List param = paramArray[i];
				List ret = this.query(sql, param);
				rets.add(ret);
			}
			return (List[]) rets.toArray();
		}
	}

	/**
	 * 批处理方法（更新，删除，插入）
	 * @param sqlArray
	 * @param paramArray
	 * @return
	 * @throws DaoException
	 */
	private int[] executeBatch_old(String[] sqlArray, List[] paramArray) throws DaoException {
		List rets = new ArrayList();
		if (sqlArray.length != paramArray.length) {
			throw new DaoException("sql size not equal parameter size");
		} else {
			for (int i = 0; i < sqlArray.length; i++) {
				int ret = this.execute(sqlArray[i], paramArray[i]);
				rets.add(new Integer(ret));
			}

			int[] retArray = new int[rets.size()];
			for (int i = 0; i < retArray.length; i++) {
				retArray[i] = ((Integer) rets.get(i)).intValue();
			}

			return retArray;
		}
	}
	
	/**
	 * 批处理执行（更新，删除，插入）
	 * @param sqlArray
	 * @param paramArray
	 * @return 执行失败返回null
	 * @throws DaoException
	 */
	public int[] executeBatch(List<String> l_sql) throws DaoException {
		int[] a = null;
		if (l_sql.size() == 0) {
			throw new DaoException("sql size equal zero");
		} else {
			try {
				// 保存当前自动提交模式
				boolean autoCommit = conn.getAutoCommit();
				// 关闭自动提交
				conn.setAutoCommit(false);
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < l_sql.size(); i++) {
					String sql = l_sql.get(i);
					System.out.println(String.format("批处理执行第%d条语句:%s",i,sql));
					stmt.addBatch(sql);
				}
				// 同时提交所有的sql语句
				a = stmt.executeBatch();
				// 提交修改
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} catch (Exception e) {
				System.out.println(ExceptionDetail.getErrorMessage(e));
				rollback();
			}
		}
		return a;
	}

	public void close() throws DaoException {
		try {
			if (conn != null && conn.getAutoCommit()) {
				conn.close();
			} else {
				if (conn == null) {
					throw new DaoException(
							"can not close null connection, first new then close");
				} else {
					throw new DaoException(
							"transaction is running, rollbakc or commit befor close please.");
				}
			}
		} catch (SQLException ex) {
			throw new DaoException("Can not close common dao");
		}
	}
	
	
	
	
	
	public static void main(String[] arg){
//		try {
//			JdbcDao dao=new JdbcDao();
//			List l=dao.query("select * from ko_workqueue", null);
//			dao.close();
//		} catch (DaoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			String driverClassName="net.sourceforge.jtds.jdbc.Driver";
//			String url="jdbc:jtds:sqlserver://172.10.0.102:1433/GlwlDb";
			String url="jdbc:jtds:sqlserver://172.10.0.122:1433/BsCallDB_test";
			String username="sa";
//			String password="sa";
			String password="sa2014";
			Class.forName(driverClassName).newInstance();
			Connection conn=DriverManager.getConnection(url,username,password);
			JdbcDao dao=new JdbcDao(conn);
//			String sql="select CUSTOMERNUM,CONSIGNMENTTIME,CONSIGNMENTTYPE,LOADINGAREA,RECEIVICEAREA,GoodsSummary from kf_ConsignmentNote where GoodsSummary<>'' and CUSTOMERNUM<>'' order by CONSIGNMENTTIME desc";
//			String sql="select CUSTOMERNUM,CONSIGNOR,LOADING,LOADINGPARTYADDRESS,GoodsSummary,CONSIGNMENTNOTEID,CONSIGNMENTTIME from kf_ConsignmentNote where CUSTOMERNUM='4124011'";
			String sql="select * from ko_call";
//			String sql="select * from kf_GoodsDetail where Parent='24bc080546914b14b6c219e36b5cc67f'";
//			String sql="select * from kf_GoodsDetail";
//			String sql="select * from kf_ConsignmentNote where guid='24bc080546914b14b6c219e36b5cc67f'";
//			String sql="select * from test_phone";
//			String sql="select * from sendcarinfo where carguid in(select  kf_transport_capacity_info.guid from kf_transport_capacity_info,base_user where kf_transport_capacity_info.maindriverid =base_user.personnelID)";
//			String sql="select * from base_user";
			List<Map> l=dao.query(sql, null);
			System.out.println(l.size());
			System.out.println(l);
			conn.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}