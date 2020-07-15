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
	//ע��Ϊ����/���໥���䣬
	public static String url="jdbc:mysql://localhost:3306/my_test2?serverTimezone=GMT&characterEncoding=utf-8";
	public static String user="root";
	public static String password="123456";
	public static String driverName="com.mysql.jdbc.Driver";
	
	//1 ���������ļ�
	public static DataSource ds=null;
	static {
		
		try {
//			Properties p=new Properties();
//			//FileInputStream in = new  FileInputStream("/src/db.properties");
//			//InputStream in=JDBCUtil.class.getResourceAsStream("resource/db.properties");
//			p.load(in);
//			ds=DruidDataSourceFactory.createDataSource(p);
			//�������ֻʹ��getconn�����е� 1�������� 2�������ӵ������ݿ���Ϣ��
			//Ҳ����ʹ��Alibaba��˾�ṩ��Durid������ֱ��ͨ���ļ�p�������ݿ⣨����ѡ����ߣ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//��ȡ�������ݿ�
	public static Connection getconn() {
		try {
			//1.��������
		Class.forName(JDBCUtil.driverName);
			//2.�������ӵ������ݿ���Ϣ
		return DriverManager.getConnection(JDBCUtil.url,JDBCUtil.user,JDBCUtil.password);
			
		//return ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//�ͷ���Դ
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
