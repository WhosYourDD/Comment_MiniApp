package com.igeekhome.test;

import java.io.IOException;
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
/*
* 将数据库保存的  “所有评论信息”  返回给前端
 * 按照店铺的名字查找该店铺的所有评论并展示
 */
public class ReturnComm extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应编码格式
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=UTF-8");
		
		int curPage=0;//当前页数
		int pageSize=15;//每页数据个数
		int a=0;
	
		Statement st=null;
		Statement st2=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		//请求要返回评论的店铺名字
		String shopname2=req.getParameter("shopname");
		System.out.println("查看评论的店铺名："+shopname2);
		//请求当前展示第几页数据
		String curpa=req.getParameter("curPage");
		if(curpa!=null) {
			curPage = Integer.parseInt(curpa);
			System.out.println("当前评论请求的页数："+curPage);
			a=curPage*pageSize;
		}
		//请求当前是哪个大类，要查的表名
		String commentablename=req.getParameter("commenttablename");
		System.out.println("当前四大类，查comment表"+commentablename);
		//当前要加热度的表名
		String shoptablename=req.getParameter("shoptablename");
		System.out.println("当前店铺所在list表，加热度，查表"+shoptablename);
		
		if(shopname2!=null) {
		try {
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			String sql="select *from "+commentablename+" where shopname ='"+shopname2+"' limit "+a+","+pageSize+" ";
			rs=st.executeQuery(sql);
						
			Comment co=null;
			while(rs.next()) {
				co=new Comment();				
				co.setShopname(rs.getString("shopname"));				
				co.setUsername(rs.getString("username"));	
				co.setUserphoto(rs.getString("userphoto"));
				co.setTime(rs.getString("time"));
				co.setComment(rs.getString("comment"));
				co.setId(rs.getInt("id"));
				co.setLikenum(rs.getInt("likenum"));
				System.out.println(rs.getInt("id")+rs.getString("shopname")+rs.getString("username")+rs.getString("userphoto")+rs.getString("time")+rs.getString("comment")+rs.getInt("likenum"));
				ja.put(new JSONObject(co));
			}
				
			//每查看一次该店铺评论，给该店铺热度+1
			String sql2="update "+shoptablename+" set score=score+1 where name='"+shopname2+"'";
			st.executeUpdate(sql2);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  finally {
			  JDBCUtil.close(conn, st, rs);
		  }
		resp.getWriter().write(ja.toString());
		}
	}
}
