package com.advise;

import java.io.IOException;
/*
 * 请求用户的建议信息：mail和advice
 * 存储到数据库 advice 表中
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.jdbc.utils.JDBCUtil;

public class AdviceClass extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应编码格式
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=UTF-8");
		Statement st=null;
		String mail=req.getParameter("mail");
		String advice=req.getParameter("advice");
		
		if(mail!=null&&advice!=null) {	
		Connection conn=null;
		try {
			//连接数据库，得到返回的conn对象
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			String sql="insert into advice (mail,advicetext) value('"+mail+"','"+advice+"')";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, null);
		}
		}

}
}
