package com.mrgeek.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * 
* <p>Title: JDBCUtil.java<／p>
* <p>Description: JDBC工具类<／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_16:44:11
* @version 1.0
 */
public class JDBCUtil {
	
	/**
	 * 和数据库建立连接
	* <p>Title: getConnection<／p>
	* <p>Description: <／p>
	* @return Connection 连接
	* @throws ClassNotFoundException
	* @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		//1.加载驱动
		//确定链接的数据库的类型
		Class.forName("com.mysql.jdbc.Driver");
		//确定请求路径
		String url = "jdbc:mysql://localhost:3306/studentInfo?characterEncoding=UTF-8";
		//确定用户名
		String user = "root";
		//确定密码
		String password = "20020920z";
		
		//2.建立连接
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		return connection;
		
	}
	
	/**
	 * 关闭连接数据库建立的连接通道
	* <p>Title: closeConnection<／p>
	* <p>Description: <／p>
	* @param resultSet 执行sql语句返回的结果
	* @param statement Statement 对象
	* @param connection 和数据库建立的链接
	 * @throws Exception 
	 */
	public static void closeJDBC(ResultSet resultSet,Statement statement,Connection connection) throws Exception {
		
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	/**
	 * 关闭数据库连接
	* <p>Title: closeJDBC<／p>
	* <p>Description: <／p>
	* @param statement
	* @param connection
	* @throws SQLException
	 */
	public static void closeJDBC(Statement statement,Connection connection) throws SQLException {
		
		statement.close();
		connection.close();
	}

}
