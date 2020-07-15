package com.igeekhome.test;

import java.io.IOException;
import java.net.URLDecoder;
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
import org.json.JSONObject;

import com.jdbc.utils.JDBCUtil;

/**
 * 拿到前端  “评论数据”，存到数据库comment里
 * 保存数据包括：店铺名字shopname,评论的用户名字username,评论时间time,评论内容comment
 */
public class CommTest extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应编码格式
		
		  req.setCharacterEncoding("utf-8");
		  resp.setContentType("application/json;charset=UTF-8");	
		Statement st=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
			
		String shopname=req.getParameter("shopname");
		String username=req.getParameter("username");
		String userphoto=req.getParameter("photo");
		String time=req.getParameter("time");
		String comment=req.getParameter("comment");
		
		String likeString=req.getParameter("likenum");
		String idString=req.getParameter("idnum");
		
		int likenum=0,idnum=0;
		if(likeString!=null&&idString!=null) {
			likenum= Integer.parseInt(likeString);
			idnum= Integer.parseInt(idString);
			
		}
		
		//请求当前是哪个大类，要查的表名
		String commentablename=req.getParameter("commenttablename");
		System.out.println("当前四大类，查comment表"+commentablename);
		//当前要加热度的表名
		String shoptablename=req.getParameter("shoptablename");
		System.out.println("当前店铺所在list表，查表"+shoptablename);
		
		if(shopname!=null) {
			System.out.println(shopname);
		}
		if(username!=null) {
			System.out.println(username);
		}
		if(time!=null) {
			System.out.println(time);
		}
		if(comment!=null) {
			System.out.println(comment);
		}
		
		  try { 
			conn=JDBCUtil.getconn();
		  st=conn.createStatement();
		  //插入评论
		  if(likenum!=1&&likenum!=-1) {
		  String sql="insert into "+commentablename+"(shopname,username,userphoto,time,comment) value('"+shopname+"','"+username+"','"+userphoto+"','"+time+"','"+comment+"')"; 
		  //插入一次评论给 该店铺 的热度＋2//
		  String sql2="update "+shoptablename+" set score=score+2 where name='"+shopname+"'";
		  st.executeUpdate(sql);
		  st.executeUpdate(sql2);
		  }
		//点赞一次，赞数＋1
		if(likenum==1) {
			String sql3="update "+commentablename+" set likenum=likenum+1 where id="+idnum+" ";
			st.executeUpdate(sql3);
		}
		 //取消一次赞
		if(likenum==-1) {
			 String sql3="update "+commentablename+" set likenum=likenum-1 where id="+idnum+" ";
			 st.executeUpdate(sql3);
		 }
			  
		  
		  } catch (SQLException e) { e.printStackTrace(); }
		  finally {
			  JDBCUtil.close(conn, st, rs);
			  
		  }
		
		
		
	}

}
