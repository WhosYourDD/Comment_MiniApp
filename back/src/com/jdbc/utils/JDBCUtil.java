package com.jdbc.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

public class JDBCUtil {
	//注释为三个/的相互批配，
	public static String url="jdbc:mysql://localhost:3306/my_test2?serverTimezone=GMT&characterEncoding=utf-8";
	public static String user="root";
	public static String password="123456";
	public static String driverName="com.mysql.jdbc.Driver";
	
	//1 加载配置文件
	public static DataSource ds=null;
	static {
		
		try {
//			Properties p=new Properties();
//			//FileInputStream in = new  FileInputStream("/src/db.properties");
//			//InputStream in=JDBCUtil.class.getResourceAsStream("resource/db.properties");
//			p.load(in);
//			ds=DruidDataSourceFactory.createDataSource(p);
			//这里可以只使用getconn方法中的 1加载驱动 2返回连接到的数据库信息；
			//也可以使用Alibaba公司提供的Durid方法，直接通过文件p加载数据库（我们选择后者）
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取连接数据库
	public static Connection getconn() {
		try {
			//1.加载驱动
		Class.forName(JDBCUtil.driverName);
			//2.返回连接到的数据库信息
		return DriverManager.getConnection(JDBCUtil.url,JDBCUtil.user,JDBCUtil.password);
			
		//return ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//释放资源
	public static void close(Connection conn,Statement st,ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
