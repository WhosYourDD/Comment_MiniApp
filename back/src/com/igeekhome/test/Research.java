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
/*
 * 
 * �õ�ģ��ƥ������ݣ������ݿ���ң����շ���ƥ�䵽��shopname������ֵscore
 */

import com.jdbc.utils.JDBCUtil;

public class Research extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������Ӧ�����ʽ
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=UTF-8");
		
		Statement st=null;
		ResultSet rs=null;
		Connection conn=null;
		JSONArray ja=new JSONArray();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String shopname3=req.getParameter("searchtext");
		//String shopname3="��";
		/*��ӡ�������۵ĵ�������
		 * System.out.println(shopname2);
		 */
		System.out.println("�������ݣ�"+shopname3);
		if(shopname3!=null) {
		
		try {
			conn=JDBCUtil.getconn();
			st=conn.createStatement();
			
			//һ�����������ݽ��ж������
			String sql="select *from restable where name like '"+'%'+shopname3+'%'+"' UNION select * from studylist WHERE `name` like '"+'%'+shopname3+'%'+"'  UNION select * from shoplist WHERE `name` like '"+'%'+shopname3+'%'+"'  UNION select * from livelist WHERE `name` like '"+'%'+shopname3+'%'+"' ORDER BY (score) DESC";
			rs=st.executeQuery(sql);
			Resturate res=null;
			while(rs.next()) {
				res=new Resturate();				
				res.setName(rs.getString("name"));			
				res.setScore(rs.getInt("score"));
				res.setDetail(rs.getString("detail"));
				res.setPhotourl(rs.getString("photourl"));
				res.setClasses(rs.getString("classes"));
				ja.put(new JSONObject(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*��ӡ������ĵ��̵�����������Ϣ��������
		System.out.println(ja.toString());
		*/
		System.out.println(ja.toString());
		resp.getWriter().write(ja.toString());
		}
	}

}
