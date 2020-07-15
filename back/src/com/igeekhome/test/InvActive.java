package com.igeekhome.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jdbc.utils.JDBCUtil;

/**
 * Servlet implementation class InvActive
 */
public class InvActive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		Statement st=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
		String type=req.getParameter("type");
		String aname=req.getParameter("aname");
		String place=req.getParameter("place");
		String name=req.getParameter("name");
		String time=req.getParameter("time");
		String link=req.getParameter("link");
		String detail=req.getParameter("detail");
		
		
		try {
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			String sql="insert into invactive(type,aname,place,name,time,link,detail) value('"+type+"','"+aname+"','"+place+"','"+name+"','"+time+"','"+link+"','"+detail+"')";
			st.executeUpdate(sql);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, rs);
		}
		
		
	}

}
