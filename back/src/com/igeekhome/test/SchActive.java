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
 * Servlet implementation class SchActive
 */
public class SchActive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置响应编码格式
				req.setCharacterEncoding("utf-8");
				resp.setContentType("text/html;charset=utf-8");
				
				Statement st=null;
				ResultSet rs=null;
				Connection conn=null;
				JSONArray ja=new JSONArray();
				
				String tablename=req.getParameter("tablename");
				System.out.println("当前查表："+tablename);
				
			
				
				try {
					conn=JDBCUtil.getconn();
					st=conn.createStatement();
						
					String sql="select *from "+tablename+" where DATE_SUB(CURDATE(), INTERVAL 3 DAY) <= date(time)";
					rs=st.executeQuery(sql);
					
				
					ActiveClass ac=null;
					System.out.println(ac);
					while(rs.next()) {
						ac=new ActiveClass();
						ac.setType(rs.getString("type"));
						ac.setAname(rs.getString("aname"));
						ac.setPlace(rs.getString("place"));
						ac.setName(rs.getString("name"));
						ac.setTime(rs.getString("time"));
						ac.setLink(rs.getString("link"));
						ac.setDetail(rs.getString("detail"));
						System.out.println("type:"+rs.getString("type")+"  aname:"+rs.getString("aname")+"  place:"+rs.getString("place")+"  name:"+rs.getString("name")+"  time:"+rs.getString("time")+"  link:"+rs.getString("link")+"  detail:"+rs.getString("detail"));
						ja.put(new JSONObject(ac));
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					JDBCUtil.close(conn, st, rs);
				}
				
				resp.getWriter().write(ja.toString());
			}
			
	}


